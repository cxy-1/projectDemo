package com.sugon.analysis.platform.service;

import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.object.oracle.TResumeInfoDo;
import com.sugon.analysis.domain.data.query.PersonQo;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.PersonalAnalysisMapper;
import com.sugon.analysis.platform.dto.BeTheBestDTO;
import com.sugon.analysis.platform.dto.BeTheBestPersonSearchQuery;
import com.sugon.analysis.platform.entity.BeTheBestEntity;
import com.sugon.analysis.platform.entity.BeTheBestInfoEntity;
import com.sugon.analysis.platform.repository.BeTheBestInfoRepository;
import com.sugon.analysis.platform.repository.BeTheBestRepository;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.repository.oracle.TResumeInfoDao;
import com.sugon.analysis.util.ChildUnit;
import com.sugon.analysis.util.CommonUnit;
import com.sugon.analysis.util.RoleOfCompany;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.sugon.analysis.util.ChildUnit.NJSGAJ_ID;

/**
 * @description: 勇争先数据维护服务层
 * @author: liuwei
 * @create: 2020-06-08 11:35
 **/
@Service
public class BeTheBestService {
    @Resource
    private TPersonDao tPersonDao;

    @Resource
    private PersonalAnalysisMapper personalAnalysisMapper;

    @Resource
    private BeTheBestRepository beTheBestRepository;
    @Resource
    private BeTheBestInfoRepository beTheBestInfoRepository;

    @Resource
    private ChildUnit childUnit;

    @Resource
    private TResumeInfoDao tResumeInfoDao;

    @Resource(name = "tertiaryJdbcTemplate")
    private JdbcTemplate tertiaryJdbcTemplate;

    @Resource(name = "primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    public ResultModel searchPerson(BeTheBestPersonSearchQuery query) {
        Map<String,Object> result= new HashMap<>();
        List<Map<String,Object>>  rows = new ArrayList<>();
        Set<String> idNos = new HashSet<>(tertiaryJdbcTemplate.queryForList("SELECT DISTINCT ID_NO FROM ZHZG.BE_THE_BEST_INFO WHERE DELSTATUS=0 AND ACTIVE='"+CommonUnit.active+"'", String.class));

        List<PersonQo> personQos = personalAnalysisMapper.pageQueryPeople(query.getPoliceNo(), query.getPersonName(), null, query.getDeptIds(),query.getStart(),query.getEnd());
        for (PersonQo personQo : personQos) {
            Map<String, Object> row = new HashMap<>();
            row.put("personName",personQo.getPersonName());
            row.put("policeNo",personQo.getPoliceNo());
            row.put("deptName",personQo.getDeptName());
            row.put("idNo",personQo.getIdNo());
            if(idNos.contains(personQo.getIdNo())){
                row.put("editable",true);
            }else{
                row.put("editable",false);
            }
            rows.add(row);
        }

        Integer total = personalAnalysisMapper.countPeople(query.getPoliceNo(), query.getPersonName(), null, query.getDeptIds());
        result.put("total",total);
        result.put("rows",rows);
        return ResultModel.success(result);
    }

    public ResultModel searchEditablePerson(BeTheBestPersonSearchQuery query) {
        String deptId = RoleOfCompany.getRoleCompanyId(query.getIdNumber());
        List<String> subPersonIdNos=null;
        if(!NJSGAJ_ID.equals(deptId)){
            subPersonIdNos = childUnit.getSubPersonIdNos(deptId);
        }
        Map<String,Object> result= new HashMap<>();

        String sql = "SELECT * FROM ZHZG.BE_THE_BEST_INFO WHERE DELSTATUS=0 AND ACTIVE='"+CommonUnit.active+"' " ;
        if(subPersonIdNos !=null && subPersonIdNos.size()>0){
            sql += CommonUnit.convertValues2Sql("ID_NO",subPersonIdNos);
        }
        if(StringUtils.isNotBlank(query.getPoliceNo())){
            sql += " AND POLICE_NO='"+query.getPoliceNo()+"' ";
        }
        if (StringUtils.isNotBlank(query.getPersonName())) {
            sql += " AND PERSON_NAME LIKE '%"+query.getPersonName()+"%' ";
        }
        result.put("total",tertiaryJdbcTemplate.queryForMap("SELECT COUNT(*) COUNT FROM ("+sql+") ").get("COUNT"));
        result.put("rows",tertiaryJdbcTemplate.queryForList("SELECT * FROM  (SELECT T.*,ROWNUM RN FROM ("+sql+") T WHERE ROWNUM<=" + query.getEnd() + ")  WHERE RN >=" + query.getStart()));

        return ResultModel.success(result);
    }


