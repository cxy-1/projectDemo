package com.sugon.analysis.service;

import com.sugon.analysis.domain.data.object.oracle.SysDeptDo;
import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.query.KPIQuery;
import com.sugon.analysis.domain.data.query.TPersonCountAndAge;
import com.sugon.analysis.domain.data.query.TeamDetail;
import com.sugon.analysis.domain.param.TeamSelect;
import com.sugon.analysis.domain.param.WindowType;
import com.sugon.analysis.domain.view.EchartsVo;
import com.sugon.analysis.domain.view.teambuild.*;
import com.sugon.analysis.enums.PersonStereoConstant;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.TeamBuildMapper;
import com.sugon.analysis.repository.oracle.SysDeptDao;
import com.sugon.analysis.repository.oracle.SysUserDao;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.repository.sugon.oracle.KpiZGResultDao;
import com.sugon.analysis.util.ChildUnit;
import com.sugon.analysis.util.CommonUnit;
import com.sugon.analysis.util.ExportExcel;
import com.sugon.analysis.util.RoleOfCompany;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sugon.analysis.enums.ResponsesStatus.NODATA;
import static com.sugon.analysis.service.TeamReportService.TABLE_KPI;
import static com.sugon.analysis.service.TeamReportService.TABLE_KPI_2019;
import static com.sugon.analysis.util.ChildUnit.NJSGAJ_ID;
import static java.util.stream.Collectors.*;

/**
 * @program: analysis->TeamBuildService
 * @description:
 * @author: gaowh
 * @create: 2019-10-28 15:34
 **/
@Service
public class TeamBuildService extends TeamBaseService {
	@Resource
	private TeamBuildMapper teamBuildMapper;
	@Resource
	private TPersonDao tPersonDao;
	@Resource
	private SysDeptDao sysDeptDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private MongoTemplate mongoTemplate;
	@Resource
	private HttpServletResponse response;
	@Resource
	private SatisfiedDataSevice satisfiedDataSevice;
	@Resource
	private PersonStereoService personStereoService;
	@Resource
	private KpiZGResultDao kpiZGResultDao;

	/**
	 * ���β���������
	 */
	private static List<String> zzbSubList = Stream.of(
			"2014111018472484800000000567", "2014111018472484800000000566", "2014111018472522300000002932", "2014111018472481700000000388",
			"2014111018472487900000000748", "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"
	).collect(toList());

	/**
	 * ˮ�Ϸ־֣������־֣������־�
	 */
	private static List<String> specialDeptList = Stream.of(
			"2014111018472516100000002607", "2014111018472497300000001287", "2014111018472497300000001308"
	).collect(toList());

	/**
	 * ���β�id
	 */
	private static String ZZB_ID = "2014111018472522300000002937";

	/**
	 * ������������1
	 */
	private static List<String> personRewordOne = Stream.of(
			"������ʿ", "ȫ������ϵͳһ��Ӣ��ģ��", "ȫ������ϵͳ����Ӣ��ģ��",
			"ȫ����ģ", "ȫ���ؼ��������񾯲�", "ȫ���������񾯲�", "ȫ�����˺�����"
	).collect(toList());

	/**
	 * ������������2
	 */
	private static List<String> personRewordTwo = Stream.of(
			"ʡ��ģ", "ʡ���˺�����",
			"ʡ�������꽱��", "���պ�����"
	).collect(toList());

	/**
	 * ������������3
	 */
	private static List<String> personRewordThree = Stream.of(
			"���Ͷ�ģ��", "ʡ���㹲���Ÿɲ�", "ʡ���㹲����Ա", "�ж��Ͼ��������",
			"�����˺�����", "��ʮ��ܳ�����", "���������꽱��", "�������ϸɲ�"
	).collect(toList());

	private static final String FIRST_LEVEL = "һ�ȹ�";

	private static final String SECOND_LEVEL = "���ȹ�";

	private static final String THIRD_LEVEL = "���ȹ�";

	/*********************************************************
	 *                    ���齨���Ч��ҳ                    *
	 *********************************************************/

	/**
	 * �־ֻ�����Ϣ
	 */
	public ResultModel getTeamInfo(String idNumber) {
		//���ظ�ǰ�˵�����
		ResultModel resultModel = new ResultModel();
		Map<String, Object> maps = new HashMap<>(16);
		//Ȩ��id
		String dataId = RoleOfCompany.getRoleCompanyId(idNumber);
		//Ȩ��List
		List<String> roleList = new ArrayList<>();
		//����Ȩ��id���������Ϣ
		SysDeptDo unitInfo = sysDeptDao.findByIdAndDelStatus(dataId, 0);
		List<String> fjList = new ArrayList<>();
		List<String> bmList = new ArrayList<>();
		teamBuildMapper.findAllTeam("1", "0").forEach(v -> {
			fjList.add(v.getId());
		});
		teamBuildMapper.findAllTeam("0", "1").forEach(v -> {
			bmList.add(v.getId());
		});
		if ("2014110416460086100000002942".equals(dataId)) {
			//�о�Ȩ��
			List<TeamInfoVo> list1 = teamBuildMapper.findAllTeam("1", "0");
			if (list1.size() > 0){
				list1.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					v.setIsZoom(true);
				});
			}
			List<TeamInfoVo> list2 = teamBuildMapper.findAllTeam("0", "1");
			if (list2.size() > 0){
				list2.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					v.setIsZoom(true);
				});
			}
			maps.put("fj", list1);
			maps.put("bm1", list2);
			roleList.add("fj");
			roleList.add("bm1");
			maps.put("role", roleList);
			maps.put("map", "�Ͼ��й�����");
		} else if (bmList.contains(dataId)) {
			//�оֲ���Ȩ��
			List<TeamInfoVo> list1 = teamBuildMapper.findAllTeam("0", "1");
			if (list1.size() > 0){
				list1.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					if (v.getId().equals(dataId)) {
						v.setIsZoom(true);
					}
				});
			}
			maps.put("bm1", list1);
			roleList.add("bm1");
			maps.put("role", roleList);
		} else if (fjList.contains(dataId)) {
			//�־�Ȩ��
			List<TeamInfoVo> list1 = teamBuildMapper.findAllTeam("1", "0");
			if (list1.size() > 0){
				list1.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					if (v.getId().equals(dataId)) {
						v.setIsZoom(true);
					}
				});
			}
			maps.put("fj", list1);
			roleList.add("fj");
			maps.put("role", roleList);
			maps.put("map", "�Ͼ��й�����");
		} else if (fjList.contains(unitInfo.getParentId())
				&& unitInfo.getUnitName().contains(PersonStereoConstant.policeStation.getData())
				&& !(unitInfo.getUnitName().contains(PersonStereoConstant.police_zrq.getData()))) {
			//�־��ɳ���Ȩ��
			List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
			sysDeptDao.findByParentIdAndDelStatus(unitInfo.getParentId(), 0).forEach(v -> {
				TeamInfoVo teamInfoVo = new TeamInfoVo();
				if (v.getUnitName().contains(PersonStereoConstant.policeStation.getData())) {
					teamInfoVo.setId(v.getId());
					String fullName = v.getUnitName();
					String str = fullName.substring(0, fullName.indexOf("��"));
					String simpleName = fullName.substring(str.length() + 1);
					teamInfoVo.setTeamName(simpleName);
					teamInfoVoList.add(teamInfoVo);
				}
			});
			if (teamInfoVoList.size() > 0){
				teamInfoVoList.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					if (v.getId().equals(dataId)) {
						v.setIsZoom(true);
					}
				});
			}
			maps.put("pcs", teamInfoVoList);
			roleList.add("pcs");
			maps.put("role", roleList);
			maps.put("map", sysDeptDao.findByIdAndDelStatus(sysDeptDao.findByIdAndDelStatus(dataId, 0).getParentId(), 0).getUnitName());
		} else if (bmList.contains(unitInfo.getParentId())) {
			//�����¼�����Ȩ��
			List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
			sysDeptDao.findByParentIdAndDelStatus(unitInfo.getParentId(), 0).forEach(v -> {
				TeamInfoVo teamInfoVo = new TeamInfoVo();
				teamInfoVo.setId(v.getId());
				teamInfoVo.setTeamName(v.getUnitName());
				teamInfoVoList.add(teamInfoVo);
			});
			if (teamInfoVoList.size() > 0){
				teamInfoVoList.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					if (v.getId().equals(dataId)) {
						v.setIsZoom(true);
					}
				});
			}
			maps.put("bm2", teamInfoVoList);
			roleList.add("bm2");
			maps.put("role", roleList);
		} else if (fjList.contains(unitInfo.getParentId())) {
			//�־��¼�����Ȩ��
			List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
			sysDeptDao.findByParentIdAndDelStatus(unitInfo.getParentId(), 0).forEach(v -> {
				TeamInfoVo teamInfoVo = new TeamInfoVo();
				if (!v.getUnitName().contains(PersonStereoConstant.policeStation.getData())) {
					teamInfoVo.setId(v.getId());
					teamInfoVo.setTeamName(v.getUnitName());
					teamInfoVoList.add(teamInfoVo);
				}
			});
			if (teamInfoVoList.size() > 0){
				teamInfoVoList.forEach(v -> {
					v.setSimpleName(CommonUnit.getUnitName(v.getTeamName()));
					if (v.getId().equals(dataId)) {
						v.setIsZoom(true);
					}
				});
			}
			maps.put("bm2", teamInfoVoList);
			roleList.add("bm2");
			maps.put("role", roleList);
		}else if(zzbSubList.contains(unitInfo.getId())) {
			List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
			List<SysDeptDo> allByIdInAndDelStatus = sysDeptDao.findAllByIdInAndDelStatus(zzbSubList, 0);
			allByIdInAndDelStatus.forEach(v->{
				TeamInfoVo info = new TeamInfoVo();
				info.setId(v.getId());
				info.setSimpleName(CommonUnit.getUnitName(v.getUnitName()));
				info.setTeamName(v.getUnitName());
				teamInfoVoList.add(info);
			});
			maps.put("bm2", teamInfoVoList);
			roleList.add("bm2");
			maps.put("role", roleList);
		}else {
			resultModel.setResult(NODATA);
			return resultModel;
		}
		resultModel.setResult(maps);
		return resultModel;
	}

	/**
	 * ��ȡ�Ӳ�����Ϣ
	 */
	public ResultModel getChildUnit(String unitId) {
		//���ظ�ǰ�˵�����
		ResultModel resultModel = new ResultModel();
		//���ź��ɳ����ֿ�ͳ��
		Map<String, Object> maps = new HashMap<>(16);
		//�Ӳ���list
		List<SysDeptDo> childUnitList = new ArrayList<>();
		if (ZZB_ID.equals(unitId)) {
			childUnitList.addAll(sysDeptDao.findAllByIdInAndDelStatus(zzbSubList, 0));
		} else {
			childUnitList.addAll(sysDeptDao.findByParentIdAndDelStatus(unitId, 0));
		}
		List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
		//�ɳ���list
		List<TeamInfoVo> policeStationList = new ArrayList<>();
		//����list
		List<TeamInfoVo> deptList = new ArrayList<>();
		if (childUnitList.size() > 0) {
			childUnitList.forEach(v -> {
				TeamInfoVo teamInfoVo = new TeamInfoVo();
				if (StringUtils.isNotEmpty(v.getDeptLevel()) && Integer.parseInt(v.getDeptLevel()) <= 3) {
					teamInfoVo.setTeamName(v.getUnitName());
					teamInfoVo.setId(v.getId());
					teamInfoVo.setIsZoom(true);
					teamInfoVoList.add(teamInfoVo);
				}
			});
		}
		//���Ӳ��ŷ�Ϊ�ɳ����Ͳ���
		if (teamInfoVoList.size() > 0) {
			teamInfoVoList.forEach(v -> {
				String parentId = sysDeptDao.findByIdAndDelStatus(v.getId(), 0).getParentId();
				if (v.getTeamName().contains(PersonStereoConstant.policeStation.getData()) && !specialDeptList.contains(parentId)) {
					String fullName = v.getTeamName();
					String str = fullName.substring(0, fullName.indexOf("��"));
					String simpleName = fullName.substring(str.length() + 1);
					v.setTeamName(simpleName);
					policeStationList.add(v);
				} else {
					deptList.add(v);
				}
			});
			maps.put("pcs", policeStationList);
			maps.put("bm2", deptList);
			List<String> roleList = new ArrayList<>();
			if (policeStationList.size() > 0) {
				roleList.add("pcs");
				roleList.add("bm2");
				maps.put("role", roleList);
				maps.put("map", sysDeptDao.findByIdAndDelStatus(unitId, 0).getUnitName());
			} else {
				roleList.add("bm2");
				maps.put("role", roleList);
			}
			resultModel.setResult(maps);
		} else {
			resultModel.setResult(NODATA);
		}
		return resultModel;
	}

	/**
	 * ����������Ϣ
	 */
	public List<Map<String, Object>> getPersonCount(TeamSelect teamSelect) {
		//������λ��Ա�����֤��map
		Map<String, Integer> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�ÿ����λ��������Ա���֤��
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				int size = teamSelect.getUnitMap().get(v).size();
				idNoMap.put(v, size);
			});
		}
		//����ǰ�˵�map
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<Map.Entry<String, Integer>> arrayList = new ArrayList<>(idNoMap.entrySet());
		//����λ������������
		arrayList.sort((map1, map2) -> (map2.getValue() - map1.getValue()));
		//���
		for (Map.Entry<String, Integer> entry : arrayList) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", sysDeptDao.findByIdAndDelStatus(entry.getKey(), 0).getUnitName());
			map.put("value", entry.getValue());
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * ����ƽ������
	 */
	public List<Map<String, Object>> getAgeInfo(TeamSelect teamSelect) {
		//��λ��ƽ�������map
		Map<String, Double> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�ÿ����λ��������Ա���֤��
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				List<TPersonDo> subPerson = teamSelect.getUnitMap().get(v);
				Double avgAge = subPerson.stream().map(p -> Double.parseDouble(p.getAge())).collect(Collectors.averagingDouble(a -> a));
				maps.put(sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName(), avgAge);
			});
		}
		List<Map.Entry<String, Double>> arrayList = new ArrayList<>(maps.entrySet());
		//��ƽ��������������
		arrayList.sort((Comparator.comparing(Map.Entry::getValue)));
		//����ǰ�˵�map
		List<Map<String, Object>> resultList = new ArrayList<>();
		//���
		for (Map.Entry<String, Double> entry : arrayList) {
			if(entry.getValue()==0){
				continue;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("name", entry.getKey());
			map.put("value", String.format("%.2f", entry.getValue()));
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * ����Ů��ռ��
	 */
	public List<Map<String, Object>> getFemaleCount(TeamSelect teamSelect) {
		//��λ��Ů��������list
		List<Map<String, Object>> resultList = new ArrayList<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�ÿ����λ��������Ա���֤��
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				List<TPersonDo> subPerson = teamSelect.getUnitMap().get(v);

				Map<String, Object> map = new HashMap<>();
				long femaleCount = subPerson.stream().filter(p->"2".equals(p.getSex())).count();
				map.put("name", sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName());
				if(subPerson.size()==0){
					return;
				}
				map.put("value", (double) femaleCount / (double) subPerson.size());
				resultList.add(map);
			});
		}
		resultList.sort((o1, o2) -> {
			Double name1 = (Double) o1.get("value");
			Double name2 = (Double) o2.get("value");
			return name2.compareTo(name1);
		});
		resultList.forEach(v -> {
			DecimalFormat df = new DecimalFormat("0.00%");
			v.put("value", df.format(v.get("value")));
		});
		return resultList;
	}

	/**
	 * ���鼯��������Ϣ
	 */
	public List<Map<String, Object>> getOrgHonorInfo(TeamSelect teamSelect) {
		//��λ��������������list
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				//��λ��������������map
				Map<String, Object> maps = new HashMap<>(16);
				String name = sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName();
				int value = teamBuildMapper.findOrgHonorCount(name);
				maps.put("name", name);
				maps.put("value", value);
				resultList.add(maps);
			});
		}
		resultList.sort((o1, o2) -> {
			Integer name1 = (Integer) o1.get("value");
			Integer name2 = (Integer) o2.get("value");
			return name2.compareTo(name1);
		});
		return resultList;
	}

