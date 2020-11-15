package com.sugon.analysis.platform.service;

import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.platform.dto.ModelUploadDTO;
import com.sugon.analysis.platform.dto.ReportModelHistoryQuery;
import com.sugon.analysis.platform.entity.ReportModelHistoryEntity;
import com.sugon.analysis.platform.repository.ReportModelHistoryRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @description: 模板管理服务层
 * @author: liuwei
 * @create: 2020-05-27 17:00
 **/
@Service
public class ReportModelManagerService {
	@Value("${spring.file.template}")
	private String modelPath;

	@Value("${spring.profiles.active}")
	private String active;

	@Value("${local.url}")
	private String webPath;

	@Resource
	private ReportModelHistoryRepository reportModelHistoryRepository;

	public ResultModel uploadFile(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		if(!originalFilename.endsWith(".docx")){
			return  ResultModel.failure("文件格式错误，docx");
		}
		String filePath = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
		try {
			IOUtils.copy(file.getInputStream(),new File(modelPath + filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return ResultModel.failure("文件获取失败"+e.getMessage());
		}
		return ResultModel.success(filePath);
	}

	public ResultModel uploadModel(ModelUploadDTO modelUploadDTO) {


		long currentTimeMillis = System.currentTimeMillis();
		ReportModelHistoryEntity reportModelHistoryEntity = new ReportModelHistoryEntity();
		reportModelHistoryEntity.setRemark(modelUploadDTO.getRemark());
		reportModelHistoryEntity.setModelType(modelUploadDTO.getType());
		reportModelHistoryEntity.setModelLevel(modelUploadDTO.getLevel());
		reportModelHistoryEntity.setDelstatus("0");

		reportModelHistoryEntity.setFilePath(modelUploadDTO.getFile());
		reportModelHistoryEntity.setActive(active);


		reportModelHistoryEntity.setCreateTime(new java.sql.Timestamp(currentTimeMillis));
		reportModelHistoryRepository.save(reportModelHistoryEntity);
		return ResultModel.success();
	}

	public ResultModel deleteModel(List<String> ids) {
		reportModelHistoryRepository.delete(reportModelHistoryRepository.findAll(ids));
		return ResultModel.success();
	}

	public ResultModel detail(String id) {
		return ResultModel.success(reportModelHistoryRepository.findOne(id));
	}

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public ResultModel queryList(ReportModelHistoryQuery query) {
		Pageable pageable = new PageRequest(query.getPageNo()-1,query.getSize());
		Specification<ReportModelHistoryEntity> speci = new Specification<ReportModelHistoryEntity>() {
			@Override
			public Predicate toPredicate(Root<ReportModelHistoryEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(StringUtils.isNotBlank(query.getType())){
					predicates.add(criteriaBuilder.equal(root.get("modelType"),query.getType()));
				}
				if(StringUtils.isNotBlank(query.getLevel())){
					predicates.add(criteriaBuilder.equal(root.get("modelLevel"),query.getLevel()));
				}
				if(query.getDateRange()!=null && query.getDateRange().length==2){
					Timestamp endDate=null;
					Timestamp startDate=null;
					try {
						startDate = new Timestamp(dateFormat.parse(query.getDateRange()[0]).getTime());
						endDate = new Timestamp(dateFormat.parse(query.getDateRange()[1]).getTime());
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),startDate));
						predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"),endDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				predicates.add(criteriaBuilder.equal(root.get("active"),active));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		Page<ReportModelHistoryEntity> page = reportModelHistoryRepository.findAll(speci, pageable);
		page.getContent().forEach(r->{
			r.setFilePath(webPath+r.getFilePath());
			r.setModelType(modelTypes.get(Integer.parseInt(r.getModelType())-1));
			r.setModelLevel(modelLevels.get(Integer.parseInt(r.getModelLevel())-1));
		});
		return ResultModel.success(page);
	}
	List<String> modelTypes= Arrays.asList("民警画像报告模板","综合评估报告模板");
	List<String> modelLevels= Arrays.asList("市局","分局/部门","科所队");

	public void downloadFile(String filePath, HttpServletResponse response) {
		File file = new File(modelPath + filePath);
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			IOUtils.copy(new FileInputStream(file),outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