    public ResultModel update(BeTheBestDTO dto) {
        Boolean atLeastOne=false;
        for (BeTheBestEntity entity : dto.getData()) {
            if(!"1".equals(entity.getDelstatus())){
                atLeastOne = true;
                break;
            }
        }
        if(!atLeastOne){
            return ResultModel.failure("至少保留一项");
        }


        List<Map<String, Object>> maps = primaryJdbcTemplate.queryForList("SELECT TIME_TO_WORK_PSB,PERSON_NAME,ORG_SIMPLE_NAME,OFFICE_SIMPLE_NAME,POLICE_NO,T1.POST,T1.SEX FROM NJGAZNL.T_PERSON T1 LEFT JOIN NJGAZNL.SYS_USER T2 ON T1.IDNO=T2.IDENTITYNUM WHERE IDNO='" + dto.getIdNo() + "'");
        if(maps.size()==0){
            return ResultModel.failure("用户不存在");
        }

        Map<String, Object> objectMap = maps.get(0);
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());

        BeTheBestInfoEntity beTheBestInfoEntity = beTheBestInfoRepository.findByIdNoAndDelstatusAndActive(dto.getIdNo(),"0",CommonUnit.active);
        if(beTheBestInfoEntity==null){
            beTheBestInfoEntity = new BeTheBestInfoEntity();
            beTheBestInfoEntity.setCreateTime(currentTimeStamp);
        }
        beTheBestInfoEntity.setIdNo(dto.getIdNo());
        beTheBestInfoEntity.setDeptName(String.valueOf(objectMap.get("ORG_SIMPLE_NAME"))+(objectMap.get("OFFICE_SIMPLE_NAME")==null?"":objectMap.get("OFFICE_SIMPLE_NAME")));
        beTheBestInfoEntity.setPoliceNo(String.valueOf(objectMap.get("POLICE_NO")));
        beTheBestInfoEntity.setPersonName(String.valueOf(objectMap.get("PERSON_NAME")));
        beTheBestInfoEntity.setPost(String.valueOf(objectMap.get("POST")));
        beTheBestInfoEntity.setSex("1".equals(objectMap.get("SEX"))?"男":"女");
        beTheBestInfoEntity.setUpdateTime(currentTimeStamp);
        beTheBestInfoEntity.setDelstatus("0");
        beTheBestInfoEntity.setActive(CommonUnit.active);
        BeTheBestInfoEntity save = beTheBestInfoRepository.save(beTheBestInfoEntity);

        //处理被删除的记录
        dto.getData().stream().filter(a-> "1".equals(a.getDelstatus())).collect(Collectors.toList()).forEach(b->{
            b.setActive(CommonUnit.active);
            b.setInfoId(save.getId());
            beTheBestRepository.save(b);
        });
        //处理新增或修改的记录
        dto.getData().stream().filter(a->!"1".equals(a.getDelstatus())).collect(Collectors.toList()).forEach(b->{
            BeTheBestEntity byInfoIdAndTime = beTheBestRepository.findByInfoIdAndTimeAndActive(save.getId(), b.getTime(),CommonUnit.active);
            //修改
            if(byInfoIdAndTime!=null){
                b.setId(byInfoIdAndTime.getId());
                b.setDeptName(byInfoIdAndTime.getDeptName());
            }else{
                String startTime = String.valueOf(objectMap.get("TIME_TO_WORK_PSB")).substring(0, 10).replaceAll("-", "");
                List<TResumeInfoDo> tResumeInfoDoList = tResumeInfoDao.
                        findAllByIdNoAndDelStatus(dto.getIdNo(), 0, startTime);
                Collections.reverse(tResumeInfoDoList);
                for (TResumeInfoDo tResumeInfoDo : tResumeInfoDoList) {
                    if(new SimpleDateFormat("YYYYMM").format(tResumeInfoDo.getStartingDate()).compareTo(b.getTime())<=0){
                        b.setDeptName(tResumeInfoDo.getOrgName());
                        break;
                    }
                }
            }
            b.setDelstatus("0");
            b.setInfoId(save.getId());
            b.setActive(CommonUnit.active);
            beTheBestRepository.save(b);
        });

        return ResultModel.success();
    }

    public ResultModel deleteInfo(List<String> idNos) {
        List<BeTheBestInfoEntity> beTheBestInfoEntities = beTheBestInfoRepository.findByIdNoInAndDelstatusAndActive(idNos,"0",CommonUnit.active);
        for (BeTheBestInfoEntity entity : beTheBestInfoEntities) {
            entity.setDelstatus("1");
            entity.setActive(CommonUnit.active);
        }
        beTheBestInfoRepository.save(beTheBestInfoEntities);
        return ResultModel.success();
    }


    public ResultModel dateRange(String idNumber) {
        String[] dateRange = new String[2];

        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(idNumber, "0");
        if (tPersonDo.getTimeToWorkPsb() != null) {
            String startTime = tPersonDo.getTimeToWorkPsb().substring(0, 10).replaceAll("-", "");
            List<TResumeInfoDo> tResumeInfoDoList = tResumeInfoDao.
                    findAllByIdNoAndDelStatus(idNumber, 0, startTime);
            dateRange[0] = new SimpleDateFormat("YYYYMM").format(tResumeInfoDoList.get(0).getStartingDate());
        }

        LocalDate now = LocalDate.now();
        dateRange[1]  = now.toString().substring(0,7).replace("-","");
        return ResultModel.success(dateRange);
    }
}
