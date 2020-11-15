package com.sugon.analysis.service;

import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.sugon.analysis.domain.data.object.oracle.SysDeptDo;
import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.query.KPIQuery;
import com.sugon.analysis.domain.data.query.PersonQo;
import com.sugon.analysis.domain.data.query.TPersonCountAndAge;
import com.sugon.analysis.domain.data.query.TeamDetail;
import com.sugon.analysis.domain.param.ReportVo;
import com.sugon.analysis.domain.view.EchartsVo;
import com.sugon.analysis.domain.view.report.TeamReport;
import com.sugon.analysis.domain.view.teambuild.AgeDetailVo;
import com.sugon.analysis.domain.view.teambuild.BreakRuleCaseVo;
import com.sugon.analysis.domain.view.teambuild.RulesAndCount;
import com.sugon.analysis.domain.view.teambuild.TeamInfoVo;
import com.sugon.analysis.entity.NationMapPojo;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.PersonalAnalysisMapper;
import com.sugon.analysis.mappper.oracle.TeamBuildMapper;
import com.sugon.analysis.repository.oracle.SysDeptDao;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.util.ChildUnit;
import com.sugon.analysis.util.CommonUnit;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sugon.analysis.util.CommonUnit.*;

/**
 * @program: analysis->TeamReportService
 * @description:
 * @author: gaowh
 * @create: 2019-12-27 14:44
 **/
@Service
public class TeamReportService {
	@Resource
	private TeamBuildService teamBuildService;
	@Resource
	private TeamBuildMapper teamBuildMapper;
	@Resource
	private PersonalAnalysisService personalAnalysisService;

	@Resource
	private PersonStereoService personStereoService;

	@Resource
	private TPersonDao tPersonDao;

	@Resource
	private SysDeptDao sysDeptDao;

	private DecimalFormat decimalFormat = new DecimalFormat("0.00%");

	@Resource
	private PDFService pdfService;

	@Resource(name = "primaryJdbcTemplate")
	private JdbcTemplate primaryJdbcTemplate;

	@Resource(name = "secondaryJdbcTemplate")
	private JdbcTemplate secondaryJdbcTemplate;


	@Value("${spring.profiles.active}")
	private String active;

	@Resource
	private TeamComparisonService teamComparisonService;

	@Resource
	private PersonalAnalysisMapper personalAnalysisMapper;

	/**�Ͼ��й�����id*/
	private static String NJSGAJ_ID = "2014110416460086100000002942";

	//�������ݿ⼨Ч����
	public final static String TABLE_KPI = "SELECT " +
			" T1.PERFORM_YEAR YEAR,T7.SIMPLECHINANAME, " +
			" T3.INDICATOR_NAME, " +
			" T6.GROUP_NAME||T5.GROUP_NAME GROUPNAME, " +
			" SUM(T.SCORE) SCORE,T8.QUARTER_NAME QUARTER,T8.QUARTER_BEGIN,NVL(T6.GROUP_SORT,T5.GROUP_SORT) GROUP_SORT1,T5.GROUP_SORT GROUP_SORT2 " +
			" FROM " +
			" ( " +
			"  SELECT " +
			"   PERFORM_ID, " +
			"   INDICATOR_DETAIL_ID, " +
			"   DEPT_ID, " +
			"   TO_CHAR(REPORT_TIME,'YYYYMMDD') TIME, " +
			"   SUM ( " +
			"    EXTRA_POINTS - NVL (DEDUCT_POINTS, 0) " +
			"   ) SCORE " +
			"  FROM " +
			"   NJGAZNL.T_PERFORM_DETAIL " +
			"  WHERE " +
			"   CHECK_STATUS = 3 " +
			"  AND DELSTATUS = 0 " +
			"  GROUP BY " +
			"   PERFORM_ID, " +
			"   INDICATOR_DETAIL_ID, " +
			"   DEPT_ID,TO_CHAR(REPORT_TIME,'YYYYMMDD') " +
			" ) T " +
			"LEFT JOIN NJGAZNL.T_PERFORM T1 ON T .PERFORM_ID = T1. ID " +
			"LEFT JOIN NJGAZNL.SYS_DEPT T7 ON T.DEPT_ID = T7.ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_INDICATOR_DETAIL T2 ON T .INDICATOR_DETAIL_ID = T2. ID " +
			"AND T2.PERFORM_ID = T1. ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_INDICATOR T3 ON T2.INDICATOR_ID = T3. ID " +
			"AND T3.PERFORM_ID = T1. ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_GROUP_DETAIL T4 ON T .DEPT_ID = T4.DEPT_ID " +
			"AND T4.PERFORM_ID = T1. ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_GROUP T5 ON T4. GROUP_ID = T5. ID " +
			"AND T5.PERFORM_ID = T1. ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_GROUP T6 ON T5.P_GROUP_ID = T6. ID " +
			"LEFT JOIN NJGAZNL.T_PERFORM_QUARTER T8 ON T.TIME BETWEEN TO_CHAR(T8.QUARTER_BEGIN,'YYYYMMDD') AND TO_CHAR(T8.QUARTER_END,'YYYYMMDD') " +
			"WHERE T8.DELSTATUS=0 " +
			"group by T1.PERFORM_YEAR,T7.SIMPLECHINANAME, " +
			" T3.INDICATOR_NAME, " +
			" T6.GROUP_NAME||T5.GROUP_NAME, " +
			" T8.QUARTER_NAME,T8.QUARTER_BEGIN,T5.GROUP_SORT,T6.GROUP_SORT";
	public final static String TABLE_KPI_2019 = "SELECT " +
			" T1.PERFORM_YEAR YEAR,T7.SIMPLECHINANAME, " +
			" T3.INDICATOR_NAME, " +
			" T6.GROUP_NAME||T5.GROUP_NAME GROUPNAME, " +
			" SUM(T.SCORE) SCORE,T8.QUARTER_NAME QUARTER,T8.QUARTER_BEGIN,NVL(T6.GROUP_SORT,T5.GROUP_SORT) GROUP_SORT1,T5.GROUP_SORT GROUP_SORT2 " +
			" FROM " +
			" ( " +
			"  SELECT " +
			"   PERFORM_ID, " +
			"   INDICATOR_DETAIL_ID, " +
			"   DEPT_ID, " +
			"   TO_CHAR(REPORT_TIME,'YYYYMMDD') TIME, " +
			"   SUM ( " +
			"    EXTRA_POINTS - NVL (DEDUCT_POINTS, 0) " +
			"   ) SCORE " +
			"  FROM " +
			"   ZHZG.T_PERFORM_DETAIL " +
			"  WHERE " +
			"   CHECK_STATUS = 3 " +
			"  AND DELSTATUS = 0 " +
			"  GROUP BY " +
			"   PERFORM_ID, " +
			"   INDICATOR_DETAIL_ID, " +
			"   DEPT_ID,TO_CHAR(REPORT_TIME,'YYYYMMDD') " +
			" ) T " +
			"LEFT JOIN ZHZG.T_PERFORM T1 ON T .PERFORM_ID = T1. ID " +
			"LEFT JOIN ZHZG.SYS_DEPT T7 ON T.DEPT_ID = T7.ID " +
			"LEFT JOIN ZHZG.T_PERFORM_INDICATOR_DETAIL T2 ON T .INDICATOR_DETAIL_ID = T2. ID " +
			"AND T2.PERFORM_ID = T1. ID " +
			"LEFT JOIN ZHZG.T_PERFORM_INDICATOR T3 ON T2.INDICATOR_ID = T3. ID " +
			"AND T3.PERFORM_ID = T1. ID " +
			"LEFT JOIN ZHZG.T_PERFORM_GROUP_DETAIL T4 ON T .DEPT_ID = T4.DEPT_ID " +
			"AND T4.PERFORM_ID = T1. ID " +
			"LEFT JOIN ZHZG.T_PERFORM_GROUP T5 ON T4. GROUP_ID = T5. ID " +
			"AND T5.PERFORM_ID = T1. ID " +
			"LEFT JOIN ZHZG.T_PERFORM_GROUP T6 ON T5.P_GROUP_ID = T6. ID " +
			"LEFT JOIN ZHZG.T_PERFORM_QUARTER T8 ON T.TIME BETWEEN TO_CHAR(T8.QUARTER_BEGIN,'YYYYMMDD') AND TO_CHAR(T8.QUARTER_END,'YYYYMMDD') " +
			"WHERE T8.DELSTATUS=0 " +
			"group by T1.PERFORM_YEAR,T7.SIMPLECHINANAME, " +
			" T3.INDICATOR_NAME, " +
			" T6.GROUP_NAME||T5.GROUP_NAME, " +
			" T8.QUARTER_NAME,T8.QUARTER_BEGIN,T5.GROUP_SORT,T6.GROUP_SORT";

	/**
	 * ������������
	 */
	@Async
	public Future<Boolean> getPersonCount(ReportVo reportVo, TeamReport teamReport) {
		System.out.println("getPersonCount");
		ChildUnit childUnit = new ChildUnit();
		//����unitId��ȡ�����Ӧ�Ӳ����е������֤���루���У�
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(reportVo.getUnitId()));
		Integer count = idNoList.size();
		//��ѯ��Ů
		Integer femaleCount = idNoList.size()>0?teamBuildMapper.findFemaleCount(idNoList):0;
		int maleCount = count - femaleCount;
		double malePercent, femalePercent;
		if (maleCount != 0 && femaleCount != 0) {
//            Integer gcd = getgcd(maleCount, femaleCount);
			malePercent = (double) maleCount / (double) femaleCount;
			femalePercent = 1;
			String percent = ",��Ů����" + new DecimalFormat("#.##").format(malePercent) + "��" + new DecimalFormat("#.##").format(femalePercent) ;
			teamReport.setProportion(percent);
		}
		if(reportVo.getUnitId().equals(NJSGAJ_ID)){
			teamReport.setDeptName1("�Ͼ��о�");
			teamReport.setDeptName2("ȫ�й�������");
		}else{
			String unitName = sysDeptDao.findByIdAndDelStatus(reportVo.getUnitId(), 0).getUnitName();
			teamReport.setDeptName1(unitName);
			teamReport.setDeptName2(unitName);
		}
		teamReport.setPersonCount(count.toString());
		teamReport.setManCount(String.valueOf(maleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*maleCount/count));
		teamReport.setWomanCount(String.valueOf(femaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*femaleCount/count));
		teamReport.setManPercentage(decimalFormat.format((double)maleCount/(double)count));
		teamReport.setWomanPercentage(decimalFormat.format((double)femaleCount/(double)count));

