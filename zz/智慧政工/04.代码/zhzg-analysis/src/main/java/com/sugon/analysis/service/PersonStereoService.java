package com.sugon.analysis.service;

import com.sugon.analysis.domain.data.object.oracle.*;
import com.sugon.analysis.domain.data.query.*;
import com.sugon.analysis.domain.data.subset.CompanyNew;
import com.sugon.analysis.domain.data.subset.CompanyNewTreeSubset;
import com.sugon.analysis.domain.data.subset.CustomCache;
import com.sugon.analysis.domain.param.DeptAndPermisson;
import com.sugon.analysis.domain.param.DeptAndType;
import com.sugon.analysis.domain.param.PersonstereoDetailVo;
import com.sugon.analysis.domain.view.personstereo.*;
import com.sugon.analysis.domain.view.return2view.*;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.TeamPersonnelMapper;
import com.sugon.analysis.repository.mongodb.CacheRepository;
import com.sugon.analysis.repository.oracle.*;
import com.sugon.analysis.util.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.sugon.analysis.enums.ResponsesStatus.SUCCESS;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description: 人员立体解剖数据
 * @date:2019/10/28
 */
@Slf4j
@Service
public class PersonStereoService {
    @Value("${local.url}")
    private String webPath;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private TPersonDao tPersonDao;

    @Resource
    private SysDeptDao sysDeptDao;

    @Resource
    private TEducationDao tEducationDao;

    @Resource
    private SgQueryCategoryDao sgQueryCategoryDao;

    @Resource
    private TeamPersonnelMapper teamPersonnelMapper;

    @Resource
    private TPerformDao tPerformDao;

    @Resource
    private TPerformGroupDao tPerformGroupDao;

    @Resource
    private TPefrormGroupDetailDao tPefrormGroupDetailDao;

    @Resource
    private PerComparisonService perComparisonService;

    @Resource
    private PersonalAnalysisService personalAnalysisService;
    @Resource
    private TeamBuildService teamBuildService;

    @Resource
    private SysUserDao sysUserDao;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private DecimalFormat decimalFormat1 = new DecimalFormat("0.00%");

    private static final int ONE_THOUSAND = 1000;

    /**
     * @人数
     */
    private static final String PERSON_SUM = "总人数";
    /**
     * @平均年龄
     */
    private static final String AVG_AGE = "平均年龄";
    /**
     * @正处职
     */
    private static final String UPRIGHT_POSITION = "副处比例";

    private static final String C_DEPUTY_DUTIES = "副处职";

    private static final String C_PROFESSIONAL_DUTIES = "正科职";

    private static final String C_SUBSIDIARY_DUTIES = "副科职";

    private static final String POLICE_STATION = "派出所";

    private static final String POLICE_SCHOOL = "警察院校毕业生";

    private static final String SOLDIER = "转业及复员军人";

    private static final String SOCIAL = "社会招考";

    private static final String FOLD = "实施公务员法机关调入";

    private static final String ELSE_SOURCE = "其他";

    private static final String ONE = "1";

    private static final String ONE_THOUSAND_AND_TREE = "1003";

    private static final String DOCTOR = "博士";

    private static final String MASTER = "硕士";

    private static final String COLLEGE = "本科";

    private static final String JUNIOR_COLLEGE = "大专";

    private static final String UNDER_HIGHT_SCHOOL = "高中及以下";

    private static final String PERCENT = "%";

    private static final String DEPTNAME = "单位名称";

    private static final String ASSESSENT_UNIT = "政工考核单元";


    /**
     * 市局
     */
    private static final String URBAN_ID = "2014110416460086100000002942";

    /**
     * 政治部
     */
    private static final String POLITICAL = "2014111018472522300000002937";

    /**
     * 政治部综合处
     */
    private static final String GENERAL_OFFICE = "2014111018472475400000000147";

    /**
     * 政治部下属部门
     */
    private String[] politicalSub = new String[]{"2014111018472484800000000567", "2014111018472484800000000566",
            "2014111018472522300000002932", "2014111018472481700000000388", "2014111018472487900000000748",
            "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"};

    /**
     * 所有的分局
     */
    private String[] branch = new String[]{"2014111018472522300000002936", "2014111018472508300000001991",
            "2014111018472517600000002837", "2014111018472505100000001665", "2014111018472516100000002727",
            "2014111018472500400000001420", "2014111018472511400000002322", "2014111018472512900000002390",
            "2014111018472516100000002593", "2014111018472517600000002930", "2014111018472509800000002206",
            "2014111018472505100000001734", "2014111018472497300000001265"};

    /**
     * 正处职以以上
     */
    private String[] upInthePost = new String[]{"0720", "0717", "0718", "0715", "0716", "0620", "0520"};

    /**
     * 副处职以上
     */
    private String[] upDutys = new String[]{"0730", "0820", "0720", "0717", "0718", "0715", "0716", "0620", "0520"};

    /**
     * 来源
     */
    private String[] source = new String[]{"警察院校毕业生", "转业及复员军人", "社会招考", "实施公务员法机关调入", "其它"};

    private String[] ageRange = new String[]{"50岁以上", "40-50岁", "30-40岁", "20-30岁", "20岁以下"};

    private String[] unitWorkAge = new String[]{"5年以下", "5(含)-10", "10(含)-20", "20(含)-30", "30(含)以上"};

    private String[] marriage = new String[]{"未婚", "已婚", "丧偶", "离婚"};

    private String[] schoolType = new String[]{"警察院校", "部队院校", "地方院校", "其它院校"};

    private String[] social = new String[]{"101", "102", "104", "127", "126", "112", "111", "106", "107", "105", "128"};

    private String[] entry = new String[]{"120", "134", "131"};

    private String[] soldier = new String[]{"115", "116"};

    /**
     * 主要领导和政工领导
     */
    private String[] headrs = new String[]{"18", "19", "20"};

    /**
     * 内下设所长和教导员
     */
    private String[] headrs1 = new String[]{"30", "31", "32", "33"};

    /**
     * 领导班子成员
     */
    private String[] headrs2 = new String[]{"18", "19", "20", "21"};

    /**
     * 领导班子成员内下设
     */
    private String[] headrs3 = new String[]{"30", "31", "32", "33", "34", "35"};

    /**
     * overall 市局领导班子权限角色
     */
    private String[] overAllHeader=new String[]{"7","8"};

    @Scheduled(cron = "0 30 4 * * ?")
    public void updateCache(){
        List<CustomCache> list = Cache.getCacheListByKeyLike("personStereo&");
        for (CustomCache cache : list) {
            String[] keys =cache.getKey().split("&");
            DeptAndType deptAndType = new DeptAndType();
            deptAndType.setCompanyId(keys[1]);
            deptAndType.setType(keys[2]);
            Cache.delete(cache.getKey());
            getPersonStereoInfo(deptAndType);
        }
    }