//    /**
//     * �����ۺ��������Ϣ
//     */
//    public List<Map<String, String>> getSatisfiedInfo(TeamSelect teamSelect) {
//        //��λ���ۺ�����ȵ�list
//        List<Map<String, String>> satisfiedList = new ArrayList<>();
//        teamSelect.getUnitId().forEach(v -> {
//            Map<String, String> map = new HashMap<>();
//            String value = satisfiedDataSevice.getUnitSatisfied(v);
//            map.put("name", sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName());
//            map.put("value", StringUtils.isNotEmpty(value)?
//                    String.format("%.2f", Double.parseDouble(value)):null);
//            satisfiedList.add(map);
//        });
//        //ȥ��������ȶ��飬��������ȸߵ�����
//        return satisfiedList.stream().filter(v -> StringUtils.isNotEmpty(v.get("value"))).sorted(((o1, o2) -> {
//            Double date1 = Double.parseDouble(o1.get("value"));
//            Double date2 = Double.parseDouble(o2.get("value"));
//            return date2.compareTo(date1);
//        })).collect(toList());
//    }

	/**
	 * �����ۺ��������Ϣ
	 */
	public List<Map<String, String>> getSatisfiedInfo(TeamSelect teamSelect) {
		return satisfiedDataSevice.getUnitSatisfiedBySql(teamSelect.getUnitId());
	}

	/**
	 * ����ͼ참����Ϣ
	 */
	public List<Map<String, Object>> getBreakRuleCase(TeamSelect teamSelect) {
		//��λ��������������list
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				//��λ������ͼ참������map
				Map<String, Object> maps = new HashMap<>(16);
				String name = sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName();
				int value = teamBuildMapper.findBreakRuleCaseCount("%" + name + "%");
				maps.put("name", name);
				maps.put("value", value);
				resultList.add(maps);
			});
		}
		resultList.sort((o1, o2) -> {
			Integer name1 = (Integer) o1.get("value");
			Integer name2 = (Integer) o2.get("value");
			return name1.compareTo(name2);
		});
		return resultList;
	}

	/**
	 * ��������Ϣ
	 */
	public Map<String, Object> getWindowsInfo(WindowType windowType) {
		//����������map
		Map<String, Object> resultMap = new HashMap<>();
		String name = sysDeptDao.findByIdAndDelStatus(windowType.getUnitId(), 0).getUnitName();
		//�ۺ������
		String satisfied = satisfiedDataSevice.getUnitSatisfied(windowType.getUnitId());
		//�ͼ참������
		int caseCount = teamBuildMapper.findBreakRuleCaseCount("%" + name + "%");
		//������������
		int orgHonorCount = teamBuildMapper.findOrgHonorCount(name);
		//����ά�ȵ���ɫ�ȼ�
		TeamSelect teamSelect = new TeamSelect();
		List<String> unitIdList = new ArrayList<>();
		unitIdList.add(windowType.getUnitId());
		teamSelect.setType(windowType.getType());
		teamSelect.setUnitId(unitIdList);
		int zhmydColorLevel = getOneColorLevel(windowType, teamSelect, name, "zhmyd");
		int jjajColorLevel = getOneColorLevel(windowType, teamSelect, name, "jjaj");
		int jtryColorLevel = getOneColorLevel(windowType, teamSelect, name, "jtry");
		resultMap.put("name", name);
		resultMap.put("jxkh", new HashMap<String, Object>() {{
			put("value", 94);
			put("colorLevel", 4);
		}});
		resultMap.put("zhmyd", new HashMap<String, Object>() {{
			put("value", StringUtils.isNotEmpty(satisfied) ? String.format("%.2f", Double.parseDouble(satisfied)) : null);
			put("colorLevel", zhmydColorLevel);
		}});
		resultMap.put("jjaj", new HashMap<String, Object>() {{
			put("value", caseCount);
			put("colorLevel", jjajColorLevel);
		}});
		resultMap.put("jtry", new HashMap<String, Object>() {{
			put("value", orgHonorCount);
			put("colorLevel", jtryColorLevel);
		}});
		return resultMap;
	}

	/**
	 * �������������Ϣ
	 */
	public List<Map<String, Object>> getPersonHonorInfo(TeamSelect teamSelect) {
		//��λ��������������list
		List<Map<String, Object>> resultList = new ArrayList<>();
		//������λ��Ա�����֤��map
		Map<String, List<String>> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�ÿ����λ��������Ա���֤��
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				List<String> idNo = new ArrayList<>(childUnit.getSubPersonIdNos(v));
				idNoMap.put(v, idNo);
			});
		}
		for (Map.Entry<String, List<String>> entry : idNoMap.entrySet()) {
			Map<String, Object> map = new HashMap<>();
			int value = 0;
			if (entry.getValue().size() > 0) {
				value = teamBuildMapper.findPersonHonorCount(entry.getValue());
			}
			map.put("name", sysDeptDao.findByIdAndDelStatus(entry.getKey(), 0).getUnitName());
			map.put("value", value);
			resultList.add(map);
		}
		resultList.sort((o1, o2) -> {
			Integer name1 = (Integer) o1.get("value");
			Integer name2 = (Integer) o2.get("value");
			return name2.compareTo(name1);
		});
		return resultList;
	}

	/**
	 * ��������ռ����Ϣ
	 */
	public List<Map<String, Object>> getHonorInfo(TeamSelect teamSelect) {
		//��λ��������list
		List<Map<String, Object>> resultList = new ArrayList<>();
		//������λ��Ա�����֤��map
		Map<String, List<String>> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�ÿ����λ��������Ա���֤��
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				List<String> idNo = new ArrayList<>(childUnit.getSubPersonIdNos(v));
				if (idNo.size() > 0) {
					idNoMap.put(v, idNo);
				}
			});
		}
		for (Map.Entry<String, List<String>> entry : idNoMap.entrySet()) {
			Map<String, Object> map = new HashMap<>();
			//��λ����
			String name = sysDeptDao.findByIdAndDelStatus(entry.getKey(), 0).getUnitName();
			//��λ����
			int personCount = (int) entry.getValue().stream().filter(Objects::nonNull).count();
			//����������
			int personValue = teamBuildMapper.findPersonHonorCount(entry.getValue());
			//����������
			int orgValue = teamBuildMapper.findOrgHonorCount(name);
			//��������
			int value = personValue + orgValue;
			map.put("name", name);
			map.put("value", (double) value / (double) personCount);
			resultList.add(map);
		}
		resultList.sort((o1, o2) -> {
			Double name1 = (Double) o1.get("value");
			Double name2 = (Double) o2.get("value");
			return name2.compareTo(name1);
		});
		if (resultList.size() > 0){
			resultList.forEach(v -> {
				DecimalFormat df = new DecimalFormat("0.00%");
				v.put("value", df.format(v.get("value")));
			});
		}
		return resultList;
	}

	/**
	 * ��ȡɫ�ȷ�Χ
	 */
	public Map<String, Map<String, Object>> getColorEdge(TeamSelect teamSelect) {
		//ɫ�ȷ�Χ
		Map<String, Map<String, Object>> maps = new HashMap<>(16);
		//��Ч���˷�Χ
		Map<String, Object> jxkhMaps = new HashMap<>(16);
		jxkhMaps.put("left", 91d);
		jxkhMaps.put("right", 98d);
		//�ۺ�����ȷ�Χ
		Map<String, Object> zhmydMaps = new HashMap<>(16);
		List<Map<String, String>> satisfiedInfoList = getSatisfiedInfo(teamSelect);
		String zhmydLeft = satisfiedInfoList.size()>0?satisfiedInfoList.get(getSatisfiedInfo(teamSelect).size() - 1).get("value"):"0";
		String zhmydRight = satisfiedInfoList.size()>0?satisfiedInfoList.get(0).get("value"):"0";
//        String zhmydMin = getSatisfiedMaxOrMin(teamSelect, false);
//        String zhmydMax = getSatisfiedMaxOrMin(teamSelect, true);
		zhmydMaps.put("left", StringUtils.isNotEmpty(zhmydLeft) ?
				String.format("%.2f", Double.parseDouble(zhmydLeft)) : null);
		zhmydMaps.put("right", StringUtils.isNotEmpty(zhmydRight) ?
				String.format("%.2f", Double.parseDouble(zhmydRight)) : null);
		//�ͼ참����Χ
		Map<String, Object> jjajMaps = new HashMap<>(16);
		double jjajLeft = Double.parseDouble(getBreakRuleCase(teamSelect).get(getOrgHonorInfo(teamSelect).size() - 1).get("value").toString());
		double jjajRight = Double.parseDouble(getBreakRuleCase(teamSelect).get(0).get("value").toString());
		jjajMaps.put("left", jjajLeft);
		jjajMaps.put("right", jjajRight);
		//����������Χ
		Map<String, Object> jtryMaps = new HashMap<>(16);
		double jtryLeft = Double.parseDouble(getOrgHonorInfo(teamSelect).get(getOrgHonorInfo(teamSelect).size() - 1).get("value").toString());
		double jtryRight = Double.parseDouble(getOrgHonorInfo(teamSelect).get(0).get("value").toString());
		jtryMaps.put("left", jtryLeft);
		jtryMaps.put("right", jtryRight);

		maps.put("jxkh", jxkhMaps);
		maps.put("zhmyd", zhmydMaps);
		maps.put("jjaj", jjajMaps);
		maps.put("jtry", jtryMaps);
		return maps;
	}

	/**
	 * ��ȡ�ۺ���������ֵ�����ֵ
	 */
	public String getSatisfiedMaxOrMin(TeamSelect teamSelect, Boolean isMax) {
		List<Map<String, Object>> satisfiedList = new ArrayList<>();
		teamSelect.getUnitId().forEach(v -> {
			Map<String, Object> map = new HashMap<>();
			String value = satisfiedDataSevice.getUnitSatisfied(v);
			map.put("name", sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName());
			map.put("value", StringUtils.isNotEmpty(value) ?
					String.format("%.2f", Double.parseDouble(value)) : null);
			satisfiedList.add(map);
		});
		List<Map<String, Object>> resultList = satisfiedList.stream().filter(v -> StringUtils.isNotEmpty((String) v.get("value"))).sorted(((o1, o2) -> {
			Double date1 = Double.parseDouble((String) o1.get("value"));
			Double date2 = Double.parseDouble((String) o2.get("value"));
			return date2.compareTo(date1);
		})).collect(toList());
		if (resultList.size() > 0) {
			if (isMax) {
				return (String) resultList.get(0).get("value");
			} else {
				return (String) resultList.get(resultList.size() - 1).get("value");
			}
		} else {
			return null;
		}
	}

	/**
	 * ������ɫ�ȼ�
	 */
	public int getOneColorLevel(WindowType windowType, TeamSelect teamSelect, String name, String type) {
		//��Ч����,�ۺ������,�ͼ참��,������������ֵ
		double base;
		if (ONE.equals(teamSelect.getType())) {
			base = getBase(windowType, type, SIX);
		} else {
			base = getBase(windowType, type, TEN);
		}
		double count;
		double left;
		switch (type) {
			case "jxkh":
				count = 0;
				left = Double.parseDouble(windowType.getJxkh().get("left").toString());
				break;
			case "zhmyd":
				if (StringUtils.isEmpty(satisfiedDataSevice.getUnitSatisfied(windowType.getUnitId()))) {
					return -1;
				} else {
					count = Double.parseDouble(satisfiedDataSevice.getUnitSatisfied(windowType.getUnitId()));
				}
				left = Double.parseDouble(windowType.getZhmyd().get("left").toString());
				break;
			case "jjaj":
				count = teamBuildMapper.findBreakRuleCaseCount("%" + name + "%");
				left = Double.parseDouble(windowType.getJjaj().get("left").toString());
				break;
			case "jtry":
				count = teamBuildMapper.findOrgHonorCount(name);
				left = Double.parseDouble(windowType.getJtry().get("left").toString());
				break;
			default:
				count = 0;
				left = 0;
				break;
		}
		if (ONE.equals(teamSelect.getType())) {
			if ((int) ((count - left) / base) <= 0) {
				return 1;
			} else if ((int) ((count - left) / base) >= SIX) {
				return 5;
			} else {
				return (int) ((count - left) / base);
			}
		} else {
			if ((int) ((count - left) / base) <= 0) {
				return 1;
			} else if ((int) ((count - left) / base) >= TEN) {
				return 9;
			} else {
				return (int) ((count - left) / base);
			}
		}
	}

	private double getBase(WindowType windowType, String type, int count) {
		switch (type) {
			case "jxkh":
				return (windowType.getJxkh().get("right") - windowType.getJxkh().get("left")) / count;
			case "zhmyd":
				return (windowType.getZhmyd().get("right") - windowType.getZhmyd().get("left")) / count;
			case "jjaj":
//                return (windowType.getJjaj().get("left") - windowType.getJjaj().get("right")) / count;
				return (windowType.getJjaj().get("right") - windowType.getJjaj().get("left")) / count;
			case "jtry":
				return (windowType.getJtry().get("right") - windowType.getJtry().get("left")) / count;
			default:
				return 0;
		}
	}

	/**
	 * ��������ɫ�ȼ�
	 */
	public List<ColorLevelVo> getColorLevel(TeamSelect teamSelect) {
		//����λ����ɫ�ȼ�
		List<ColorLevelVo> colorLevelVoList = new ArrayList<>();
		//ɫ�ȷ�Χ
		Map<String, Map<String, Object>> maps = getColorEdge(teamSelect);
		//��Ч����,�ۺ������,�ͼ참��,������������ֵ
		double jxkhBase, zhmydBase, jjajBase, jtryBase;
		if (ONE.equals(teamSelect.getType())) {
			jxkhBase = getColorBase(maps, "jxkh", SIX);
			zhmydBase = getColorBase(maps, "zhmyd", SIX);
//            jjajBase = -getColorBase(maps, "jjaj", SIX);
			jjajBase = getColorBase(maps, "jjaj", SIX);
			jtryBase = getColorBase(maps, "jtry", SIX);
		} else {
			jxkhBase = getColorBase(maps, "jxkh", TEN);
			zhmydBase = getColorBase(maps, "zhmyd", TEN);
//            jjajBase = -getColorBase(maps, "jjaj", TEN);
			jjajBase = getColorBase(maps, "jjaj", TEN);
			jtryBase = getColorBase(maps, "jtry", TEN);
		}
		teamSelect.getUnitId().forEach(v -> {
			ColorLevelVo colorLevelVo = new ColorLevelVo();
			String name = sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName();
			//�õ�λ�ۺ�����ȼ���Сֵ
			double zhmydCount = -1;
			double zhmydLeft = -1;
//            String satisfiedData = satisfiedDataSevice.getUnitSatisfied(v);
			List<Map<String, String>> satisfiedDataList = satisfiedDataSevice.getUnitSatisfiedBySql(Arrays.asList(v.split(",")));
			String satisfiedData = null;
			if (satisfiedDataList.size() > 0) {
				satisfiedData = satisfiedDataList.get(0).get("value");
			}
			if (StringUtils.isNotEmpty(satisfiedData)) {
				zhmydCount = Double.parseDouble(satisfiedData);
				zhmydLeft = Double.parseDouble(maps.get("zhmyd").get("left").toString());
			}
			//�õ�λ�ͼ참�����������ֵ
			double jjajCount = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).size();
			double jjajLeft = Double.parseDouble(maps.get("jjaj").get("left").toString());
			//�õ�λ����������������Сֵ
			double jtryCount = teamBuildMapper.findOrgHonorCount(name);
			double jtryLeft = Double.parseDouble(maps.get("jtry").get("left").toString());
			int colorLevel;
//            if (zhmydCount == -1 || zhmydLeft == -1 || zhmydBase == -1){
//                colorLevel = (int) ((FOUR + (jjajLeft - jjajCount) / jjajBase + (jtryCount - jtryLeft) / jtryBase) / THREE);
//            }else {
//                colorLevel = (int) ((FOUR + (zhmydCount - zhmydLeft) / zhmydBase + (jjajLeft - jjajCount) / jjajBase + (jtryCount - jtryLeft) / jtryBase) / FOUR);
//            }
			int level1, level2, level3, level4;
			if (zhmydCount == -1 || zhmydLeft == -1 || zhmydBase == -1) {
				level1 = getColorLevel(4, teamSelect.getType());
				level2 = 0;
				level3 = getColorLevel((int) ((jjajCount - jjajLeft) / jjajBase), teamSelect.getType());
				level4 = getColorLevel((int) ((jtryCount - jtryLeft) / jtryBase), teamSelect.getType());
				colorLevel = (level1 + level3 + level4) / THREE;
			} else {
				level1 = getColorLevel(4, teamSelect.getType());
				level2 = getColorLevel((int) ((zhmydCount - zhmydLeft) / zhmydBase), teamSelect.getType());
				level3 = getColorLevel((int) ((jjajCount - jjajLeft) / jjajBase), teamSelect.getType());
				level4 = getColorLevel((int) ((jtryCount - jtryLeft) / jtryBase), teamSelect.getType());
				colorLevel = (level1 + level2 + level3 + level4) / FOUR;
			}
			colorLevelVo.setDeptId(v);
			colorLevelVo.setDeptName(sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName());
			colorLevelVo.setSimpleName(CommonUnit.getUnitName(colorLevelVo.getDeptName()));
			colorLevelVo.setColorLevel(colorLevel);
			colorLevelVo.setLevel1(level1);
			colorLevelVo.setLevel2(level2);
			colorLevelVo.setLevel3(level3);
			colorLevelVo.setLevel4(level4);
			colorLevelVoList.add(colorLevelVo);
		});
		return colorLevelVoList;
	}

	/**
	 * ��ͼ��ɫ�ȼ��߽紦��
	 */
	private int getColorLevel(int colorlevel, String type) {
		if (ONE.equals(type)) {
			if (colorlevel <= 0) {
				return 1;
			} else if (colorlevel >= SIX) {
				return 5;
			} else {
				return colorlevel;
			}
		} else {
			if (colorlevel <= 0) {
				return 1;
			} else if (colorlevel >= TEN) {
				return 9;
			} else {
				return colorlevel;
			}
		}
	}

