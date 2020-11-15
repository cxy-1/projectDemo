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
	 * 政治部下属部门
	 */
	private static List<String> zzbSubList = Stream.of(
			"2014111018472484800000000567", "2014111018472484800000000566", "2014111018472522300000002932", "2014111018472481700000000388",
			"2014111018472487900000000748", "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"
	).collect(toList());

	/**
	 * 水上分局，公交分局，地铁分局
	 */
	private static List<String> specialDeptList = Stream.of(
			"2014111018472516100000002607", "2014111018472497300000001287", "2014111018472497300000001308"
	).collect(toList());

	/**
	 * 政治部id
	 */
	private static String ZZB_ID = "2014111018472522300000002937";

	/**
	 * 个人荣誉排名1
	 */
	private static List<String> personRewordOne = Stream.of(
			"革命烈士", "全国公安系统一级英雄模范", "全国公安系统二级英雄模范",
			"全国劳模", "全国特级优秀人民警察", "全国优秀人民警察", "全国三八红旗手"
	).collect(toList());

	/**
	 * 个人荣誉排名2
	 */
	private static List<String> personRewordTwo = Stream.of(
			"省劳模", "省三八红旗手",
			"省五四青年奖章", "江苏好青年"
	).collect(toList());

	/**
	 * 个人荣誉排名3
	 */
	private static List<String> personRewordThree = Stream.of(
			"市劳动模范", "省优秀共青团干部", "省优秀共青团员", "感动南京年度人物",
			"市三八红旗手", "市十大杰出青年", "市五四青年奖章", "市最美老干部"
	).collect(toList());

	private static final String FIRST_LEVEL = "一等功";

	private static final String SECOND_LEVEL = "二等功";

	private static final String THIRD_LEVEL = "三等功";

	/*********************************************************
	 *                    队伍建设成效首页                    *
	 *********************************************************/

	/**
	 * 分局或部门信息
	 */
	public ResultModel getTeamInfo(String idNumber) {
		//返回给前端的数据
		ResultModel resultModel = new ResultModel();
		Map<String, Object> maps = new HashMap<>(16);
		//权限id
		String dataId = RoleOfCompany.getRoleCompanyId(idNumber);
		//权限List
		List<String> roleList = new ArrayList<>();
		//根据权限id查出部门信息
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
			//市局权限
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
			maps.put("map", "南京市公安局");
		} else if (bmList.contains(dataId)) {
			//市局部门权限
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
			//分局权限
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
			maps.put("map", "南京市公安局");
		} else if (fjList.contains(unitInfo.getParentId())
				&& unitInfo.getUnitName().contains(PersonStereoConstant.policeStation.getData())
				&& !(unitInfo.getUnitName().contains(PersonStereoConstant.police_zrq.getData()))) {
			//分局派出所权限
			List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
			sysDeptDao.findByParentIdAndDelStatus(unitInfo.getParentId(), 0).forEach(v -> {
				TeamInfoVo teamInfoVo = new TeamInfoVo();
				if (v.getUnitName().contains(PersonStereoConstant.policeStation.getData())) {
					teamInfoVo.setId(v.getId());
					String fullName = v.getUnitName();
					String str = fullName.substring(0, fullName.indexOf("局"));
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
			//部门下级部门权限
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
			//分局下级部门权限
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
	 * 获取子部门信息
	 */
	public ResultModel getChildUnit(String unitId) {
		//返回给前端的数据
		ResultModel resultModel = new ResultModel();
		//部门和派出所分开统计
		Map<String, Object> maps = new HashMap<>(16);
		//子部门list
		List<SysDeptDo> childUnitList = new ArrayList<>();
		if (ZZB_ID.equals(unitId)) {
			childUnitList.addAll(sysDeptDao.findAllByIdInAndDelStatus(zzbSubList, 0));
		} else {
			childUnitList.addAll(sysDeptDao.findByParentIdAndDelStatus(unitId, 0));
		}
		List<TeamInfoVo> teamInfoVoList = new ArrayList<>();
		//派出所list
		List<TeamInfoVo> policeStationList = new ArrayList<>();
		//部门list
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
		//将子部门分为派出所和部门
		if (teamInfoVoList.size() > 0) {
			teamInfoVoList.forEach(v -> {
				String parentId = sysDeptDao.findByIdAndDelStatus(v.getId(), 0).getParentId();
				if (v.getTeamName().contains(PersonStereoConstant.policeStation.getData()) && !specialDeptList.contains(parentId)) {
					String fullName = v.getTeamName();
					String str = fullName.substring(0, fullName.indexOf("局"));
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
	 * 队伍体量信息
	 */
	public List<Map<String, Object>> getPersonCount(TeamSelect teamSelect) {
		//各个单位人员的身份证号map
		Map<String, Integer> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到每个单位的所有人员身份证号
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				int size = teamSelect.getUnitMap().get(v).size();
				idNoMap.put(v, size);
			});
		}
		//传给前端的map
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<Map.Entry<String, Integer>> arrayList = new ArrayList<>(idNoMap.entrySet());
		//按单位人数降序排序
		arrayList.sort((map1, map2) -> (map2.getValue() - map1.getValue()));
		//输出
		for (Map.Entry<String, Integer> entry : arrayList) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", sysDeptDao.findByIdAndDelStatus(entry.getKey(), 0).getUnitName());
			map.put("value", entry.getValue());
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 队伍平均年龄
	 */
	public List<Map<String, Object>> getAgeInfo(TeamSelect teamSelect) {
		//单位及平均年龄的map
		Map<String, Double> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到每个单位的所有人员身份证号
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				List<TPersonDo> subPerson = teamSelect.getUnitMap().get(v);
				Double avgAge = subPerson.stream().map(p -> Double.parseDouble(p.getAge())).collect(Collectors.averagingDouble(a -> a));
				maps.put(sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName(), avgAge);
			});
		}
		List<Map.Entry<String, Double>> arrayList = new ArrayList<>(maps.entrySet());
		//按平均年龄升序排序
		arrayList.sort((Comparator.comparing(Map.Entry::getValue)));
		//传给前端的map
		List<Map<String, Object>> resultList = new ArrayList<>();
		//输出
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
	 * 队伍女警占比
	 */
	public List<Map<String, Object>> getFemaleCount(TeamSelect teamSelect) {
		//单位及女警人数的list
		List<Map<String, Object>> resultList = new ArrayList<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到每个单位的所有人员身份证号
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
	 * 队伍集体荣誉信息
	 */
	public List<Map<String, Object>> getOrgHonorInfo(TeamSelect teamSelect) {
		//单位及集体荣誉数的list
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				//单位及集体荣誉数的map
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
//     * 队伍综合满意度信息
//     */
//    public List<Map<String, String>> getSatisfiedInfo(TeamSelect teamSelect) {
//        //单位及综合满意度的list
//        List<Map<String, String>> satisfiedList = new ArrayList<>();
//        teamSelect.getUnitId().forEach(v -> {
//            Map<String, String> map = new HashMap<>();
//            String value = satisfiedDataSevice.getUnitSatisfied(v);
//            map.put("name", sysDeptDao.findByIdAndDelStatus(v, 0).getUnitName());
//            map.put("value", StringUtils.isNotEmpty(value)?
//                    String.format("%.2f", Double.parseDouble(value)):null);
//            satisfiedList.add(map);
//        });
//        //去除无满意度队伍，并按满意度高低排序
//        return satisfiedList.stream().filter(v -> StringUtils.isNotEmpty(v.get("value"))).sorted(((o1, o2) -> {
//            Double date1 = Double.parseDouble(o1.get("value"));
//            Double date2 = Double.parseDouble(o2.get("value"));
//            return date2.compareTo(date1);
//        })).collect(toList());
//    }

	/**
	 * 队伍综合满意度信息
	 */
	public List<Map<String, String>> getSatisfiedInfo(TeamSelect teamSelect) {
		return satisfiedDataSevice.getUnitSatisfiedBySql(teamSelect.getUnitId());
	}

	/**
	 * 队伍纪检案件信息
	 */
	public List<Map<String, Object>> getBreakRuleCase(TeamSelect teamSelect) {
		//单位及集体荣誉数的list
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (teamSelect.getUnitId().size() > 0){
			teamSelect.getUnitId().forEach(v -> {
				//单位及队伍纪检案件数的map
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
	 * 悬浮窗信息
	 */
	public Map<String, Object> getWindowsInfo(WindowType windowType) {
		//悬浮窗数据map
		Map<String, Object> resultMap = new HashMap<>();
		String name = sysDeptDao.findByIdAndDelStatus(windowType.getUnitId(), 0).getUnitName();
		//综合满意度
		String satisfied = satisfiedDataSevice.getUnitSatisfied(windowType.getUnitId());
		//纪检案件数量
		int caseCount = teamBuildMapper.findBreakRuleCaseCount("%" + name + "%");
		//集体荣誉数量
		int orgHonorCount = teamBuildMapper.findOrgHonorCount(name);
		//各个维度的颜色等级
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
	 * 队伍个人荣誉信息
	 */
	public List<Map<String, Object>> getPersonHonorInfo(TeamSelect teamSelect) {
		//单位及个人荣誉数的list
		List<Map<String, Object>> resultList = new ArrayList<>();
		//各个单位人员的身份证号map
		Map<String, List<String>> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到每个单位的所有人员身份证号
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
	 * 队伍荣誉占比信息
	 */
	public List<Map<String, Object>> getHonorInfo(TeamSelect teamSelect) {
		//单位荣誉数的list
		List<Map<String, Object>> resultList = new ArrayList<>();
		//各个单位人员的身份证号map
		Map<String, List<String>> idNoMap = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到每个单位的所有人员身份证号
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
			//单位名称
			String name = sysDeptDao.findByIdAndDelStatus(entry.getKey(), 0).getUnitName();
			//单位人数
			int personCount = (int) entry.getValue().stream().filter(Objects::nonNull).count();
			//个人荣誉数
			int personValue = teamBuildMapper.findPersonHonorCount(entry.getValue());
			//集体荣誉数
			int orgValue = teamBuildMapper.findOrgHonorCount(name);
			//荣誉总数
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
	 * 获取色度范围
	 */
	public Map<String, Map<String, Object>> getColorEdge(TeamSelect teamSelect) {
		//色度范围
		Map<String, Map<String, Object>> maps = new HashMap<>(16);
		//绩效考核范围
		Map<String, Object> jxkhMaps = new HashMap<>(16);
		jxkhMaps.put("left", 91d);
		jxkhMaps.put("right", 98d);
		//综合满意度范围
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
		//纪检案件范围
		Map<String, Object> jjajMaps = new HashMap<>(16);
		double jjajLeft = Double.parseDouble(getBreakRuleCase(teamSelect).get(getOrgHonorInfo(teamSelect).size() - 1).get("value").toString());
		double jjajRight = Double.parseDouble(getBreakRuleCase(teamSelect).get(0).get("value").toString());
		jjajMaps.put("left", jjajLeft);
		jjajMaps.put("right", jjajRight);
		//集体荣誉范围
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
	 * 获取综合满意度最高值或最低值
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
	 * 计算颜色等级
	 */
	public int getOneColorLevel(WindowType windowType, TeamSelect teamSelect, String name, String type) {
		//绩效考核,综合满意度,纪检案件,集体荣誉基础值
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
	 * 计算总颜色等级
	 */
	public List<ColorLevelVo> getColorLevel(TeamSelect teamSelect) {
		//各单位的颜色等级
		List<ColorLevelVo> colorLevelVoList = new ArrayList<>();
		//色度范围
		Map<String, Map<String, Object>> maps = getColorEdge(teamSelect);
		//绩效考核,综合满意度,纪检案件,集体荣誉基础值
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
			//该单位综合满意度及最小值
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
			//该单位纪检案件数量及最大值
			double jjajCount = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).size();
			double jjajLeft = Double.parseDouble(maps.get("jjaj").get("left").toString());
			//该单位集体荣誉数量及最小值
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
	 * 地图颜色等级边界处理
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
//     * 计算颜色基础值，并且均分
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
	 * 计算颜色基础值，并且均分
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
	 *                    综合成效评估页面                    *
	 *********************************************************/

	/**
	 * 人员构成（总人数、领导人数、民警人数、党员人数）
	 */
	public ResultModel getPersonDetail(TeamDetail teamDetail) {
		//返回给前端的数据
		ResultModel resultModel = new ResultModel();
		//返回给前端的list
		Map<String, Object> map = new HashMap<>();
		String deptLevel = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getDeptLevel();
		if(zzbSubList.contains(teamDetail.getUnitId())){
			deptLevel="3";
		}
		//队伍总人数
		int count;
		int leaderCount = 0;
		int policeCount;
		int partyCount;
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到所有人员身份证号
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
		//查询队伍领导班子人数
		map.put("leaderCount", leaderCount);
		//查询队伍民警人数
		policeCount = count - leaderCount;
		map.put("policeCount", policeCount);
		//查询队伍党员人数
		partyCount = idNoList.size() > 0 ? teamBuildMapper.findPartyCountByIdNo(idNoList) : 0;
		map.put("partyCount", partyCount);

		resultModel.setResult(map);
		return resultModel;
	}

	/**
	 * 年龄结构概况
	 */
	public Map<String, Object> getAgeInfo(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		//35岁以下人员
		List<TPersonDo> youngList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) < 35).collect(Collectors.toList());
		//35（含）-50岁人员
		List<TPersonDo> middleList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50).collect(Collectors.toList());
		//50岁（含）以上人员
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
	 * 年龄结构详情
	 */
	public Map<String, Object> getAgeDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
//        //根据单位id得到所有人员身份证号
//        List<String> idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
//        //查询人员信息
//        List<AgeDetailVo> tPersonDoList = new ArrayList<>();
//        if (idNoList.size() > 0){
//            tPersonDoList.addAll(teamBuildMapper.findAgeDetail(idNoList));
//        }
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		DecimalFormat df = new DecimalFormat("0.00%");
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		if ("0".equals(teamDetail.getType())) {
			//35岁以下人员
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
			//35（含）-50岁人员
			List<TPersonDo> middleList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
			maps.put("detail", splitList(middleList, pageNum, pageSize));
			maps.put("total", middleList.size());
			maps.put("percent", df.format((double) middleList.size() / (double) tPersonDoList.size()));
		} else if ("2".equals(teamDetail.getType())) {
			//50岁（含）以上人员
			List<TPersonDo> oldList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
			maps.put("detail", splitList(oldList, pageNum, pageSize));
			maps.put("total", oldList.size());
			maps.put("percent", df.format((double) oldList.size() / (double) tPersonDoList.size()));
		}
		return maps;
	}

	/**
	 * 年龄结构导出
	 */
	public void getAgeExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到所有人员身份证号
		List<TPersonDo> tPersonDoList = childUnit.getSubPerson(teamDetail.getUnitId());
		if ("0".equals(teamDetail.getType())) {
			//35岁以下人员
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) < 35)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		} else if ("1".equals(teamDetail.getType())) {
			//35（含）-50岁人员
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 35).filter(v -> Integer.parseInt(v.getAge()) < 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		} else if ("2".equals(teamDetail.getType())) {
			//50岁（含）以上人员
			tPersonDoList = tPersonDoList.stream().filter(v -> Integer.parseInt(v.getAge()) >= 50)
					.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		}
		List<TPersonDo> resultList = tPersonDoList.stream()
				.sorted(Comparator.comparing(v -> Integer.parseInt(v.getAge()))).collect(Collectors.toList());
		//excel标题
		String title = "年龄结构";
		//excel列头信息
		String[] rowsName = new String[]{"姓名", "警号", "年龄"};
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
	 * 教育培训概况
	 */
	public Map<String, Object> getTrainInfo(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		Map<String, Object> maps = new HashMap<>();
		//根据单位id得到所有人员身份证号
		List<String> idNoList = null;
		//查询教育培训详情
		List<TrainCountVo> resultList;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		resultList = teamBuildMapper.trainCountByYear(idNoList);
		//转换格式
		List<EchartsVo> echartsList = new ArrayList<>();
		if (resultList.size() > 0) {
			resultList.forEach(v -> {
				EchartsVo echartsVo = new EchartsVo();
				echartsVo.setName(v.getYear());
				echartsVo.setValue(v.getCount());
				echartsList.add(echartsVo);
			});
		}
		//参训人次最多的年份和人次
		Optional<EchartsVo> op1 = echartsList.stream()
				.max(Comparator.comparingInt(e -> Integer.parseInt(e.getValue())));
		maps.put("chart", echartsList);
		if (echartsList.size() > 0){
			if ("1".equals(teamDetail.getIsReport())) {
				maps.put("content1", echartsList.get(0).getName() + "," + echartsList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum());
				maps.put("content2", op1.get().getName() + "," + op1.get().getValue());
			}else {
				maps.put("content1", "自<strong>" + echartsList.get(0).getName() + "</strong>年共参与培训<strong>" + echartsList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum() + "</strong>人次");
				maps.put("content2", "其中<strong>" + op1.get().getName() +
						"</strong>年培训人次最高，为<strong>" + op1.get().getValue() +
						"</strong>人次");
			}
		}
		return maps;
	}

	/**
	 * 教育培训详情
	 */
	public Map<String, Object> getTrainDetail(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//获取筛选条件
		String type = teamDetail.getType();
		//根据单位id得到所有人员身份证号
		List<String> idNoList = null;
		Map<String, Object> maps = new HashMap<>();
		//查询教育培训详情
		List<TrainDetailVo> resultList;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//根据筛选条件（年份）进行过滤筛选
		resultList = teamBuildMapper.findTrainDetail(idNoList,type);

		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * 教育培训导出
	 */
	public void getTrainExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//查询教育培训详情
		List<TrainDetailVo> resultList;
		//根据单位id得到所有人员身份证号
		List<String> idNoList = null;
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		resultList = teamBuildMapper.findTrainDetail(idNoList,teamDetail.getType());

		//excel标题
		String title = "教育培训";
		//excel列头信息
		String[] rowsName = new String[]{"姓名", "警号", "部门", "培训时间", "组织单位"};
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
	 * 集体荣誉概况
	 */
	public Map<String, Object> getOrgHonor(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//单位及子部门id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());
		Map<String, Object> maps = new LinkedHashMap<>();
		//查询集体荣誉及详情
		List<OrgHonorVo> resultList = new ArrayList<>();
		if (deptList.size() > 0){
			resultList.addAll(teamBuildMapper.findOrgHonorDetail(deptList));
		}
		//集体荣誉分组统计
		Map<String, Long> orgHonorCountMap = resultList.stream().collect(Collectors.groupingBy(OrgHonorVo::getHonorName, counting()));
		//计算集体荣誉全局平均值
		List<TeamCountVo> orgHonorAllList = new ArrayList<>();
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			orgHonorAllList.addAll(teamBuildMapper.findOrgHonor("2"));
		} else {
			orgHonorAllList.addAll(teamBuildMapper.findOrgHonor("3"));
		}
		//集体荣誉平均值
		double orgHonorAvg = orgHonorAllList.stream().collect(averagingDouble(TeamCountVo::getCount));
		double orgHonorCount = resultList.size();

		//转换数据格式
		List<EchartsVo> echartsVoList = new ArrayList<>();
		List<String> honorDesc = new ArrayList<>();
		String one = null, two = null, three = null;
		for (Map.Entry<String, Long> entry : orgHonorCountMap.entrySet()) {
			EchartsVo echartsVo = new EchartsVo();
			echartsVo.setName(entry.getKey());
			echartsVo.setValue(entry.getValue().toString());
			echartsVoList.add(echartsVo);
			if ("集体一等功".equals(entry.getKey())) {
				one = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>集体一等功</strong>";
			} else if ("集体二等功".equals(entry.getKey())) {
				two = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>集体二等功</strong>";
			} else if ("集体三等功".equals(entry.getKey())) {
				three = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>集体三等功</strong>";
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
		maps.put("content1", "共获得<strong>" + resultList.size() + "</strong>项集体荣誉");
		if (honorDesc.size() > 0) {
			maps.put("content2", "其中" + honorDescStr[0]);
		}
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			maps.put("content3", "较全局平均集体荣誉个数<strong>" + compare(orgHonorAvg, orgHonorCount) + "</strong>");
		}
		return maps;
	}

	/**
	 * 集体荣誉详情
	 */
	public Map<String, Object> getOrgHonorDetail(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//单位及子部门id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());

		//获取过滤条件
		String type = teamDetail.getType();

		//返回集体荣誉及详情
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
	 * 集体荣誉导出
	 */
	public void getOrgHonorExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//单位及子部门id
		List<String> deptList = childUnit.getSubDeptId(teamDetail.getUnitId());
		//返回集体荣誉及详情
		List<OrgHonorVo> resultList = new ArrayList<>();
		if (deptList.size() > 0){
			resultList = teamBuildMapper.findOrgHonorDetail(deptList);
		}
		//获取过滤条件
		String type = teamDetail.getType();

		//返回集体荣誉及详情
		if (deptList.size() > 0&&type!=null){
			resultList = resultList.stream().filter(c->c.getHonorName()!=null).filter(v->v.getHonorName().equals(type)).collect(toList());
		}
		resultList.sort(Comparator.comparing((OrgHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())).thenComparing((OrgHonorVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		//excel标题
		String title = "集体荣誉";
		//excel列头信息
		String[] rowsName = new String[]{"表彰称号", "部门", "授奖时间", "表彰部门"};
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
	 * 个人荣誉概况
	 */
	public Map<String, Object> getPersonHonor(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到所有人员身份证号
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//查询个人荣誉数量
		List<PersonHonorVo> resultList =  teamBuildMapper.findPersonHonorDetail(idNoList,null);
//        if (idNoList.size() > 0){
//            resultList.addAll(teamBuildMapper.findPersonHonorDetail(idNoList));
//        }
//        List<PersonHonorVo> resultList = new ArrayList<>(teamBuildMapper.findPersonHonorDetail(idNoList));
		//个人荣誉分组统计
		Map<String, Long> personHonorCountMap = resultList.stream().filter(v->v.getAwardName()!=null).collect(Collectors.groupingBy(PersonHonorVo::getAwardName, counting()));
		//合并一等功、二等功、三等功
		Map<String, Long> groupPersonHonorMap = new HashMap<>(16);
		long firstCount = 0;
		long secondCount = 0;
		long thirdCount = 0;
		for (Map.Entry<String, Long> entry : personHonorCountMap.entrySet()) {
			if (entry.getKey().contains("一等功")) {
				firstCount += entry.getValue();
			} else if (entry.getKey().contains("二等功")) {
				secondCount += entry.getValue();
			} else if (entry.getKey().contains("三等功")) {
				thirdCount += entry.getValue();
			} else {
				groupPersonHonorMap.put(entry.getKey(), entry.getValue());
			}
		}
		if ("1".equals(teamDetail.getIsReport())){
			groupPersonHonorMap.put("一等功", firstCount);
			groupPersonHonorMap.put("二等功", secondCount);
			groupPersonHonorMap.put("三等功", thirdCount);
		}else {
			if (firstCount > 0) {
				groupPersonHonorMap.put("一等功", firstCount);
			}
			if (secondCount > 0) {
				groupPersonHonorMap.put("二等功", secondCount);
			}
			if (thirdCount > 0) {
				groupPersonHonorMap.put("三等功", thirdCount);
			}
		}

		//转换数据格式
		List<EchartsVo> echartsVoList = new ArrayList<>();
		List<String> honorDesc = new ArrayList<>();
		String one = null, two = null, three = null;
		for (Map.Entry<String, Long> entry : groupPersonHonorMap.entrySet()) {
			EchartsVo echartsVo = new EchartsVo();
			echartsVo.setName(entry.getKey());
			echartsVo.setValue(entry.getValue().toString());
			echartsVoList.add(echartsVo);
			if ("一等功".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					one = "," + entry.getValue().toString();
				} else {
					one = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>一等功</strong>";
				}
			} else if ("二等功".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					two = "," + entry.getValue().toString();

				} else {
					two = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>二等功</strong>";
				}
			} else if ("三等功".equals(entry.getKey())) {
				if ("1".equals(teamDetail.getIsReport())) {
					three = "," + entry.getValue().toString();
				} else {
					three = ",<strong>" + entry.getValue().toString() + "</strong>项" + "<strong>三等功</strong>";
				}
			}
		}
		//计算个人荣誉全局平均值
		List<TeamCountVo> personHonorAllList = new ArrayList<>();
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			personHonorAllList.addAll(teamBuildMapper.findPersonHonor("2"));
		} else {
			personHonorAllList.addAll(teamBuildMapper.findPersonHonor("3"));
		}
		//个人荣誉平均值
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
		//去掉value为0的数据
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
			maps.put("content1", "共获得<strong>" + resultList.size() + "</strong>项个人荣誉");
		}
		if (honorDesc.size() > 0) {
			maps.put("content2", "其中" + honorDescStr[0]);
		}
		if (teamBuildMapper.findUnitId().contains(teamDetail.getUnitId())) {
			if ("1".equals(teamDetail.getIsReport())) {
				maps.put("content3", String.format("%.2f", personHonorAvg) + "," + compare(personHonorAvg, personHonorCount));
			}
			else {
				maps.put("content3", "较全局平均个人荣誉<strong>" + String.format("%.2f", personHonorAvg) + "</strong>项<strong>" + compare(personHonorAvg, personHonorCount) + "</strong>");
			}
		}
		return maps;
	}

	/**
	 * 个人荣誉详情
	 */
	public Map<String, Object> getPersonHonorDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new HashMap<>();
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到所有人员身份证号
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//获取筛选条件
		String type = teamDetail.getType();

		//查询个人荣誉数量
		List<PersonHonorVo> resultList  = teamBuildMapper.findPersonHonorDetail(idNoList,teamDetail.getType());
        /*if (idNoList.size() > 0){
            resultList.addAll(teamBuildMapper.findPersonHonorDetail(idNoList));
        }*/


		resultList.sort(Comparator.comparing((PersonHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())));
		//获取请求参数中的页码和每页数据量
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();

		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * 个人荣誉导出
	 */
	public void getPersonHonorExport(TeamDetail teamDetail) {
		ChildUnit childUnit = new ChildUnit();
		//根据单位id得到所有人员身份证号
		List<String> idNoList = new ArrayList<>();
		if(!NJSGAJ_ID.equals(teamDetail.getUnitId())){
			idNoList = new ArrayList<>(childUnit.getSubPersonIdNos(teamDetail.getUnitId()));
		}
		//查询个人荣誉数量
		List<PersonHonorVo> resultList= teamBuildMapper.findPersonHonorDetail(idNoList,teamDetail.getType());

		resultList.sort(Comparator.comparing((PersonHonorVo v)->v.getDate(),Comparator.nullsLast(Comparator.reverseOrder())));
		//excel标题
		String title = "个人荣誉";
		//excel列头信息
		String[] rowsName = new String[]{"表彰称号", "姓名", "警号", "授奖时间", "表彰部门"};
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
	 * 纪检案件概况
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
			caseList.forEach(a->a.setName(a.getName()+"年"));
		}
		chartMap.put("dept", new ArrayList<>());
		chartMap.put("personal", caseList);
		maps.put("chart", chartMap);
		//查询纪检案件性质信息
		List<RulesAndCount> rulesAndCountList = teamBuildMapper.findRulesCountByWtxz("%" + name + "%");
		List<BreakRuleCaseVo> ruleList = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null);
		if (caseList.size() > 0) {
			String year = caseList.get(0).getName();
			int count = caseList.stream().mapToInt(v -> Integer.parseInt(v.getValue())).sum();
			maps.put("content1", "自<strong>" + year + "</strong>年共有<strong>" + count + "</strong>人涉及纪检案件");
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
				count.append(",<strong>").append(rulesAndCountList.get(i).getCount()).append("</strong>人涉及<strong>").append(rulesAndCountList.get(i).getWtxz()).append("</strong>");
			}
			if(count.length() >1) {
				maps.put("content2", "其中" + count.toString());
			}
		}
		maps.put("content3", "");
		return maps;
	}

	/**
	 * 纪检案件详情
	 */
	public Map<String, Object> getBreakRuleCaseDetail(TeamDetail teamDetail) {
		Map<String, Object> maps = new LinkedHashMap<>();
		String name;
		if ("2014110416460086100000002942".equals(teamDetail.getUnitId())){
			name = "";
		}else {
			name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
		}

		//获取过滤条件(年),去掉结尾的 年
		String type = teamDetail.getType();
//        String type ="";
//        if(type1!=null){
//             type=type1.substring(0,teamDetail.getType().indexOf("年"));
//        }
//        //查询纪检案件信息
//        if(type=""){
//
//        }
		List<BreakRuleCaseVo> resultList=new ArrayList<>();
		if(type!=null){
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).filter(c->c.getDate().substring(0,4).equals(type.substring(0,type.indexOf("年")))).collect(toList());
		}else{
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).collect(toList());
		}
		resultList.sort(Comparator.comparing((BreakRuleCaseVo v)->v.getDate(),Comparator.reverseOrder()).thenComparing((BreakRuleCaseVo v)->CommonUnit.deptList.indexOf(v.getDeptName())));
		if (resultList.size() > 0) {
			resultList.forEach(v -> v.setType("个人"));
		}
		int pageNum = teamDetail.getPageNum();
		int pageSize = teamDetail.getPageSize();
		maps.put("detail", splitList(resultList, pageNum, pageSize));
		maps.put("total", resultList.size());
		return maps;
	}

	/**
	 * 纪检案件导出
	 */
	public void getBreakRuleCaseExport(TeamDetail teamDetail) {
		//获取单位名称
		String name;
		if ("2014110416460086100000002942".equals(teamDetail.getUnitId())){
			name = "";
		}else {
			name = sysDeptDao.findByIdAndDelStatus(teamDetail.getUnitId(), 0).getUnitName();
		}
		//查询纪检案件信息
		List<BreakRuleCaseVo> resultList = teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null);
		if (resultList.size() > 0) {
			resultList.forEach(v -> v.setType("个人"));
		}
		String type = teamDetail.getType();
		if(type!=null){
			resultList= teamBuildMapper.findBreakRuleCaseDetail("%" + name + "%",null).stream().filter(v->v.getDate()!=null).filter(c->c.getDate().substring(0,4).equals(type.substring(0,4))).collect(toList());
		}
		//excel标题
		String title = "纪检案件";
		//excel列头信息
		String[] rowsName = new String[]{"单位", "姓名", "警号", "处分决定时间", "处分结束时间", "问题性质"};
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
	 * 获取占比描述
	 */
	private String compare(double avg, double count) {
		String description;
		DecimalFormat df = new DecimalFormat("0.00%");
		if (count >= avg) {
			description = "高" + df.format((count - avg) / avg);
		} else {
			description = "低" + df.format((avg - count) / avg);
		}
		return description;
	}

	/**
	 * list分页
	 */
	private List splitList(List list, int pageNum, int pageSize) {
		int sum = list.size(); //记录总数
		int pageNo = (pageNum - 1) * pageSize;   //每页的起始索引
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
	 * excel导出
	 */
	public void exportExcel(String title, String[] rowsName, List<Object[]> list) {
		//给文件命名。随机命名
		String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
		//告诉浏览器数据格式，将头和数据传到前台
		String headStr = "attachment; filename=\"" + fileName + "\"";
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", headStr);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//调用poi的工具类
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
	 * 集体荣誉排序
	 *
	 * @param rewardAndCountList
	 * @return
	 */
	private List<EchartsVo> getOrgHonorSort(List<EchartsVo> rewardAndCountList) {
		List<EchartsVo> resultList = new ArrayList<>();
		List<List<EchartsVo>> list = new ArrayList<>();
		//增加11个不同集体荣誉的list
		for (int i = 0; i < 11; i++) {
			list.add(new ArrayList<>());
		}
		for (EchartsVo a : rewardAndCountList) {
			if ("全国优秀公安局".equals(a.getName())) {
				list.get(0).add(a);
			} else if ("全国优秀公安基层单位".equals(a.getName())) {
				list.get(1).add(a);
			} else if ("全省优秀公安局".equals(a.getName())) {
				list.get(2).add(a);
			} else if ("集体一等功".equals(a.getName())) {
				list.get(3).add(a);
			} else if ("集体二等功".equals(a.getName())) {
				list.get(4).add(a);
			} else if ("集体三等功".equals(a.getName())) {
				list.get(5).add(a);
			} else if ("集体嘉奖".equals(a.getName())) {
				list.get(6).add(a);
			} else if ("通报表扬".equals(a.getName())) {
				list.get(7).add(a);
			} else if ("荣誉称号".equals(a.getName())) {
				list.get(8).add(a);
			} else if ("先进集体".equals(a.getName())) {
				list.get(9).add(a);
			}
		}
		list.forEach(resultList::addAll);
		return resultList;
	}

	/**
	 * 个人荣誉排序
	 *
	 * @param rewardAndCountList
	 * @return
	 */
	private List<EchartsVo> getPersonHonorSort(List<EchartsVo> rewardAndCountList) {
		List<EchartsVo> resultList = new ArrayList<>();
		//分组比较
		List<EchartsVo> listOne = new ArrayList<>(), listTwo = new ArrayList<>(), listThree = new ArrayList<>();
		List<List<EchartsVo>> sortOne = new ArrayList<>(), sortTwo = new ArrayList<>(), sortThree = new ArrayList<>();
		//第一组排序list
		for (int i = 0; i < 7; i++) {
			sortOne.add(new ArrayList<>());
		}
		//第二组排序list
		for (int i = 0; i < 4; i++) {
			sortTwo.add(new ArrayList<>());
		}
		//第三组排序list
		for (int i = 0; i < 8; i++) {
			sortThree.add(new ArrayList<>());
		}
		//一等功、二等功、三等功
		List<EchartsVo> firstLevel = new ArrayList<>(), secondLevel = new ArrayList<>(), thirdLevel = new ArrayList<>();
		//其他奖励
		List<EchartsVo> otherList = new ArrayList<>();
		//比较结果
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
				if ("革命烈士".equals(a.getName())) {
					sortOne.get(0).add(a);
					listOne.remove(a);
					break;
				} else if ("全国公安系统一级英雄模范".equals(a.getName())) {
					sortOne.get(1).add(a);
					listOne.remove(a);
					break;
				} else if ("全国公安系统二级英雄模范".equals(a.getName())) {
					sortOne.get(2).add(a);
					listOne.remove(a);
					break;
				} else if ("全国劳模".equals(a.getName())) {
					sortOne.get(3).add(a);
					listOne.remove(a);
					break;
				} else if ("全国特级优秀人民警察".equals(a.getName())) {
					sortOne.get(4).add(a);
					listOne.remove(a);
					break;
				} else if ("全国优秀人民警察".equals(a.getName())) {
					sortOne.get(5).add(a);
					listOne.remove(a);
					break;
				} else if ("全国三八红旗手".equals(a.getName())) {
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
				if ("省劳模".equals(a.getName())) {
					sortTwo.get(0).add(a);
					listTwo.remove(a);
					break;
				} else if ("省三八红旗手".equals(a.getName())) {
					sortTwo.get(1).add(a);
					listTwo.remove(a);
					break;
				} else if ("省五四青年奖章".equals(a.getName())) {
					sortTwo.get(2).add(a);
					listTwo.remove(a);
					break;
				} else if ("江苏好青年".equals(a.getName())) {
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
				if ("市劳动模范".equals(a.getName())) {
					sortThree.get(0).add(a);
					listThree.remove(a);
					break;
				} else if ("省优秀共青团干部".equals(a.getName())) {
					sortThree.get(1).add(a);
					listThree.remove(a);
					break;
				} else if ("省优秀共青团员".equals(a.getName())) {
					sortThree.get(2).add(a);
					listThree.remove(a);
					break;
				} else if ("感动南京年度人物".equals(a.getName())) {
					sortThree.get(3).add(a);
					listThree.remove(a);
					break;
				} else if ("市三八红旗手".equals(a.getName())) {
					sortThree.get(4).add(a);
					listThree.remove(a);
					break;
				} else if ("市十大杰出青年".equals(a.getName())) {
					sortThree.get(5).add(a);
					listThree.remove(a);
					break;
				} else if ("市五四青年奖章".equals(a.getName())) {
					sortThree.get(6).add(a);
					listThree.remove(a);
					break;
				} else if ("市最美老干部".equals(a.getName())) {
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
	 * 驼峰命名转下划线命名
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
		items.put("companyName","单位");
		items.put("basicPartDev","党建基础");
		items.put("workHandlePower","工作执行力");
		items.put("lgcm","立功创模");
		items.put("zgjs","政工竞赛");
		items.put("xwxc","新闻宣传");
		items.put("zgxxdy","政工信息调研");
		items.put("totalScore","平台考核总分");
		kpiItems.put("2019",items);

		items = new LinkedHashMap<>();
		items.put("companyName","单位");
		items.put("basicPartDev","党建基础");
		items.put("lgcm","立功创模");
		items.put("xwxc","新闻宣传");
		items.put("item1","警营专项文化和两微运维");
		items.put("item2","警营文体工作");
		items.put("zgxxdy","政工信息调研");
		items.put("workHandlePower","工作执行力");
		items.put("item3","警体运动会");
		items.put("item4","实战训练");
		items.put("item5","教官队伍建设");
		items.put("item6","警辅管理");
		items.put("item7","工作任务完成");
		items.put("totalScore","平台考核总分");
		kpiItems.put("2018",items);

		items = new LinkedHashMap<>();
		items.put("companyName","单位");
		items.put("totalScore","平台考核总分");
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
			sql +="ORDER BY TRANSLATE(QUARTER,'一二三四五六七八九' , '123456789') DESC,TRANSLATE(GROUPNAME,'一二三四五六七八九' , '123456789'),TOTAL_SCORE DESC";
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
				title += year+"年";
			}
			if(StringUtils.isNotBlank(quarter)){
				title += quarter;
			}
			if(StringUtils.isNotBlank(groupName)){
				title += groupName;
			}
			String[] rowsName = new String[]{"年份","季度","考核单元","单位","考核成绩","排名"};
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

			exportExcel(title+"政工绩效考核得分表",rowsName,dataList);
			return null;
		}else{
			return ResultModel.success(ruleList);
		}
	}

	/**
	 * 获取绩效考核年份菜单
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
			m.put("label",s+"年");
			m.put("children",getKPIQuarterMenu(s));
			result.add(m);
		}

		return result;
	}

	/**
	 * 获取绩效考核季度菜单
	 * @return
	 * @param year
	 */
	public List<Map<String,Object>>  getKPIQuarterMenu(String year)
	{
		List<Map<String,Object>> result = new ArrayList<>();

		List<String> quarters;
		//从2019年起，使用擎天数据
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
	 * 获取绩效考核组别菜单
	 * @return
	 */
	public List<Map<String,Object>> getKPIGroupMenu(String year,String quarter)
	{
		List<Map<String,Object>> result = new ArrayList<>();
		List<String> groupMenu;
		/**
		 * 判断取哪个库的绩效数据
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