    @Autowired
    private CacheRepository cacheRepository;
    /**
     * @param deptAndType
     * @return
     * @根据单位部门id统计人员的一些信息
     */
    public List<PersonStereoVo> getPersonStereoInfo(DeptAndType deptAndType) {
        String companyId;
        if (StringUtils.isNotBlank(deptAndType.getCompanyId())) {
            companyId = deptAndType.getCompanyId();
        } else {
            companyId = RoleOfCompany.getRoleCompanyId(deptAndType.getIdNumber());
        }


        String cacheKey = "personStereo&"+companyId+"&"+deptAndType.getType();
        List<PersonStereoVo> personStereoVos = Cache.getList(cacheKey, PersonStereoVo.class);
        if(personStereoVos !=null){
            return personStereoVos;
        }


        List<PersonStereoVo> personStereoVosList = new ArrayList<>();
        List<PersonStereoVo> result = new ArrayList<>();

        List<DeptAndPermisson> departIds = getCompanyTree(companyId);

        if (departIds.size() > 0) {
            departIds.forEach(v -> {
                //遍历之前清空childList
                childList = new ArrayList<>();
                //获得所有该部门下的id
                List<String> deptIdsQoList = getCompanyAllId(v.getId());
                //根据部门ids进行统计
                Query query = Query.query(Criteria.where("ID").is(v.getId()));
                List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class,
                        "companyNewTreeSubset");
                if (list.size() > 0) {
                    CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
                    PersonStereoVo personStereoVo = getPersonStereoAnalysis(deptIdsQoList);
                    //查询该部门的一把手二把手
                    List<HeadInfo> headInfoList = getDeptHead(v.getId());
                    personStereoVo.setHeadInfoList(headInfoList);
                    personStereoVo.setCompanyId(v.getId());

                    String name = companyNewTreeSubset.getCompanyList().get(0).getSimpleName();
                    if (!"政治部".equals(name) && name.contains("政治部")) {
                        personStereoVo.setDeptName(name.replaceAll("政治部", "").trim());
                    } else {
                        personStereoVo.setDeptName(name);
                    }
                    personStereoVo.setIsSub(v.getIsSub());
                    personStereoVo.setIsClick(v.getIsClick());
                    personStereoVo.setIsWhite(v.getIsWhite());
                    personStereoVosList.add(personStereoVo);
                }
            });
            result = getSortByType(personStereoVosList, deptAndType.getType());
        }
        Cache.save(cacheKey,result);
        return result;
    }

    /**
     * 获取领导人
     *
     * @param unitId
     * @return
     */
    public List<HeadInfo> getDeptHead(String unitId) {
        SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(unitId, 0);
        List<String> heardsList = new ArrayList<>();
        if ("2".equals(sysDeptDo.getDeptLevel())) {
            if (POLITICAL.equals(unitId)) {
                heardsList.add("9");
//                heardsList.addAll(Arrays.asList(headrs));
            } else if ("2014111018472484800000000567".equals(unitId)) {
                heardsList.add("10");
            } else {
                heardsList.addAll(Arrays.asList(headrs));
            }
        }
        if ("3".equals(sysDeptDo.getDeptLevel())) {
            heardsList.addAll(Arrays.asList(headrs1));
        }
        return getDetailHeadList(heardsList, unitId);
    }


    private List<HeadInfo> getDetailHeadList(List<String> heardsList, String unitId) {
        List<HeadInfo> headInfoList = new ArrayList<>();
        if (heardsList.size() > 0) {
            List<String> deptIdsQoList = new ArrayList<>();
            if (unitId.equals(POLITICAL)) {
                deptIdsQoList.add(URBAN_ID);
            } else if ("2014111018472484800000000567".equals(unitId)) {
                deptIdsQoList.add("2014111018472522300000002937");

            } else {
                deptIdsQoList.addAll(getCompanyAllId(unitId));
            }
            //找出所有的人
            //in可能超过1000，处理
            List<SysUserDo> sysUserDoList=new ArrayList<>();
            if (deptIdsQoList.size() > 1000) {
                int listSize = deptIdsQoList.size() / 1000;
                for (int i = 0; i < deptIdsQoList.size() / 1000; i++) {
                    sysUserDoList.addAll(sysUserDao.findAllByDelStatusAndPostTypeAndDeptIdIn("0", 0,deptIdsQoList.subList(1000 * i, 1000 * (i + 1))));
                }
                sysUserDoList.addAll(sysUserDao.findAllByDelStatusAndPostTypeAndDeptIdIn("0", 0,deptIdsQoList.subList(1000 * listSize, deptIdsQoList.size())));
            } else {
                sysUserDoList.addAll(sysUserDao.findAllByDelStatusAndPostTypeAndDeptIdIn("0", 0, deptIdsQoList));
            }


            sysUserDoList = sysUserDoList.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                            Comparator.comparing(SysUserDo::getIdentitynum))), ArrayList::new));
            if (sysUserDoList.size() > 0) {
                sysUserDoList.forEach(v -> {
                    String roleIds = "36";
                    if(StringUtils.isNotBlank(v.getPersonRoles())) {
                        roleIds = v.getPersonRoles();
                    }
                    List<String> listString = Arrays.asList(roleIds.split(","));
                    String[] strings = new String[heardsList.size()];
                    String result = RoleOfCompany.isGetRoles(listString, heardsList.toArray(strings));
                    if ("1".equals(result)) {
                        HeadInfo headInfo = new HeadInfo();
                        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(v.getIdentitynum(), "0");
                        if (tPersonDo != null) {
                            headInfo.setDuty(tPersonDo.getPost());
                            if (StringUtils.isNotEmpty(tPersonDo.getOfficeSimpleName())) {
                                headInfo.setDeptName(tPersonDo.getOrgSimpleName() + tPersonDo.getOfficeSimpleName());
                            } else {
                                headInfo.setDeptName(tPersonDo.getOrgSimpleName());
                            }
                        }
                        headInfo.setName(v.getChinaName());
                        headInfo.setIdNo(v.getIdentitynum());
//                        headInfo.setImg(perComparisonService.download(v.getIdentitynum()));
                        headInfo.setImg(webPath + "teamPerson/getFtpImgPath?idNumber=" + v.getIdentitynum());
                        if (listString.contains("9")) {
                            headInfo.setSort(1);
                        } else if (listString.contains("10")) {
                            headInfo.setSort(2);
                        } else if (listString.contains("20")) {
                            headInfo.setSort(3);
                        } else if (listString.contains("18") || listString.contains("19")) {
                            headInfo.setSort(4);
                        } else if (listString.contains("21")) {
                            headInfo.setSort(5);
                        } else if (listString.contains("32") || listString.contains("33")) {
                            headInfo.setSort(6);
                        } else if (listString.contains("30") || listString.contains("31")) {
                            headInfo.setSort(7);
                        } else if (listString.contains("34") || listString.contains("35")) {
                            headInfo.setSort(8);
                        }
                        headInfoList.add(headInfo);
                    }

                });
            }

        }
        List<HeadInfo> result = new ArrayList<>();
        if (headInfoList.size() > 0) {
            result = headInfoList.stream().sorted(Comparator.comparing(HeadInfo::getSort)).collect(Collectors.toList());
        }
        return result;

    }

    private List<PersonStereoVo> getSortByType(List<PersonStereoVo> personStereoVoList, String type) {
        List<PersonStereoVo> result = new ArrayList<>();
        if (personStereoVoList.size() > 0) {
            if (PERSON_SUM.equals(type)) {
                result = personStereoVoList.
                        stream().
                        sorted(Comparator.comparing(PersonStereoVo::getPersonSum).reversed()).
                        collect(Collectors.toList());
            } else if (AVG_AGE.equals(type)) {

                result = personStereoVoList.
                        stream().
                        filter(v -> v.getAveAge() != null).
                        sorted(Comparator.comparing(PersonStereoVo::getAveAge).reversed()).
                        collect(Collectors.toList());
            } else if (UPRIGHT_POSITION.equals(type)) {
                result = personStereoVoList.
                        stream().
                        sorted(Comparator.comparing(PersonStereoVo::getUprightPosition).reversed()).
                        collect(Collectors.toList());
            } else if ("组织机构".equals(type)) {
                result = personStereoVoList;
                result.sort((o1, o2) -> {
                    int i1 = CommonUnit.deptList.indexOf(o1.getDeptName());
                    int i2 = CommonUnit.deptList.indexOf(o2.getDeptName());
                    if (i1 == -1) {
                        return 1;
                    }
                    if (i2 == -1) {
                        return -1;
                    }
                    return i1 - i2;
                });
            }
        }

        return result;


    }

    /**
     * @param id
     * @return
     * @获取传过来id对应所有的部门
     */
    List<String> getCompanyAllId(String id) {
        //用来装部门Id
        List<String> deptIdsQoList = new ArrayList<>();
        //如果政治部
        if (POLITICAL.equals(id)) {
            deptIdsQoList.add(id);
            deptIdsQoList.addAll(Arrays.asList(politicalSub));
        } else {
            deptIdsQoList.add(id);
        }
//        //重复代码？？？？为何add两遍
//        deptIdsQoList.add(id);
        List<String> deptResultId = new ArrayList<>(deptIdsQoList);
        if (deptIdsQoList.size() > 0) {
            deptIdsQoList.forEach(v -> {
                Query query = Query.query(Criteria.where("ID").is(v));
                List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class,
                        "companyNewTreeSubset");
                if (list.size() > 0) {
                    CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
                    List<CompanyNew> companyList = companyNewTreeSubset.getCompanyList();
                    if (companyList.size() > 0) {
                        childList = new ArrayList<>();
                        deptResultId.addAll(getChildId(companyList.get(0).getChildCompany()));
                    }
                }

            });
        }
        //去重
        return deptResultId.stream().distinct().collect(Collectors.toList());
    }

    private List<String> childList = new ArrayList<>();

    /**
     * @param childCompany
     * @return
     * @递归查询子部门
     */
    private List<String> getChildId(List<CompanyNew> childCompany) {
        if (childCompany.size() > 0) {
            childCompany.forEach(v -> {
                childList.add(v.getId());
                if (v.getChildCompany().size() > 0) {
                    getChildId(v.getChildCompany());
                }
            });
        }
        return childList;
    }

    private PersonStereoVo getPersonStereoAnalysis(List<String> deptIds) {

        PersonStereoVo personStereoVo = new PersonStereoVo();
        //查询该部门的总人数和年龄
//        List<TPersonCountAndAge> tPersonCountAndAgeList = tPersonDao.findCountByDeptIds(deptIds);
        TPersonCountAndAge tPersonCountAndAgeList = teamBuildService.getCountAndAgeByUnitIds(deptIds);
        personStereoVo.setPersonSum(tPersonCountAndAgeList.getNum());
        if (StringUtils.isNotBlank(tPersonCountAndAgeList.getAgeSum())) {
            personStereoVo.setAveAge(String.format("%.2f", Double.parseDouble(tPersonCountAndAgeList.getAgeSum())));
        } else {
            personStereoVo.setAveAge("0");
        }
        PersonStereoVo personStereoVo1 = getPosition(deptIds);
        personStereoVo.setInThePost(personStereoVo1.getInThePost());
        personStereoVo.setDeputyDuties(personStereoVo1.getDeputyDuties());
        personStereoVo.setProfessionalDuties(personStereoVo1.getProfessionalDuties());
        personStereoVo.setSubsidiaryDuties(personStereoVo1.getSubsidiaryDuties());
        return personStereoVo;
    }

    /**
     * @param unitId
     * @return
     * @分析语句
     */
    public UnitDescription getUnitDescription(String unitId) {
        UnitDescription unitDescription = new UnitDescription();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        unitDescription.setNow(year + "年" + month + "月");
        Query query = Query.query(Criteria.where("ID").is(unitId));
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
        String unitName = companyNewTreeSubset.getCompanyList().get(0).getSimpleName();
        unitDescription.setUnitName(unitName);
        TPersonCountAndAge tPersonCountAndAgeList = teamBuildService.getCountAndAgeByUnitIds(getCompanyAllId(unitId));
        if (StringUtils.isNotBlank(tPersonCountAndAgeList.getAgeSum())) {
//            double sum = Double.valueOf(tPersonCountAndAgeList.get(0).getAgeSum());
            unitDescription.setAvgAge(String.format("%.2f", Double.parseDouble(tPersonCountAndAgeList.getAgeSum())));
            unitDescription.setPersonSum(tPersonCountAndAgeList.getNum());
        }
        TPersonCountAndAge tPersonCountAndAgeAll = teamBuildService.getCountAndAgeByUnitIds(new ArrayList<>());
        if (StringUtils.isNotBlank(tPersonCountAndAgeAll.getAgeSum())) {
//            double sum = Double.valueOf(tPersonCountAndAgeAll.getAgeSum());
            unitDescription.setAllAvgAge(String.format("%.2f", Double.parseDouble(tPersonCountAndAgeAll.getAgeSum())));
            unitDescription.setPersonAllSum(tPersonCountAndAgeAll.getNum());
        }
        if (unitDescription.getAvgAge() != null) {
            if (Double.valueOf(unitDescription.getAvgAge()) > Double.valueOf(unitDescription.getAllAvgAge())) {
                unitDescription.setAgeCompare("高");
            } else if (Double.valueOf(unitDescription.getAvgAge()) < Double.valueOf(unitDescription.getAllAvgAge())) {
                unitDescription.setAgeCompare("低");
            } else {
                unitDescription.setAgeCompare("等");
            }
        } else {
            unitDescription.setAgeCompare("低");
        }
        List<AgeRandCount> ageRandCountList = teamPersonnelMapper.queryAgeRange(getCompanyAllId(unitId));
        if (ageRandCountList.size() > 0) {
            unitDescription.setTolAgeBetween(ageRandCountList.get(0).getAgeRand());
        }
        Map<String, Integer> resultMap = getUnitEducation(getCompanyAllId(unitId));
        int i = 0;
        StringBuilder educationTol = new StringBuilder();
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            educationTol.append(entry.getKey());
            i++;
            if (i == 1) {
                break;
            }
        }
        unitDescription.setEducationTol(educationTol.toString());
        Integer educationTolValue = resultMap.get(educationTol.toString());
        double sum = 0;
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            sum = entry.getValue() + sum;
        }
        if (educationTolValue != 0) {
            unitDescription.setDegreeOfEducation(decimalFormat1.format(educationTolValue / (double) unitDescription.getPersonSum()));
        } else {
            unitDescription.setDegreeOfEducation("0.00%");
        }

        if (resultMap.get(COLLEGE) != 0) {
            //本科占比
            unitDescription.setBachelorDegreeOf(decimalFormat1.
                    format(resultMap.get(COLLEGE) / (double) unitDescription.getPersonSum()));
        } else {
            unitDescription.setBachelorDegreeOf("0.00%");
        }
        //市局本科占比
        unitDescription.setAllBachelorDegreeOf(decimalFormat1.format(
                teamPersonnelMapper.queryAllUniversity() / (double) unitDescription.getPersonAllSum()));

        PersonStereoVo personStereoVo = getPosition(getCompanyAllId(unitId));
        if (personStereoVo.getUprightPosition() != 0) {
            unitDescription.setUprightPositionDegreeOf(decimalFormat1.format(
                    personStereoVo.getUprightPosition() / (double) unitDescription.getPersonSum()));
        } else {
            unitDescription.setUprightPositionDegreeOf("0.00%");
        }
        if (Double.valueOf(unitDescription.getBachelorDegreeOf().replaceAll(PERCENT, "")) >
                Double.valueOf(unitDescription.getAllBachelorDegreeOf().replaceAll(PERCENT, ""))) {
            unitDescription.setEducationCompare("高于");

        } else if (Double.valueOf(unitDescription.getBachelorDegreeOf().replaceAll(PERCENT, "")) <
                Double.valueOf(unitDescription.getAllBachelorDegreeOf().replaceAll(PERCENT, ""))) {
            unitDescription.setEducationCompare("低于");
        } else {
            unitDescription.setEducationCompare("等于");
        }
        //全局的副处职
        List<String> allUpDuty = new ArrayList<>();
        PersonStereoVo personStereoAll = getPosition(allUpDuty);
        if (personStereoAll.getUprightPosition() != 0) {
            unitDescription.setAllUprightPositionDegreeOf(decimalFormat1.format(
                    personStereoAll.getUprightPosition() / (double) unitDescription.getPersonAllSum()));
        } else {
            unitDescription.setDegreeOfEducation("0.00%");
        }
        if (Double.valueOf(unitDescription.getUprightPositionDegreeOf().replaceAll(PERCENT, "")) >
                Double.valueOf(unitDescription.getAllUprightPositionDegreeOf().replaceAll(PERCENT, ""))) {
            unitDescription.setDutyCompare("高于");

        } else if (Double.valueOf(unitDescription.getUprightPositionDegreeOf().replaceAll(PERCENT, "")) <
                Double.valueOf(unitDescription.getAllUprightPositionDegreeOf().replaceAll(PERCENT, ""))) {
            unitDescription.setDutyCompare("低于");
        } else {
            unitDescription.setDutyCompare("等于");
        }
        return unitDescription;

    }

    /**
     * @return
     * @获取各种职位的人数
     */
    private PersonStereoVo getPosition(List<String> deptIds) {
        List<String> idNos = new ArrayList<>();
        //根据所有的部门查询人员idNo
        if (deptIds.size() > 0) {
            List<SysUserDo> sysUserDoList = sysUserDao.
                    findAllByDelStatusAndPostTypeAndDeptIdIn("0", 0, deptIds);
            if (sysUserDoList.size() > 0) {
                sysUserDoList.forEach(f -> idNos.add(f.getIdentitynum()));
            }
        } else {
            ChildUnit childUnit = new ChildUnit();
            idNos.addAll(childUnit.getSubPersonIdNos("2014110416460086100000002942"));
        }
        Map<String, Integer> dutyMap = personalAnalysisService.getPosSubCount(personalAnalysisService.getPersonByIdNos(idNos));
        PersonStereoVo personStereoVo = new PersonStereoVo();
        personStereoVo.setInThePost((dutyMap.get("正处职及以上") == null ? 0 : dutyMap.get("正处职及以上")));
        personStereoVo.setDeputyDuties(dutyMap.get("副处职") == null ? 0 : dutyMap.get("副处职"));
        personStereoVo.setProfessionalDuties(dutyMap.get("正科职") == null ? 0 : dutyMap.get("正科职"));
        personStereoVo.setSubsidiaryDuties(dutyMap.get("副科职") == null ? 0 : dutyMap.get("副科职"));
        personStereoVo.setUprightPosition(dutyMap.get("副处职及以上") == null ? 0 : dutyMap.get("副处职及以上"));

        return personStereoVo;
    }

    /**
     * @param unitId
     * @return
     * @description: 统计进入来源
     */
    public ResultSourceVo getSourceCount(String unitId) {
        ResultSourceVo resultSourceVo = new ResultSourceVo();
        List<UnitSourceVo> unitSourceVoList = new ArrayList<>();
        String name = getMyselfUnitName(unitId);
        List<String> list1 = Arrays.asList(politicalSub);
        List<CompanyNew> childList = new ArrayList<>(getAllIdByUnitId(unitId));
        if (childList.size() > 0) {
            childList.forEach(v -> {
                UnitSourceVo unitSourceVo = new UnitSourceVo();
                unitSourceVo.setDeptId(v.getId());
                if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                    unitSourceVo.setDeptName(v.getSimpleName().replaceAll(name, ""));
                } else {
                    unitSourceVo.setDeptName(v.getSimpleName());
                }
                if (v.getSimpleName().contains(POLICE_STATION)) {
                    unitSourceVo.setType("派出所");
                } else {
                    unitSourceVo.setType("机关");
                }
                List<String> deptIdList = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                if(sysDeptDo !=null) {
                    if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(v.getId()) ) {
                        deptIdList.add(v.getId());
                    } else {
                        deptIdList.addAll(getCompanyAllId(v.getId()));
                    }
                }
                List<SourceCount> sourceCountList = teamPersonnelMapper.querySource(deptIdList);
                LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
                final int[] count1 = {0};
                final int[] count2 = {0};
                final int[] count3 = {0};
                final int[] count4 = {0};
                final int[] count5 = {0};
                if (sourceCountList.size() > 0) {
                    sourceCountList.forEach(s -> {
                        String sourceName = s.getSourceName();
                        String schoolType = s.getSchoolType();
                        int count = s.getCount();
                        String finalSourceName = CommonUnit.getSourceName(sourceName);
                        if (finalSourceName.equals("社会招考") && (schoolType.equals("1") || schoolType.equals("1003"))) {//警察院校毕业生
                            count1[0] = count1[0] + count;
//                            result.put("警察院校毕业生", count + integer);
                        } else if (finalSourceName.equals("社会招考")) {
                            count2[0] = count + count2[0];
//                            Integer integer = result.get(finalSourceName);
//                            result.put(finalSourceName, count + integer);
                        } else if (finalSourceName.equals("实施公务员法机关调入")) {
                            count3[0] = count + count3[0];
                        } else if (finalSourceName.equals("转业及复员军人")) {
                            count4[0] = count + count4[0];
                        } else if (finalSourceName.equals("其他")) {
                            count5[0] = count + count5[0];
                        }
                    });
                    result.put("警察院校毕业生", count1[0]);
                    result.put("社会招考", count2[0]);
                    result.put("实施公务员法机关调入", count3[0]);
                    result.put("转业及复员军人", count4[0]);
                    result.put("其他", count5[0]);
                    if (result.get(POLICE_SCHOOL) != null) {
                        unitSourceVo.setPoliceSchool(result.get("警察院校毕业生"));
                    }
                    if (result.get(SOLDIER) != null) {
                        unitSourceVo.setSoldier(result.get("转业及复员军人"));
                    }
                    if (result.get(SOCIAL) != null) {
                        unitSourceVo.setSocial(result.get("社会招考"));
                    }
                    if (result.get(FOLD) != null) {
                        unitSourceVo.setFold(result.get("实施公务员法机关调入"));
                    }
                    if (result.get(ELSE_SOURCE) != null) {
                        unitSourceVo.setElseSource(result.get("其他"));
                    }
                    unitSourceVoList.add(unitSourceVo);
                }
            });
        }
        resultSourceVo.setUnitSourceVoList(unitSourceVoList);
        resultSourceVo.setColumn(Arrays.asList(source));
        return resultSourceVo;
    }

    /**
     * @param unitId
     * @return
     * @统计年龄段的人数
     */
    public ResultUnitAgeCountVo getAgeCount(String unitId) {
        ResultUnitAgeCountVo resultUnitAgeCountVo = new ResultUnitAgeCountVo();
        List<UnitAgeCountVo> unitAgeCountVoArrayList = new ArrayList<>();
        List<CompanyNew> childList = getAllIdByUnitId(unitId);
        String name = getMyselfUnitName(unitId);
        List<String> list1 = Arrays.asList(politicalSub);
        if (childList.size() > 0) {
            childList.forEach(v -> {
                UnitAgeCountVo unitAgeCountVo = new UnitAgeCountVo();
                if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                    unitAgeCountVo.setDeptName(v.getSimpleName().replaceAll(name, ""));
                } else {
                    unitAgeCountVo.setDeptName(v.getSimpleName());
                }
                if (v.getSimpleName().contains(POLICE_STATION)) {
                    unitAgeCountVo.setType("派出所");
                } else {
                    unitAgeCountVo.setType("机关");
                }
                unitAgeCountVo.setDeptId(v.getId());
                List<String> deptIdList = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                if(sysDeptDo !=null) {
                    if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(v.getId())) {
                        deptIdList.add(v.getId());
                    } else {
                        deptIdList.addAll(getCompanyAllId(v.getId()));
                    }
                }
                List<AgeRandCount> ageRandCountList = teamPersonnelMapper.queryAgeCount(deptIdList);
                if (ageRandCountList.size() > 0) {
                    ageRandCountList.forEach(a -> {
                        switch (a.getAgeRand()) {
                            case "50岁以上":
                                unitAgeCountVo.setMoreThanFity(a.getCount());
                                break;
                            case "40-50岁":
                                unitAgeCountVo.setFortyOneAndFifty(a.getCount());
                                break;
                            case "30-40岁":
                                unitAgeCountVo.setThirtyAndForty(a.getCount());
                                break;
                            case "20-30岁":
                                unitAgeCountVo.setTwentyAndThirty(a.getCount());
                                break;
                            case "20岁以下":
                                unitAgeCountVo.setUnderTwenty(a.getCount());
                                break;
                            default:
                                break;
                        }
                    });
                    unitAgeCountVoArrayList.add(unitAgeCountVo);
                }

            });
        }
        resultUnitAgeCountVo.setUnitAgeCountVoList(unitAgeCountVoArrayList);
        List<String> column = new ArrayList<>(Arrays.asList(ageRange));
        resultUnitAgeCountVo.setColumn(column);
        return resultUnitAgeCountVo;
    }

    /**
     * @param unitId
     * @return
     * @description: 得到所有部门的子部门
     */
    private List<CompanyNew> getChildDept(String unitId) {
        Query query = Query.query(Criteria.where("ID").is(unitId));
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
        return companyNewTreeSubset.getCompanyList().get(0).getChildCompany();
    }


    /**
     * @param unitId
     * @return
     * @统计博士硕士高中及以下数据
     */
    public ResultUnitEducationVo getEducationCount(String unitId) {
        ResultUnitEducationVo resultUnitEducationVo = new ResultUnitEducationVo();
        List<UnitEducationCount> unitEducationCountList = new ArrayList<>();
        List<CompanyNew> childList = getAllIdByUnitId(unitId);
        String name = getMyselfUnitName(unitId);
        List<String> list1 = Arrays.asList(politicalSub);
        if (childList.size() > 0) {
            childList.forEach(v -> {
                ChildUnit childUnit = new ChildUnit();
                List<String> idNos = childUnit.getSubPersonIdNos(v.getId());
                if (idNos.size() > 0) {
                    UnitEducationCount unitEducationCount = new UnitEducationCount();
                    if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                        unitEducationCount.setDeptName(v.getSimpleName().replaceAll(name, ""));
                    } else {
                        unitEducationCount.setDeptName(v.getSimpleName());
                    }
                    if (v.getSimpleName().contains(POLICE_STATION)) {
                        unitEducationCount.setType("派出所");
                    } else {
                        unitEducationCount.setType("机关");
                    }
                    unitEducationCount.setDeptId(v.getId());
                    List<String> deptIdList = new ArrayList<>();
                    SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                    if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(v.getId())) {
                        deptIdList.add(v.getId());
                    } else {
                        deptIdList.addAll(getCompanyAllId(v.getId()));
                    }
                    Map<String, Integer> resultMap = getUnitEducation(deptIdList);
                    if (resultMap.get(DOCTOR) != null) {
                        unitEducationCount.setDoctor(resultMap.get(DOCTOR));
                    }
                    if (resultMap.get(MASTER) != null) {
                        unitEducationCount.setMaster(resultMap.get(MASTER));
                    }
                    if (resultMap.get(COLLEGE) != null) {
                        unitEducationCount.setCollege(resultMap.get(COLLEGE));
                    }
                    if (resultMap.get(JUNIOR_COLLEGE) != null) {
                        unitEducationCount.setJuniorCollege(resultMap.get(JUNIOR_COLLEGE));
                    }
                    if (resultMap.get(UNDER_HIGHT_SCHOOL) != null) {
                        unitEducationCount.setSenior(resultMap.get(UNDER_HIGHT_SCHOOL));
                    }
                    unitEducationCountList.add(unitEducationCount);
                }

            });
        }
        resultUnitEducationVo.setUnitEducationCountList(unitEducationCountList);
        List<String> column = new ArrayList<>();
        column.add("博士");
        column.add("硕士");
        column.add("本科");
        column.add("大专");
        column.add("高中及以下");
        resultUnitEducationVo.setColumn(column);
        return resultUnitEducationVo;
    }

    /**
     * @return
     * @获取各单位的博士硕士本科大专等人数
     */
    private Map<String, Integer> getUnitEducation(List<String> deptIds) {
        List<EducationCount> educationCountList = teamPersonnelMapper.queryEducation(deptIds);
        //分组统计各学历人数
        //博士
        int count1 = 0;
        //硕士
        int count2 = 0;
        //本科
        int count3 = 0;
        //大专
        int count4 = 0;
        //高中及以下
        int count5 = 0;
        Map<String, Integer> mapResult = new LinkedHashMap<>();
        for (EducationCount tEducation : educationCountList) {
            String type = tEducation.getEducationTitile();
            if (StringUtils.isNotEmpty(type)) {
                if ("11001".equals(type)) {
                    count1 += tEducation.getCount();
                } else if (type.charAt(0) == '1') {
                    count2 += tEducation.getCount();
                } else if (type.charAt(0) == '2') {
                    count3 += tEducation.getCount();
                } else if (type.charAt(0) == '3') {
                    count4 += tEducation.getCount();
                } else {
                    count5 += tEducation.getCount();
                }
            }
        }
        if (count1 != 0) {
            mapResult.put("博士", count1);
        }

        if (count2 != 0) {
            mapResult.put("硕士", count2);
        }
        if (count3 != 0) {
            mapResult.put("本科", count3);
        }
        if (count4 != 0) {
            mapResult.put("大专", count4);
        }
        if (count5 != 0) {
            mapResult.put("高中及以下", count5);
        }
        if (mapResult.size() > 0) {
            return mapResult.entrySet()
                    .stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            return mapResult;
        }
    }

    /**
     * @param unitId
     * @return
     * @description: 根据单位查询下属部门的工作年限
     */
    public ResultUnitWorkAgeVo getWorkAgeCount(String unitId) {
        ResultUnitWorkAgeVo resultUnitWorkAgeVo = new ResultUnitWorkAgeVo();
        List<UnitWorkAgeCountVo> unitWorkAgeCountVoArrayList = new ArrayList<>();
        List<CompanyNew> childList = getAllIdByUnitId(unitId);
        String name = getMyselfUnitName(unitId);
        List<String> list1 = Arrays.asList(politicalSub);
        if (childList.size() > 0) {
            childList.forEach(v -> {
                ChildUnit childUnit = new ChildUnit();
                List<String> idNos = childUnit.getSubPersonIdNos(v.getId());
                if (idNos.size() > 0) {
                    UnitWorkAgeCountVo unitWorkAgeCountVo = new UnitWorkAgeCountVo();
                    if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                        unitWorkAgeCountVo.setDeptName(v.getSimpleName().replaceAll(name, ""));
                    } else {
                        unitWorkAgeCountVo.setDeptName(v.getSimpleName());
                    }
                    if (v.getSimpleName().contains(POLICE_STATION)) {
                        unitWorkAgeCountVo.setType("派出所");
                    } else {
                        unitWorkAgeCountVo.setType("机关");
                    }
                    unitWorkAgeCountVo.setDeptId(v.getId());
                    List<String> deptIdList = new ArrayList<>();
                    SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                    if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(v.getId())) {
                        deptIdList.add(v.getId());
                    } else {
                        deptIdList.addAll(getCompanyAllId(v.getId()));
                    }
                    List<WorkAgeCount> workAgeCountList = teamPersonnelMapper.queryWorkAgeCount(deptIdList);
                    if (workAgeCountList.size() > 0) {
                        workAgeCountList.forEach(w -> {
                            switch (w.getWorkAgeRand()) {
                                case "5年以下":
                                    unitWorkAgeCountVo.setUnderFive(w.getCount());
                                    break;
                                case "5(含)-10":
                                    unitWorkAgeCountVo.setFiveAndNine(w.getCount());
                                    break;
                                case "10(含)-20":
                                    unitWorkAgeCountVo.setTenAndNineteen(w.getCount());
                                    break;
                                case "20(含)-30":
                                    unitWorkAgeCountVo.setTwentyAndTwentyNine(w.getCount());
                                    break;
                                case "30(含)以上":
                                    unitWorkAgeCountVo.setMoreThanThirty(w.getCount());
                                    break;
                                default:
                                    break;
                            }

                        });
                    }
                    unitWorkAgeCountVoArrayList.add(unitWorkAgeCountVo);
                }

            });
        }
        resultUnitWorkAgeVo.setUnitWorkAgeCountVoList(unitWorkAgeCountVoArrayList);
        resultUnitWorkAgeVo.setColumn(Arrays.asList(unitWorkAge));
        return resultUnitWorkAgeVo;

    }

    /**
     * @param unitId
     * @return 婚姻统计
     */
    public ResultUnitMaritalVo getMaritalCount(String unitId) {
        ResultUnitMaritalVo resultUnitMaritalVo = new ResultUnitMaritalVo();
        List<UnitMaritalVo> unitMaritalVoList = new ArrayList<>();
        List<CompanyNew> childList = getAllIdByUnitId(unitId);
        String name = getMyselfUnitName(unitId);
        List<String> list1 = Arrays.asList(politicalSub);
        if (childList.size() > 0) {
            childList.forEach(v -> {
                ChildUnit childUnit = new ChildUnit();
                List<String> idNos = childUnit.getSubPersonIdNos(v.getId());
                if (idNos.size() > 0) {
                    UnitMaritalVo unitMaritalVo = new UnitMaritalVo();
                    if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                        unitMaritalVo.setDeptName(v.getSimpleName().replaceAll(name, ""));
                    } else {
                        unitMaritalVo.setDeptName(v.getSimpleName());
                    }
                    if (v.getSimpleName().contains(POLICE_STATION)) {
                        unitMaritalVo.setType("派出所");
                    } else {
                        unitMaritalVo.setType("机关");
                    }
                    unitMaritalVo.setDeptId(v.getId());
                    List<String> deptIdList = new ArrayList<>();
                    SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                    if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(v.getId())) {
                        deptIdList.add(v.getId());
                    } else {
                        deptIdList.addAll(getCompanyAllId(v.getId()));
                    }
                    List<MaritalCount> maritalCountList = teamPersonnelMapper.queryMaritalCount(deptIdList);
                    if (maritalCountList.size() > 0) {
                        maritalCountList.forEach(m -> {
                            switch (m.getCvName()) {
                                case "未婚":
                                    unitMaritalVo.setUnmarried(m.getCount());
                                    break;
                                case "已婚":
                                    unitMaritalVo.setMarried(m.getCount());
                                    break;
                                case "丧偶":
                                    unitMaritalVo.setBereftOfPerson(m.getCount());
                                    break;
                                case "离婚":
                                    unitMaritalVo.setDivorce(m.getCount());
                                    break;
                                default:
                                    break;
                            }
                        });
                    }
                    unitMaritalVoList.add(unitMaritalVo);
                }
            });
        }
        resultUnitMaritalVo.setUnitMaritalVoList(unitMaritalVoList);
        resultUnitMaritalVo.setColumn(Arrays.asList(marriage));
        return resultUnitMaritalVo;
    }


    /**
     * 获取警察院校
     *
     * @param unitId
     * @return
     */
    public ResultUnitSchoolVo getSchoolCount(String unitId) {
        ResultUnitSchoolVo resultUnitWorkAgeVo = new ResultUnitSchoolVo();
        List<UnitSchoolCountVo> unitSchoolCountVos = new ArrayList<>();
        List<CompanyNew> childList = getAllIdByUnitId(unitId);
        String name = getMyselfUnitName(unitId);
        if (childList.size() > 0) {
            childList.forEach(v -> {
                UnitSchoolCountVo unitSchoolCountVo = new UnitSchoolCountVo();
                if (!unitId.equals(v.getId()) && !name.equals(v.getSimpleName())) {
                    unitSchoolCountVo.setDeptName(v.getSimpleName().replaceAll(name, ""));
                } else {
                    unitSchoolCountVo.setDeptName(v.getSimpleName());
                }
                if (v.getSimpleName().contains(POLICE_STATION)) {
                    unitSchoolCountVo.setType("派出所");
                } else {
                    unitSchoolCountVo.setType("机关");
                }
                unitSchoolCountVo.setDeptId(v.getId());
                List<String> deptIdList = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v.getId(), 0);
                if ("2".equals(sysDeptDo.getDeptLevel())) {
                    deptIdList.add(v.getId());
                } else {
                    deptIdList.addAll(getCompanyAllId(v.getId()));
                }
                List<String> policeList = teamPersonnelMapper.queryPoliceSchoolCount(deptIdList);
                int policeCount = policeList.size();
                unitSchoolCountVo.setPoliceColleges(policeCount);
                List<String> armyList = teamPersonnelMapper.queryArmySchoolCount(getCompanyAllId(v.getId()));
                int armyCount = armyList.size();
                unitSchoolCountVo.setArmyColleges(armyCount);
                unitSchoolCountVo.setLocalColleges(0);
                List<String> elseList = teamPersonnelMapper.queryElseSchoolCount(getCompanyAllId(v.getId()));
                int elseCount = elseList.size();
                unitSchoolCountVo.setElseColleges(elseCount);
                unitSchoolCountVos.add(unitSchoolCountVo);
            });
        }
        resultUnitWorkAgeVo.setUnitSchoolCountVoList(unitSchoolCountVos);
        resultUnitWorkAgeVo.setColumn(Arrays.asList(schoolType));
        return resultUnitWorkAgeVo;

    }

    /**
     * 学校详情
     *
     * @param personstereoDetailVo
     * @return
     */
    public List<UserInResultVo> getSchoolDetail(PersonstereoDetailVo personstereoDetailVo) {
        List<TPersonDo> tPersonDoList = new ArrayList<>();
        //查询来源明细
        List<String> list = personstereoDetailVo.getDeptIds();
        if (list.size() > 0) {
            list.forEach(v -> tPersonDoList.addAll(getSchoolDetailByDeptIds(getCompanyAllId(v), personstereoDetailVo.getType())));
        }
        return CommonUnit.changeToEntity(tPersonDoList);

    }

    private List<TPersonDo> getSchoolDetailByDeptIds(List<String> deptIds, String type) {
        //返回给前端的数据
        List<TPersonDo> listTotal = new ArrayList<>();
        if (deptIds.size() <= 0) {
            return listTotal;
        }
        //符合条件的结果详情
        List<TPersonDo> listResult = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            if ("警察院校".equals(type)) {
                ids.addAll(teamPersonnelMapper.queryPoliceSchoolCount(deptIds));
            }
            if ("部队院校".equals(type)) {
                ids.addAll(teamPersonnelMapper.queryArmySchoolCount(deptIds));
            }
            if ("其它院校".equals(type)) {
                ids.addAll(teamPersonnelMapper.queryElseSchoolCount(deptIds));
            }
        }
        //符合条件的结果
        if (ids.size() > 0) {
            if (ids.size() > ONE_THOUSAND) {
                int listSize = ids.size() / 1000;
                for (int i = 0; i < listSize; i++) {
                    listResult.addAll(tPersonDao.findAllByDelStatusAndIdNoIn("0", ids.subList(1000 * i, 1000 * (i + 1))));
                }
                listResult.addAll(tPersonDao.findAllByDelStatusAndIdNoIn("0", ids.subList(1000 * listSize, ids.size())));
            } else {
                listResult.addAll(tPersonDao.findAllByDelStatusAndIdNoIn("0", ids));
            }
        }
        return listResult;
    }

    /**
     * 根据父节点id 查询下属部门
     *
     * @param parentId
     * @return
     */
    private List<DeptAndPermisson> getCompanyTree(String parentId) {
        List<DeptAndPermisson> sysDeptDoListResult = new ArrayList<>();
        List<SysDeptDo> sysDeptDoList = sysDeptDao.findAllByParentIdAndDelStatusOrderBySearchCode(parentId, 0);
        List<String> list = Arrays.asList(politicalSub);
        if (URBAN_ID.equals(parentId)) {
            //合并政治部
            if (sysDeptDoList.size() > 0) {
                for (SysDeptDo sysDeptDo : sysDeptDoList) {
                    if (!list.contains(sysDeptDo.getId())) {
                        DeptAndPermisson deptAndPermisson = new DeptAndPermisson();
                        deptAndPermisson.setId(sysDeptDo.getId());
                        deptAndPermisson.setIsSub("1");
                        deptAndPermisson.setIsClick("1");
                        deptAndPermisson.setIsWhite("1");
                        sysDeptDoListResult.add(deptAndPermisson);
                    }
                }
            }
        } else {
            if (POLITICAL.equals(parentId)) {
                list.forEach(l -> {
                    if (!GENERAL_OFFICE.equals(l)) {
                        sysDeptDoList.add(sysDeptDao.findByIdAndDelStatus(l, 0));
                    }
                });
            }
            if (sysDeptDoList.size() > 0) {
                sysDeptDoList.forEach(v -> {
                    DeptAndPermisson deptAndPermisson = new DeptAndPermisson();
                    deptAndPermisson.setId(v.getId());
                    deptAndPermisson.setIsSub("0");
                    deptAndPermisson.setIsClick("1");
                    deptAndPermisson.setIsWhite("0");
                    sysDeptDoListResult.add(deptAndPermisson);
                });
            }

        }
        return sysDeptDoListResult;
    }

    /**
     * 根据传递过来的id判断需要展示那些部门的id
     *
     * @param unitId
     * @return
     */
    private List<CompanyNew> getAllIdByUnitId(String unitId) {
        SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(unitId, 0);
        List<String> list1 = Arrays.asList(politicalSub);
        List<CompanyNew> childList = new ArrayList<>();

        //判读父节点是不是南京市公安局
        if (sysDeptDo.getParentId().equals(URBAN_ID)) {
            Query query1 = Query.query(Criteria.where("ID").is(unitId));
            List<CompanyNewTreeSubset> listUnit = mongoTemplate.find(query1, CompanyNewTreeSubset.class, "companyNewTreeSubset");
            CompanyNewTreeSubset companyNewTreeSubset1 = listUnit.get(0);
            childList.add(companyNewTreeSubset1.getCompanyList().get(0));
//            childList.addAll(getChildDept(unitId));
            if(list1.contains(unitId)) {
                return childList;
            }
            if (POLITICAL.equals(unitId)){
                list1.forEach(l -> {
                    Query query = Query.query(Criteria.where("ID").is(l));
                    List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
                    CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
                    CompanyNew companyNew = companyNewTreeSubset.getCompanyList().get(0);
                    childList.add(companyNew);
                });
            } else {
                //返回分局的信息
                childList.addAll(getChildDept(unitId));
            }

        } else {
            //内下设机构
            Query query = Query.query(Criteria.where("ID").is(unitId));
            List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
            CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
            childList.add(companyNewTreeSubset.getCompanyList().get(0));
        }

        return childList;
    }

    /**
     * 来源详情
     *
     * @param personstereoDetailVo
     * @return
     */
    public ResultModel getUnitSourceDetail(PersonstereoDetailVo personstereoDetailVo) {
        ResultModel resultModel = new ResultModel();
        List<TPersonDo> tPersonDoList = new ArrayList<>();
        //查询来源明细
        List<String> list = personstereoDetailVo.getDeptIds();
        List<String> list1 = Arrays.asList(politicalSub);
        if (list.size() > 0) {
            list.forEach(v -> {
                List<String> deptIdList = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v, 0);
                if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(sysDeptDo.getId())) {
                    deptIdList.add(v);
                } else {
                    deptIdList.addAll(getCompanyAllId(v));
                }
                tPersonDoList.addAll(getPeopleByDeptIds(deptIdList, personstereoDetailVo.getType()));
            });
        }