//    /**
//     * ������ɫ����ֵ�����Ҿ���
//     */
//    public double getColorBase(Map<String, Map<String, Object>> maps, String type, int count) {
//        if ("jjaj".equals(type)){
//            double left = (double) maps.get(type).get("left");
//            double right = (double) maps.get(type).get("right");
//            return (left - right) / count;
//        }else {
//            double left = (double) maps.get(type).get("left");
//            double right = (double) maps.get(type).get("right");
//            return (right - left) / count;
//        }
//    }

	/**
	 * ������ɫ����ֵ�����Ҿ���
	 */
	public double getColorBase(Map<String, Map<String, Object>> maps, String type, int count) {
//        if ("jjaj".equals(type)){
//            double left = (double) maps.get(type).get("left");
//            double right = (double) maps.get(type).get("right");
//            return (left - right) / count;
//        } else if ("zhmyd".equals(type)) {
//            if (maps.get(type).get("left") != null){
//                double left = Double.parseDouble(maps.get(type).get("left").toString());
//                double right = Double.parseDouble(maps.get(type).get("right").toString());
//                return (right - left) / count;
//            }else {
//                return -1;
//            }
//        } else {
//            double left = (double) maps.get(type).get("left");
//            double right = (double) maps.get(type).get("right");
//            return (right - left) / count;
//        }
		if ("zhmyd".equals(type)) {
			if (maps.get(type).get("left") != null) {
				double left = Double.parseDouble(maps.get(type).get("left").toString());
				double right = Double.parseDouble(maps.get(type).get("right").toString());
				return (right - left) / count;
			} else {
				return -1;
			}
		} else {
			double left = (double) maps.get(type).get("left");
			double right = (double) maps.get(type).get("right");
			return (right - left) / count;
		}
	}

	/*********************************************************
	 *                    �ۺϳ�Ч����ҳ��                    *
	 *********************************************************/

	/**
	 * ��Ա���ɣ����������쵼����������������Ա������
	 */
	public ResultModel getPersonDetail(TeamDetail teamDetail) {
		//���ظ�ǰ�˵�����
		ResultModel resultModel = new ResultModel();
		//���ظ�ǰ�˵�list
		Map<String, Object> map = new HashMap<>();
		String deptLevel = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getDeptLevel();
		if(zzbSubList.contains(teamDetail.getUnitId())){
			deptLevel="3";
		}
		//����������
		int count;
		int leaderCount = 0;
		int policeCount;
		int partyCount;
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		count = idNoList.size();
		map.put("count", count);
		if ("1".equals(deptLevel)) {
			leaderCount = teamBuildMapper.findOneLeaderCount();
		} else if ("2".equals(deptLevel)) {
			if(ZZB_ID.equals(teamDetail.getUnitId())){
				leaderCount = personStereoService.getDeptHead(ZZB_ID).size();
			}else{
				leaderCount = idNoList.size() > 0 ? teamBuildMapper.findTwoLeaderCount(tPersonDao.findAllBydeptIdIn(Arrays.asList(teamDetail.getUnitId())).stream().map(a -> a.getIdNo()).collect(Collectors.toList())) : 0;
			}
		} else if ("3".equals(deptLevel)) {
			if(zzbSubList.contains(teamDetail.getUnitId())){
				leaderCount = idNoList.size() > 0 ? teamBuildMapper.findTwoLeaderCount(tPersonDao.findAllBydeptIdIn(Arrays.asList(teamDetail.getUnitId())).stream().map(a -> a.getIdNo()).collect(Collectors.toList())) : 0;
			}else{
				leaderCount = idNoList.size() > 0 ? teamBuildMapper.findThreeLeaderCount(tPersonDao.findAllBydeptIdIn(Arrays.asList(teamDetail.getUnitId())).stream().map(a -> a.getIdNo()).collect(Collectors.toList())) : 0;
			}

		}
		//��ѯ�����쵼��������
		map.put("leaderCount", leaderCount);
		//��ѯ����������
		policeCount = count - leaderCount;
		map.put("policeCount", policeCount);
		//��ѯ���鵳Ա����
		partyCount = idNoList.size() > 0 ? teamBuildMapper.findPartyCountByIdNo(idNoList) : 0;
		map.put("partyCount", partyCount);

		resultModel.setResult(map);
		return resultModel;
	}

	/**
	 * ����ṹ�ſ�
	 */
	public Map<String, Object> getAgeInfo(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		//35��������Ա
		List<TPersonDo> youngList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) < 35).collect(Collectors.toList());
		//35������-50����Ա
		List<TPersonDo> middleList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50).collect(Collectors.toList());
		//50�꣨����������Ա
		List<TPersonDo> oldList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 50).collect(Collectors.toList());

		DecimalFormat df = new DecimalFormat("0.00%");
		maps.put("young", youngList.size());
		maps.put("middle", middleList.size());
		maps.put("old", oldList.size());
		maps.put("youngPercent", tPersonDoList.size() == 0?"-":df.format((double) youngList.size() / (double) tPersonDoList.size()));
		maps.put("middlePercent", tPersonDoList.size() == 0?"-":df.format((double) middleList.size() / (double) tPersonDoList.size()));
		maps.put("oldPercent", tPersonDoList.size() == 0?"-":df.format((double) oldList.size() / (double) tPersonDoList.size()));
		return maps;
	}

	/**
	 * ����ṹ����
	 */
	public Map<String, Object> getAgeDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