		BufferedImage path = pdfService.base64toMultipart(reportVo.getColumnSex(), reportVo.getUnitId());
		teamReport.setColumnSex(new PictureRenderData(576, 211,".png", path));
		return null;
	}

	/**
	 * ְ��
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getDutyRank(ReportVo reportVo, TeamReport teamReport) {
		System.out.println("getDutyRank");
		ChildUnit childUnit = new ChildUnit();
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(reportVo.getUnitId()));
		//��ȡ�����˶�Ӧ��TPersonDoʵ��
		List<TPersonDo> tPersonDoList = personalAnalysisService.getPersonByIdNos(idNoList);

		if (tPersonDoList.size() > 0) {
			Map<String, Long> map = personalAnalysisService.selectDutyRankByType(tPersonDoList, "������");
			long sum = map.values().stream().mapToLong(l -> l).sum();
			teamReport.setAffairsPolice(String.valueOf(sum));
			double per = (double) sum / (double) tPersonDoList.size();
			teamReport.setAffairsPercentage(decimalFormat.format(per));
			Map<String, Long> map1 = personalAnalysisService.selectDutyRankByType(tPersonDoList, "ִ������");
			long sum1 = map1.values().stream().mapToLong(l -> l).sum();
			teamReport.setEnforcePolice(String.valueOf(sum1));
			double per1 = (double) sum1 / (double) tPersonDoList.size();
			teamReport.setEnforcePercentage(decimalFormat.format(per1));
			Map<String, Long> map2 = personalAnalysisService.selectDutyRankByType(tPersonDoList, "�ۺϹ���");
			long sum2 = map2.values().stream().mapToLong(l -> l).sum();
			teamReport.setManagementPolice(String.valueOf(sum2));
			double per2 = (double) sum2 / (double) tPersonDoList.size();
			teamReport.setManagementPercentage(decimalFormat.format(per2));
		}
		return null;
	}


	/**
	 * ְ��
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getDuty(ReportVo reportVo, TeamReport teamReport) {
		ChildUnit childUnit = new ChildUnit();
		List<TPersonDo> subPerson = childUnit.getSubPerson(reportVo.getUnitId());
		//�������֤����ͳ��ְ��
		Map<String, Integer> dutyMap = personalAnalysisService.getPosSubCount(subPerson);
		StringBuilder dutyContentBuilder = new StringBuilder();

		for (String duty : duties) {
			if(dutyMap.get(duty)!=null){
				dutyContentBuilder.append(duty+dutyMap.get(duty)+"�ˣ�");
			}
		}
		if(dutyContentBuilder.length()>0){
			dutyContentBuilder.delete(dutyContentBuilder.length()-1,dutyContentBuilder.length());
			dutyContentBuilder.append("��");
		}
		teamReport.setDutyContent(dutyContentBuilder.toString());


		Map<String,String> nonLeaderDutyMap= new HashMap<>();
		for (Pair<String, String> noLeaderDuty : nonLeaderDuties) {
			String[] split = noLeaderDuty.getRight().split(",");
			for (String code : split) {
				nonLeaderDutyMap.put(code,noLeaderDuty.getLeft());
			}
		}
		Set<String> codes = nonLeaderDutyMap.keySet();
		Map<String, Long> collect = subPerson.parallelStream().filter(p -> codes.contains(p.getPoliceJobGrade())).map(a -> {
			a.setPoliceJobGrade(nonLeaderDutyMap.get(a.getPoliceJobGrade()));
			return a;
		}).collect(Collectors.groupingBy(a -> a.getPoliceJobGrade(), Collectors.counting()));
		StringBuilder nonLeaderDutyContentBuilder = new StringBuilder();
		for (Pair<String, String> duty : nonLeaderDuties) {
			if (collect.get(duty.getLeft())!=null){
				nonLeaderDutyContentBuilder.append(duty.getLeft()+collect.get(duty.getLeft())+"�ˣ�");
			}
		}
		if(nonLeaderDutyContentBuilder.length()>0){
			nonLeaderDutyContentBuilder.insert(0,"���쵼ְ���У�");
			nonLeaderDutyContentBuilder.delete(nonLeaderDutyContentBuilder.length()-1,nonLeaderDutyContentBuilder.length());
			nonLeaderDutyContentBuilder.append("��");
		}
		teamReport.setNonLeaderDutyContent(nonLeaderDutyContentBuilder.toString());

		return null;
	}
	/**
	 * �쵼���� ���¼�ҵ��
	 *
	 * @param reportVo
	 * @param teamReport
	 * @param richContent �Ƿ񷵻�ͼƬ�����
	 * @return
	 */
	@Async
	public Future<Boolean> getLeaderData(ReportVo reportVo, TeamReport teamReport,Boolean richContent) {
		ExecutorService es = Executors.newFixedThreadPool(10);


		ChildUnit childUnit = new ChildUnit();
		//��λ�㼶 1-�о֣�2-�־֡����� 3-�ɳ������־ֲ��š���������
		String unitId = reportVo.getUnitId();
		final SysDeptDo sysDept = sysDeptDao.findByIdAndDelStatus(unitId, 0);
		String deptLevel = sysDept.getDeptLevel();
		if(zzbSubList.contains(unitId)){
			deptLevel = "3";
		}
		//����unitId��ȡ�����Ӧ�Ӳ����е������֤���루���У�
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(unitId));

		//��ȡ�о������� �־ֺͲ�����ص���Ϣ��ͨ����ԱȨ��idNumber��
		Map<String, Object> teamInfo ;
		List<String> fjpcsIds = null;
		List<String> bmIds = null;

		/**
		 * �쵼�������֤����
		 */
		//deptLevel=1ʱ��superIdNosΪ�о��쵼���ӣ�deptLevel=2ʱ��superIdNosΪ���Ż�־��쵼����
		final List<String> superIdNos = new ArrayList<>();
		final List<String> subIdNos = new ArrayList<>();
		final List<String> fjpcsIdNos = new ArrayList<>();
		final List<String> bmIdIdNos = new ArrayList<>();
		final List<String> overAllIdList = new ArrayList<>();
		String role=null;

		//�Ƿ����о�
		if("1".equals(deptLevel)){
			teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);
			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
			fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));

			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));

			superIdNos.addAll(teamBuildMapper.findOneLeaderIdNum().stream().distinct().collect(Collectors.toList()));

			List<String> temp = teamBuildMapper.findTwoLeaderIdNum(fjpcsIdNos);
			fjpcsIdNos.clear();
			fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));

			temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
			bmIdIdNos.clear();
			bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));

			temp.clear();
			temp.addAll(superIdNos);
			temp.addAll(fjpcsIdNos);
			temp.addAll(bmIdIdNos);
			overAllIdList.addAll(temp);

			teamReport.setOverAllName("�оֺͲ��š��־�");
			teamReport.setSuperName("�о�");
			teamReport.setSubName("���š��־�");

			role = "�о�";

			teamReport.setDeptAlias("ȫ��");
		}else if("2".equals(deptLevel)){
			teamInfo = (Map<String, Object>) teamBuildService.getChildUnit(unitId).getData();

			List<String> roleList = (List<String>) teamInfo.get("role");
			if(roleList.contains("pcs")){
				role = "�־�";
			}else{
				role = "����";
			}

			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>)teamInfo.get("pcs");
			if(teamInfos.size()>0){
				fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
				fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));
			}

			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm2");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));

			List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
			superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));
			List<String> temp=new ArrayList<>();
			if(fjpcsIdNos.size()>0){
				temp = teamBuildMapper.findThreeLeaderIdNum(fjpcsIdNos);
				fjpcsIdNos.clear();
				fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
			}
			if(bmIdIdNos.size()>0){
				temp = teamBuildMapper.findThreeLeaderIdNum(bmIdIdNos);
				bmIdIdNos.clear();
				bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
			}
			if(ZZB_ID.equals(unitId)){
				superIdNos.clear();
				superIdNos.addAll(personStereoService.getDeptHead(unitId).stream().map(a->a.getIdNo()).collect(Collectors.toList()));

				bmIdIdNos.clear();
				bmIdIdNos.addAll(childUnit.getSubPersonIdNos(zzbSubList));
				temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
				bmIdIdNos.clear();
				bmIdIdNos.addAll(temp);
			}


			temp.clear();
			temp.addAll(superIdNos);
			temp.addAll(fjpcsIdNos);
			temp.addAll(bmIdIdNos);
			overAllIdList.addAll(temp);

			if(role.equals("�־�")){
				teamReport.setOverAllName("�־ֺ�������");
				teamReport.setSuperName("�־�");
				teamReport.setSubName("������");
				teamReport.setDeptAlias("�־�");
			}else{
				teamReport.setOverAllName("���ź�������");
				teamReport.setSuperName("����");
				teamReport.setSubName("������");
				teamReport.setDeptAlias("����");
			}

		}else if("3".equals(deptLevel)){
			teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);
			String roleName = ((List<String>) teamInfo.get("role")).get(0);
			if("bm2".equals(roleName)){
				role = "������";
			}else if("pcs".equals(roleName)){
				role = "�ɳ���";
			}
			teamReport.setDeptAlias(role);

			List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
			if(zzbSubList.contains(unitId)){
				role = "����";
				superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));
			}else{
				superIdNos.addAll(teamBuildMapper.findThreeLeaderIdNum(collect));
			}

			overAllIdList.addAll(superIdNos.stream().distinct().collect(Collectors.toList()));

			teamReport.setOverAllName(role);
			teamReport.setSuperName(role);
		}
		subIdNos.addAll(fjpcsIdNos);
		subIdNos.addAll(bmIdIdNos);



		//�쵼����������
		teamReport.setOverAllLeaderCount(String.valueOf(overAllIdList.size()));
		teamReport.setSuperLeaderCount(String.valueOf(superIdNos.size()));
		teamReport.setSubLeaderCount(String.valueOf(subIdNos.size()));
		//�쵼����ռ����������
		teamReport.setOverAllLeaderCountPercent(decimalFormat.format((double) overAllIdList.size() / (double) idNoList.size()));

		//ְλ����
		es.execute(new Runnable() {
			@Override
			public void run() {

				Map<String, Integer> dutyMap = personalAnalysisService.getPosSubCount(personalAnalysisService.getPersonByIdNos(superIdNos));
				Iterator<Map.Entry<String, Integer>> iterator = dutyMap.entrySet().iterator();
				while (iterator.hasNext()){
					Map.Entry<String, Integer> next = iterator.next();
					if(!duties.contains(next.getKey())){
						iterator.remove();
					}
				}


				//==============����ģ���쵼���ӷ��� ��ͷ�Ƕλ�============================
				//�����쵼����
				StringBuilder superLeaderDutyContentBuilder = new StringBuilder();
				for (String duty : duties) {
					superLeaderDutyContentBuilder.append(String.valueOf(dutyMap.get(duty) == null ? "" : duty+"�쵼"+dutyMap.get(duty)+"�ˣ�"));
				}
				superLeaderDutyContentBuilder.delete(superLeaderDutyContentBuilder.length()-1,superLeaderDutyContentBuilder.length());
				teamReport.setSuperLeaderDutyContent(superLeaderDutyContentBuilder.toString());


				if(subIdNos.size()>0){
					Map<String, Integer> nxsDutyMap = personalAnalysisService.getPosSubCount(personalAnalysisService.getPersonByIdNos(subIdNos));
					iterator = nxsDutyMap.entrySet().iterator();
					while (iterator.hasNext()){
						Map.Entry<String, Integer> next = iterator.next();
						if(!duties.contains(next.getKey())){
							iterator.remove();
						}
					}
					StringBuilder subLeaderDutyContentBuilder = new StringBuilder();
					for (String duty : duties) {
						subLeaderDutyContentBuilder.append(String.valueOf(nxsDutyMap.get(duty) == null ? "" : duty+"�쵼"+nxsDutyMap.get(duty)+"�ˣ�"));
					}
					subLeaderDutyContentBuilder.delete(subLeaderDutyContentBuilder.length()-1,subLeaderDutyContentBuilder.length());
					teamReport.setSubLeaderDutyContent(subLeaderDutyContentBuilder.toString());
				}
			}
		});


		//================================����ṹ==================================================
		es.execute(new Runnable() {
			@Override
			public void run() {
				List<AgeDetailVo> tPersonDoList = new ArrayList<>();
				tPersonDoList.addAll(teamBuildMapper.findAgeDetail(overAllIdList));


				//35��������Ա
				List<AgeDetailVo> youngList = tPersonDoList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
				//35������-50����Ա
				List<AgeDetailVo> middleList = tPersonDoList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
				//50�꣨����������Ա
				List<AgeDetailVo> oldList = tPersonDoList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

				teamReport.setYoungLeaderCount(String.valueOf(youngList.size()));
				teamReport.setYoungLeaderPercent(String.valueOf(tPersonDoList.size() == 0 ? "-" : decimalFormat.format((double) youngList.size() / (double) tPersonDoList.size())));
				teamReport.setMiddleLeaderCount(String.valueOf(middleList.size()));
				teamReport.setMiddleLeaderPercent(String.valueOf(tPersonDoList.size() == 0 ? "-" : decimalFormat.format((double) middleList.size() / (double) tPersonDoList.size())));
				teamReport.setOldLeaderCount(String.valueOf(oldList.size()));
				teamReport.setOldLeaderPercent(String.valueOf(tPersonDoList.size() == 0 ? "-" : decimalFormat.format((double) oldList.size() / (double) tPersonDoList.size())));

				StringBuilder leaderAgeContentBuilder = new StringBuilder();
				if(!"0".equals(teamReport.getYoungLeaderCount())){
					leaderAgeContentBuilder.append("35������" + teamReport.getYoungLeaderCount() + "�ˣ�ռ��������" + teamReport.getYoungLeaderPercent()+";");
				}
				if(!"0".equals(teamReport.getMiddleLeaderCount())){
					leaderAgeContentBuilder.append("35��������50��" + teamReport.getMiddleLeaderCount() + "�ˣ�ռ��������" + teamReport.getMiddleLeaderPercent()+";");
				}
				if(!"0".equals(teamReport.getOldLeaderCount())){
					leaderAgeContentBuilder.append("50������������" + teamReport.getOldLeaderCount() + "��,ռ��������" + teamReport.getOldLeaderPercent()+";");
				}
				leaderAgeContentBuilder.delete(leaderAgeContentBuilder.length()-1,leaderAgeContentBuilder.length());
				teamReport.setLeaderAgeContent(leaderAgeContentBuilder.toString());

				double avgAge = tPersonDoList.stream().mapToDouble(AgeDetailVo::getAge).average().getAsDouble();
				//������λС�����������룩
				teamReport.setAvgLeaderAge(String.format("%.2f", avgAge));

				//��ǰ��λ�쵼����
				List<AgeDetailVo> tPersonDoSuperList = new ArrayList<>();
				tPersonDoSuperList.addAll(teamBuildMapper.findAgeDetail(superIdNos));



				//===========���ڵ�ǰ��λ ����ṹ================
				//35��������Ա
				List<AgeDetailVo> youngSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
				//35������-50����Ա
				List<AgeDetailVo> middleSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
				//50�꣨����������Ա
				List<AgeDetailVo> oldSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

				teamReport.setYoungLeaderSuperCount(String.valueOf(youngSuperList.size()));
				teamReport.setYoungLeaderSuperPercent(String.valueOf(tPersonDoSuperList.size() == 0 ? "-" : decimalFormat.format((double) youngSuperList.size() / (double) tPersonDoSuperList.size())));
				teamReport.setMiddleLeaderSuperCount(String.valueOf(middleSuperList.size()));
				teamReport.setMiddleLeaderSuperPercent(String.valueOf(tPersonDoSuperList.size() == 0 ? "-" : decimalFormat.format((double) middleSuperList.size() / (double) tPersonDoSuperList.size())));
				teamReport.setOldLeaderSuperCount(String.valueOf(oldSuperList.size()));
				teamReport.setOldLeaderSuperPercent(String.valueOf(tPersonDoSuperList.size() == 0 ? "-" : decimalFormat.format((double) oldSuperList.size() / (double) tPersonDoSuperList.size())));

				leaderAgeContentBuilder = new StringBuilder();
				if(!"0".equals(teamReport.getYoungLeaderSuperCount())){
					leaderAgeContentBuilder.append("35������" + teamReport.getYoungLeaderSuperCount() + "�ˣ�ռ��������" + teamReport.getYoungLeaderSuperPercent()+";");
				}
				if(!"0".equals(teamReport.getMiddleLeaderSuperCount())){
					leaderAgeContentBuilder.append("35��������50��" + teamReport.getMiddleLeaderSuperCount() + "�ˣ�ռ��������" + teamReport.getMiddleLeaderSuperPercent()+";");
				}
				if(!"0".equals(teamReport.getOldLeaderSuperCount())){
					leaderAgeContentBuilder.append("50������������" + teamReport.getOldLeaderSuperCount() + "��,ռ��������" + teamReport.getOldLeaderSuperPercent()+";");
				}
				leaderAgeContentBuilder.delete(leaderAgeContentBuilder.length()-1,leaderAgeContentBuilder.length());
				teamReport.setLeaderAgeSuperContent(leaderAgeContentBuilder.toString());

				int maxSuperAge = tPersonDoSuperList.stream().mapToInt(AgeDetailVo::getAge).max().getAsInt();
				int minSuperAge = tPersonDoSuperList.stream().mapToInt(AgeDetailVo::getAge).min().getAsInt();
				double avgSuperAge = tPersonDoSuperList.stream().mapToDouble(AgeDetailVo::getAge).average().getAsDouble();
				teamReport.setSuperLeaderMaxAge(String.valueOf(maxSuperAge));
				teamReport.setSuperLeaderMinAge(String.valueOf(minSuperAge));
				teamReport.setSuperLeaderAvgAge(String.format("%.2f", avgSuperAge));

				//==========���ڵ�ǰ��λ���� ����ṹ==============
				if(subIdNos.size()>0){
					//��ǰ��λ���赥λ�쵼����
					List<AgeDetailVo> tPersonDoSubList = new ArrayList<>();

					tPersonDoSubList.addAll(teamBuildMapper.findAgeDetail(subIdNos));
					//35��������Ա
					List<AgeDetailVo> youngSubList = tPersonDoSubList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
					//35������-50����Ա
					List<AgeDetailVo> middleSubList = tPersonDoSubList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
					//50�꣨����������Ա
					List<AgeDetailVo> oldSubList = tPersonDoSubList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

					teamReport.setYoungLeaderSubCount(String.valueOf(youngSubList.size()));
					teamReport.setYoungLeaderSubPercent(String.valueOf(tPersonDoSubList.size() == 0 ? "-" : decimalFormat.format((double) youngSubList.size() / (double) tPersonDoSubList.size())));
					teamReport.setMiddleLeaderSubCount(String.valueOf(middleSubList.size()));
					teamReport.setMiddleLeaderSubPercent(String.valueOf(tPersonDoSubList.size() == 0 ? "-" : decimalFormat.format((double) middleSubList.size() / (double) tPersonDoSubList.size())));
					teamReport.setOldLeaderSubCount(String.valueOf(oldSubList.size()));
					teamReport.setOldLeaderSubPercent(String.valueOf(tPersonDoSubList.size() == 0 ? "-" : decimalFormat.format((double) oldSubList.size() / (double) tPersonDoSubList.size())));

					leaderAgeContentBuilder = new StringBuilder();
					if(!"0".equals(teamReport.getYoungLeaderSubCount())){
						leaderAgeContentBuilder.append("35������" + teamReport.getYoungLeaderSubCount() + "�ˣ�ռ��������" + teamReport.getYoungLeaderSubPercent()+";");
					}
					if(!"0".equals(teamReport.getMiddleLeaderSubCount())){
						leaderAgeContentBuilder.append("35��������50��" + teamReport.getMiddleLeaderSubCount() + "�ˣ�ռ��������" + teamReport.getMiddleLeaderSubPercent()+";");
					}
					if(!"0".equals(teamReport.getOldLeaderSubCount())){
						leaderAgeContentBuilder.append("50������������" + teamReport.getOldLeaderSubCount() + "��,ռ��������" + teamReport.getOldLeaderSubPercent()+";");
					}
					leaderAgeContentBuilder.delete(leaderAgeContentBuilder.length()-1,leaderAgeContentBuilder.length());
					teamReport.setLeaderAgeSubContent(leaderAgeContentBuilder.toString());

					int maxSubAge = tPersonDoSubList.stream().mapToInt(AgeDetailVo::getAge).max().getAsInt();
					int minSubAge = tPersonDoSubList.stream().mapToInt(AgeDetailVo::getAge).min().getAsInt();
					double avgSubAge = tPersonDoSubList.stream().mapToDouble(AgeDetailVo::getAge).average().getAsDouble();
					teamReport.setSubLeaderMaxAge(String.valueOf(maxSubAge));
					teamReport.setSubLeaderMinAge(String.valueOf(minSubAge));
					teamReport.setSubLeaderAvgAge(String.format("%.2f", avgSubAge));
				}
			}
		});


		//===============��Ů���� �о��쵼=====================================
		es.execute(new Runnable() {
			@Override
			public void run() {
				int count = overAllIdList.size();
				//��ѯ��Ů
				Integer femaleCount = overAllIdList.size() > 0 ? teamBuildMapper.findFemaleCount(overAllIdList) : 0;
				//��
				int maleCount = count - femaleCount;
				teamReport.setLeaderMaleCount(String.valueOf(maleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*maleCount/count));
				teamReport.setLeaderFemaleCount(String.valueOf(femaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*femaleCount/count));
				double malePercent, femalePercent;
				if (maleCount != 0 && femaleCount != 0) {
					malePercent = (double) maleCount / (double) femaleCount;
					String percent = new DecimalFormat("#.##").format(malePercent) + ":" + "1";
					teamReport.setLeaderMfPercent("����Ů����Ϊ"+percent);
				}

				//=========���ڵ�ǰ��λ ��Ů����============
				int superLeaderCount = superIdNos.size();
				//��ѯ��Ů
				Integer superFemaleCount = superLeaderCount > 0 ? teamBuildMapper.findFemaleCount(superIdNos) : 0;
				//��
				int superMaleCount = superLeaderCount - superFemaleCount;
				teamReport.setLeaderMaleSuperCount(String.valueOf(superMaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*superMaleCount/superLeaderCount));
				teamReport.setLeaderFemaleSuperCount(String.valueOf(superFemaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*superFemaleCount/superLeaderCount));
				double superMalePercent, superFemalePercent;
				if (superMaleCount != 0 && superFemaleCount != 0) {
					superMalePercent = (double) superMaleCount / (double) superFemaleCount;
					String percent = new DecimalFormat("#.##").format(superMalePercent) + ":" + "1";
					teamReport.setLeaderMfSuperPercent("����Ů����Ϊ"+percent);
				}else{
					teamReport.setLeaderMfSuperPercent("");
				}

				//=============���ڲ��� ��Ů����====================
				if(subIdNos.size()>0){
					int subLeaderCount = subIdNos.size();
					//��ѯ��Ů
					Integer subFemaleCount = subLeaderCount > 0 ? teamBuildMapper.findFemaleCount(subIdNos) : 0;
					//��
					int subMaleCount = subLeaderCount - subFemaleCount;
					teamReport.setLeaderMaleSubCount(String.valueOf(subMaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*subMaleCount/subLeaderCount));
					teamReport.setLeaderFemaleSubCount(String.valueOf(subFemaleCount)+"�ˣ�ռ��"+decimalFormat.format(1f*subFemaleCount/subLeaderCount));
					double subMalePercent, subFemalePercent;
					if (subMaleCount != 0 && subFemaleCount != 0) {
						subMalePercent = (double) subMaleCount / (double) subFemaleCount;
						String percent = new DecimalFormat("#.##").format(subMalePercent) + ":" + "1";
						teamReport.setLeaderMfSubPercent("����Ů����Ϊ"+percent);
					}else{
						teamReport.setLeaderMfSubPercent("");
					}
				}
			}
		});


		//===================�Ļ��̶� �о��쵼===================================
		es.execute(new Runnable() {
			@Override
			public void run() {
				Map<String, Integer> educationByIdNos = personalAnalysisService.getEducationByIdNos(overAllIdList);
				//ͨ��map��value����ҳ���Ӧ��key
				int max = 0;
				String temp = "";
				int value = 0;
				for (String key : educationByIdNos.keySet()) {
					value = educationByIdNos.get(key);
					if (max < value) {
						max = value;
						temp = key;
					}
				}
				System.out.println("max-value:" + max + "  key:" + temp);
				teamReport.setLeaderMainEduName(temp);
				double leaderMainEduPercent = 0;
				if (max != 0 && overAllIdList.size() != 0) {
					leaderMainEduPercent = (double) max / (double) overAllIdList.size();
				}
				teamReport.setLeaderMainEduPercent("��"+max+"��,ռ����Ա��"+decimalFormat.format(leaderMainEduPercent));

				//==============���ڵ�ǰ��λ �Ļ��̶�======================
				Map<String, Integer> educationByIdNosSuper = personalAnalysisService.getEducationByIdNos(superIdNos);

				//ͨ��map��value����ҳ���Ӧ��key(ð������)
				int maxSuper = 0;
				String tempSuper = "";
				int valueSuper = 0;
				for (String keySuper : educationByIdNosSuper.keySet()) {
					valueSuper = educationByIdNosSuper.get(keySuper);
					if (maxSuper < valueSuper) {
						maxSuper = valueSuper;
						tempSuper = keySuper;
					}
				}
				teamReport.setSuperLeaderMainEduName(tempSuper);
				double superLeaderMainEduPercent = 0;
				if (maxSuper != 0 && superIdNos.size() != 0) {
					superLeaderMainEduPercent = (double) maxSuper / (double) superIdNos.size();
				}
				teamReport.setSuperLeaderMainEduPercent("��"+maxSuper+"��,ռ����Ա��"+decimalFormat.format(superLeaderMainEduPercent));

				//==============���ڲ��� �Ļ��̶�================================
				if(subIdNos.size()>0){

					Map<String, Integer> educationByIdNosSub = personalAnalysisService.getEducationByIdNos(subIdNos);
					//ͨ��map��value����ҳ���Ӧ��key
					int maxSub = 0;
					String tempSub = "";
					int valueSub = 0;
					for (String keySub : educationByIdNosSub.keySet()) {
						valueSub = educationByIdNosSub.get(keySub);
						if (maxSub < valueSub) {
							maxSub = valueSub;
							tempSub = keySub;
						}
					}
					teamReport.setSubLeaderMainEduName(tempSub);
					double subLeaderMainEduPercent = 0;
					if (maxSub != 0 && subIdNos.size() != 0) {
						subLeaderMainEduPercent = (double) maxSub / (double) subIdNos.size();
					}
					teamReport.setSubLeaderMainEduPercent("��"+maxSub+"��,ռ����Ա��"+decimalFormat.format(subLeaderMainEduPercent));
				}
			}
		});

		//====================����ֲ� ======================================================
		es.execute(new Runnable() {
			@Override
			public void run() {
				List<NationMapPojo> leaderNationMapList = personalAnalysisMapper.queryNationByIdNum(overAllIdList);
				//ͨ��map��value����ҳ���Ӧ��key
				//����ֵ maxNation
				int maxNation = 0;
				//����ֵ��Ӧ�ļ� tempNation
				String tempNation = "";
				int valueNation = 0;
				for (NationMapPojo nationMapPojo :
						leaderNationMapList) {
					valueNation = nationMapPojo.getNationNum();
					if (maxNation < valueNation) {
						maxNation = valueNation;
						tempNation = nationMapPojo.getNationName();
					}
				}

				teamReport.setLeaderMainNationName(tempNation);
				double leaderMainNationPercent = 0;
				if (maxNation != 0 && overAllIdList.size() != 0) {
					leaderMainNationPercent = (double) maxNation / (double) overAllIdList.size();
				}
				teamReport.setLeaderMainNationPercent(decimalFormat.format(leaderMainNationPercent));
				//================��ǰ��λ ����ֲ� =============================
				List<NationMapPojo> leaderNationSuperMapList = personalAnalysisMapper.queryNationByIdNum(superIdNos);
				//ͨ��map��value����ҳ���Ӧ��key
				//����ֵ maxNation
				int maxNationSuper = 0;
				//����ֵ��Ӧ�ļ� tempNation
				String tempNationSuper = "";
				int valueNationSuper = 0;

				for (NationMapPojo nationMapPojo :
						leaderNationSuperMapList) {
					valueNationSuper = nationMapPojo.getNationNum();
					if (maxNationSuper < valueNationSuper) {
						maxNationSuper = valueNationSuper;
						tempNationSuper = nationMapPojo.getNationName();
					}
				}
				teamReport.setLeaderMainNationNameSuper(tempNationSuper);
				double leaderMainNationSuperPercent = 0;
				if (maxNationSuper != 0 && superIdNos.size() != 0) {
					leaderMainNationSuperPercent = (double) maxNationSuper / (double) superIdNos.size();
				}
				teamReport.setLeaderMainNationPercentSuper(decimalFormat.format(leaderMainNationSuperPercent));
				//================����ֲ� �����쵼=============================
				if(subIdNos.size()>0){
					List<NationMapPojo> leaderNationSubMapList = personalAnalysisMapper.queryNationByIdNum(subIdNos);
					//ͨ��map��value����ҳ���Ӧ��key
					//����ֵ maxNation
					int maxNationSub = 0;
					//����ֵ��Ӧ�ļ� tempNation
					String tempNationSub = "";
					int valueNationSub = 0;
					for (NationMapPojo nationMapPojo :
							leaderNationSubMapList) {
						valueNationSub = nationMapPojo.getNationNum();
						if (maxNationSub < valueNationSub) {
							maxNationSub = valueNationSub;
							tempNationSub = nationMapPojo.getNationName();
						}
					}
					teamReport.setLeaderMainNationNameSub(tempNationSub);
					double leaderMainNationSubPercent = 0;
					if (maxNationSub != 0 && subIdNos.size() != 0) {
						leaderMainNationSubPercent = (double) maxNationSub / (double) subIdNos.size();
					}
					teamReport.setLeaderMainNationPercentSub(decimalFormat.format(leaderMainNationSubPercent));
				}
			}
		});

		//========================��ְʱ�� =========================================================

		es.execute(new Runnable() {
			@Override
			public void run() {
				List<Map<String, Object>> maps = personalAnalysisMapper.queryAppointmentTime(overAllIdList);

				maps.forEach(m->{
					String jobGrade = null;
					String jobType = null;
					switch (String.valueOf(m.get("POLICE_JOB_GRADE"))) {
						case "0925":
						case "102":
						case "10":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0830":
						case "0920":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0730":
						case "0820":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0720":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0717":
						case "0718":
							jobGrade = "����ְ���쵼";
							jobType = "����";
							break;
						case "0715":
						case "0716":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0620":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0520":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						default:
							break;
					}
					m.put("POLICE_JOB_GRADE",jobGrade);
					m.put("POLICE_JOB_GRADE_TYPE",jobType);
				});


				teamReport.setMainPostNum(String.valueOf(maps.stream().filter(m->"��ְ".equals(m.get("POLICE_JOB_GRADE_TYPE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>3).count()));
				teamReport.setMainPostNumOppositie(String.valueOf(maps.stream().filter(m->"��ְ".equals(m.get("POLICE_JOB_GRADE_TYPE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=3).count()));
				teamReport.setAssistantPostNum(String.valueOf(maps.stream().filter(m->"��ְ".equals(m.get("POLICE_JOB_GRADE_TYPE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>2).count()));
				teamReport.setAssistantPostNumOppositie(String.valueOf(maps.stream().filter(m->"��ְ".equals(m.get("POLICE_JOB_GRADE_TYPE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=2).count()));

				if(richContent){
					Map<Object, List<Map<String, Object>>> groupByJobGrade = maps.parallelStream().filter(m -> duties.contains(m.get("POLICE_JOB_GRADE"))).collect(Collectors.groupingBy(m -> m.get("POLICE_JOB_GRADE"), Collectors.toList()));


					List<Map<String, Object>> list = new ArrayList<>();
					for (String duty : duties) {
						List<Map<String, Object>> collect = groupByJobGrade.get(duty);
						if(collect==null){
							continue;
						}
						final Integer maxYear = collect.stream().map(m -> Integer.parseInt(String.valueOf(m.get("YEAR")))).max(Integer::compareTo).get();
						list.addAll(collect.stream().filter(m -> Integer.parseInt(String.valueOf(m.get("YEAR"))) == maxYear).collect(Collectors.toList()));
					}

					for (Map<String, Object> map : list) {
						if("0".equals(String.valueOf(map.get("YEAR")))){
							map.put("YEAR","δ��һ��");
						}
						if(map.get("POST")!=null){
							map.put("POST",StringUtils.join(getStrList(String.valueOf(map.get("POST")),6),"\n"));
						}
						if(map.get("DEPTNAME")!=null){
							map.put("DEPTNAME",StringUtils.join(getStrList(String.valueOf(map.get("DEPTNAME")),6),"\n"));
						}
					}


					List<Pair<String, String>> heads = new ArrayList<>();
					heads.add(Pair.of("��λ","DEPTNAME"));
					heads.add(Pair.of("����","PERSON_NAME"));
					heads.add(Pair.of("��������","BIRTHDAY"));
					heads.add(Pair.of("ְ��","POST"));
					heads.add(Pair.of("����ְ�񼶱�","POLICE_JOB_GRADE"));
					heads.add(Pair.of("��ְʱ��(��)","YEAR"));
					teamReport.setPostTable(getMiniTableRenderData(heads,list,true));
				}
			}
		});

		//========================�뾯���� =================================================================
		es.execute(new Runnable() {
			@Override
			public void run() {
				List<Pair<Integer,Integer>> ranges = new ArrayList<>();
				ranges.add(Pair.of(null, 5));
				ranges.add(Pair.of(5, 10));
				ranges.add(Pair.of(10, 15));
				ranges.add(Pair.of(15, 20));
				ranges.add(Pair.of(20, 25));
				ranges.add(Pair.of(25, 30));
				ranges.add(Pair.of(30, null));

				List<PersonQo> superList = personalAnalysisMapper.queryPeopleByJoinTime("","",superIdNos);
				StringBuilder stringBuilder = new StringBuilder();
				for (Pair<Integer,Integer> range : ranges) {
					Integer left = range.getLeft();
					Integer right = range.getRight();
					Stream<PersonQo> stream = superList.stream();
					if(left != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())>left);
					}
					if(right != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())<=right);
					}
					long count = stream.count();
					if(count==0){
						continue;
					}
					String rangeStr="";
					if(left !=null && right!=null){
						rangeStr = left+"-"+right+"��(��)";
					}else if(right == null){
						rangeStr = left+"������";
					}else if(left == null){
						rangeStr = right+"��(��)����";
					}
					stringBuilder.append(rangeStr+"��"+count+"�ˣ�ռ��������"+decimalFormat.format(1f*count/superIdNos.size())+"��");
				}
				stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
				teamReport.setSuperEntryPeriodContent(stringBuilder.toString());

				List<Map<String, Object>> collect;
				List<Pair<String, String>> heads;
				if(richContent){
//                    final Integer superMaxYear = superList.stream().map(p -> Integer.parseInt(p.getTimeToWorkPsb())).max(Integer::compareTo).get();
					collect = superList.stream().filter(p-> Integer.parseInt(p.getTimeToWorkPsb()) > 35).map(p->{
						HashMap<String, Object> map = new HashMap<>();
						map.put("YEAR",p.getTimeToWorkPsb());
						map.put("DEPTNAME",(p.getOrgSimpleName()==null?"":p.getOrgSimpleName())+(p.getOfficeSimpleName()==null?"":p.getOfficeSimpleName()));
						map.put("PERSON_NAME",p.getPersonName());
						map.put("POST",p.getPost());
						map.put("BIRTHDAY",p.getBirthDay());
						return map;
					}).collect(Collectors.toList());
					collect.sort((o1, o2) -> Integer.parseInt(String.valueOf(o2.get("YEAR")))-Integer.parseInt(String.valueOf(o1.get("YEAR"))));

					heads = new ArrayList<>();
					heads.add(Pair.of("��λ","DEPTNAME"));
					heads.add(Pair.of("����","PERSON_NAME"));
					heads.add(Pair.of("��������","BIRTHDAY"));
					heads.add(Pair.of("ְ��","POST"));
					heads.add(Pair.of("�뾯����(��)","YEAR"));
					teamReport.setSuperLeaderEntryTable(getMiniTableRenderData(heads,collect,true));
				}

				if(subIdNos.size()>0){
					List<PersonQo> subList = personalAnalysisMapper.queryPeopleByJoinTime("","",subIdNos);
					stringBuilder = new StringBuilder();
					for (Pair<Integer,Integer> range : ranges) {
						Integer left = range.getLeft();
						Integer right = range.getRight();
						Stream<PersonQo> stream = subList.stream();
						if(left != null){
							stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())>left);
						}
						if(right != null){
							stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())<=right);
						}
						long count = stream.count();
						if(count==0){
							continue;
						}
						String rangeStr="";
						if(left !=null && right!=null){
							rangeStr = left+"-"+right+"��(��)";
						}else if(right == null){
							rangeStr = left+"������";
						}else if(left == null){
							rangeStr = right+"��(��)����";
						}
						stringBuilder.append(rangeStr+"��"+count+"�ˣ�ռ��������"+decimalFormat.format(1f*count/subIdNos.size())+"��");
					}
					stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
					teamReport.setSubEntryPeriodContent(stringBuilder.toString());

					if (richContent) {
//                        final Integer subMaxYear = subList.stream().map(p -> Integer.parseInt(p.getTimeToWorkPsb())).max(Integer::compareTo).get();
						collect = subList.stream().filter(p-> Integer.parseInt(p.getTimeToWorkPsb()) > 35).map(p->{
							HashMap<String, Object> map = new HashMap<>();
							map.put("YEAR",p.getTimeToWorkPsb());
							map.put("DEPTNAME",(p.getOrgSimpleName()==null?"":p.getOrgSimpleName())+(p.getOfficeSimpleName()==null?"":p.getOfficeSimpleName()));
							map.put("PERSON_NAME",p.getPersonName());
							map.put("POST",p.getPost());
							map.put("BIRTHDAY",p.getBirthDay());
							return map;
						}).collect(Collectors.toList());
						collect.sort((o1, o2) -> Integer.parseInt(String.valueOf(o2.get("YEAR")))-Integer.parseInt(String.valueOf(o1.get("YEAR"))));

						heads = new ArrayList<>();
						heads.add(Pair.of("��λ","DEPTNAME"));
						heads.add(Pair.of("����","PERSON_NAME"));
						heads.add(Pair.of("��������","BIRTHDAY"));
						heads.add(Pair.of("ְ��","POST"));
						heads.add(Pair.of("�뾯����(��)","YEAR"));
						teamReport.setSubLeaderEntryTable(getMiniTableRenderData(heads,collect,true));
					}
				}

			}
		});

//==================== Υ�Ͱ���================
		es.execute(new Runnable() {
			@Override
			public void run() {

				List<EchartsVo> allCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%", overAllIdList);
				Integer leaderRuleCount = allCases.stream().map(e -> Integer.parseInt(e.getValue())).collect(Collectors.summingInt(a -> a));

				List<EchartsVo> superCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%",superIdNos);
				Integer superLeaderRuleCount = superCases.stream().map(e -> Integer.parseInt(e.getValue())).collect(Collectors.summingInt(a -> a));

				Integer subLeaderRuleCount;


				if(allCases.size()>0){
					int currentYear = teamReport.getCurrentYear();



					String leaderRuleCurrentYearCount ="0";
					if(allCases.get(allCases.size()-1).getName().equals(String.valueOf(currentYear))){
						leaderRuleCurrentYearCount = allCases.get(allCases.size()-1).getValue();
					}
					teamReport.setLeaderRuleTitle("���ߣ�Υ�Ͱ���");
					String leaderRuleContent;

					if(subIdNos.size()>0){
						List<EchartsVo> subCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%",subIdNos);
						subLeaderRuleCount = subCases.stream().map(e -> Integer.parseInt(e.getValue())).collect(Collectors.summingInt(a -> a));

						List<String> strList = new ArrayList<>();
						if (superLeaderRuleCount!=0) {
							strList.add(teamReport.getSuperName() + "�쵼���ӳ�Ա����"+superLeaderRuleCount+"�˴��漰�ͼ참��");
						}
						if (subLeaderRuleCount!=0) {
							strList.add(teamReport.getSubName()+"�쵼���ӳ�Ա����"+subLeaderRuleCount+"�˴��漰�ͼ참��");
						}
						leaderRuleContent = teamReport.getOverAllName() + "�쵼���ӳ�Ա����" + allCases.get(0).getName() + "���ֹĿǰ����" + leaderRuleCount + "�˴��漰�ͼ참��"+("0".equals(leaderRuleCurrentYearCount)?"":("������" + currentYear + "����" + leaderRuleCurrentYearCount + "�˴�"))+"��" + StringUtils.join(strList,",")+"�������漰������Ա���Ƽ���ϸ������ʾ��";

						String superLeaderRuleContent = "��" + allCases.get(0).getName() + "��" + teamReport.getSuperName() + "�쵼���ӳ�Ա����" + superLeaderRuleCount + "�˴��漰�ͼ참��";
						if(superCases.size()>0){
							superLeaderRuleContent+=",����";
							for (EchartsVo superCase : superCases) {
								superLeaderRuleContent += superCase.getName()+"�깲��"+superCase.getValue()+"�˴�,";
							}
							superLeaderRuleContent = superLeaderRuleContent.substring(0,superLeaderRuleContent.length()-1);
						}
						teamReport.setSuperLeaderRuleContent(superLeaderRuleContent);
						String subLeaderRuleContent = "��" + allCases.get(0).getName() + "��" + teamReport.getSubName() + "�쵼���ӳ�Ա����" + subLeaderRuleCount + "�˴��漰�ͼ참��";
						if(subCases.size()>0){
							subLeaderRuleContent+=",����";
							for (EchartsVo superCase : subCases) {
								subLeaderRuleContent += superCase.getName()+"�깲��"+superCase.getValue()+"�˴�,";
							}
							subLeaderRuleContent = subLeaderRuleContent.substring(0,subLeaderRuleContent.length()-1);
						}
						teamReport.setSubLeaderRuleContent(subLeaderRuleContent);
					}else{
						leaderRuleContent = teamReport.getOverAllName() + "�쵼���ӳ�Ա����" + allCases.get(0).getName() + "���ֹĿǰ����" + leaderRuleCount + "�˴��漰�ͼ참��������" + currentYear + "����" + leaderRuleCurrentYearCount + "�˴Ρ������漰������Ա���Ƽ���ϸ������ʾ��";

						String superLeaderRuleContent = "��" + allCases.get(0).getName() + "��" + teamReport.getOverAllName() + "�쵼���ӳ�Ա����" + leaderRuleCount + "�˴��漰�ͼ참��";
						if(allCases.size()>0){
							superLeaderRuleContent+=",����";
							for (EchartsVo superCase : allCases) {
								superLeaderRuleContent += superCase.getName()+"�깲��"+superCase.getValue()+"�˴�,";
							}
							superLeaderRuleContent = superLeaderRuleContent.substring(0,superLeaderRuleContent.length()-1);
						}
						teamReport.setSuperLeaderRuleContent(superLeaderRuleContent);
					}

					teamReport.setLeaderRuleContent(leaderRuleContent);

					if (richContent) {
						List<BreakRuleCaseVo> details = teamBuildMapper.findBreakRuleCaseDetail("%%", overAllIdList);
						List<Map<String, Object>> list = details.stream().map(d -> {
							Map<String, Object> result = new HashMap<>();
							result.put("DEPT",StringUtils.join(getStrList(d.getDeptName(),5),"\n"));
							result.put("NAME",d.getPersonName());
							result.put("POLICE_NO",d.getPoliceNo());
							result.put("CFJDSJ",d.getDate());
							result.put("CFDQSJ",d.getDate1());
							result.put("WTXZ",StringUtils.join(getStrList(d.getDetail(),5),"\n"));
							result.put("POST",d.getPost());
							result.put("BIRTHDAY",StringUtils.substring(d.getIdNo(),6,14));
							return result;
						}).collect(Collectors.toList());
						list.sort(new Comparator<Map<String, Object>>() {
							@Override
							public int compare(Map<String, Object> o1, Map<String, Object> o2) {
								return String.valueOf(o2.get("CFJDSJ")).compareTo(String.valueOf(o1.get("CFJDSJ")));
							}
						});
						List<Pair<String, String>> heads = new ArrayList<>();
						heads.add(Pair.of("��λ","DEPT"));
						heads.add(Pair.of("����","NAME"));
						heads.add(Pair.of("��������","BIRTHDAY"));
						heads.add(Pair.of("ְ��","POST"));
						heads.add(Pair.of("����","POLICE_NO"));
						heads.add(Pair.of("���־���ʱ��","CFJDSJ"));
						heads.add(Pair.of("���ֵ���ʱ��","CFDQSJ"));
						heads.add(Pair.of("��������","WTXZ"));
						teamReport.setLeaderRuleTable(getMiniTableRenderData(heads,list));


						teamReport.setLeaderRuleTrendTitle(teamReport.getOverAllName()+"�쵼����Υ�Ͱ�������ͼ");
						BufferedImage path = pdfService.base64toMultipart(reportVo.getLeaderRuleTrend(), reportVo.getUnitId());
						teamReport.setLeaderRuleTrend(new PictureRenderData(576, 211,".png", path));
					}
				}else{
					teamReport.setLeaderRuleTrend(null);
					teamReport.setLeaderRuleTitle(null);
					teamReport.setLeaderRuleContent(teamReport.getOverAllName()+"�쵼���ӳ�Ա����2014���ֹĿǰ����0���漰�ͼ참��");
					teamReport.setLeaderRuleTable(new MiniTableRenderData(new ArrayList<>()));

					teamReport.setSuperLeaderRuleContent(teamReport.getSuperName()+"�쵼���ӳ�Ա����2014���ֹĿǰ����0���漰�ͼ참��");
					teamReport.setSubLeaderRuleContent(teamReport.getSubName()+"�쵼���ӳ�Ա����2014���ֹĿǰ����0���漰�ͼ참��");
				}
			}
		});

		if(richContent){
			es.execute(new Runnable() {
				@Override
				public void run() {
					BufferedImage path = pdfService.base64toMultipart(reportVo.getSuperLeaderDuty(), reportVo.getUnitId());
					teamReport.setSuperLeaderDuty(new PictureRenderData(576, 211,".png", path));


					path = pdfService.base64toMultipart(reportVo.getLeaderPieAge(), reportVo.getUnitId());
					teamReport.setLeaderPieAge(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getLeaderColumnAge(), reportVo.getUnitId());
					teamReport.setLeaderColumnAge(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSuperLeaderNation(), reportVo.getUnitId());
					teamReport.setSuperLeaderNation(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSuperLeaderEdution(), reportVo.getUnitId());
					teamReport.setSuperLeaderEducation(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSuperLeaderEntryPeriod(), reportVo.getUnitId());
					teamReport.setSuperLeaderEntryPeriod(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getLeaderSex(), reportVo.getUnitId());
					teamReport.setLeaderSex(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getLeaderColumnSex(), reportVo.getUnitId());
					teamReport.setLeaderColumnSex(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getLeaderMainPost(), reportVo.getUnitId());
					teamReport.setLeaderMainPost(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getLeaderAssistantPost(), reportVo.getUnitId());
					teamReport.setLeaderAssistantPost(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSubLeaderDuty(), reportVo.getUnitId());
					teamReport.setSubLeaderDuty(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSubLeaderNation(), reportVo.getUnitId());
					teamReport.setSubLeaderNation(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSubLeaderEdution(), reportVo.getUnitId());
					teamReport.setSubLeaderEducation(new PictureRenderData(576, 211, ".png", path));

					path = pdfService.base64toMultipart(reportVo.getSubLeaderEntryPeriod(), reportVo.getUnitId());
					teamReport.setSubLeaderEntryPeriod(new PictureRenderData(576, 211, ".png", path));
				}
			});
		}

		es.shutdown();
		while (!es.isTerminated()){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}



	/**
	 * ����
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getAge(ReportVo reportVo, TeamReport teamReport) {
		System.out.println("getAge");
		TeamDetail teamDetail = new TeamDetail();
		teamDetail.setUnitId(reportVo.getUnitId());
		//��ȡ��Ӧ�������Ĳ��ŵ�unitId
		List<String> unitIdList = personStereoService.getCompanyAllId(reportVo.getUnitId());

		if (unitIdList.size() > 0) {
			TPersonCountAndAge tPersonCountAndAge;
			if(reportVo.getUnitId().equals(NJSGAJ_ID)){
				tPersonCountAndAge = teamBuildService.getCountAndAgeByUnitIds(new ArrayList<>());
			}else{

				tPersonCountAndAge = teamBuildService.getCountAndAgeByUnitIds(unitIdList);
			}
			teamReport.setAvgAge(String.format("%.2f", Double.parseDouble(tPersonCountAndAge.getAgeSum())));
			//��ȡ����ṹ�ſ���30�꣬30-35�ȵȣ�
			Map<String, Object> mapList = teamBuildService.getAgeInfo(teamDetail);
			teamReport.setAge1(mapList.get("young").toString());
			teamReport.setAgePercentage1(mapList.get("youngPercent").toString());
			teamReport.setAge2(mapList.get("middle").toString());
			teamReport.setAgePercentage2(mapList.get("middlePercent").toString());
			teamReport.setAge3(mapList.get("old").toString());
			teamReport.setAgePercentage3(mapList.get("oldPercent").toString());

			StringBuilder ageContentBuilder = new StringBuilder();
			if (!"0".equals(teamReport.getAge1())) {
				ageContentBuilder.append("35��������"+teamReport.getAge1()+"�ˣ�ռ��������"+teamReport.getAgePercentage1()+"��");
			}
			if (!"0".equals(teamReport.getAge2())) {
				ageContentBuilder.append("35��������50����"+teamReport.getAge2()+"�ˣ�ռ��������"+teamReport.getAgePercentage2()+"��");
			}
			if (!"0".equals(teamReport.getAge3())) {
				ageContentBuilder.append("50��������������"+teamReport.getAge3()+"�ˣ�ռ��������"+teamReport.getAgePercentage3()+"��");
			}
			ageContentBuilder.delete(ageContentBuilder.length()-1,ageContentBuilder.length());
			teamReport.setAgeContent(ageContentBuilder.toString());
			//���ص������ɵ�ͼƬ����ͼ�����ļ�·��
			BufferedImage path = pdfService.base64toMultipart(reportVo.getAge(), reportVo.getUnitId());
//            System.out.println("path" +path);
			teamReport.setAgeImage(new PictureRenderData(576, 211,".png",  path));
		}
		return null;
	}

	/**
	 * ����
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getHonor(ReportVo reportVo, TeamReport teamReport) {
		System.out.println("getHonor");
		TeamDetail teamDetail = new TeamDetail();
		teamDetail.setUnitId(reportVo.getUnitId());
		teamDetail.setIsReport("1");
		//��ȡ���������ſ�
		Map<String, Object> honorMap = teamBuildService.getPersonHonor(teamDetail);
		if (honorMap.get("content1") != null) {
			String honorCount = honorMap.get("content1").toString();
			teamReport.setHonor1(honorCount);
		}
		if (honorMap.get("content2") != null) {
			String honorCount = honorMap.get("content2").toString();
			String[] strings = honorCount.split(",");
			teamReport.setHonor2(strings[1]);
			teamReport.setHonor3(strings[2]);
			teamReport.setHonor4(strings[3]);
		}
		if (honorMap.get("content3") != null) {
			String honorCount = honorMap.get("content3").toString();
			String[] strings = honorCount.split(",");
			teamReport.setHonor5(strings[0]);
			teamReport.setHonor6(strings[1]);
		}
		teamReport.setHonorImage(new PictureRenderData(576, 211,".png", pdfService.base64toMultipart(reportVo.getPersonHonor(), reportVo.getUnitId())));
		return null;
	}

	/**
	 * �ͼ�
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getRule(ReportVo reportVo, TeamReport teamReport) {
		TeamDetail teamDetail = new TeamDetail();
		teamDetail.setUnitId(reportVo.getUnitId());
		//��ȡ��Ӧ�ĵ�λ����
		String name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();

		List<EchartsVo> caseList = new ArrayList<>();
		if ("�Ͼ��й�����".equals(name)){
			caseList.addAll(teamBuildMapper.findBreakRuleCaseCountByYear("%%",null));
		}else {
			caseList.addAll(teamBuildMapper.findBreakRuleCaseCountByYear("%" + name + "%",null));
		}

		if (caseList.size() > 0) {
			String year = caseList.get(0).getName();
			int count = caseList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum();
			teamReport.setRule1("��2014��");
			teamReport.setRule2(Integer.toString(count));
		}else {
			teamReport.setRule2("0");
		}
		List<BreakRuleCaseVo> ruleList = new ArrayList<>();
		//����װΥ�Ͱ������Ͷ�Ӧ�Ĵ���
		List<RulesAndCount> rulesAndCountList = new ArrayList<>();
		if (NJSGAJ_ID.equals(reportVo.getUnitId())) {
			//��ѯ�ͼ참����Ϣ
			ruleList.addAll(teamBuildMapper.findBreakRuleCaseDetail("%%",null));
			rulesAndCountList.addAll(teamBuildMapper.findRulesCountByWtxz("%%"));
		} else {
			//��ѯ�ͼ참����Ϣ
			ruleList.addAll(teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null));
			rulesAndCountList.addAll(teamBuildMapper.findRulesCountByWtxz("%" + name + "%"));
		}

		if(rulesAndCountList.size() >0) {
			if(NJSGAJ_ID.equals(reportVo.getUnitId())){
				int sum = ruleList.size();
				int tempSum=0;
				int index=0;

				StringBuilder count = new StringBuilder();
				count.append(",����");
				while (tempSum<(sum/2)){
					tempSum+=rulesAndCountList.get(index).getCount();
					count.append(rulesAndCountList.get(index).getCount()).append("�˴��漰").append(rulesAndCountList.get(index).getWtxz()).append(",");
					index++;
				}
				count.append("��������Υ�Ͱ����漰�˴�"+(sum-tempSum));
				teamReport.setRule3(count.toString());
			}else{
				int flag = rulesAndCountList.size();
				StringBuilder count = new StringBuilder();
				count.append(",����");
				for (int i=0;i<flag;i++) {
					count.append(rulesAndCountList.get(i).getCount()).append("�˴��漰").append(rulesAndCountList.get(i).getWtxz()).append(",");
				}
				count.delete(count.length()-1,count.length());
				if(count.length() >1) {
					teamReport.setRule3(count.toString());
				}
			}
		}

		if(NJSGAJ_ID.equals(reportVo.getUnitId())) {
			ruleList = ruleList.stream().filter(a->Integer.parseInt(a.getDate().substring(0,4))>=(teamReport.getCurrentYear()-2)).collect(Collectors.toList());
		}

		//���´������Υ�Ͱ������ű�
		List<TextRenderData> headerList = new ArrayList<>();
		headerList.add(new TextRenderData("��λ"));
		headerList.add(new TextRenderData("����"));
		headerList.add(new TextRenderData("����"));
		headerList.add(new TextRenderData("���־���ʱ��"));
		headerList.add(new TextRenderData("���ֵ���ʱ��"));
		headerList.add(new TextRenderData("��������"));
		headerList.forEach(v -> {
			Style style = new Style();
			style.setFontSize(9);
			style.setFontFamily("��������_GBK");
			style.setBold(true);
			v.setStyle(style);
		});
		RowRenderData header = new RowRenderData(headerList, "c2c2c2");
		List<RowRenderData> rowList = new ArrayList<>();
		if (ruleList.size() > 0) {
			ruleList.forEach(v -> {
				String detail;
				if(v.getDetail()!=null){
					detail = StringUtils.join(getStrList(v.getDetail(),10),"\n");
				}else{
					detail ="-";
				}

				String deptName;
				if(v.getDeptName()!=null){
					deptName = StringUtils.join(getStrList(v.getDeptName(),14),"\n");
				}else{
					deptName="-";
				}
				RowRenderData row = RowRenderData.build(deptName, v.getPersonName(), v.getPoliceNo()==null?"-":v.getPoliceNo(), v.getDate()==null?"-":v.getDate(),v.getDate1()==null?"-":v.getDate1(), detail);
				for (CellRenderData textRenderData : row.getCells()) {
					Style style = new Style();
					style.setFontSize(9);
					style.setFontFamily("��������_GBK");
					textRenderData.getCellText().setStyle(style);
				}
				rowList.add(row);
			});
		}
		rowList.sort(Comparator.comparing(v->v.getCells().get(3).getCellText().getText(),Comparator.nullsLast(Comparator.  reverseOrder())));
		MiniTableRenderData result = new MiniTableRenderData(header, rowList,MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
		result.setNoDatadesc("��������");
		teamReport.setRuleTable(result);


		Map<String,Object> teamInfo = teamComparisonService.getTeamInfoByUnitId(NJSGAJ_ID);
		List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
		List<TeamInfoVo> teams=new ArrayList<>();
		teams.addAll(teamInfos);
		teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
		teams.addAll(teamInfos);


		String sql;
		if(!NJSGAJ_ID.equals(reportVo.getUnitId())){
			sql = "SELECT COUNT(*) COUNT,DW FROM NJGAZNL.M_JLSC_AJJB WHERE CFJDSJ IS NOT NULL  AND DW LIKE '%"+name+"%' GROUP BY DW ORDER BY  COUNT DESC ";
		}else{
			StringBuilder sqlBuilder= new StringBuilder("SELECT COUNT(*) COUNT,DW FROM (");
			sqlBuilder.append("SELECT CASE");
			for (TeamInfoVo team : teams) {
				sqlBuilder.append(" WHEN INSTR(DW,'"+team.getTeamName()+"')>0 THEN '"+team.getTeamName()+"' ");
			}
			sqlBuilder.append("ELSE  NULL END DW FROM NJGAZNL.M_JLSC_AJJB WHERE CFJDSJ IS NOT NULL");
			sqlBuilder.append(") WHERE DW IS NOT NULL GROUP BY DW ORDER BY  COUNT DESC ");
			sql = sqlBuilder.toString();
		}
		List<Map<String, Object>> maps = primaryJdbcTemplate.queryForList(sql);

		if(teamReport.getRule2().equals("0")){
			teamReport.setRuleContent((teamReport.getRule1()==null?"":teamReport.getRule1())+"�漰�ͼ참������"+teamReport.getRule2()+"�˴�"+(teamReport.getRule3()==null?"":teamReport.getRule3())+"��");

			teamReport.setRuleTrend(new PictureRenderData(0,0,""));
			teamReport.setRuleTrendTitle(null);
		}else{
			teamReport.setRuleContent((teamReport.getRule1()==null?"":teamReport.getRule1())+"�漰�ͼ참������"+teamReport.getRule2()+"�˴�"+(teamReport.getRule3()==null?"":teamReport.getRule3())+"������"+maps.get(0).get("DW")+"��λ����������࣬����"+maps.get(0).get("COUNT")+"�˴Ρ�");

			BufferedImage path = pdfService.base64toMultipart(reportVo.getRuleTrend(), reportVo.getUnitId());
			teamReport.setRuleTrend(new PictureRenderData(576, 211,".png",  path));

			teamReport.setRuleTrendTitle("����Υ�Ͱ����漰�˴�����");
		}


		maps.sort((o1, o2) -> {
			int i1 = deptList.indexOf(o1.get("DW"));
			int i2 = deptList.indexOf(o2.get("DW"));
			return i1-i2;
		});

		maps.forEach(m->{
			m.put("DW",CommonUnit.getUnitName(String.valueOf(m.get("DW"))));
		});
		List<Pair<String, String>> heads = new ArrayList<>();
		heads.add(Pair.of("��λ","DW"));
		heads.add(Pair.of("�˴�","COUNT"));
		teamReport.setRuleStatisticsTable(getMiniTableRenderData(heads,maps,true,true));

		sql = "SELECT COUNT(*) COUNT,DW FROM NJGAZNL.M_JLSC_AJJB WHERE CFJDSJ IS NOT NULL ";
		if(!NJSGAJ_ID.equals(reportVo.getUnitId())){
			sql += " AND DW LIKE '%"+name+"%' ";
		}
		sql +=" GROUP BY DW ORDER BY  COUNT DESC ";
		maps = primaryJdbcTemplate.queryForList(sql);
		if(NJSGAJ_ID.equals(reportVo.getUnitId())){
			Integer integer = maps.stream().filter(m -> !deptList.contains(m.get("DW"))).map(a -> Integer.parseInt(String.valueOf(a.get("COUNT")))).distinct().sorted((o1, o2) -> (o2 - o1)).collect(Collectors.toList()).get(2);
			maps = maps.stream().filter(m->Integer.parseInt(String.valueOf(m.get("COUNT")))>=integer).collect(Collectors.toList());
		}
		heads = new ArrayList<>();
		heads.add(Pair.of("��λ","DW"));
		heads.add(Pair.of("�˴�","COUNT"));
		teamReport.setRuleTop5Table(getMiniTableRenderData(heads,maps));

		return null;
	}

	/**
	 * ��Ч
	 *
	 * @param reportVo
	 * @param teamReport
	 * @param builder
	 * @return
	 */
	@Async
	public Future<Boolean> getKpi(ReportVo reportVo, TeamReport teamReport, ConfigureBuilder builder) {
		int currentYear = teamReport.getCurrentYear();
		List<Map<String,Object >> ruleList = new ArrayList<>();

		List<Map<String, Object>> kpiMenu = teamBuildService.getKPIMenu();
		Map<String, Object> yearMap = kpiMenu.get(0);
		String year = String.valueOf(yearMap.get("value"));

		List<Map<String, Object>> quarterMap = (List<Map<String, Object>>) yearMap.get("children");
		String quarter = String.valueOf(quarterMap.get(0).get("value"));

		teamReport.setKpiContent(year+"��"+quarter);

		KPIQuery kpiQuery = new KPIQuery();
		kpiQuery.setIsDownload(false);
		kpiQuery.setMenu(Arrays.asList(year,quarter));
		ResultModel kpi = teamBuildService.getKPI(kpiQuery);
		if(kpi!=null && kpi.getData()!=null){
			ruleList = (List<Map<String,Object >>)kpi.getData();
		}
//        if(NJSGAJ_ID.equals(reportVo.getUnitId())){
//            ruleList.addAll(primaryJdbcTemplate.queryForList("SELECT T.*,DENSE_RANK() OVER(PARTITION BY  YEAR,QUARTER,GROUPNAME ORDER BY SCORE DESC) RN FROM (SELECT YEAR,SIMPLECHINANAME,GROUPNAME,SUM(SCORE) SCORE,QUARTER FROM (SELECT * FROM (" + TABLE_KPI + ") WHERE YEAR ='"+year+"' AND QUARTER='"+quarter+"') GROUP BY YEAR,SIMPLECHINANAME,GROUPNAME,QUARTER) T ORDER BY TRANSLATE(GROUPNAME,'һ�����������߰˾�' , '123456789'),SCORE DESC"));
//        }

		ruleList.forEach(m->{
			m.put("SIMPLECHINANAME",CommonUnit.getUnitName(String.valueOf(m.get("SIMPLECHINANAME"))));
		});

		teamReport.setKpiTable(ruleList);
		builder.bind("kpiTable",new KPITablePolicy());

		return null;
	}

	private MiniTableRenderData getMiniTableRenderData(List<Pair<String,String>> heads,List<Map<String, Object>> list) {
		return getMiniTableRenderData(heads,list,false);
	}

	private MiniTableRenderData getMiniTableRenderData(List<Pair<String,String>> heads,List<Map<String, Object>> list,Boolean hasNo){
		return getMiniTableRenderData(heads,list,hasNo,false);
	}

	private MiniTableRenderData getMiniTableRenderData(List<Pair<String,String>> heads,List<Map<String, Object>> list,Boolean hasNo,Boolean divide) {
		List<TextRenderData> headerList = new ArrayList<>();
		if(hasNo){
			heads.add(0,Pair.of("���","no."));
			int index=0;
			for (Map<String, Object> map : list) {
				map.put("no.",++index);
			}

		}
		for (Pair<String, String> head : heads) {
			headerList.add(new TextRenderData(head.getLeft()));
		}

		headerList.forEach(v -> {
			Style style = new Style();
			style.setFontSize(9);
			style.setFontFamily("��������_GBK");
			style.setBold(true);
			v.setStyle(style);
		});
		RowRenderData header = new RowRenderData(headerList, "c2c2c2");
		List<RowRenderData> rowList = new ArrayList<>();
		if (list.size() > 0) {
			list.forEach(v -> {
				String[] values = new String[heads.size()];
				for (int i = 0; i < heads.size(); i++) {
					values[i] = handleNull(String.valueOf(v.get(heads.get(i).getRight())));
				}
				RowRenderData row = RowRenderData.build(values);
				for (CellRenderData textRenderData : row.getCells()) {
					Style style = new Style();
					style.setFontSize(9);
					style.setFontFamily("��������_GBK");
					textRenderData.getCellText().setStyle(style);
				}
				rowList.add(row);
			});
		}

		if(divide){
			List<RowRenderData> temp = new ArrayList<>();
			List<CellRenderData> rowData = header.getCells();
			rowData.addAll(rowData);

			int cycle;
			if(rowList.size()%2==0){
				cycle = rowList.size();
			}else{
				cycle = rowList.size()-1;
			}
			for (int i = 0; i < cycle; i+=2) {
				RowRenderData rowRenderData = rowList.get(i);
				RowRenderData nextRowRenderData = rowList.get(i+1);
				rowRenderData.getCells().addAll(nextRowRenderData.getCells());
				temp.add(rowRenderData);
			}

//            for (int i = 0; i < cycle; i+=2) {
//                RowRenderData rowRenderData = rowList.get(i);
//                RowRenderData nextRowRenderData = rowList.get(i+1);
//                rowRenderData.getCells().addAll(nextRowRenderData.getCells());
//                temp.add(rowRenderData);
//            }
			if(rowList.size()%2!=0){
				RowRenderData rowRenderData = rowList.get(rowList.size() - 1);
				List<CellRenderData> renderData = rowRenderData.getCells();
				List<CellRenderData> tempData = new ArrayList<>();
				for (CellRenderData renderDatum : renderData) {
					TextRenderData dest= new TextRenderData();
					BeanUtils.copyProperties(renderDatum.getCellText(),dest);
					CellRenderData cellRenderData = new CellRenderData();
					cellRenderData.setCellText(dest);
					tempData.add(cellRenderData);
				}

				for (CellRenderData textRenderData : tempData) {
					textRenderData.getCellText().setText("-");
				}

				renderData.addAll(tempData);
				temp.add(rowRenderData);
			}

			rowList.clear();
			rowList.addAll(temp);
		}

		MiniTableRenderData result = new MiniTableRenderData(header, rowList,MiniTableRenderData.WIDTH_A4_FULL);
		result.setNoDatadesc("��������");
		return result;
	}

	/**
	 * ��ԭʼ�ַ����ָ��ָ�����ȵ��ַ����б�
	 *
	 * @param inputString ԭʼ�ַ���
	 * @param length      ָ������
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}

	/**
	 * ��ԭʼ�ַ����ָ��ָ�����ȵ��ַ����б�
	 *
	 * @param inputString ԭʼ�ַ���
	 * @param length      ָ������
	 * @param size        ָ���б��С
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length,
										  int size) {
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length,
					(index + 1) * length);
			list.add(childStr);
		}
		return list;
	}

	/**
	 * �ָ��ַ����������ʼλ�ô����ַ������ȣ����ؿ�
	 *
	 * @param str ԭʼ�ַ���
	 * @param f   ��ʼλ��
	 * @param t   ����λ��
	 * @return
	 */
	public static String substring(String str, int f, int t) {
		if (f > str.length()) {
			return null;
		}
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}

	/**
	 * ������ѵ
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getTrain(ReportVo reportVo, TeamReport teamReport) {
		TeamDetail teamDetail = new TeamDetail();
		teamDetail.setUnitId(reportVo.getUnitId());
		teamDetail.setIsReport("1");
		//��ȡ������ѵ�ſ�
		Map<String, Object> trainMap = teamBuildService.getTrainInfo(teamDetail);
		if (trainMap.get("content1") != null) {
			String content1 = trainMap.get("content1").toString();
			String[] str = content1.split(",");
			teamReport.setTraining1(str[0]);
			teamReport.setTraining2(str[1]);
		}
		if (trainMap.get("content2") != null) {
			String content2 = trainMap.get("content2").toString();
			String[] str = content2.split(",");
			teamReport.setTraining3(str[0]);
			teamReport.setTraining4(str[1]);
		}
		teamReport.setTrainingImage(new PictureRenderData(576, 211, ".png", pdfService.base64toMultipart(reportVo.getTrain(), reportVo.getUnitId())));


		ChildUnit childUnit = new ChildUnit();
		//��λ�㼶 1-�о֣�2-�־֡����� 3-�ɳ������־ֲ��š���������
		String unitId = teamDetail.getUnitId();
		String deptLevel = sysDeptDao.findByIdAndDelStatus(unitId, 0).getDeptLevel();
		if(zzbSubList.contains(unitId)){
			deptLevel="3";
		}
		//����unitId��ȡ�����Ӧ�Ӳ����е������֤���루���У�
//        List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(unitId));

		//��ȡ�о������� �־ֺͲ�����ص���Ϣ��ͨ����ԱȨ��idNumber��
		Map<String, Object> teamInfo ;
		List<String> fjpcsIds=new ArrayList<>();
		List<String> bmIds=new ArrayList<>();
		List<String> deptIds=new ArrayList<>();


		//�Ƿ����о�
		if("1".equals(deptLevel)){
			teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);
			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
			fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());

		}else if("2".equals(deptLevel)){
			teamInfo = (Map<String, Object>) teamBuildService.getChildUnit(unitId).getData();

			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>)teamInfo.get("pcs");
			if(teamInfos.size()>0){
				fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			}
			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm2");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());


		}else if("3".equals(deptLevel)){
			deptIds.add(unitId);

		}
		deptIds.addAll(fjpcsIds);
		deptIds.addAll(bmIds);
		List<SysDeptDo> sysDeptDoList = sysDeptDao.findAllByIdInAndDelStatus(deptIds, 0);
		List<Map<String, Object>> maps = new ArrayList<>();
		for (SysDeptDo sysDeptDo : sysDeptDoList) {
			List<String> idNos = childUnit.getSubPersonIdNos(sysDeptDo.getId());
			if(idNos.size()==0){
				continue;
			}

			Map<String, Object> m = new HashMap<>();
			m.put("NAME",sysDeptDo.getUnitName());
			m.put("DWID",sysDeptDo.getId());
			m.put("COUNT",idNos.size());
			Integer value = teamBuildMapper.trainCountByYear(idNos).stream().filter(a->teamReport.getLastYear().equals(a.getYear())).collect(Collectors.summingInt(a -> Integer.parseInt(a.getCount())));
			m.put("AVG",String.format("%.2f",1f*value/idNos.size()));
			m.put("VALUE",String.valueOf(value));
			maps.add(m);
		}


		if(NJSGAJ_ID.equals(reportVo.getUnitId())){
			maps.sort((o1, o2) -> {
				int i1 = deptList.indexOf(o1.get("NAME"));
				int i2 = deptList.indexOf(o2.get("NAME"));
				return i1-i2;
			});
			maps.forEach(m->{
				m.put("NAME",CommonUnit.getUnitName(String.valueOf(m.get("NAME"))));
			});
		}

		for (int i = 0; i < maps.size(); i++) {
			//���������־֣��־ֺͲ��ŵķֽ��
			if("2014111018472512900000002390".equals(String.valueOf(maps.get(i).get("DWID")))){
				if(i%2!=0){
					Map<String, Object> m = new HashMap<>();
					m.put("NAME","-");
					m.put("COUNT","-");
					m.put("AVG","-");
					m.put("VALUE","-");
					maps.add(i,m);
				}
				break;
			}
		}


		List<Pair<String, String>> heads = new ArrayList<>();
		heads.add(Pair.of("��λ","NAME"));
		heads.add(Pair.of("�˴�","VALUE"));
		heads.add(Pair.of("����","COUNT"));
		heads.add(Pair.of("ƽ���˴�","AVG"));
		teamReport.setTrainingTable(getMiniTableRenderData(heads,maps,false,true));

		return null;
	}
	/**
	 * �μӹ���ʱ��
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getWorkTime(ReportVo reportVo, TeamReport teamReport) {
		ChildUnit childUnit = new ChildUnit();
		//����unitId��ȡ�����Ӧ�Ӳ����е������֤���루���У�
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(reportVo.getUnitId()));

		List<Pair<Integer,Integer>> ranges = new ArrayList<>();
		ranges.add(Pair.of(null, 5));
		ranges.add(Pair.of(5, 10));
		ranges.add(Pair.of(10, 15));
		ranges.add(Pair.of(15, 20));
		ranges.add(Pair.of(20, 25));
		ranges.add(Pair.of(25, 30));
		ranges.add(Pair.of(30, null));


		StringBuilder stringBuilder = new StringBuilder();
		HashMap<String,Object> map = new LinkedHashMap<>();
		List<PersonQo> personList = personalAnalysisMapper.queryPeopleByWorkTime(idNoList);
		for (Pair<Integer,Integer> range : ranges) {
			Integer left = range.getLeft();
			Integer right = range.getRight();
			Stream<PersonQo> stream = personList.stream();
			if(left != null){
				stream = stream.filter(p->Integer.parseInt(p.getTimeToWork())>left);
			}
			if(right != null){
				stream = stream.filter(p->Integer.parseInt(p.getTimeToWork())<=right);
			}
			long count = stream.count();
			if(count==0){
				continue;
			}
			String rangeStr="";
			if(left !=null && right!=null){
				rangeStr = left+"-"+right+"�꣨����";
			}else if(right == null){
				rangeStr = left+"������";
			}else if(left == null){
				rangeStr = right+"�꣨��������";
			}
			stringBuilder.append(rangeStr+"��"+count+"�ˣ�ռ��������"+decimalFormat.format(1f*count/idNoList.size())+"��");
		}
		stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
		teamReport.setWorkTimeContent(stringBuilder.toString());


		final Integer maxYear = personList.stream().map(p -> Integer.parseInt(p.getTimeToWork())).max(Integer::compareTo).get();
		List<Map<String, Object>> collect = personList.stream().filter(p-> Integer.parseInt(p.getTimeToWork()) == maxYear).map(p->{
			HashMap<String, Object> m = new HashMap<>();
			m.put("YEAR",p.getTimeToWork());
			m.put("DEPTNAME",(p.getOrgSimpleName()==null?"":p.getOrgSimpleName())+(p.getOfficeSimpleName()==null?"":p.getOfficeSimpleName()));
			m.put("PERSON_NAME",p.getPersonName());
			m.put("POST",p.getPost());
			return m;
		}).collect(Collectors.toList());

		List<Pair<String,String>> heads = new ArrayList<>();
		heads.add(Pair.of("��λ","DEPTNAME"));
		heads.add(Pair.of("����","PERSON_NAME"));
		heads.add(Pair.of("ְ��","POST"));
		heads.add(Pair.of("�μӹ���ʱ��(��)","YEAR"));
		teamReport.setWorkTimeTable(getMiniTableRenderData(heads,collect,true));

		BufferedImage path = pdfService.base64toMultipart(reportVo.getWorkTime(), reportVo.getUnitId());
		teamReport.setWorkTime(new PictureRenderData(576, 211,".png",  path));

		return null;
	}
	/**
	 * �Ļ��̶�
	 *
	 * @param reportVo
	 * @param teamReport
	 * @return
	 */
	@Async
	public Future<Boolean> getEducation(ReportVo reportVo, TeamReport teamReport) {
		ChildUnit childUnit = new ChildUnit();
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(reportVo.getUnitId()));
		Map<String, Integer> educationByIdNos = personalAnalysisService.getEducationByIdNos(idNoList);
		//ͨ��map��value����ҳ���Ӧ��key
		int max = 0;
		String temp = "";
		int value = 0;
		for (String key : educationByIdNos.keySet()) {
			value = educationByIdNos.get(key);
			if (max < value) {
				max = value;
				temp = key;
			}
		}
		System.out.println("max-value:" + max + "  key:" + temp);

		teamReport.setEducation(temp);
		double leaderMainEduPercent = 0;
		if (max != 0 && idNoList.size() != 0) {
			leaderMainEduPercent = (double) max / (double) idNoList.size();
		}
		teamReport.setEducationPercent(decimalFormat.format(leaderMainEduPercent));

		BufferedImage path = pdfService.base64toMultipart(reportVo.getEducationImg(), reportVo.getUnitId());
		teamReport.setEducationImg(new PictureRenderData(576, 211,".png",  path));
		return null;
	}
	/**
	 * ���������
	 *
	 * @param reportVo
	 * @param teamReport
	 * @param builder
	 * @return
	 */
	@Async
	public Future<Boolean> getMymyd(ReportVo reportVo, TeamReport teamReport, ConfigureBuilder builder) {
		ChildUnit childUnit = new ChildUnit();
		//��λ�㼶 1-�о֣�2-�־֡����� 3-�ɳ������־ֲ��š���������
		String unitId = reportVo.getUnitId();
		String deptLevel = sysDeptDao.findByIdAndDelStatus(unitId, 0).getDeptLevel();
		if(zzbSubList.contains(unitId)){
			deptLevel = "3";
		}
		//��ȡ�о������� �־ֺͲ�����ص���Ϣ��ͨ����ԱȨ��idNumber��
		Map<String, Object> teamInfo ;
		List<String> deptIds = new ArrayList<>();
		List<String> fjpcsIds = new ArrayList<>();
		List<String> bmIds = new ArrayList<>();
		String role=null;

//        //�Ƿ����о�
//        if("1".equals(deptLevel)){
		teamInfo = teamComparisonService.getTeamInfoByUnitId(NJSGAJ_ID);
		List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
		fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
		teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
		bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
		deptIds.addAll(fjpcsIds);
		deptIds.addAll(bmIds);
//        }else {
//            teamInfo = (Map<String, Object>) teamBuildService.getChildUnit(unitId).getData();
//
//            List<String> roleList = (List<String>) teamInfo.get("role");
//            if(roleList.contains("pcs")){
//                role = "�־�";
//            }else{
//                role = "����";
//            }
//
//            List<TeamInfoVo> teamInfos = teamInfo.get("pcs") != null ? (List<TeamInfoVo>)teamInfo.get("pcs") : new ArrayList<>();
//            fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
//
//            teamInfos = (List<TeamInfoVo>) teamInfo.get("bm2");
//            bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
//
//            deptIds.addAll(fjpcsIds);
//            deptIds.addAll(bmIds);
//        }

		//ƴ��sql����ѯ��ѡ������Ա��Ϣ
		String tableName ="";
		if("dev".equals(active)){
			tableName =  "SUGON.IF_V4_DWDATABYMONTH_TABLE";
		}else{ tableName =  "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571";}
		String sql = "SELECT" +
				" DWID," +
				" ROUND(AVG ( " +
				"  CASE " +
				"  WHEN CKYWJZHMYL IS NOT NULL THEN " +
				"   CKYWJZHMYL " +
				"  ELSE " +
				"   CKYWMYL - 7.4 " +
				"  END " +
				" ),1) CKYWMYL, " +
				" ROUND(AVG (QUESTION3DF),1) QUESTION3DF, " +
				" ROUND(AVG ( " +
				"  CASE " +
				"  WHEN JCJJZHMYL IS NOT NULL THEN " +
				"   JCJJZHMYL " +
				"  ELSE " +
				"   JCJMYL - 9.3 " +
				"  END " +
				" ),1) JCJMYL, " +
				" ROUND(AVG (ZHAJMYL),1) ZHAJMYL, " +
				" ROUND(AVG (ZHJTSGMYL),1) ZHJTSGMYL, " +
				" ROUND(AVG (QUESTION1DF),1) QUESTION1DF, " +
				" ROUND(AVG (QUESTION2DF),1) QUESTION2DF, " +
				" ROUND(AVG (MYD12345),1) MYD12345 " +
				"FROM " +
				tableName +
				" WHERE 1=1 "+
				(deptIds.size()>0?" AND DWID IN ('"+StringUtils.join(deptIds,"','")+"')":"")
				+" GROUP BY " +
				" DWID";


		List<Map<String, Object>> maps = secondaryJdbcTemplate.queryForList(sql);

		List<String> dwids = maps.stream().map(m -> m.get("DWID").toString()).collect(Collectors.toList());
		List<SysDeptDo> depts = sysDeptDao.findAllByIdInAndDelStatus(dwids, 0);
		Map<String,String> deptMap = new HashMap<>();
		for (SysDeptDo dept : depts) {
			deptMap.put(dept.getId(),dept.getUnitName());
		}

		List<RowRenderData> mydTable = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			map.put("deptName",deptMap.get(map.get("DWID")));
		}

		maps.sort((o1, o2) -> {
			int i1 = CommonUnit.deptList.indexOf(o1.get("deptName"));
			int i2 = CommonUnit.deptList.indexOf(o2.get("deptName"));
			return i1-i2;
		});


		int index=0;
		for (Map<String, Object> map : maps) {
			if(map.get("JCJMYL")==null && map.get("ZHAJMYL")==null && map.get("ZHJTSGMYL")==null && map.get("CKYWMYL")==null && map.get("MYD12345")==null && map.get("QUESTION1DF")==null && map.get("QUESTION2DF")==null && map.get("QUESTION3DF")==null ){
				continue;
			}
			mydTable.add(RowRenderData.build(++index+"",CommonUnit.getUnitName(handleNull(map.get("deptName"))),handleNull(map.get("JCJMYL")),handleNull(map.get("ZHAJMYL")),handleNull(map.get("ZHJTSGMYL")),handleNull(map.get("CKYWMYL")),handleNull(map.get("MYD12345")),handleNull(map.get("QUESTION1DF")),handleNull(map.get("QUESTION2DF")),handleNull(map.get("QUESTION3DF"))));
		}


		teamReport.setMydTable(mydTable);
		builder.bind("mydTable", new DetailTablePolicy(2));



		Map<String,Object>  max = new HashMap<>();
		Map<String,Object>  min = new HashMap<>();
		for (Map<String, Object> map : maps) {
			String deptName = String.valueOf(map.remove("deptName"));
			String dwid = String.valueOf(map.remove("DWID"));
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if(entry.getValue()!=null){
					if(max.get("value")==null){
						max.put("value",entry.getValue());
						List<Pair<String,String>> dwIds = new ArrayList<>();
						dwIds.add(Pair.of(entry.getKey(),deptName));
						max.put("dwIds",dwIds);
					}else{
						if(Float.parseFloat(entry.getValue().toString())>Float.parseFloat(max.get("value").toString())){
							max.put("value",entry.getValue());
							List<Pair<String,String>> dwIds = new ArrayList<>();
							dwIds.add(Pair.of(entry.getKey(),deptName));
							max.put("dwIds",dwIds);
						}else if(Float.parseFloat(entry.getValue().toString())==Float.parseFloat(max.get("value").toString())){
							List<Pair<String,String>> dwIds = (List<Pair<String,String>>) max.get("dwIds");
							dwIds.add(Pair.of(entry.getKey(),deptName));
						}
					}

					if(min.get("value")==null){
						min.put("value",entry.getValue());
						List<Pair<String,String>> dwIds = new ArrayList<>();
						dwIds.add(Pair.of(entry.getKey(),deptName));
						min.put("dwIds",dwIds);
					}else{
						if(Float.parseFloat(entry.getValue().toString())<Float.parseFloat(min.get("value").toString())){
							min.put("value",entry.getValue());
							List<Pair<String,String>> dwIds = new ArrayList<>();
							dwIds.add(Pair.of(entry.getKey(),deptName));
							min.put("dwIds",dwIds);
						}else if(Float.parseFloat(entry.getValue().toString())==Float.parseFloat(min.get("value").toString())){
							List<Pair<String,String>> dwIds = (List<Pair<String,String>>) min.get("dwIds");
							dwIds.add(Pair.of(entry.getKey(),deptName));
						}
					}
				}
			}

		}
		StringBuilder stringBuilder = new StringBuilder();
		if(max.get("value")!=null){
			stringBuilder.append("ƽ���÷���ߵ�Ϊ");
			List<Pair<String,String>> dwIds = (List<Pair<String,String>>) max.get("dwIds");
			for (Pair<String, String> pair : dwIds) {
				String name = getScoreName(pair.getLeft());
				String unitName = pair.getRight();
//                String unitName = sysDeptDao.findOne(dwId).getUnitName();
				stringBuilder.append(unitName+"��"+name+"��");
			}
			stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
			stringBuilder.append(",�÷�Ϊ"+max.get("value")+"��");
		}
		if(min.get("value")!=null){
			stringBuilder.append("ƽ���÷���͵�Ϊ");
			List<Pair<String,String>> dwIds = (List<Pair<String,String>>) min.get("dwIds");
			for (Pair<String, String> pair : dwIds) {
				String name = getScoreName(pair.getLeft());
				String unitName = pair.getRight();
//                String unitName = sysDeptDao.findOne(dwId).getUnitName();
				stringBuilder.append(unitName+"��"+name+"��");
			}
			stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
			stringBuilder.append(",�÷�Ϊ"+min.get("value"));
		}
		teamReport.setMydContent(stringBuilder.toString());


		return null;
	}
	private String handleNull(Object str){
		if(str==null){
			return "-";
		}else{
			return String.valueOf(str);
		}
	}
	private String getScoreName(String columnName) {
		switch (columnName){
			case "JCJMYL":return "110�Ӵ���������";
			case "ZHAJMYL":return "�ۺϰ���������";
			case "ZHJTSGMYL":return "�ۺϽ�ͨ�¹�������";
			case "CKYWMYL":return "���ڷ���������";
			case "MYD12345":return "12345������������";
			case "QUESTION1DF":return "����ΰ�������";
			case "QUESTION2DF":return "�������������";
			case "QUESTION3DF":return "��������Ϥ��";
			default:return null;
		}
	}

	/**
	 * �����Լ��
	 */
	private int getgcd(int num1, int num2) {
		// �Ȼ�þ���ֵ����֤����Ҳ������
		num1 = Math.abs(num1);
		num2 = Math.abs(num2);
		// �����������ٶ���һ�����ϴ�����ڶ����ϴ��ڵڶ��ֵ���ʱ��ߵ�����
		int remainder = num1 % num2;
		// ���Ϊ 0����ֱ�ӵó�
		if (remainder == 0) {
			return num2;
		}
		// �ݹ����
		return getgcd(num2, remainder);
	}

	public ResultModel getLeaderData(TeamDetail teamDetail) {
		ExecutorService es = Executors.newFixedThreadPool(10);

		Map<String,Object> result = new HashMap<>();


		ChildUnit childUnit = new ChildUnit();
		//��λ�㼶 1-�о֣�2-�־֡����� 3-�ɳ������־ֲ��š���������
		String unitId = teamDetail.getUnitId();
		String deptLevel = sysDeptDao.findByIdAndDelStatus(unitId, 0).getDeptLevel();
		if(zzbSubList.contains(unitId)){
			deptLevel="3";
		}
		result.put("deptLevel",deptLevel);

		final String finalDeptLevel  = deptLevel;

		//����
		es.execute(new Runnable() {
			@Override
			public void run() {
				ReportVo reportVo = new ReportVo();
				reportVo.setUnitId(teamDetail.getUnitId());
				TeamReport teamReport = new TeamReport();
				getLeaderData(reportVo,teamReport,false);
				Map<String,Object> content = new HashMap<>();
				String superName = teamReport.getSuperName();
				String subName = teamReport.getSubName();
				content.put("superDutyContent", superName +"�쵼���ӳ�Ա��"+teamReport.getSuperLeaderCount()+"�ˣ�"+teamReport.getSuperLeaderDutyContent());
				if(!"3".equals(finalDeptLevel))
					content.put("subDutyContent", subName +"�쵼���ӳ�Ա��"+teamReport.getSubLeaderCount()+"�ˣ�"+teamReport.getSubLeaderDutyContent());

				content.put("superAgeContent",superName+"�쵼���ӳ�Ա��"+teamReport.getLeaderAgeSuperContent()+"��������"+teamReport.getSuperLeaderMaxAge()+"�꣬��С"+teamReport.getSuperLeaderMinAge()+"�꣬ƽ������"+teamReport.getSuperLeaderAvgAge()+"��");
				if(!"3".equals(finalDeptLevel))
					content.put("subAgeContent",subName+"�쵼���ӳ�Ա��"+teamReport.getLeaderAgeSubContent()+"��������"+teamReport.getSubLeaderMaxAge()+"�꣬��С"+teamReport.getSubLeaderMinAge()+"�꣬ƽ������"+teamReport.getSubLeaderAvgAge()+"��");


				content.put("superSexContent",superName+"�쵼���ӳ�Ա������"+teamReport.getLeaderMaleSuperCount()+"�ˣ�Ů��"+teamReport.getLeaderFemaleSuperCount()+"��"+teamReport.getLeaderMfSuperPercent());
				if(!"3".equals(finalDeptLevel))
					content.put("subSexContent",subName+"�쵼���ӳ�Ա������"+teamReport.getLeaderMaleSubCount()+"�ˣ�Ů��"+teamReport.getLeaderFemaleSubCount()+"��"+teamReport.getLeaderMfSubPercent());


				content.put("superEducationContent",superName+"�쵼���ӳ�Ա����Ҫ��"+teamReport.getSuperLeaderMainEduName()+"ѧ��Ϊ����ռ����Ա��"+teamReport.getSuperLeaderMainEduPercent());
				if(!"3".equals(finalDeptLevel))
					content.put("subEducationContent",subName+"�쵼���ӳ�Ա����Ҫ��"+teamReport.getSubLeaderMainEduName()+"ѧ��Ϊ����ռ����Ա��"+teamReport.getSubLeaderMainEduPercent());


				content.put("superNationContent",superName+"�쵼���ӳ�Ա����Ҫ��"+teamReport.getLeaderMainNationNameSuper()+"Ϊ����ռ����Ա��"+teamReport.getLeaderMainNationPercentSuper());
				if(!"3".equals(finalDeptLevel))
					content.put("subNationContent",subName+"�쵼���ӳ�Ա����Ҫ��"+teamReport.getLeaderMainNationNameSub()+"Ϊ����ռ����Ա��"+teamReport.getLeaderMainNationPercentSub());

				float mainPostSum = Float.parseFloat(teamReport.getMainPostNum()) + Float.parseFloat(teamReport.getMainPostNumOppositie());
				content.put("mainPostNum",teamReport.getOverAllName()+"�쵼���ӳ�Ա�У�����ְ�쵼��ְ���������ϵ���"+teamReport.getMainPostNum()+"�ˣ�ռ��"+decimalFormat.format(Float.parseFloat(teamReport.getMainPostNum())/ mainPostSum)+"���������µ���"+teamReport.getMainPostNumOppositie()+"�ˣ�ռ��"+decimalFormat.format(Float.parseFloat(teamReport.getMainPostNumOppositie())/ mainPostSum));
				float assistantPostSum = Float.parseFloat(teamReport.getAssistantPostNum()) + Float.parseFloat(teamReport.getAssistantPostNumOppositie());
				content.put("assistantPostNum",teamReport.getOverAllName()+"�쵼���ӳ�Ա�У��ڸ�ְ�쵼��ְ���������ϵ���"+teamReport.getAssistantPostNum()+"�ˣ�ռ��"+decimalFormat.format(Float.parseFloat(teamReport.getAssistantPostNum())/ assistantPostSum)+"���������µ���"+teamReport.getAssistantPostNum()+"�ˣ�ռ��"+decimalFormat.format(Float.parseFloat(teamReport.getAssistantPostNumOppositie())/ assistantPostSum));

				content.put("superEntryPeriodContent",superName+"�쵼���ӳ�Ա���뾯������"+teamReport.getSuperEntryPeriodContent().replace("ռ��������","ռ"));
				if(!"3".equals(finalDeptLevel))
					content.put("subEntryPeriodContent",subName+"�쵼���ӳ�Ա���뾯������"+teamReport.getSubEntryPeriodContent().replace("ռ��������","ռ"));

				content.put("superLeaderRuleContent",teamReport.getSuperLeaderRuleContent());
				if(!"3".equals(finalDeptLevel))
					content.put("subLeaderRuleContent",teamReport.getSubLeaderRuleContent());

				result.put("content",content);
				System.out.println();
			}
		});

		//��ȡ�о������� �־ֺͲ�����ص���Ϣ��ͨ����ԱȨ��idNumber��
		Map<String, Object> teamInfo ;
		List<String> fjpcsIds;
		List<String> bmIds;

		/**
		 * �쵼�������֤����
		 */
		//deptLevel=1ʱ��superIdNosΪ�о��쵼���ӣ�deptLevel=2ʱ��superIdNosΪ���Ż�־��쵼����
		final List<String> superIdNos = new ArrayList<>();
		final List<String> subIdNos = new ArrayList<>();
		final List<String> fjpcsIdNos = new ArrayList<>();
		final List<String> bmIdIdNos = new ArrayList<>();
		final List<String> overAllIdList = new ArrayList<>();
		String role=null;
		String superName=null;
		String subName=null;

		//�Ƿ����о�
		if("1".equals(deptLevel)){
			teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);
			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
			fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));

			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));

			superIdNos.addAll(teamBuildMapper.findOneLeaderIdNum().stream().distinct().collect(Collectors.toList()));

			List<String> temp = teamBuildMapper.findTwoLeaderIdNum(fjpcsIdNos);
			fjpcsIdNos.clear();
			fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));

			temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
			bmIdIdNos.clear();
			bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));

			temp.clear();
			temp.addAll(superIdNos);
			temp.addAll(fjpcsIdNos);
			temp.addAll(bmIdIdNos);
			overAllIdList.addAll(temp);

			superName = "�о�";
			subName = "���š��־�";
		}else if("2".equals(deptLevel)){
			teamInfo = (Map<String, Object>) teamBuildService.getChildUnit(unitId).getData();

			List<String> roleList = (List<String>) teamInfo.get("role");
			if(roleList.contains("pcs")){
				role = "�־�";
			}else{
				role = "����";
			}

			List<TeamInfoVo> teamInfos = (List<TeamInfoVo>)teamInfo.get("pcs");
			if(teamInfos.size()>0){
				fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
				fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));
			}

			teamInfos = (List<TeamInfoVo>) teamInfo.get("bm2");
			bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
			bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));

			List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
			superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));

			List<String> temp=new ArrayList<>();
			if(fjpcsIdNos.size()>0){
				temp = teamBuildMapper.findThreeLeaderIdNum(fjpcsIdNos);
				fjpcsIdNos.clear();
				fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
			}

			if(bmIdIdNos.size()>0){
				temp = teamBuildMapper.findThreeLeaderIdNum(bmIdIdNos);
				bmIdIdNos.clear();
				bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
			}

			if(ZZB_ID.equals(unitId)){
				superIdNos.clear();
				superIdNos.addAll(personStereoService.getDeptHead(unitId).stream().map(a->a.getIdNo()).collect(Collectors.toList()));

				bmIdIdNos.clear();
				bmIdIdNos.addAll(childUnit.getSubPersonIdNos(zzbSubList));
				temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
				bmIdIdNos.clear();
				bmIdIdNos.addAll(temp);
			}

			temp.clear();
			temp.addAll(superIdNos);
			temp.addAll(fjpcsIdNos);
			temp.addAll(bmIdIdNos);
			overAllIdList.addAll(temp);

			if(role.equals("�־�")){
				superName = "�־�";
				subName = "������";
			}else{
				superName = "����";
				subName = "������";
			}
		}else if("3".equals(deptLevel)){
			teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);
			String roleName =( (List<String>) teamInfo.get("role")).get(0);
			if("bm2".equals(roleName)){
				role = "������";
			}else if("pcs".equals(roleName)){
				role = "�ɳ���";
			}

			List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
			if(zzbSubList.contains(unitId)){
				superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));
			}else{
				superIdNos.addAll(teamBuildMapper.findThreeLeaderIdNum(collect));
			}

			overAllIdList.addAll(superIdNos.stream().distinct().collect(Collectors.toList()));

			superName = role;
		}
		subIdNos.addAll(fjpcsIdNos);
		subIdNos.addAll(bmIdIdNos);

		result.put("superName",superName);
		result.put("subName",subName);


		final String finalSuperName=superName;
		final String finalSubName=subName;
		//ְλ����
		es.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> duty = new LinkedHashMap<>();

				//�־֡��ɳ����쵼���֤����
				Map<String, Integer> superDutyMap = personalAnalysisService.getPosSubCount(personalAnalysisService.getPersonByIdNos(superIdNos));
				Iterator<Map.Entry<String, Integer>> iterator = superDutyMap.entrySet().iterator();
				while (iterator.hasNext()){
					Map.Entry<String, Integer> next = iterator.next();
					if(!duties.contains(next.getKey())){
						iterator.remove();
					}
				}
				duty.put("super",superDutyMap);


				if(subIdNos.size()>0){

					//���ŵ�  �쵼 ���֤����
					Map<String, Integer> subDutyMap = personalAnalysisService.getPosSubCount(personalAnalysisService.getPersonByIdNos(subIdNos));
					iterator = subDutyMap.entrySet().iterator();
					while (iterator.hasNext()){
						Map.Entry<String, Integer> next = iterator.next();
						if(!duties.contains(next.getKey())){
							iterator.remove();
						}
					}
					duty.put("sub",subDutyMap);
				}

				result.put("duty",duty);
			}
		});
		//�����Ա�����
		es.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> pieAge = new HashMap<>();
				Map<String,Object> columnAge = new HashMap<>();
				Map<String,Object> pieSex = new HashMap<>();
				Map<String,Object> columnSex = new HashMap<>();
				List<AgeDetailVo> tPersonDoList = new ArrayList<>();
				tPersonDoList.addAll(teamBuildMapper.findAgeDetail(overAllIdList));


				//35��������Ա
				List<AgeDetailVo> youngList = tPersonDoList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
				//35������-50����Ա
				List<AgeDetailVo> middleList = tPersonDoList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
				//50�꣨����������Ա
				List<AgeDetailVo> oldList = tPersonDoList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

				if(youngList.size()>0){
					pieAge.put("35������",youngList.size());
				}
				if(middleList.size()>0){
					pieAge.put("35������-50��",middleList.size());
				}
				if(oldList.size()>0){
					pieAge.put("50�꣨��������",oldList.size());
				}

				result.put("pieAge",pieAge);


				int size = tPersonDoList.stream().filter(v -> "��".equals(v.getSex())).collect(Collectors.toList()).size();
				if (size>0) {
					pieSex.put("��", size);
				}
				size = tPersonDoList.stream().filter(v -> "Ů".equals(v.getSex())).collect(Collectors.toList()).size();
				if (size>0) {
					pieSex.put("Ů",size);
				}
				result.put("pieSex",pieSex);

				//�־��쵼����
				List<AgeDetailVo> tPersonDoSuperList = new ArrayList<>();
				tPersonDoSuperList.addAll(teamBuildMapper.findAgeDetail(superIdNos));


				Map<String,Object> superSex = new HashMap<>();
				size = tPersonDoSuperList.stream().filter(v -> "��".equals(v.getSex())).collect(Collectors.toList()).size();
				superSex.put("��", size);
				size = tPersonDoSuperList.stream().filter(v->"Ů".equals(v.getSex())).collect(Collectors.toList()).size();
				superSex.put("Ů",size);
				columnSex.put(finalSuperName,superSex);


				//===========���ڵ�ǰ��λ ����ṹ================
				//35��������Ա
				List<AgeDetailVo> youngSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
				//35������-50����Ա
				List<AgeDetailVo> middleSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
				//50�꣨����������Ա
				List<AgeDetailVo> oldSuperList = tPersonDoSuperList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

				Map<String,Object> map = new HashMap<>();
				if (youngSuperList.size()>0) {
					map.put("35������",youngSuperList.size());
				}
				if (middleSuperList.size()>0) {
					map.put("35������-50��",middleSuperList.size());
				}
				if (oldSuperList.size()>0) {
					map.put("50�꣨��������",oldSuperList.size());
				}
				columnAge.put(finalSuperName,map);

				//==========�����¼���λ ����ṹ==============
				if(subIdNos.size()>0){
					//�����쵼����
					List<AgeDetailVo> tPersonDoSubList = new ArrayList<>();
					tPersonDoSubList.addAll(teamBuildMapper.findAgeDetail(subIdNos));

					Map<String,Object> subSex = new HashMap<>();
					size = tPersonDoSubList.stream().filter(v -> "��".equals(v.getSex())).collect(Collectors.toList()).size();
					subSex.put("��", size);
					size = tPersonDoSubList.stream().filter(v->"Ů".equals(v.getSex())).collect(Collectors.toList()).size();
					subSex.put("Ů",size);
					columnSex.put(finalSubName,subSex);


					//35��������Ա
					List<AgeDetailVo> youngSubList = tPersonDoSubList.stream().filter(v -> v.getAge() < 35).collect(Collectors.toList());
					//35������-50����Ա
					List<AgeDetailVo> middleSubList = tPersonDoSubList.stream().filter(v -> v.getAge() >= 35).filter(v -> v.getAge() < 50).collect(Collectors.toList());
					//50�꣨����������Ա
					List<AgeDetailVo> oldSubList = tPersonDoSubList.stream().filter(v -> v.getAge() >= 50).collect(Collectors.toList());

					map = new HashMap<>();
					if (youngSubList.size()>0) {
						map.put("35������",youngSubList.size());
					}
					if (middleSubList.size()>0) {
						map.put("35������-50��",middleSubList.size());
					}
					if (oldSubList.size()>0) {
						map.put("50�꣨��������",oldSubList.size());
					}
					columnAge.put(finalSubName,map);
				}

				result.put("columnAge",columnAge);
				result.put("columnSex",columnSex);

			}
		});

		//�Ļ��̶�����
		es.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> education = new HashMap<>();
				Map<String, Integer> superEducationByIdNos = personalAnalysisService.getEducationByIdNos(superIdNos);

				education.put("super",superEducationByIdNos);

				if(subIdNos.size()>0){
					Map<String, Integer> educationByIdNosSub = personalAnalysisService.getEducationByIdNos(subIdNos);
					education.put("sub",educationByIdNosSub);
				}

				result.put("education",education);
			}
		});

		//��������
		es.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> nation = new HashMap<>();

				//================����ֲ� ��ǰ��λ=============================
				List<NationMapPojo> superLeaderNationList = personalAnalysisMapper.queryNationByIdNum(superIdNos);

				Map<String,Integer> leaderNationSuperMap = new LinkedHashMap<>();
				for (NationMapPojo nationMapPojo : superLeaderNationList) {
					leaderNationSuperMap.put(nationMapPojo.getNationName(),nationMapPojo.getNationNum());
				}
				nation.put("super",leaderNationSuperMap);

				//================����ֲ� ��ǰ��λ���赥λ=============================
				if(subIdNos.size()>0){
					List<NationMapPojo> leaderNationSubMapList = personalAnalysisMapper.queryNationByIdNum(subIdNos);
					Map<String,Integer> leaderNationSubMap = new LinkedHashMap<>();
					for (NationMapPojo nationMapPojo : leaderNationSubMapList) {
						leaderNationSubMap.put(nationMapPojo.getNationName(),nationMapPojo.getNationNum());
					}
					nation.put("sub",leaderNationSubMap);
				}



				result.put("nation",nation);
			}
		});


		//��ְʱ������
		es.execute(new Runnable() {
			@Override
			public void run() {
				List<Map<String, Object>> maps = personalAnalysisMapper.queryAppointmentTime(overAllIdList);

				maps.forEach(m->{
					String jobGrade = null;
					String jobType = null;
					switch (String.valueOf(m.get("POLICE_JOB_GRADE"))) {
						case "0925":
						case "102":
						case "10":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0830":
						case "0920":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0730":
						case "0820":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0720":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0717":
						case "0718":
							jobGrade = "����ְ���쵼";
							jobType = "����";
							break;
						case "0715":
						case "0716":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0620":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						case "0520":
							jobGrade = "����ְ";
							jobType = "��ְ";
							break;
						default:
							break;
					}
					m.put("POLICE_JOB_GRADE",jobGrade);
					m.put("POLICE_JOB_GRADE_TYPE",jobType);
				});


				/**
				 * ��ְ
				 */
				Map<String, Object> mainPost = new LinkedHashMap<>();
				long count11 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=3).count();
				long count12 = maps.stream().filter(m ->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>3).count();
				long count13 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=3).count();
				long count14 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>3).count();
				long count15 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=3).count();
				long count16 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>3).count();
				Map<String,Object> map;
				if (count11>0||count12>0) {
					map = new LinkedHashMap<>();
					map.put("3��(��)����",count11);
					map.put("3������",count12);
					mainPost.put("����ְ",map);
				}
				if (count13>0||count14>0) {
					map = new LinkedHashMap<>();
					map.put("3��(��)����",count13);
					map.put("3������",count14);
					mainPost.put("����ְ",map);
				}
				if (count15>0||count16>0) {
					map = new LinkedHashMap<>();
					map.put("3��(��)����",count15);
					map.put("3������",count16);
					mainPost.put("����ְ",map);
				}

				/**
				 * ��ְ
				 */
				Map<String, Object> assistantPost = new LinkedHashMap<>();
				long count21 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=2).count();
				long count22 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>2).count();
				long count23 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=2).count();
				long count24 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>2).count();
				long count25 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=2).count();
				long count26 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>2).count();
				long count27 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))<=2).count();
				long count28 = maps.stream().filter(m->"����ְ".equals(m.get("POLICE_JOB_GRADE")) && Float.parseFloat(String.valueOf(m.get("YEAR")))>2).count();

				if (count21>0||count22>0) {
					map = new LinkedHashMap<>();
					map.put("2��(��)����",count21);
					map.put("2������",count22);
					assistantPost.put("����ְ",map);
				}
				if (count23>0||count24>0) {
					map = new LinkedHashMap<>();
					map.put("2��(��)����",count23);
					map.put("2������",count24);
					assistantPost.put("����ְ",map);
				}
				if (count25>0||count26>0) {
					map = new LinkedHashMap<>();
					map.put("2��(��)����",count25);
					map.put("2������",count26);
					assistantPost.put("����ְ",map);
				}
				if (count27>0||count28>0) {
					map = new LinkedHashMap<>();
					map.put("2��(��)����",count27);
					map.put("2������",count28);
					assistantPost.put("����ְ",map);
				}

				Map<String, Object> post = new HashMap<>();
				post.put("��ְ",mainPost);
				post.put("��ְ",assistantPost);
				result.put("post",post);

			}
		});

		//�뾯��������
		es.execute(new Runnable() {
			@Override
			public void run() {

				Map<String,Object> entryPeriod = new HashMap<>();

				List<Pair<Integer,Integer>> ranges = new ArrayList<>();
				ranges.add(Pair.of(null, 5));
				ranges.add(Pair.of(5, 10));
				ranges.add(Pair.of(10, 15));
				ranges.add(Pair.of(15, 20));
				ranges.add(Pair.of(20, 25));
				ranges.add(Pair.of(25, 30));
				ranges.add(Pair.of(30, null));

				HashMap<String,Object> superMap = new LinkedHashMap<>();
				List<PersonQo> superList = personalAnalysisMapper.queryPeopleByJoinTime("","",superIdNos);
				for (Pair<Integer,Integer> range : ranges) {
					Integer left = range.getLeft();
					Integer right = range.getRight();
					Stream<PersonQo> stream = superList.stream();
					if(left != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())>left);
					}
					if(right != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())<=right);
					}
					long count = stream.count();
					if(count==0){
						continue;
					}
					String rangeStr="";
					if(left !=null && right!=null){
						rangeStr = left+"-"+right+"�꣨����";
					}else if(right == null){
						rangeStr = left+"������";
					}else if(left == null){
						rangeStr = right+"�꣨��������";
					}
					superMap.put(rangeStr,count);
				}
				entryPeriod.put("super",superMap);

				if(subIdNos.size()>0){

					HashMap<String,Object> subMap = new LinkedHashMap<>();
					List<PersonQo> subList = personalAnalysisMapper.queryPeopleByJoinTime("","",subIdNos);
					for (Pair<Integer,Integer> range : ranges) {
						Integer left = range.getLeft();
						Integer right = range.getRight();
						Stream<PersonQo> stream = subList.stream();
						if(left != null){
							stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())>left);
						}
						if(right != null){
							stream = stream.filter(p->Integer.parseInt(p.getTimeToWorkPsb())<=right);
						}
						long count = stream.count();
						if(count==0){
							continue;
						}
						String rangeStr="";
						if(left !=null && right!=null){
							rangeStr = left+"-"+right+"�꣨����";
						}else if(right == null){
							rangeStr = left+"������";
						}else if(left == null){
							rangeStr = right+"�꣨��������";
						}
						subMap.put(rangeStr,count);
					}
					entryPeriod.put("sub",subMap);
				}


				result.put("entryPeriod",entryPeriod);
			}
		});

		/**
		 * Υ�Ͱ���
		 */
		es.execute(new Runnable() {
			@Override
			public void run() {
				int currentYear = LocalDate.now().getYear();
				List<EchartsVo> allCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%", overAllIdList);

				fillCase(currentYear, allCases);

				allCases.sort(new Comparator<EchartsVo>() {
					@Override
					public int compare(EchartsVo o1, EchartsVo o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

				result.put("leaderRuleTrend",allCases);


				Map<String,Object> leaderRule = new HashMap<>();
				List<EchartsVo> superCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%", superIdNos);
				fillCase(currentYear, superCases);
				leaderRule.put("super",superCases);
				if(subIdNos.size()>0){
					List<EchartsVo> subCases = teamBuildMapper.findBreakRuleCaseCountByYear("%%", subIdNos);
					fillCase(currentYear, subCases);
					leaderRule.put("sub",subCases);
				}
				result.put("leaderRule",leaderRule);

			}
		});


		es.shutdown();
		while (!es.isTerminated()){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}



		return ResultModel.success(result);
	}

	private void fillCase(int currentYear, List<EchartsVo> allCases) {
		if(allCases.size()>0){
			String year = allCases.get(0).getName();
			List<String> names = allCases.stream().map(a -> a.getName()).collect(Collectors.toList());
			for (int i = Integer.parseInt(year); i <= currentYear; i++) {
				if(!names.contains(String.valueOf(i))){
					EchartsVo vo = new EchartsVo();
					vo.setName(String.valueOf(i));
					vo.setValue("0");
					allCases.add(vo);
				}
			}
		}else{
			EchartsVo vo = new EchartsVo();
			vo.setName(String.valueOf(currentYear));
			vo.setValue("0");
			allCases.add(vo);
		}
	}

	public ResultModel getHiddenData(TeamDetail teamDetail) {
		Map<String,Object> result = new HashMap<>();

		ExecutorService es = Executors.newFixedThreadPool(10);
		ChildUnit childUnit = new ChildUnit();
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		/**
		 * �Ա�
		 */
		es.execute(new Runnable() {
			@Override
			public void run() {

				//����unitId��ȡ�����Ӧ�Ӳ����е������֤���루���У�

				Integer count = idNoList.size();
				//��ѯ��Ů
				Integer femaleCount = idNoList.size()>0?teamBuildMapper.findFemaleCount(idNoList):0;
				int maleCount = count - femaleCount;

				Map<String,Object> columnSex = new HashMap<>();
				if (maleCount>0) {
					columnSex.put("��",maleCount);
				}
				if (femaleCount>0) {
					columnSex.put("Ů",femaleCount);
				}
				result.put("columnSex",columnSex);
			}
		});

		/**
		 * ����ʱ��
		 */
		es.execute(new Runnable() {
			@Override
			public void run() {


				List<Pair<Integer,Integer>> ranges = new ArrayList<>();
				ranges.add(Pair.of(null, 5));
				ranges.add(Pair.of(5, 10));
				ranges.add(Pair.of(10, 15));
				ranges.add(Pair.of(15, 20));
				ranges.add(Pair.of(20, 25));
				ranges.add(Pair.of(25, 30));
				ranges.add(Pair.of(30, null));


				HashMap<String,Object> map = new LinkedHashMap<>();
				List<PersonQo> personList = personalAnalysisMapper.queryPeopleByWorkTime(idNoList);
				for (Pair<Integer,Integer> range : ranges) {
					Integer left = range.getLeft();
					Integer right = range.getRight();
					Stream<PersonQo> stream = personList.stream();
					if(left != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWork())>left);
					}
					if(right != null){
						stream = stream.filter(p->Integer.parseInt(p.getTimeToWork())<=right);
					}
					long count = stream.count();
					if(count==0){
						continue;
					}
					String rangeStr="";
					if(left !=null && right!=null){
						rangeStr = left+"-"+right+"�꣨����";
					}else if(right == null){
						rangeStr = left+"������";
					}else if(left == null){
						rangeStr = right+"�꣨��������";
					}
					map.put(rangeStr,count);
				}
				result.put("workTime",map);
			}
		});


		/**
		 * �Ļ��̶�
		 */
		es.execute(new Runnable() {
			@Override
			public void run() {

				Map<String, Integer> educationByIdNos = personalAnalysisService.getEducationByIdNos(idNoList);

				result.put("edution",educationByIdNos);
			}
		});


		/**
		 * Υ�Ͱ���
		 */
		es.execute(new Runnable() {
			@Override
			public void run() {
				int currentYear = LocalDate.now().getYear();
				String name;
				if(NJSGAJ_ID.equals(teamDetail.getUnitId())){
					name="";
				}else{
					name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
				}
				List<EchartsVo> allCases = teamBuildMapper.findBreakRuleCaseCountByYear("%"+name+"%", null);
				fillCase(currentYear, allCases);

				allCases.sort(new Comparator<EchartsVo>() {
					@Override
					public int compare(EchartsVo o1, EchartsVo o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

				result.put("ruleTrend",allCases);
			}
		});


		es.shutdown();
		while (!es.isTerminated()){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ResultModel.success(result);
	}

	/**
	 * ����Ա
	 * @param reportVo
	 * @param teamReport
	 * @param builder
	 * @return
	 */
	@Async
	public Future<Boolean> getPersonnelTransfer(ReportVo reportVo, TeamReport teamReport, ConfigureBuilder builder) {

		if(!reportVo.getUnitId().equals(NJSGAJ_ID)){
			return null;
		}
		ChildUnit childUnit = new ChildUnit();

		//��λ��ǰ����
		Map<String,Integer> baseMap = new HashMap<>();
		Map<String,Object> teamInfo = teamComparisonService.getTeamInfoByUnitId(NJSGAJ_ID);
		List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
		List<String> deptIds=new ArrayList<>();
		deptIds.addAll(teamInfos.stream().map(a->a.getId()).collect(Collectors.toList()));
		teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
		deptIds.addAll(teamInfos.stream().map(a->a.getId()).collect(Collectors.toList()));
		List<SysDeptDo> deptDos = sysDeptDao.findAllByIdInAndDelStatus(deptIds, 0);
		for (SysDeptDo deptDo : deptDos) {
			baseMap.put(deptDo.getUnitName(),childUnit.getSubPersonIdNos(deptDo.getId()).size());
		}
		int size = childUnit.getSubPersonIdNos(NJSGAJ_ID).size();
		baseMap.put("�Ͼ��й�����",size - baseMap.entrySet().stream().collect(Collectors.summingInt(a->a.getValue())));


		/**
		 * ��Ա
		 */
		List<Map<String, Object>> increaseMaps = primaryJdbcTemplate.queryForList("SELECT CASE " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й��������β���������Ա����' THEN " +
				" '���β�������' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����ֹ�����ͨ��ȫ����֧�ӣ�������ͨ�ΰ��־֣�' THEN " +
				" '������ͨ�ΰ��־�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й������ؾ�֧��' THEN " +
				" '�ؾ�֧�ӣ�Ѳ��֧�ӣ�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����ֵ����־�' THEN " +
				" '���������־�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����־���Ч�ܼ��֧��' THEN " +
				" '����Ч�ܼ��֧�ӣ�����죩' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��м�ί��ί��פ�й����ּͼ�����' THEN " +
				" '��ί������ң�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й��������¾���֧��' THEN " +
				" '���¾���֧�ӣ�������鴦��'  WHEN DEPTNAME!='����ʡ�Ͼ��й�����' THEN REPLACE(DEPTNAME,'����ʡ�Ͼ��й�����','') ELSE '�Ͼ��й�����' END DEPTNAME,TYPE,COUNT(*) CNT FROM (SELECT " +
				" T4.ORG_NAME DEPTNAME,CASE WHEN CVFLAG IN ('A1','A2','A8') THEN  '���빫��Ա' WHEN CVFLAG IN ('A3','A4','A5') THEN  '�ڲ�����' WHEN CVFLAG IN ('A6','A601','A602','A603','A604','A605','A606','A607','A608','A609') THEN  '��¼����Ա' WHEN FLAG IN ('A7') THEN '����תҵ' ELSE CVNAME END TYPE " +
				"FROM " +
				" ( " +
				"  SELECT " +
				"   * " +
				"  FROM " +
				"   NJGAZNL.RS_A16 " +
				"  WHERE 1=1 " +
				(teamReport.getCurrentYear()==0?"":("  AND TO_CHAR (A1620, 'YYYY') = '"+teamReport.getCurrentYear()+"' ") )+
				" ) T1 " +
				"LEFT JOIN (SELECT * FROM NJGAZNL.SG_QUERY_CATEGORY WHERE CVTYPE='XO') T2 ON T1.A1610=T2.CVFLAG " +
				"LEFT JOIN NJGAZNL.RS_A01 T3 ON T1.PERSONID = T3.PERSONID " +
				"LEFT JOIN NJGAZNL.T_ORG T4 ON T3.B0001=T4.ID " +
				")  GROUP BY DEPTNAME,TYPE " +
				" ");
		teamReport.setIncreaseCount(increaseMaps.stream().collect(Collectors.summingInt(a->Integer.parseInt(String.valueOf(a.get("CNT"))))));
		Map<String, List<Map<String, Object>>> increaseGroupByDept = increaseMaps.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.get("DEPTNAME")), Collectors.toList()));

		/**
		 * ��Ա
		 */
		List<Map<String, Object>> reduceMaps = primaryJdbcTemplate.queryForList("SELECT CASE " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й��������β���������Ա����' THEN " +
				" '���β�������' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����ֹ�����ͨ��ȫ����֧�ӣ�������ͨ�ΰ��־֣�' THEN " +
				" '������ͨ�ΰ��־�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й������ؾ�֧��' THEN " +
				" '�ؾ�֧�ӣ�Ѳ��֧�ӣ�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����ֵ����־�' THEN " +
				" '���������־�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й����־���Ч�ܼ��֧��' THEN " +
				" '����Ч�ܼ��֧�ӣ�����죩' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��м�ί��ί��פ�й����ּͼ�����' THEN " +
				" '��ί������ң�' " +
				"WHEN DEPTNAME = '����ʡ�Ͼ��й��������¾���֧��' THEN " +
				" '���¾���֧�ӣ�������鴦��'  WHEN DEPTNAME!='����ʡ�Ͼ��й�����' THEN REPLACE(DEPTNAME,'����ʡ�Ͼ��й�����','') ELSE '�Ͼ��й�����' END DEPTNAME,TYPE,COUNT(*) CNT FROM (SELECT " +
				" T4.ORG_NAME DEPTNAME,CASE WHEN CVFLAG IN ('B5','B51','B52') THEN  '����' WHEN CVFLAG IN ('B8','B9','BA') THEN  '�ڲ�����' WHEN CVFLAG IN ('B7','BB') THEN  '��������Ա' ELSE CVNAME END TYPE " +
				"FROM " +
				" ( " +
				"  SELECT " +
				"   * " +
				"  FROM " +
				"   NJGAZNL.RS_A15 " +
				"  WHERE 1=1 " +
				(teamReport.getCurrentYear()==0?"":("  AND TO_CHAR (C1502, 'YYYY') = '"+teamReport.getCurrentYear()+"' ") )+
				" ) T1 " +
				"LEFT JOIN (SELECT * FROM NJGAZNL.SG_QUERY_CATEGORY WHERE CVTYPE='XP') T2 ON T1.C1501=T2.CVFLAG " +
				"LEFT JOIN NJGAZNL.RS_A01 T3 ON T1.PERSONID = T3.PERSONID " +
				"LEFT JOIN NJGAZNL.T_ORG T4 ON T3.B0001=T4.ID " +
				")  GROUP BY DEPTNAME,TYPE");
		teamReport.setReduceCount(reduceMaps.stream().collect(Collectors.summingInt(a->Integer.parseInt(String.valueOf(a.get("CNT"))))));
		Map<String, List<Map<String, Object>>> reduceGroupByDept = reduceMaps.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.get("DEPTNAME")), Collectors.toList()));

		List<RowRenderData> ptTable = new ArrayList<>();
		List<Map<String, Object>> maps = new ArrayList<>();
		maps.addAll(reduceMaps);
		maps.addAll(increaseMaps);

		//�ϲ����β�
		List<String> zzbDeptIds = new ArrayList<>();
		zzbDeptIds.add(ZZB_ID);
		zzbDeptIds.addAll(zzbSubList);
		List<String> zzbNames = sysDeptDao.findAllByIdInAndDelStatus(zzbDeptIds, 0).stream().map(a -> a.getUnitName()).collect(Collectors.toList());
		Iterator<Map<String, Object>> iterator = maps.iterator();
		Map<String,Integer> zzbMap = new HashMap<>();
		while (iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			if(zzbNames.indexOf(next.get("DEPTNAME"))>0){
				String type = String.valueOf(next.get("TYPE"));
				Integer cnt = Integer.parseInt(String.valueOf(next.get("CNT")));
				if(zzbMap.get(type)==null){
					zzbMap.put(type,cnt);
				}else{
					zzbMap.put(type,cnt+zzbMap.get(type));
				}
				iterator.remove();
			}
		}
		for (Map.Entry<String, Integer> entry : zzbMap.entrySet()) {
			Map<String, Object> map = new HashMap<>();
			map.put("DEPTNAME","���β�");
			map.put("TYPE",entry.getKey());
			map.put("CNT",entry.getValue());
			maps.add(map);
		}


		Map<String, Map<Object, Object>> groupByDept = maps.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.get("DEPTNAME")), Collectors.toMap(a -> a.get("TYPE"), a -> a.get("CNT"))));

		for (Map.Entry<String, Integer> entry : baseMap.entrySet()) {
			String deptName = entry.getKey();
			Map<Object, Object> valueMap = groupByDept.get(deptName);

			String[] values = new String[ptTableHeads.size()];
			values[0] = deptName;
			values[2] = baseMap.get(deptName).toString();
			Integer increaseCnt = increaseGroupByDept.get(deptName)==null?0:increaseGroupByDept.get(deptName).stream().collect(Collectors.summingInt(a -> Integer.parseInt(String.valueOf(a.get("CNT")))));
			Integer reduceCnt = reduceGroupByDept.get(deptName)==null?0: reduceGroupByDept.get(deptName).stream().collect(Collectors.summingInt(a -> Integer.parseInt(String.valueOf(a.get("CNT")))));
			values[1] =  String.valueOf(Integer.parseInt(values[2]) - increaseCnt + reduceCnt);
			values[values.length-1] = String.valueOf(increaseCnt-reduceCnt);
			if(valueMap==null){
				for (int i = 3; i < ptTableHeads.size()-1; i++) {
					values[i] ="0";
				}
			}else{
				for (int i = 3; i < ptTableHeads.size()-1; i++) {
					values[i] = String.valueOf(valueMap.get(ptTableHeads.get(i))==null?0:valueMap.get(ptTableHeads.get(i)));
				}
			}

			RowRenderData row = RowRenderData.build(values);
			ptTable.add(row);
		}

		ptTable.sort(new Comparator<RowRenderData>() {
			@Override
			public int compare(RowRenderData o1, RowRenderData o2) {
				int i1 = CommonUnit.deptList.indexOf(o1.getCells().get(0).getCellText().getText());
				int i2 = CommonUnit.deptList.indexOf(o2.getCells().get(0).getCellText().getText());
				return i1-i2;
			}
		});


		int lastYearCount = ptTable.stream().collect(Collectors.summingInt(p->Integer.parseInt(p.getCells().get(1).getCellText().getText())));
		int currentYearCount = ptTable.stream().collect(Collectors.summingInt(p->Integer.parseInt(p.getCells().get(2).getCellText().getText())));
		float percent = 1f * (currentYearCount - lastYearCount) / lastYearCount;
		if(percent>0){
			teamReport.setPtPercent("������ĩ��������"+decimalFormat.format(percent));
		}else if(percent==0){
			teamReport.setPtPercent("������ĩ������ƽ");
		}else{
			teamReport.setPtPercent("������ĩ��������"+decimalFormat.format(0-percent));
		}

		teamReport.setPtTable1(getTotalRow(ptTable,bmIds));
		teamReport.setPtTable2(getTotalRow(ptTable,fjIds));
		teamReport.setPtTable3(getTotalRow(ptTable,oldFiveDeptIds));

		builder.bind("ptTable1", new DetailTablePolicy(2));
		builder.bind("ptTable2", new DetailTablePolicy(2));
		builder.bind("ptTable3", new DetailTablePolicy(2));



		return null;
	}

	private List<RowRenderData> getTotalRow(List<RowRenderData> temp,List<String> deptIds) {
		List<String> collect = sysDeptDao.findAllByIdInAndDelStatus(deptIds, 0).stream().map(dept -> dept.getUnitName()).collect(Collectors.toList());
		List<RowRenderData> ptTable = temp.stream().filter(p->collect.contains(p.getCells().get(0).getCellText().getText())).collect(Collectors.toList());
		ptTable.forEach(r->{
			r.getCells().get(0).getCellText().setText(CommonUnit.getUnitName(r.getCells().get(0).getCellText().getText()));
		});

		String[] totalRowValues = new String[ptTableHeads.size()];
		totalRowValues[0] = "�ϼ�";
		for (int i = 1; i < ptTableHeads.size(); i++) {
			final int index = i;
			Integer total = ptTable.stream().collect(Collectors.summingInt(a -> Integer.parseInt(a.getCells().get(index).getCellText().getText())));
			totalRowValues[i] = String.valueOf(total);
		}
		RowRenderData totalRow = RowRenderData.build(totalRowValues);
		ptTable.add(0,totalRow);
		ptTable.forEach(row->{
			List<CellRenderData> cells = row.getCells();
			for (int i = 1; i < ptTableHeads.size(); i++) {
				if ("0".equals(cells.get(i).getCellText().getText())) {
					cells.get(i).getCellText().setText("-");
				}
			}
		});
		return ptTable;
	}

	private List<String> ptTableHeads = Arrays.asList(new String[]{"��λ","ȥ��","��ǰ","��¼����Ա","����תҵ","���빫��Ա","�ڲ�����","��������Ա","����","��ְ","����","����","����","�ڲ�����","���"});

}