//        System.out.println("数量:"+tPersonDoList.size());
        List<UserInResultVo> userInResultVoList = CommonUnit.changeToEntity(tPersonDoList);
        if ("1".equals(personstereoDetailVo.getIsDownload())) {
            try {
                getAgeExport(userInResultVoList, "来源分析");
                resultModel.setResult(SUCCESS);
                return resultModel;
            } catch (Exception e) {
                return null;
            }
        } else {
            resultModel.setResult(userInResultVoList);
        }
//        return userInResultVoList;
        return resultModel;

    }


    /**
     * 年龄结构导出
     */
    private void getAgeExport(List<UserInResultVo> userInResultVos, String title) {
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;
//        String[] rowsName = new String[] {"单位", "姓名", "性别","年龄","民族","政治面貌","警衔","职务","职级","学历学位","毕业院校"};
        String[] rowsName = new String[]{"单位", "姓名", "性别", "年龄", "民族", "政治面貌", "警衔", "职务", "职级", "学历学位", "毕业院校"};
        for (UserInResultVo userInResultVo : userInResultVos) {
            objs = new Object[rowsName.length];
            objs[0] = userInResultVo.getDeptName();
            objs[1] = userInResultVo.getUserName();
            objs[2] = userInResultVo.getSex();
            objs[3] = userInResultVo.getAge();
            objs[4] = userInResultVo.getNation();
            objs[5] = userInResultVo.getParty();
            objs[6] = userInResultVo.getPoliceRank();
            objs[7] = userInResultVo.getDuty();
            objs[8] = userInResultVo.getJobGrade();
            objs[9] = userInResultVo.getEducation();
            objs[10] = userInResultVo.getSchool();
            dataList.add(objs);
        }
        exportExcel(title, rowsName, dataList);
    }

    @Resource
    private HttpServletResponse response;

    /**
     * excel导出
     */
    private void exportExcel(String title, String[] rowsName, List<Object[]> list) {
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
     * 根据传过来的部门Ids和来源查询人员
     *
     * @param deptIds
     * @return
     */
    private List<TPersonDo> getPeopleByDeptIds(List<String> deptIds, String type) {
        List<TPersonDo> resultList = new ArrayList<>();
        List<String> idNos = new ArrayList<>();
        if (deptIds.size() > 0) {
            List<TPersonDo> tPersonDoList = new ArrayList<>(tPersonDao.findAllBydeptIdIn(deptIds));
            if (tPersonDoList.size() > 0) {
                tPersonDoList.forEach(t -> idNos.add(t.getIdNo()));
            }
            if (idNos.size() > 0) {
                if (type.equals(POLICE_SCHOOL)) {
                    List<String> idNoList = tEducationDao.getAllByByPoliceAndIdNo(Arrays.asList(social), idNos);
                    if (idNoList.size() > 0) {
                        resultList.addAll(tPersonDao.
                                findAllByDelStatusAndPClassIdAndIdNoIn(idNoList));
                    }
                }
                if (type.equals(SOCIAL)) {
                    List<String> idNoList = tEducationDao.getAllByBySocialAndIdNo(Arrays.asList(social), idNos);
                    if (idNoList.size() > 0) {
                        resultList.addAll(tPersonDao.findAllByDelStatusAndPClassIdAndIdNoIn(idNoList));
                    }
                }
                if (type.equals(FOLD)) {
                    tPersonDoList = tPersonDoList.
                            stream().
                            filter(v -> "120".equals(v.getSource()) || "134".equals(v.getSource()) || "131".equals(v.getSource())).
                            sorted(Comparator.comparing(TPersonDo::getPoliceNo)).
                            collect(Collectors.toList());
                    resultList.addAll(tPersonDoList);
                }
                if (type.equals(SOLDIER)) {
                    tPersonDoList = tPersonDoList.
                            stream().
                            filter(v -> "115".equals(v.getSource()) || "116".equals(v.getSource())).
                            sorted(Comparator.comparing(TPersonDo::getPoliceNo)).
                            collect(Collectors.toList());
                    resultList.addAll(tPersonDoList);
                }
                if (type.equals(ELSE_SOURCE)) {
                    List<String> sourceList = new ArrayList<>();
                    sourceList.addAll(Arrays.asList(social));
                    sourceList.addAll(Arrays.asList(entry));
                    sourceList.addAll(Arrays.asList(soldier));
                    resultList.addAll(tPersonDao.
                            findAllByDelStatusAndSourceNotInAndIdNoIn("0", sourceList, idNos));

                }

            }
        }

        return resultList;
    }

    /**
     * 年龄分析人数
     *
     * @param personstereoDetailVo
     * @return
     */
    public List<UserInResultVo> getAgeDetails(PersonstereoDetailVo personstereoDetailVo) {
        //查询来源明细
        List<String> list = personstereoDetailVo.getDeptIds();
        List<String> listArray = new ArrayList<>();
        List<String> list1 = Arrays.asList(politicalSub);
        if (list.size() > 0) {
            list.forEach(v -> {
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v, 0);
                if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(sysDeptDo.getId())) {
                    listArray.add(v);
                } else {
                    listArray.addAll(getCompanyAllId(v));
                }
            });
        }
        List<TPersonDo> tPersonDoList = new ArrayList<>(getAgeDetailsByDeptIds(listArray, personstereoDetailVo.getType()));
        List<UserInResultVo> userInResultVoList = CommonUnit.changeToEntity(tPersonDoList);
        if ("1".equals(personstereoDetailVo.getIsDownload())) {
            try {
                getAgeExport(userInResultVoList, "年龄分析");
            } catch (Exception e) {
                return null;
            }
        }
        return userInResultVoList;
    }

    /**
     * 不同年龄人员详细信息
     */
    private List<TPersonDo> getAgeDetailsByDeptIds(List<String> deptIds, String type) {
        //返回给前端的数据
        List<TPersonDo> listTotal = new ArrayList<>();
        if (deptIds.size() <= 0) {
            return listTotal;
        }
        //符合条件的结果详情
        List<TPersonDo> listResult = new ArrayList<>();
        //年龄分类
        String ageStart = "";
        String ageEnd = "";
        if (StringUtils.isNotEmpty(type)) {
            switch (type) {
                case "50岁以上":
                    ageStart = "51";
                    ageEnd = "99";
                    break;
                case "40-50岁":
                    ageStart = "41";
                    ageEnd = "50";
                    break;
                case "30-40岁":
                    ageStart = "31";
                    ageEnd = "40";
                    break;
                case "20-30岁":
                    ageStart = "21";
                    ageEnd = "30";
                    break;
                case "20岁以下":
                    ageStart = "0";
                    ageEnd = "20";
                    break;
                default:
                    break;
            }
        }
        //符合条件的结果
        if (deptIds.size() > ONE_THOUSAND) {
            int listSize = deptIds.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                listResult.addAll(tPersonDao.findAgeByIdNo(deptIds.subList(1000 * i, 1000 * (i + 1)), ageStart, ageEnd));
            }
            listResult.addAll(tPersonDao.findAgeByIdNo(deptIds.subList(1000 * listSize, deptIds.size()), ageStart, ageEnd));
        } else {
            listResult.addAll(tPersonDao.findAgeByIdNo(deptIds, ageStart, ageEnd));
        }

        return listResult;
    }

    /**
     * 查询自己单位的名称
     *
     * @param unitId
     * @return
     */
    private String getMyselfUnitName(String unitId) {
        Query query = Query.query(Criteria.where("ID").is(unitId));
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
        CompanyNew companyNew = companyNewTreeSubset.getCompanyList().get(0);
        return companyNew.getSimpleName();
    }

    /**
     * 搜索
     *
     * @param querySearchVo
     * @return
     */
    public List<PersonStereoVo> getSearch(QuerySearchVo querySearchVo) {
        List<PersonStereoVo> personStereoVosList = new ArrayList<>();
        String companyId = RoleOfCompany.getRoleCompanyId(querySearchVo.getIdNumber());
        List<DeptAndPermisson> departIds = getCompanyTree(companyId);
        List<String> allDeptIds = new ArrayList<>();
        List<SysDeptDo> sysDeptDoList = new ArrayList<>();
        List<String> queryUnitIds = new ArrayList<>();
        List<String> political = Arrays.asList(politicalSub);
        if (departIds.size() > 0) {
            departIds.forEach(v -> {
                //遍历之前清空childList
                childList = new ArrayList<>();
                //获得所有该部门下的id
                allDeptIds.addAll(getCompanyAllId(v.getId()));
            });
        }
        if (DEPTNAME.equals(querySearchVo.getType())) {
            if (!StringUtils.isNotBlank(querySearchVo.getParam())) {
                DeptAndType deptAndType = new DeptAndType();
                deptAndType.setIdNumber(querySearchVo.getIdNumber());
                deptAndType.setType(querySearchVo.getSort());
                return getPersonStereoInfo(deptAndType);
            }
            if (allDeptIds.size() > 0) {
                if (allDeptIds.size() > ONE_THOUSAND) {
                    int listSize = allDeptIds.size() / 1000;
                    for (int i = 0; i < allDeptIds.size() / ONE_THOUSAND; i++) {
                        sysDeptDoList.addAll(sysDeptDao.
                                findAllByIdInAndDelStatus(allDeptIds.subList(1000 * i, 1000 * (i + 1)), 0));
                    }
                    sysDeptDoList.addAll(sysDeptDao.findAllByIdInAndDelStatus(allDeptIds.subList(1000 * listSize, allDeptIds.size()), 0));
                } else {
                    sysDeptDoList.addAll(sysDeptDao.findAllByIdInAndDelStatus(allDeptIds, 0));
                }
            }
            //根据单位名称搜索
            if (sysDeptDoList.size() > 0) {
                sysDeptDoList = sysDeptDoList.stream().
                        filter(v -> v.getUnitName().contains(querySearchVo.getParam()) && ("2".equals(v.getDeptLevel()) || "3".equals(v.getDeptLevel()))).
                        sorted(Comparator.comparing(SysDeptDo::getSearchCode)).
                        collect(Collectors.toList());
                if (sysDeptDoList.size() > 0) {
                    sysDeptDoList.forEach(t -> queryUnitIds.add(t.getId()));
                }
            }
        }
        if (ASSESSENT_UNIT.equals(querySearchVo.getType())) {
            List<TPefrormGroupDetailDo> tPefrormGroupDetailDoList = new ArrayList<>();
            if (allDeptIds.size() > 0) {
                if (allDeptIds.size() > ONE_THOUSAND) {
                    int listSize = allDeptIds.size() / 1000;
                    for (int i = 0; i < allDeptIds.size() / ONE_THOUSAND; i++) {
                        tPefrormGroupDetailDoList.addAll(tPefrormGroupDetailDao.
                                findAllByPerformIdAndDelStatusAndGroupIdAndDeptIdIn(querySearchVo.getPerformId(), 0,
                                        querySearchVo.getGroupId(), allDeptIds.subList(1000 * i, 1000 * (i + 1))));
                    }
                    tPefrormGroupDetailDoList.addAll(tPefrormGroupDetailDao.
                            findAllByPerformIdAndDelStatusAndGroupIdAndDeptIdIn(querySearchVo.getPerformId(), 0,
                                    querySearchVo.getGroupId(), allDeptIds.subList(1000 * listSize, allDeptIds.size())));
                } else {
                    tPefrormGroupDetailDoList.addAll(tPefrormGroupDetailDao.
                            findAllByPerformIdAndDelStatusAndGroupIdAndDeptIdIn(querySearchVo.getPerformId(), 0,
                                    querySearchVo.getGroupId(), allDeptIds));
                }
                if (tPefrormGroupDetailDoList.size() > 0) {
                    tPefrormGroupDetailDoList.forEach(p -> {
                        queryUnitIds.add(p.getDeptId());
                    });
                }

            }
        }

        if (queryUnitIds.size() > 0) {
            queryUnitIds.forEach(f -> {
                //判断是否能够下转
                SysDeptDo sysDeptDoList1 = sysDeptDao.findByIdAndDelStatus(f, 0);
                DeptAndPermisson deptAndPermisson = new DeptAndPermisson();
                deptAndPermisson.setId(sysDeptDoList1.getId());
                deptAndPermisson.setIsClick("1");
                if (sysDeptDoList1.getParentId().equals(URBAN_ID)) {
                    if (!political.contains(f)) {
                        deptAndPermisson.setIsWhite("1");
                        deptAndPermisson.setIsSub("1");
                    } else {
                        deptAndPermisson.setIsWhite("0");
                        deptAndPermisson.setIsSub("0");
                    }
//                    deptAndPermisson.setIsWhite("1");
//                    deptAndPermisson.setIsSub("1");
                } else {
                    deptAndPermisson.setIsWhite("0");
                    deptAndPermisson.setIsSub("0");
                }
                List<String> deptIdsQoList = getCompanyAllId(f);
                //根据部门ids进行统计
                Query query = Query.query(Criteria.where("ID").is(f));
                List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class,
                        "companyNewTreeSubset");
                if (list.size() > 0) {
                    CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
                    PersonStereoVo personStereoVo = getPersonStereoAnalysis(deptIdsQoList);
                    List<HeadInfo> headInfoList = getDeptHead(f);
                    personStereoVo.setHeadInfoList(headInfoList);
                    personStereoVo.setDeptName(companyNewTreeSubset.getCompanyList().get(0).getSimpleName());
                    personStereoVo.setCompanyId(f);
                    personStereoVo.setIsSub(deptAndPermisson.getIsSub());
                    personStereoVo.setIsClick(deptAndPermisson.getIsClick());
                    personStereoVo.setIsWhite(deptAndPermisson.getIsWhite());
                    personStereoVosList.add(personStereoVo);
                }
            });
        }
        //走排序发方法
        return getSortByType(personStereoVosList, querySearchVo.getSort());

    }

    /**
     * 查询考核单元
     *
     * @return
     */
    public List<ResultPerformVo> getPerformanceAppraisal() {
        List<ResultPerformVo> resultPerformList = new ArrayList<>();
        //先查询年份
        List<TPerformDo> tPerformDoList = tPerformDao.findAllByDelStatus(0);
        if (tPerformDoList.size() > 0) {
            tPerformDoList.forEach(v -> {
                ResultPerformVo resultPerformVo = new ResultPerformVo();
                resultPerformVo.setName(v.getPerformYear());
                resultPerformVo.setId(v.getId());
                //根据年份的id查询单元
                List<TPerformGroupDo> tPerformGroupDoList = tPerformGroupDao.
                        findAllByPerformIdAndDelStatusAndPGroupIdIsNullOrderByGroupSort(v.getId(), 0);
                if (tPerformGroupDoList.size() > 0) {
                    List<ResultPerformVo> performVoList1 = new ArrayList<>();
                    tPerformGroupDoList.forEach(t -> {
                        ResultPerformVo resultPerformVo1 = new ResultPerformVo();
                        resultPerformVo1.setName(t.getGroupName());
                        resultPerformVo1.setId(t.getId());
                        //根据考核单元再判断有没有下级
                        List<TPerformGroupDo> tPerformGroupDoList1 = tPerformGroupDao.
                                findAllByPGroupIdAndDelStatusOrderByGroupSort(t.getId(), 0);
                        List<ResultPerformVo> performVoList2 = new ArrayList<>();
                        if (tPerformGroupDoList1.size() > 0) {
                            tPerformGroupDoList1.forEach(p -> {
                                ResultPerformVo resultPerformVo2 = new ResultPerformVo();
                                resultPerformVo2.setName(p.getGroupName());
                                resultPerformVo2.setId(p.getId());
                                performVoList2.add(resultPerformVo2);
                            });
                        }
                        resultPerformVo1.setPerformVoList(performVoList2);
                        performVoList1.add(resultPerformVo1);

                    });
                    resultPerformVo.setPerformVoList(performVoList1);
                }

                resultPerformList.add(resultPerformVo);


            });
        }
        return resultPerformList;
    }

    /**
     * 学历人员详细信息
     *
     * @param personstereoDetailVo
     * @return
     */
    public List<UserInResultVo> getEducationDetails(PersonstereoDetailVo personstereoDetailVo) {
        List<String> list = personstereoDetailVo.getDeptIds();
        List<String> listArray = new ArrayList<>();
        List<String> list1 = Arrays.asList(politicalSub);
        if (list.size() > 0) {
            list.forEach(v -> {
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v, 0);
                if ("2".equals(sysDeptDo.getDeptLevel()) &&!list1.contains(sysDeptDo.getId())) {
                    listArray.add(v);
                } else {
                    listArray.addAll(getCompanyAllId(v));
                }
            });
        }
        List<TPersonDo> tPersonDoList = new ArrayList<>(getEducationDetailsByDepts(listArray, personstereoDetailVo.getType()));
        List<UserInResultVo> userInResultVoList = CommonUnit.changeToEntity(tPersonDoList);
        if ("1".equals(personstereoDetailVo.getIsDownload())) {
            try {
                getAgeExport(userInResultVoList, "教育分析");
            } catch (Exception e) {
                return null;
            }
        }
        return userInResultVoList;

    }

    /**
     * 不同学历人员详细信息
     */
    private List<TPersonDo> getEducationDetailsByDepts(List<String> deptIds, String type) {
        //返回给前端的数据
        List<TPersonDo> listTotal = new ArrayList<>();
        if (deptIds.size() <= 0) {
            return listTotal;
        }

        List<String> query = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            switch (type) {
                case "博士":
                    query.add("11001");
                    break;
                case "硕士": {
                    String[] s = {"1", "11", "11002", "11003", "12", "13", "14", "18", "19"};
                    //硕士1开头的不为11001
                    query.addAll(Arrays.asList(s));
                    break;
                }
                case "本科": {
                    //2开头的
                    String[] s = {"2", "21", "22", "23", "24", "25", "27", "28", "29"};
                    query.addAll(Arrays.asList(s));
                    break;
                }
                case "大专": {
                    String[] s = {"3", "31", "32", "33", "34", "35", "37", "38", "39"};
                    query.addAll(Arrays.asList(s));
                    break;
                }
                case "高中及以下": {
                    String[] s = {"4", "41", "42", "47", "48", "49", "5", "51", "59", "6", "61", "62", "63", "68", "69",
                            "7", "71", "72", "73", "78", "79", "8", "81", "88", "89", "9"};
                    query.addAll(Arrays.asList(s));
                    break;
                }
                default:
                    break;
            }
        }
        List<TPersonDo> tPersonDoList = new ArrayList<>();
        if (deptIds.size() > ONE_THOUSAND) {
            int listSize = deptIds.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                tPersonDoList.addAll(tPersonDao.getEducationFirstByeducationFlag(query, "0", deptIds.subList(1000 * i, 1000 * (i + 1))));
            }
            tPersonDoList.addAll(tPersonDao.getEducationFirstByeducationFlag(query, "0", deptIds.subList(1000 * listSize, deptIds.size())));
        } else {
            tPersonDoList.addAll(tPersonDao.getEducationFirstByeducationFlag(query, "0", deptIds));
        }
        List<String> ids = new ArrayList<>();
        //去重复过滤
        tPersonDoList = tPersonDoList.stream().filter(
                v -> {
                    boolean flag = !ids.contains(v.getIdNo());
                    ids.add(v.getIdNo());
                    return flag;
                }
        ).collect(Collectors.toList());
        return tPersonDoList;
    }

    /**
     * 入警年限
     *
     * @param personstereoDetailVo
     * @return
     */
    public List<UserInResultVo> getUnitWorkAgeDetail(PersonstereoDetailVo personstereoDetailVo) {
        List<TPersonDo> tPersonDoList = new ArrayList<>();
        //查询入警年限
        List<String> list = personstereoDetailVo.getDeptIds();
        List<String> list1 = Arrays.asList(politicalSub);
        if (list.size() > 0) {
            list.forEach(v -> {
                List<String> listArray = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v, 0);
                if ("2".equals(sysDeptDo.getDeptLevel()) && !list1.contains(sysDeptDo.getId())) {
                    listArray.add(v);
                } else {
                    listArray.addAll(getCompanyAllId(v));
                }
                tPersonDoList.addAll(getUnitWorkAgeDetailByDeptIds(listArray, personstereoDetailVo.getType()));
            });

        }
        List<UserInResultVo> userInResultVoList = CommonUnit.changeToEntity(tPersonDoList);
        if ("1".equals(personstereoDetailVo.getIsDownload())) {
            try {
                getAgeExport(userInResultVoList, "工作年限分析");
            } catch (Exception e) {
                return null;
            }
        }
        return userInResultVoList;

    }

    /**
     * 单位如就年限详情
     *
     * @param deptIds
     * @param type
     * @return
     */
    private List<TPersonDo> getUnitWorkAgeDetailByDeptIds(List<String> deptIds, String type) {
        if (deptIds.size() <= 0) {
            return new ArrayList<>();
        }
        //符合条件的结果详情
        List<TPersonDo> listResult = new ArrayList<>();
        //入警年限
        String workAgeStart = "";
        String workAageEnd = "";
        if (StringUtils.isNotEmpty(type)) {
            switch (type) {
                case "5年以下":
                    workAgeStart = "0";
                    workAageEnd = "4";
                    break;
                case "5(含)-10":
                    workAgeStart = "5";
                    workAageEnd = "9";
                    break;
                case "10(含)-20":
                    workAgeStart = "10";
                    workAageEnd = "19";
                    break;
                case "20(含)-30":
                    workAgeStart = "20";
                    workAageEnd = "29";
                    break;
                case "30(含)以上":
                    workAgeStart = "30";
                    workAageEnd = "99";
                    break;
                default:
                    break;
            }

        }
        //符合条件的结果
        if (deptIds.size() > ONE_THOUSAND) {
            int listSize = deptIds.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                listResult.addAll(tPersonDao.findPersonByDeptIdsAndworkAge(deptIds.subList(1000 * i, 1000 * (i + 1)), workAgeStart, workAageEnd));
            }
            listResult.addAll(tPersonDao.findPersonByDeptIdsAndworkAge(deptIds.subList(1000 * listSize, deptIds.size()), workAgeStart, workAageEnd));
        } else {
            listResult.addAll(tPersonDao.findPersonByDeptIdsAndworkAge(deptIds, workAgeStart, workAageEnd));
        }

        return listResult;

    }

    /**
     * 查询婚姻状态
     *
     * @param personstereoDetailVo
     * @return
     */
    public List<UserInResultVo> getUnitMaritalDetail(PersonstereoDetailVo personstereoDetailVo) {
        List<TPersonDo> tPersonDoList = new ArrayList<>();

        //部门id
        List<String> list = personstereoDetailVo.getDeptIds();
        List<String> list1 = Arrays.asList(politicalSub);
        if (list.size() > 0) {
            list.forEach(v -> {
                List<String> deptIdList = new ArrayList<>();
                SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(v, 0);
                if ("2".equals(sysDeptDo.getDeptLevel()) &&!list1.contains(sysDeptDo.getId())) {
                    deptIdList.add(v);
                } else {
                    deptIdList.addAll(getCompanyAllId(v));
                }
                tPersonDoList.addAll(getUnitMaritalDetailByDeptIds(deptIdList, personstereoDetailVo.getType()));
                ;
            });
        }

        List<UserInResultVo> userInResultVoList = CommonUnit.changeToEntity(tPersonDoList);
        //下载
        if ("1".equals(personstereoDetailVo.getIsDownload())) {
            try {
                getAgeExport(userInResultVoList, "婚姻分析");
            } catch (Exception e) {
                return null;
            }
        }
        return userInResultVoList;
    }

    /**
     * 不同年龄人员详细信息
     */
    private List<TPersonDo> getUnitMaritalDetailByDeptIds(List<String> deptIds, String type) {
        //返回给前端的数据
        List<TPersonDo> listTotal = new ArrayList<>();
        if (deptIds.size() <= 0) {
            return listTotal;
        }
        //符合条件的结果详情
        List<TPersonDo> listResult = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            //符合条件的结果
            if (deptIds.size() > ONE_THOUSAND) {
                int listSize = deptIds.size() / 1000;
                for (int i = 0; i < listSize; i++) {
                    listResult.addAll(tPersonDao.findPersonByDeptIdsAndMarriayType(deptIds.subList(1000 * i, 1000 * (i + 1)), type));
                }
                listResult.addAll(tPersonDao.findPersonByDeptIdsAndMarriayType(deptIds.subList(1000 * listSize, deptIds.size()), type));
            } else {
                listResult.addAll(tPersonDao.findPersonByDeptIdsAndMarriayType(deptIds, type));
            }

        }
        return listResult;
    }

    public ResulHeadInfoVo getUnitHeadDetail(String unitId) {
        //根据部门查询所有的人员
        //页面弹框弹出来的详情对应的实体类
        ResulHeadInfoVo headInfoVo = new ResulHeadInfoVo();
        childList = new ArrayList<>();
        //用于装身份证号
        Set<String> idNoList = new HashSet<>();
        Set<String> removeList = new HashSet<>();
        List<HeadInfo> resultList = new ArrayList<>();
        //获得所有该部门下所属的部门id
        List<String> deptIdsQoList = getCompanyAllId(unitId);

        //用于装TPerson
        ChildUnit childUnit = new ChildUnit();
        idNoList.addAll(childUnit.getSubPersonIdNos(unitId));
        //获取部门实体
        SysDeptDo sysDeptDo = sysDeptDao.findByIdAndDelStatus(unitId, 0);
        if (deptIdsQoList.size() > 0) {
//            List<TPersonCountAndAge> tPersonCountAndAgeList = tPersonDao.findCountByDeptIds(deptIdsQoList);
//            int personSum = tPersonCountAndAgeList.get(0).getNum();
            List<HeadInfo> policeHead = new ArrayList<>();
            List<String> head = new ArrayList<>();
            //查询领导班子人数
            //市局领导
            if("1".equals(sysDeptDo.getDeptLevel())){
                head.addAll(Arrays.asList(overAllHeader));
            }
            //分局领导
            if ("2".equals(sysDeptDo.getDeptLevel())) {
                if(POLITICAL.equals(unitId)) {
                    head.add("9");
                }else {
                    head.addAll(Arrays.asList(headrs2));
                }
            }
            //派出所领导
            if ("3".equals(sysDeptDo.getDeptLevel())) {
                head.addAll(Arrays.asList(headrs3));
            }

            //获取对应部门的领导信息
            List<HeadInfo> headInfoList = getDetailHeadList(head, unitId);
            headInfoVo.setHeadCount(headInfoList.size());
            headInfoVo.setHeadInfoList(headInfoList);
            resultList.addAll(headInfoList);
            //内下设机构主要领导
            String[] heard1 = new String[]{"32", "33"};
            List<HeadInfo> headInfoList1 = getDetailHeadList(Arrays.asList(heard1), unitId);
            headInfoVo.setInSetsCount(headInfoList1.size());
            headInfoVo.setInSetsList(headInfoList1);
            resultList.addAll(headInfoList1);
            //内下设机构政工
            String[] heard2 = new String[]{"30", "31"};
            List<HeadInfo> headInfoList2 = getDetailHeadList(Arrays.asList(heard2), unitId);
            headInfoVo.setInSetsPoliticalCount(headInfoList2.size());
            headInfoVo.setInSetsPoliticalList(headInfoList2);
            resultList.addAll(headInfoList2);
            //副职
            String[] heard3 = new String[]{"34", "35"};
            List<HeadInfo> headInfoList3 = getDetailHeadList(Arrays.asList(heard3), unitId);
            headInfoVo.setInSetsDeputyCount(headInfoList3.size());
            headInfoVo.setInSetsDeputy(headInfoList3);
            resultList.addAll(headInfoList3);
//            int policeCount = personSum-headInfoList.size()-headInfoList1.size()-headInfoList2.size()-headInfoList3.size();
//            headInfoVo.setPoliceCount(policeCount);

            if (resultList.size() > 0) {
                resultList.forEach(r -> {
                    removeList.add(r.getIdNo());
                });
            }
            if (idNoList.size() > 0 && removeList.size() > 0) {
                idNoList.removeAll(removeList);
            }
            if (idNoList.size() > 0) {
                idNoList.forEach(f -> {
                    TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(f, "0");
                    if (tPersonDo != null) {
                        HeadInfo headInfo = new HeadInfo();
                        headInfo.setName(tPersonDo.getPersonName());
                        headInfo.setIdNo(f);
                        headInfo.setImg(webPath + "teamPerson/getFtpImgPath?idNumber=" + f);
//                        headInfo.setImg(perComparisonService.download(f));

                        if (tPersonDo.getOfficeSimpleName() != null) {
                            headInfo.setDeptName(tPersonDo.getOrgSimpleName() + tPersonDo.getOfficeSimpleName());

                        } else {
                            headInfo.setDeptName(tPersonDo.getOrgSimpleName());

                        }
                        policeHead.add(headInfo);
                    }
                });
            }
            headInfoVo.setPoliceCount(policeHead.size());
            headInfoVo.setPolice(policeHead);
        }
        return headInfoVo;

    }


}