//        //���ݵ�λid�õ�������Ա���֤��
//        List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
//        //��ѯ��Ա��Ϣ
//        List<AgeDetailVo> tPersonDoList = new ArrayList<>();
//        if (idNoList.size() > 0){
//            tPersonDoList.addAll(teamBuildMapper.findAgeDetail(idNoList));
//        }
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		DecimalFormat df = new DecimalFormat("0.00%");
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		if ("0".equals(teamDetail.getType())) {
			//35��������Ա
			List<TPersonDo> youngList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) < 35)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
			List<Map<String, String>> youngMapList = new ArrayList<>();
			if (youngList.size() > 0){
				youngList.forEach(v -> {
					Map<String, String> map = new HashMap<>();
					map.put("policeNo", v.getPoliceNo());
					map.put("personName", v.getPersonName());
					if (v.getAge().equals("0")){
						map.put("age", "-");
					}else {
						map.put("age", String.valueOf(v.getAge()));
					}
					youngMapList.add(map);
				});
			}
			maps.put("detail", splitList(youngMapList, pageNum, pageSize));
			maps.put("total", youngList.size());
			maps.put("percent", df.format((double) youngList.size() / (double) tPersonDoList.size()));
		} else if ("1".equals(teamDetail.getType())) {
			//35������-50����Ա
			List<TPersonDo> middleList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
			maps.put("detail", splitList(middleList, pageNum, pageSize));
			maps.put("total", middleList.size());
			maps.put("percent", df.format((double) middleList.size() / (double) tPersonDoList.size()));
		} else if ("2".equals(teamDetail.getType())) {
			//50�꣨����������Ա
			List<TPersonDo> oldList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
			maps.put("detail", splitList(oldList, pageNum, pageSize));
			maps.put("total", oldList.size());
			maps.put("percent", df.format((double) oldList.size() / (double) tPersonDoList.size()));
		}
		return maps;
	}

	/**
	 * ����ṹ����
	 */
	public void getAgeExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�������Ա���֤��
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		if ("0".equals(teamDetail.getType())) {
			//35��������Ա
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) < 35)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		} else if ("1".equals(teamDetail.getType())) {
			//35������-50����Ա
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		} else if ("2".equals(teamDetail.getType())) {
			//50�꣨����������Ա
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		}
		List<TPersonDo> resultList = tPersonDoList.stream()
				.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		//excel����
		String title = "����ṹ";
		//excel��ͷ��Ϣ
		String[] rowsName = new String[]{"����", "����", "����"};
		List<Object[]> dataList = new ArrayList<>();
		Object[] objs;
		for (TPersonDo ageDetailVo : resultList) {
			objs = new Object[rowsName.length];
			objs[0] = ageDetailVo.getPersonName();
			objs[1] = ageDetailVo.getPoliceNo();
			objs[2] = ageDetailVo.getAge();
			dataList.add(objs);
		}
		exportExcel(title, rowsName, dataList);
	}

	/**
	 * ������ѵ�ſ�
	 */
	public Map<String, Object> getTrainInfo(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		Map<String, Object> maps = new HashMap<>();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = null;
		//��ѯ������ѵ����
		List<TrainCountVo> resultList;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		resultList = teamBuildMapper.trainCountByYear(idNoList);
		//ת����ʽ
		List<EchartsVo> echartsList = new ArrayList<>();
		if (resultList.size() > 0) {
			resultList.forEach(v -> {
				EchartsVo echartsVo = new EchartsVo();
				echartsVo.setName(v.getYear());
				echartsVo.setValue(v.getCount());
				echartsList.add(echartsVo);
			});
		}
		//��ѵ�˴�������ݺ��˴�
		Optional<EchartsVo> op1 = echartsList.stream()
				.max(Comparator.comparingInt(e -> Integer.parseInt(e.getValue())));
		maps.put("chart", echartsList);
		if (echartsList.size() > 0){
			if ("1".equals(teamDetail.getIsReport())) {
				maps.put("content1", echartsList.get(0).getName() + "," + echartsList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum());
				maps.put("content2", op1.get().getName() + "," + op1.get().getValue());
			}else {
				maps.put("content1", "��<strong>" + echartsList.get(0).getName() + "</strong>�깲������ѵ<strong>" + echartsList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum() + "</strong>�˴�");
				maps.put("content2", "����<strong>" + op1.get().getName() +
						"</strong>����ѵ�˴���ߣ�Ϊ<strong>" + op1.get().getValue() +
						"</strong>�˴�");
			}
		}
		return maps;
	}

	/**
	 * ������ѵ����
	 */
	public Map<String, Object> getTrainDetail(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//��ȡɸѡ����
		String type = teamDetail.getType();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = null;
		Map<String, Object> maps = new HashMap<>();
		//��ѯ������ѵ����
		List<TrainDetailVo> resultList;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//����ɸѡ��������ݣ����й���ɸѡ
		resultList = teamBuildMapper.findTrainDetail(idNoList,type);

		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * ������ѵ����
	 */
	public void getTrainExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//��ѯ������ѵ����
		List<TrainDetailVo> resultList;
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = null;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		resultList = teamBuildMapper.findTrainDetail(idNoList,teamDetail.getType());

		//excel����
		String title = "������ѵ";
		//excel��ͷ��Ϣ
		String[] rowsName = new String[]{"����", "����", "����", "��ѵʱ��", "��֯��λ"};
		List<Object[]> dataList = new ArrayList<>();
		Object[] objs;
		for (TrainDetailVo trainDetailVo : resultList) {
			objs = new Object[rowsName.length];
			objs[0] = trainDetailVo.getPersonName();
			objs[1] = trainDetailVo.getPoliceNo();
			objs[2] = trainDetailVo.getUnitName();
			objs[3] = trainDetailVo.getStartTime();
			objs[4] = trainDetailVo.getTrainingCourse();
			dataList.add(objs);
		}
		exportExcel(title, rowsName, dataList);
	}

	/**
	 * ���������ſ�
	 */
	public Map<String, Object> getOrgHonor(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//��λ���Ӳ���id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());
		Map<String, Object> maps = new LinkedHashMap<>();
		//��ѯ��������������
		List<OrgHonorVo> resultList = new ArrayList<>();
		if (deptList.size() > 0){
			resultList.addAll(teamBuildMapper.findOrgHonorDetail(deptList));
		}
		//������������ͳ��
		Map<String, Long> orgHonorCountMap = resultList.stream().collect(Collectors.groupingBy(OrgHonorVo::getHonorName, counting()));
		//���㼯������ȫ��ƽ��ֵ
		List<TeamCountVo> orgHonorAllList = new ArrayList<>();
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			orgHonorAllList.addAll(teamBuildMapper.findOrgHonor("2"));
		} else {
			orgHonorAllList.addAll(teamBuildMapper.findOrgHonor("3"));
		}
		//��������ƽ��ֵ
		double orgHonorAvg = orgHonorAllList.stream().collect(averagingDouble(TeamCountVo::getCount));
		double orgHonorCount = resultList.size();

		//ת�����ݸ�ʽ
		List<EchartsVo> echartsVoList = new ArrayList<>();
		List<String> honorDesc = new ArrayList<>();
		String one = null, two = null, three = null;
		for (Map.Entry<String, Long> entry : orgHonorCountMap.entrySet()) {
			EchartsVo echartsVo = new EchartsVo();
			echartsVo.setName(entry.getKey());
			echartsVo.setValue(entry.getValue().toString());
			echartsVoList.add(echartsVo);
			if ("����һ�ȹ�".equals(entry.getKey())) {
				one = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>����һ�ȹ�</strong>";
			} else if ("������ȹ�".equals(entry.getKey())) {
				two = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>������ȹ�</strong>";
			} else if ("�������ȹ�".equals(entry.getKey())) {
				three = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>�������ȹ�</strong>";
			}
		}
		if (StringUtils.isNotEmpty(one)) {
			honorDesc.add(one);
		}
		if (StringUtils.isNotEmpty(two)) {
			honorDesc.add(two);
		}
		if (StringUtils.isNotEmpty(three)) {
			honorDesc.add(three);
		}
		final String[] honorDescStr = {""};
		if (honorDesc.size() > 0) {
			honorDesc.forEach(v -> honorDescStr[0] = honorDescStr[0] + v);
		}

		maps.put("chart", getOrgHonorSort(echartsVoList));
		maps.put("content1", "�����<strong>" + resultList.size() + "</strong>�������");
		if (honorDesc.size() > 0) {
			maps.put("content2", "����" + honorDescStr[0]);
		}
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			maps.put("content3", "��ȫ��ƽ��������������<strong>" + compare(orgHonorAvg, orgHonorCount) + "</strong>");
		}
		return maps;
	}

	/**
	 * ������������
	 */
	public Map<String, Object> getOrgHonorDetail(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//��λ���Ӳ���id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());

		//��ȡ��������
		String type = teamDetail.getType();

		//���ؼ�������������
		List<OrgHonorVo> resultList = new ArrayList<>();
		if (deptList.size() > 0&&type!=null){
			resultList.addAll(teamBuildMapper.findOrgHonorDetail(deptList).stream().filter(c->c.getHonorName()!=null).filter(v->v.getHonorName().equals(type)).collect(toList()));
		}else{
			resultList.addAll(teamBuildMapper.findOrgHonorDetail(deptList).stream().filter(c->c.getHonorName()!=null).collect(toList()));
		}
		resultList.sort(Comparator.comparing((OrgHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())).thenComparing((OrgHonorVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		Map<String, Object> maps = new HashMap<>();
		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * ������������
	 */
	public void getOrgHonorExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//��λ���Ӳ���id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());
		//���ؼ�������������
		List<OrgHonorVo> resultList = new ArrayList<>();
		if (deptList.size() > 0){
			resultList = teamBuildMapper.findOrgHonorDetail(deptList);
		}
		//��ȡ��������
		String type = teamDetail.getType();

		//���ؼ�������������
		if (deptList.size() > 0&&type!=null){
			resultList = resultList.stream().filter(c->c.getHonorName()!=null).filter(v->v.getHonorName().equals(type)).collect(toList());
		}
		resultList.sort(Comparator.comparing((OrgHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())).thenComparing((OrgHonorVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		//excel����
		String title = "��������";
		//excel��ͷ��Ϣ
		String[] rowsName = new String[]{"���óƺ�", "����", "�ڽ�ʱ��", "���ò���"};
		List<Object[]> dataList = new ArrayList<>();
		Object[] objs;
		for (OrgHonorVo orgHonorVo : resultList) {
			objs = new Object[rowsName.length];
			objs[0] = orgHonorVo.getHonorName();
			objs[1] = orgHonorVo.getDeptName();
			objs[2] = orgHonorVo.getDate();
			objs[3] = orgHonorVo.getAwardDept();
			dataList.add(objs);
		}
		exportExcel(title, rowsName, dataList);
	}

	/**
	 * ���������ſ�
	 */
	public Map<String, Object> getPersonHonor(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//��ѯ������������
		List<PersonHonorVo> resultList =  teamBuildMapper.findPersonHonorDetail(idNoList,null);
//        if (idNoList.size() > 0){
//            resultList.addAll(teamBuildMapper.findPersonHonorDetail(idNoList));
//        }
//        List<PersonHonorVo> resultList = new ArrayList<>(teamBuildMapper.findPersonHonorDetail(idNoList));
		//������������ͳ��
		Map<String, Long> personHonorCountMap = resultList.stream().filter(v->v.getAwardName()!=null).collect(Collectors.groupingBy(PersonHonorVo::getAwardName, counting()));
		//�ϲ�һ�ȹ������ȹ������ȹ�
		Map<String, Long> groupPersonHonorMap = new HashMap<>(16);
		long firstCount = 0;
		long secondCount = 0;
		long thirdCount = 0;
		for (Map.Entry<String, Long> entry : personHonorCountMap.entrySet()) {
			if (entry.getKey().contains("һ�ȹ�")) {
				firstCount += entry.getValue();
			} else if (entry.getKey().contains("���ȹ�")) {
				secondCount += entry.getValue();
			} else if (entry.getKey().contains("���ȹ�")) {
				thirdCount += entry.getValue();
			} else {
				groupPersonHonorMap.put(entry.getKey(), entry.getValue());
			}
		}
		if ("1".equals(teamDetail.getIsReport())){
			groupPersonHonorMap.put("һ�ȹ�", firstCount);
			groupPersonHonorMap.put("���ȹ�", secondCount);
			groupPersonHonorMap.put("���ȹ�", thirdCount);
		}else {
			if (firstCount > 0) {
				groupPersonHonorMap.put("һ�ȹ�", firstCount);
			}
			if (secondCount > 0) {
				groupPersonHonorMap.put("���ȹ�", secondCount);
			}
			if (thirdCount > 0) {
				groupPersonHonorMap.put("���ȹ�", thirdCount);
			}
		}

		//ת�����ݸ�ʽ
		List<EchartsVo> echartsVoList = new ArrayList<>();
		List<String> honorDesc = new ArrayList<>();
		String one = null, two = null, three = null;
		for (Map.Entry<String, Long> entry : groupPersonHonorMap.entrySet()) {
			EchartsVo echartsVo = new EchartsVo();
			echartsVo.setName(entry.getKey());
			echartsVo.setValue(entry.getValue().toString());
			echartsVoList.add(echartsVo);
			if ("һ�ȹ�".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					one = "," + entry.getValue().toString();
				} else {
					one = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>һ�ȹ�</strong>";
				}
			} else if ("���ȹ�".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					two = "," + entry.getValue().toString();

				} else {
					two = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>���ȹ�</strong>";
				}
			} else if ("���ȹ�".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					three = "," + entry.getValue().toString();
				} else {
					three = ",<strong>" + entry.getValue().toString() + "</strong>��" + "<strong>���ȹ�</strong>";
				}
			}
		}
		//�����������ȫ��ƽ��ֵ
		List<TeamCountVo> personHonorAllList = new ArrayList<>();
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			personHonorAllList.addAll(teamBuildMapper.findPersonHonor("2"));
		} else {
			personHonorAllList.addAll(teamBuildMapper.findPersonHonor("3"));
		}
		//��������ƽ��ֵ
		double personHonorAvg = personHonorAllList.stream().collect(averagingDouble(TeamCountVo::getCount));
		double personHonorCount = resultList.size();

		if (StringUtils.isNotEmpty(one)) {
			honorDesc.add(one);
		}
		if (StringUtils.isNotEmpty(two)) {
			honorDesc.add(two);
		}
		if (StringUtils.isNotEmpty(three)) {
			honorDesc.add(three);
		}
		final String[] honorDescStr = {""};
		if (honorDesc.size() > 0) {
			honorDesc.forEach(v -> honorDescStr[0] = honorDescStr[0] + v);
		}
		//ȥ��valueΪ0������
		List<EchartsVo> echartsResultList = new ArrayList<>();
		echartsVoList.forEach(v -> {
			if (!"0".equals(v.getValue())) {
				echartsResultList.add(v);
			}
		});
		maps.put("chart", getPersonHonorSort(echartsResultList));
		if ("1".equals(teamDetail.getIsReport())) {
			maps.put("content1", resultList.size());
		} else {
			maps.put("content1", "�����<strong>" + resultList.size() + "</strong>���������");
		}
		if (honorDesc.size() > 0) {
			maps.put("content2", "����" + honorDescStr[0]);
		}
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			if ("1".equals(teamDetail.getIsReport())) {
				maps.put("content3", String.format("%.2f", personHonorAvg) + "," + compare(personHonorAvg, personHonorCount));
			}
			else {
				maps.put("content3", "��ȫ��ƽ����������<strong>" + String.format("%.2f", personHonorAvg) + "</strong>��<strong>" + compare(personHonorAvg, personHonorCount) + "</strong>");
			}
		}
		return maps;
	}

	/**
	 * ������������
	 */
	public Map<String, Object> getPersonHonorDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//��ȡɸѡ����
		String type = teamDetail.getType();

		//��ѯ������������
		List<PersonHonorVo> resultList  = teamBuildMapper.findPersonHonorDetail(idNoList,teamDetail.getType());
        /*if (idNoList.size() > 0){
            resultList.addAll(teamBuildMapper.findPersonHonorDetail(idNoList));
        }*/


		resultList.sort(Comparator.comparing((PersonHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())));
		//��ȡ��������е�ҳ���ÿҳ������
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();

		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * ������������
	 */
	public void getPersonHonorExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//���ݵ�λid�õ�������Ա���֤��
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//��ѯ������������
		List<PersonHonorVo> resultList= teamBuildMapper.findPersonHonorDetail(idNoList,teamDetail.getType());

		resultList.sort(Comparator.comparing((PersonHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())));
		//excel����
		String title = "��������";
		//excel��ͷ��Ϣ
		String[] rowsName = new String[]{"���óƺ�", "����", "����", "�ڽ�ʱ��", "���ò���"};
		List<Object[]> dataList = new ArrayList<>();
		Object[] objs;
		for (PersonHonorVo personHonorVo : resultList) {
			objs = new Object[rowsName.length];
			objs[0] = personHonorVo.getAwardName();
			objs[1] = personHonorVo.getPersonName();
			objs[2] = personHonorVo.getPoliceNo();
			objs[3] = personHonorVo.getDate();
			objs[4] = personHonorVo.getAwardDept();
			dataList.add(objs);
		}
		exportExcel(title, rowsName, dataList);
	}

	/**
	 * �ͼ참���ſ�
	 */
	public Map<String, Object> getBreakRuleCase(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		Map<String, Object> chartMap = new HashMap<>(16);
		String name;
		if ("2014110416460086100000002942".equals(teamDetail.getUnitId())){
			name = "";
		}else {
			name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
		}
		List<EchartsVo> caseList = teamBuildMapper.findBreakRuleCaseCountByYear("%" + name + "%",null);
		if (caseList.size() > 0){
			caseList.forEach(a->a.setName(a.getName()+"��"));
		}
		chartMap.put("dept", new ArrayList<>());
		chartMap.put("personal", caseList);
		maps.put("chart", chartMap);
		//��ѯ�ͼ참��������Ϣ
		List<RulesAndCount> rulesAndCountList = teamBuildMapper.findRulesCountByWtxz("%" + name + "%");
		List<BreakRuleCaseVo> ruleList = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null);
		if (caseList.size() > 0) {
			String year = caseList.get(0).getName();
			int count = caseList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum();
			maps.put("content1", "��<strong>" + year + "</strong>�깲��<strong>" + count + "</strong>���漰�ͼ참��");
		}else {
			maps.put("content1", "");
		}
		maps.put("content2", "");
		if(rulesAndCountList.size() >0) {
			int flag = 0;
			StringBuilder count = new StringBuilder();
			if(rulesAndCountList.size() <3) {
				flag = ruleList.size()-1;
			}else {
				flag = 3;
			}
			for (int i=0;i<flag;i++) {
				count.append(",<strong>").append(rulesAndCountList.get(i).getCount()).append("</strong>���漰<strong>").append(rulesAndCountList.get(i).getWtxz()).append("</strong>");
			}
			if(count.length() >1) {
				maps.put("content2", "����" + count.toString());
			}
		}
		maps.put("content3", "");
		return maps;
	}

	/**
	 * �ͼ참������
	 */
	public Map<String, Object> getBreakRuleCaseDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		String name;
		if ("2014110416460086100000002942".equals(teamDetail.getUnitId())){
			name = "";
		}else {
			name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
		}

		//��ȡ��������(��),ȥ����β�� ��
		String type = teamDetail.getType();
//        String type ="";
//        if(type1!=null){
//             type=type1.substring(0,teamDetail.getType().indexOf("��"));
//        }
//        //��ѯ�ͼ참����Ϣ
//        if(type=""){
//
//        }
		List<BreakRuleCaseVo> resultList=new ArrayList<>();
		if(type!=null){
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).filter(c->c.getDate().substring(0,4).equals(type.substring(0,type.indexOf("��")))).collect(toList());
		}else{
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).collect(toList());
		}
		resultList.sort(Comparator.comparing((BreakRuleCaseVo v)->v.getDate(),Comparator.reverseOrder()).thenComparing((BreakRuleCaseVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		if (resultList.size() > 0) {
			resultList.forEach(v -> v.setType("����"));
		}
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * �ͼ참������
	 */
	public void getBreakRuleCaseExport(TeamDetail teamDetail) {
		//��ȡ��λ����
		String name;
		if ("2014110416460086100000002942".equals(teamDetail.getUnitId())){
			name = "";
		}else {
			name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
		}
		//��ѯ�ͼ참����Ϣ
		List<BreakRuleCaseVo> resultList = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null);
		if (resultList.size() > 0) {
			resultList.forEach(v -> v.setType("����"));
		}
		String type = teamDetail.getType();
		if(type!=null){
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).filter(c->c.getDate().substring(0,4).equals(type.substring(0,4))).collect(toList());
		}
		//excel����
		String title = "�ͼ참��";
		//excel��ͷ��Ϣ
		String[] rowsName = new String[]{"��λ", "����", "����", "���־���ʱ��", "���ֽ���ʱ��", "��������"};
		List<Object[]> dataList = new ArrayList<>();
		Object[] objs;
		resultList.sort(Comparator.comparing((BreakRuleCaseVo v)->v.getDate(),Comparator.reverseOrder()).thenComparing((BreakRuleCaseVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		for (BreakRuleCaseVo breakRuleCaseVo : resultList) {
			objs = new Object[rowsName.length];
			objs[0] = breakRuleCaseVo.getDeptName();
			objs[1] = breakRuleCaseVo.getPersonName();
			objs[2] = breakRuleCaseVo.getPoliceNo();
			objs[3] = breakRuleCaseVo.getDate();
			objs[4] = breakRuleCaseVo.getDate1();
			objs[5] = breakRuleCaseVo.getDetail();
			dataList.add(objs);
		}
		exportExcel(title, rowsName, dataList);
	}

	/**
	 * ��ȡռ������
	 */
	private String compare(double avg, double count) {
		String description;
		DecimalFormat df = new DecimalFormat("0.00%");
		if (count >= avg) {
			description = "��" + df.format((count - avg) / avg);
		} else {
			description = "��" + df.format((avg - count) / avg);
		}
		return description;
	}

	/**
	 * list��ҳ
	 */
	private List splitList(List list, int pageNum, int pageSize) {
		int sum = list.size(); //��¼����
		int pageNo = (pageNum - 1) * pageSize;   //ÿҳ����ʼ����
		if (pageNo > sum) {
			return new ArrayList();
		}
		if (pageNo + pageSize > sum) {
			return list.subList(pageNo, sum);
		} else {
			return list.subList(pageNo, pageNo + pageSize);
		}
	}

	/**
	 * excel����
	 */
	public void exportExcel(String title, String[] rowsName, List<Object[]> list) {
		//���ļ��������������
		String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
		//������������ݸ�ʽ����ͷ�����ݴ���ǰ̨
		String headStr = "attachment; filename=\"" + fileName + "\"";
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", headStr);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//����poi�Ĺ�����
		ExportExcel ex = new ExportExcel(title, rowsName, list);
		try {
			ex.export(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Objects.requireNonNull(out).flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Objects.requireNonNull(out).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������������
	 *
	 * @param rewardAndCountList
	 * @return
	 */
	private List<EchartsVo> getOrgHonorSort(List<EchartsVo> rewardAndCountList) {
		List<EchartsVo> resultList = new ArrayList<>();
		List<List<EchartsVo>> list = new ArrayList<>();
		//����11����ͬ����������list
		for (int i = 0; i < 11; i++) {
			list.add(new ArrayList<>());
		}
		for (EchartsVo a : rewardAndCountList) {
			if ("ȫ�����㹫����".equals(a.getName())) {
				list.get(0).add(a);
			} else if ("ȫ�����㹫�����㵥λ".equals(a.getName())) {
				list.get(1).add(a);
			} else if ("ȫʡ���㹫����".equals(a.getName())) {
				list.get(2).add(a);
			} else if ("����һ�ȹ�".equals(a.getName())) {
				list.get(3).add(a);
			} else if ("������ȹ�".equals(a.getName())) {
				list.get(4).add(a);
			} else if ("�������ȹ�".equals(a.getName())) {
				list.get(5).add(a);
			} else if ("����ν�".equals(a.getName())) {
				list.get(6).add(a);
			} else if ("ͨ������".equals(a.getName())) {
				list.get(7).add(a);
			} else if ("�����ƺ�".equals(a.getName())) {
				list.get(8).add(a);
			} else if ("�Ƚ�����".equals(a.getName())) {
				list.get(9).add(a);
			}
		}
		list.forEach(resultList::addAll);
		return resultList;
	}

	/**
	 * ������������
	 *
	 * @param rewardAndCountList
	 * @return
	 */
	private List<EchartsVo> getPersonHonorSort(List<EchartsVo> rewardAndCountList) {
		List<EchartsVo> resultList = new ArrayList<>();
		//����Ƚ�
		List<EchartsVo> listOne = new ArrayList<>(), listTwo = new ArrayList<>(), listThree = new ArrayList<>();
		List<List<EchartsVo>> sortOne = new ArrayList<>(), sortTwo = new ArrayList<>(), sortThree = new ArrayList<>();
		//��һ������list
		for (int i = 0; i < 7; i++) {
			sortOne.add(new ArrayList<>());
		}
		//�ڶ�������list
		for (int i = 0; i < 4; i++) {
			sortTwo.add(new ArrayList<>());
		}
		//����������list
		for (int i = 0; i < 8; i++) {
			sortThree.add(new ArrayList<>());
		}
		//һ�ȹ������ȹ������ȹ�
		List<EchartsVo> firstLevel = new ArrayList<>(), secondLevel = new ArrayList<>(), thirdLevel = new ArrayList<>();
		//��������
		List<EchartsVo> otherList = new ArrayList<>();
		//�ȽϽ��
		List<EchartsVo> resultOne = new ArrayList<>(), resultTwo = new ArrayList<>(), resultThree = new ArrayList<>();
		rewardAndCountList.forEach(v -> {
			if (personRewordOne.contains(v.getName())) {
				listOne.add(v);
			} else if (personRewordTwo.contains(v.getName())) {
				listTwo.add(v);
			} else if (personRewordThree.contains(v.getName())) {
				listThree.add(v);
			} else if (v.getName().contains(FIRST_LEVEL)) {
				firstLevel.add(v);
			} else if (v.getName().contains(SECOND_LEVEL)) {
				secondLevel.add(v);
			} else if (v.getName().contains(THIRD_LEVEL)) {
				thirdLevel.add(v);
			} else {
				otherList.add(v);
			}
		});
		while (listOne.size() > 0) {
			for (EchartsVo a : listOne) {
				if ("������ʿ".equals(a.getName())) {
					sortOne.get(0).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ������ϵͳһ��Ӣ��ģ��".equals(a.getName())) {
					sortOne.get(1).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ������ϵͳ����Ӣ��ģ��".equals(a.getName())) {
					sortOne.get(2).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ����ģ".equals(a.getName())) {
					sortOne.get(3).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ���ؼ��������񾯲�".equals(a.getName())) {
					sortOne.get(4).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ���������񾯲�".equals(a.getName())) {
					sortOne.get(5).add(a);
					listOne.remove(a);
					break;
				} else if ("ȫ�����˺�����".equals(a.getName())) {
					sortOne.get(6).add(a);
					listOne.remove(a);
					break;
				} else {
					listOne.remove(a);
					break;
				}
			}
		}
		sortOne.forEach(resultOne::addAll);
		while (listTwo.size() > 0) {
			for (EchartsVo a : listTwo) {
				if ("ʡ��ģ".equals(a.getName())) {
					sortTwo.get(0).add(a);
					listTwo.remove(a);
					break;
				} else if ("ʡ���˺�����".equals(a.getName())) {
					sortTwo.get(1).add(a);
					listTwo.remove(a);
					break;
				} else if ("ʡ�������꽱��".equals(a.getName())) {
					sortTwo.get(2).add(a);
					listTwo.remove(a);
					break;
				} else if ("���պ�����".equals(a.getName())) {
					sortTwo.get(3).add(a);
					listTwo.remove(a);
					break;
				} else {
					listTwo.remove(a);
					break;
				}
			}
		}
		sortTwo.forEach(resultTwo::addAll);
		while (listThree.size() > 0) {
			for (EchartsVo a : listThree) {
				if ("���Ͷ�ģ��".equals(a.getName())) {
					sortThree.get(0).add(a);
					listThree.remove(a);
					break;
				} else if ("ʡ���㹲���Ÿɲ�".equals(a.getName())) {
					sortThree.get(1).add(a);
					listThree.remove(a);
					break;
				} else if ("ʡ���㹲����Ա".equals(a.getName())) {
					sortThree.get(2).add(a);
					listThree.remove(a);
					break;
				} else if ("�ж��Ͼ��������".equals(a.getName())) {
					sortThree.get(3).add(a);
					listThree.remove(a);
					break;
				} else if ("�����˺�����".equals(a.getName())) {
					sortThree.get(4).add(a);
					listThree.remove(a);
					break;
				} else if ("��ʮ��ܳ�����".equals(a.getName())) {
					sortThree.get(5).add(a);
					listThree.remove(a);
					break;
				} else if ("���������꽱��".equals(a.getName())) {
					sortThree.get(6).add(a);
					listThree.remove(a);
					break;
				} else if ("�������ϸɲ�".equals(a.getName())) {
					sortThree.get(7).add(a);
					listThree.remove(a);
					break;
				} else {
					listThree.remove(a);
					break;
				}
			}
		}
		sortThree.forEach(resultThree::addAll);
		resultList.addAll(resultOne);
		resultList.addAll(firstLevel);
		resultList.addAll(resultTwo);
		resultList.addAll(secondLevel);
		resultList.addAll(resultThree);
		resultList.addAll(thirdLevel);
		resultList.addAll(otherList);
		return resultList;
	}
	@Resource(name = "primaryJdbcTemplate")
	private JdbcTemplate primaryJdbcTemplate;
	@Resource(name = "tertiaryJdbcTemplate")
	private JdbcTemplate tertiaryJdbcTemplate;

	public TPersonCountAndAge  getCountAndAgeByUnitIds(List<String> unitIds){
		StringBuilder stringBuilder = new StringBuilder("select AVG(PERSON_AGE) AS ageAvg,count(*) AS num from NJGAZNL.T_PERSON where delstatus = '0' and  PCLASSID = '00001' and idno in (select DISTINCT  identitynum from NJGAZNL.SYS_USER t where  delstatus = '0' and post_type = '0'");
		if(unitIds.contains(ZZB_ID)){
			unitIds.addAll(zzbSubList);
		}
		if(unitIds.size()>0){
			List<String> list = new ArrayList<>();
			unitIds.forEach(u->list.add(" deptid ='"+u+"' "));
			stringBuilder.append(" and ("+StringUtils.join(list," OR ")+")");
		}
		if(unitIds.contains(ZZB_ID)){
			List<String> collect = personStereoService.getDeptHead(ZZB_ID).stream().map(a -> a.getIdNo()).collect(toList());
			if(collect.size()>0){
				stringBuilder.append(" OR identitynum IN ('"+StringUtils.join(collect,"','")+"')");
			}
		}
		stringBuilder.append(")");
		Map<String, Object> map = primaryJdbcTemplate.queryForMap(stringBuilder.toString());
		TPersonCountAndAge result = new TPersonCountAndAge();
		result.setNum(Integer.parseInt(String.valueOf(map.get("num"))));
		result.setAgeSum(map.get("ageAvg")==null?"0":String.valueOf(map.get("ageAvg")));
		return result;
	}
	/**
	 * �շ�����ת�»�������
	 * @param c
	 * @return
	 */
	public static String camel2under(String c)
	{
		String separator = "_";
		c = c.replaceAll("([a-z])([A-Z])", "$1"+separator+"$2").toLowerCase();
		return c;
	}
	public static Map<String,Map<String,String>> kpiItems = new HashMap<>();
	static{
		Map<String,String> items = new LinkedHashMap<>();
		items.put("companyName","��λ");
		items.put("basicPartDev","��������");
		items.put("workHandlePower","����ִ����");
		items.put("lgcm","������ģ");
		items.put("zgjs","��������");
		items.put("xwxc","��������");
		items.put("zgxxdy","������Ϣ����");
		items.put("totalScore","ƽ̨�����ܷ�");
		kpiItems.put("2019",items);

		items = new LinkedHashMap<>();
		items.put("companyName","��λ");
		items.put("basicPartDev","��������");
		items.put("lgcm","������ģ");
		items.put("xwxc","��������");
		items.put("item1","��Ӫר���Ļ�����΢��ά");
		items.put("item2","��Ӫ���幤��");
		items.put("zgxxdy","������Ϣ����");
		items.put("workHandlePower","����ִ����");
		items.put("item3","�����˶���");
		items.put("item4","ʵսѵ��");
		items.put("item5","�̹ٶ��齨��");
		items.put("item6","��������");
		items.put("item7","�����������");
		items.put("totalScore","ƽ̨�����ܷ�");
		kpiItems.put("2018",items);

		items = new LinkedHashMap<>();
		items.put("companyName","��λ");
		items.put("totalScore","ƽ̨�����ܷ�");
		kpiItems.put("2017",items);
		kpiItems.put("2016",items);
	}
	public ResultModel getKPI(KPIQuery kpiQuery) {
		String unitName=null;
		if (kpiQuery.getUnitId()!=null){
			unitName = sysDeptDao.getOne(kpiQuery.getUnitId()).getUnitName();
		}
		List<String> menu = kpiQuery.getMenu();
		String year = menu.get(0);
		String quarter = menu.size()>1?menu.get(1):null;
		String groupName = menu.size()>2?menu.get(2):null;
//        List<String> indicators = kpiQuery.getItems();
		List<Map<String,Object >> ruleList = new ArrayList<>();
		Map<String, String> items=null;
		if(Integer.parseInt(year)<2019 ){
			String sql ="SELECT YEAR,COMPANY_NAME SIMPLECHINANAME,GROUPNAME,QUARTER,TOTAL_SCORE SCORE,RANK() OVER(PARTITION BY  YEAR,QUARTER,GROUPNAME ORDER BY TOTAL_SCORE DESC) RN FROM KPI_ZG_RESULT  WHERE 1=1";
			if(StringUtils.isNotBlank(year)){
				sql += " AND YEAR ='" + year+"' ";
			}
			if(StringUtils.isNotBlank(quarter)){
				sql += " AND QUARTER ='" + quarter+"' ";
			}
			if(StringUtils.isNotBlank(groupName)){
				sql += " AND GROUPNAME ='" + groupName+"' ";
			}
			if(StringUtils.isNotBlank(unitName)){
				sql += " AND COMPANY_NAME ='" + unitName+"' ";
			}
			sql +="ORDER BY TRANSLATE(QUARTER,'һ�����������߰˾�' , '123456789') DESC,TRANSLATE(GROUPNAME,'һ�����������߰˾�' , '123456789'),TOTAL_SCORE DESC";
			ruleList.addAll(tertiaryJdbcTemplate.queryForList(sql));
		}else{
			JdbcTemplate jdbcTemplate;
			String kpiTable;
			if(Integer.parseInt(year)==2019 ){
				jdbcTemplate = tertiaryJdbcTemplate;
				kpiTable = TABLE_KPI_2019;
			}else{
				jdbcTemplate = primaryJdbcTemplate;
				kpiTable = TABLE_KPI;
			}
			String sql = "SELECT T.*,RANK() OVER(PARTITION BY  YEAR,QUARTER,GROUPNAME ORDER BY SCORE DESC) RN FROM (SELECT YEAR,SIMPLECHINANAME,GROUPNAME,SUM(SCORE) SCORE,QUARTER,QUARTER_BEGIN,GROUP_SORT1,GROUP_SORT2 FROM (SELECT * FROM (" + kpiTable + ") WHERE 1=1 " ;
			if(StringUtils.isNotBlank(year)){
				sql += " AND YEAR ='" + year+"' ";
			}
			if(StringUtils.isNotBlank(quarter)){
				sql += " AND QUARTER ='" + quarter+"' ";
			}
			if(StringUtils.isNotBlank(groupName)){
				sql += " AND GROUPNAME ='" + groupName+"' ";
			}
			if(StringUtils.isNotBlank(unitName)){
				sql += " AND SIMPLECHINANAME ='" + unitName+"' ";
			}
			sql+= ") GROUP BY YEAR,SIMPLECHINANAME,GROUPNAME,QUARTER,QUARTER_BEGIN,GROUP_SORT1,GROUP_SORT2) T ORDER BY QUARTER_BEGIN DESC, GROUP_SORT1,GROUP_SORT2,SCORE DESC";
			ruleList.addAll(jdbcTemplate.queryForList(sql));

		}
		if(kpiQuery.getIsDownload()){
			String title="";
			if(StringUtils.isNotBlank(year)){
				title += year+"��";
			}
			if(StringUtils.isNotBlank(quarter)){
				title += quarter;
			}
			if(StringUtils.isNotBlank(groupName)){
				title += groupName;
			}
			String[] rowsName = new String[]{"���","����","���˵�Ԫ","��λ","���˳ɼ�","����"};
			List<Object[]> dataList = new ArrayList<>();
			Object[] objs;
			for (Map<String, Object> map : ruleList) {
				objs = new Object[rowsName.length];
				objs[0] = map.get("YEAR");
				objs[1] = map.get("QUARTER");
				objs[2] = map.get("GROUPNAME");
				objs[3] = map.get("SIMPLECHINANAME");
				objs[4] = map.get("SCORE");
				objs[5] = map.get("RN");
				dataList.add(objs);
			}

			exportExcel(title+"������Ч���˵÷ֱ�",rowsName,dataList);
			return null;
		}else{
			return ResultModel.success(ruleList);
		}
	}

	/**
	 * ��ȡ��Ч������ݲ˵�
	 * @return
	 */
	public List<Map<String,Object>> getKPIMenu()
	{
		List<Map<String,Object>> result = new ArrayList<>();
		List<String> kpiYearMenu = kpiZGResultDao.getKPIYearMenu();
		Collections.reverse(kpiYearMenu);

		List<String> years = primaryJdbcTemplate.queryForList("SELECT DISTINCT YEAR FROM (" + TABLE_KPI + ")", String.class);

		Set<String> yearSet = new HashSet<>();
		yearSet.addAll(kpiYearMenu);
		yearSet.addAll(years);
		List<String> yearList = new ArrayList<>(yearSet);
		yearList.sort(Comparator.reverseOrder());
		for (String s : yearList) {
			Map<String, Object> m = new HashMap<>();
			m.put("value",s);
			m.put("label",s+"��");
			m.put("children",getKPIQuarterMenu(s));
			result.add(m);
		}

		return result;
	}

	/**
	 * ��ȡ��Ч���˼��Ȳ˵�
	 * @return
	 * @param year
	 */
	public List<Map<String,Object>>  getKPIQuarterMenu(String year)
	{
		List<Map<String,Object>> result = new ArrayList<>();

		List<String> quarters;
		//��2019����ʹ����������
		if(Integer.parseInt(year)>2019){
			quarters = primaryJdbcTemplate.queryForList("SELECT QUARTER FROM (SELECT DISTINCT QUARTER,QUARTER_BEGIN FROM (" + TABLE_KPI + ") WHERE YEAR ='"+year+"') ORDER BY QUARTER_BEGIN DESC", String.class);
		}else if(Integer.parseInt(year)==2019){
			quarters = tertiaryJdbcTemplate.queryForList("SELECT QUARTER FROM (SELECT DISTINCT QUARTER,QUARTER_BEGIN FROM (" + TABLE_KPI_2019 + ") WHERE YEAR ='"+year+"') ORDER BY QUARTER_BEGIN DESC", String.class);
		}else{
			quarters = kpiZGResultDao.getKPIQuarterMenu(year);
		}

		for (String s : quarters) {
			Map<String, Object> m = new HashMap<>();
			m.put("value",s);
			m.put("label",s);
			m.put("children",getKPIGroupMenu(year,s));
			result.add(m);
		}
		return result;
	}

	/**
	 * ��ȡ��Ч�������˵�
	 * @return
	 */
	public List<Map<String,Object>> getKPIGroupMenu(String year,String quarter)
	{
		List<Map<String,Object>> result = new ArrayList<>();
		List<String> groupMenu;
		/**
		 * �ж�ȡ�ĸ���ļ�Ч����
		 */
		if(Integer.parseInt(year)<2019){
			groupMenu = kpiZGResultDao.getKPIGroupMenu(year, quarter);
		}else if(Integer.parseInt(year)==2019){
			groupMenu = tertiaryJdbcTemplate.queryForList("SELECT GROUPNAME FROM (SELECT DISTINCT GROUPNAME,GROUP_SORT1,GROUP_SORT2 FROM (" + TABLE_KPI_2019 + ") WHERE YEAR ='"+year+"' AND QUARTER='"+quarter+"') ORDER  BY GROUP_SORT1,GROUP_SORT2", String.class);
		}else{
			groupMenu = primaryJdbcTemplate.queryForList("SELECT GROUPNAME FROM (SELECT DISTINCT GROUPNAME,GROUP_SORT1,GROUP_SORT2 FROM (" + TABLE_KPI + ") WHERE YEAR ='"+year+"' AND QUARTER='"+quarter+"') ORDER  BY GROUP_SORT1,GROUP_SORT2", String.class);
		}
		for (String s : groupMenu) {
			Map<String, Object> m = new HashMap<>();
			m.put("value",s);
			m.put("label",s);
			m.put("leaf",true);
			result.add(m);
		}
		return result;
	}
}
