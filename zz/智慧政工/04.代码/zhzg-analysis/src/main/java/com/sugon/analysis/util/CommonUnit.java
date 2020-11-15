package com.sugon.analysis.util;

import com.sugon.analysis.domain.data.object.oracle.SgQueryCategoryDo;
import com.sugon.analysis.domain.data.object.oracle.SysDeptDo;
import com.sugon.analysis.domain.data.object.oracle.TEducationDo;
import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.view.personstereo.UserInResultVo;
import com.sugon.analysis.repository.oracle.SgQueryCategoryDao;
import com.sugon.analysis.repository.oracle.SysDeptDao;
import com.sugon.analysis.repository.oracle.TEducationDao;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/2
 */
@Component
public class CommonUnit {

    /**
     * 政治部下属部门
     */
    public static List<String> zzbSubList = Stream.of(
            "2014111018472484800000000567", "2014111018472484800000000566", "2014111018472522300000002932", "2014111018472481700000000388",
            "2014111018472487900000000748", "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"
    ).collect(toList());

    /**
     * 政治部下属部门
     */
    public static List<String> specialDeptList = Stream.of(
            "2014111018472516100000002607", "2014111018472497300000001287", "2014111018472497300000001308"
    ).collect(toList());

    /**
     * 政治部id
     */
    public static String ZZB_ID = "2014111018472522300000002937";
    public static List<String> duties = Arrays.asList("正厅职","副厅职","副局职","正处职","副处职","正科职","副科职");
    /**
     * 非领导职务
     */
    public static List<Pair<String,String>> nonLeaderDuties = Arrays.asList(Pair.of("正厅级","0525"),Pair.of("巡视员","0530"),Pair.of("副厅级","0625"),Pair.of("副巡视员","0630"),Pair.of("副区级","0717"),Pair.of("南京副巡视员","0718"),Pair.of("正处级","0730"),Pair.of("调研员","0740"),Pair.of("副处级","0830"),Pair.of("副调研员","0840"));
    public static List<String> education = Arrays.asList("博士", "硕士", "本科", "大专", "高中及以下");

    private static SgQueryCategoryDao sgQueryCategoryDao;

    private static TEducationDao tEducationDao;

    private static SysDeptDao sysDeptDao;

    private static JdbcTemplate tertiaryJdbcTemplate;

    public static String active;

    @Value("${spring.profiles.active}")
    public void setActive(String active) {
        CommonUnit.active = active;
    }

    @Resource(name = "tertiaryJdbcTemplate")
    public  void setTertiaryJdbcTemplate(JdbcTemplate tertiaryJdbcTemplate) {
        CommonUnit.tertiaryJdbcTemplate = tertiaryJdbcTemplate;
    }

    @Resource
    public  void setSysDeptDao(SysDeptDao sysDeptDao) {
        CommonUnit.sysDeptDao = sysDeptDao;
    }

    @Resource
    public void setSgQueryCategoryDao(SgQueryCategoryDao sgQueryCategoryDao) {
        CommonUnit.sgQueryCategoryDao = sgQueryCategoryDao;
    }

    @Resource
    public void setEducationDao(TEducationDao tEducationDao) {
        CommonUnit.tEducationDao = tEducationDao;
    }
    private static Map<String, String> sourceMap = null;

    private static Map<String, List<String>> source = null;

    public static String getSourceName(String name) {
        String temp = getSourceMap().get(name);
        if (StringUtils.isEmpty(temp)) {
            return "其他";
        }
        return temp;
    }

    public static List<String> deptList = new CopyOnWriteArrayList<>();

    //----------------增减员用单位表--------------------//
    //老五县：浦口、江宁、六合、溧水、高淳
    public static List<String> oldFiveDeptIds = Arrays.asList(new String[]{"2014111018472511400000002322","2014111018472516100000002593","2014111018472517600000002930","2014111018472509800000002206","2014111018472505100000001734"});
    //市局、部门
    public static List<String> bmIds = Arrays.asList(new String[]{"2014110416460086100000002942","2014111018472522300000002937","2014111018472481700000000388","2014111018472484800000000512","2014111018472497300000001246","2014111018472486400000000648","2014111018472522300000002938","2014111018472522300000002940","1808061651531350000000009569","2014111018472497300000001250","2014111018472495800000001145","2014111018472484800000000531","2014111018472480100000000351","2014111018472481700000000359","2014111018472484800000000550","2014111018472478600000000217","2014111018472484800000000540","2014111018472484800000000578","2014111018472484800000000511","2014111018472484800000000526","2014111018472480100000000330","2014111018472492600000000966","2014111018472491100000000862","2014111018472486400000000599","2014111018472480100000000339","2014111018472489500000000793","2014111018472516100000002607","2014111018472497300000001287","2014111018472484800000000517","2014111018472477000000000192","1506241826242730000000000272","1603211549598110000000023053","1908200929504940000000000466","2014111018472487900000000747"});
    //分局（不含老五县）
    public static List<String> fjIds = Arrays.asList(new String[]{ "2014111018472512900000002390","2014111018472522300000002936","2014111018472508300000001991","2014111018472517600000002837","2014111018472505100000001665","2014111018472516100000002727","2014111018472500400000001420","2014111018472497300000001265","2014111018472497300000001308"});
//--------------------增减员用单位表-------------------------------//


    //分局部门id按组织架构排序
    private static List<String> deptIds = Arrays.asList(new String[]{"2014110416460086100000002942","2014111018472522300000002937","2014111018472481700000000388","2014111018472484800000000512","2014111018472497300000001246","2014111018472486400000000648","2014111018472522300000002938","2014111018472522300000002940","1808061651531350000000009569","2014111018472497300000001250","2014111018472495800000001145","2014111018472484800000000531","2014111018472480100000000351","2014111018472481700000000359","2014111018472484800000000550","2014111018472478600000000217","2014111018472484800000000540","2014111018472484800000000578","2014111018472484800000000511","2014111018472484800000000526","2014111018472480100000000330","2014111018472492600000000966","2014111018472491100000000862","2014111018472486400000000599","2014111018472480100000000339","2014111018472489500000000793","2014111018472516100000002607","2014111018472497300000001287","2014111018472484800000000517","2014111018472477000000000192","1506241826242730000000000272","1603211549598110000000023053","1908200929504940000000000466","2014111018472487900000000747",
            "2014111018472512900000002390","2014111018472522300000002936","2014111018472508300000001991","2014111018472517600000002837","2014111018472505100000001665","2014111018472516100000002727","2014111018472500400000001420","2014111018472511400000002322","2014111018472516100000002593","2014111018472517600000002930","2014111018472497300000001265","2014111018472497300000001308","2014111018472509800000002206","2014111018472505100000001734"});

    @Scheduled(initialDelay = 0,fixedDelay = 1000*60*60*24)
    public void updateDeptList(){
        deptList.clear();
        List<SysDeptDo> depts = sysDeptDao.findAllByIdInAndDelStatus(deptIds, 0);
        depts.sort(new Comparator<SysDeptDo>() {
            @Override
            public int compare(SysDeptDo o1, SysDeptDo o2) {
                int i1 = deptIds.indexOf(o1.getId());
                int i2 = deptIds.indexOf(o2.getId());
                return i1-i2;
            }
        });
        deptList.addAll(depts.stream().map(a->a.getUnitName()).collect(toList()));
    }

//    static {
//        deptList.add("南京市公安局");
//        deptList.add("政治部");
//        deptList.add("人事处");
//        deptList.add("宣传处");
//        deptList.add("组织教育处");
//        deptList.add("人民警察训练学校");
//        deptList.add("离退休干部处");
//        deptList.add("工会委员会");
//        deptList.add("纪委（监察室）");
//        deptList.add("警辅处");
//        deptList.add("办公室");
//        deptList.add("警务保障部");
//        deptList.add("审计处");
//        deptList.add("科技信息化处");
//        deptList.add("大数据中心");
//        deptList.add("信访处");
//        deptList.add("指挥中心");
//        deptList.add("警务督察支队");
//        deptList.add("国内安全保卫支队");
//        deptList.add("单位内部安全保卫支队");
//        deptList.add("治安警察支队");
//        deptList.add("刑事警察支队（刑事侦查处）");
//        deptList.add("监所管理支队");
//        deptList.add("技术侦察支队");
//        deptList.add("交通警察支队");
//        deptList.add("出入境管理支队");
//        deptList.add("网络安全保卫支队");
//        deptList.add("法制支队");
//        deptList.add("特警支队（巡警支队）");
//        deptList.add("人口管理支队");
//        deptList.add("经济犯罪侦查支队");
//        deptList.add("禁毒支队");
//        deptList.add("警卫支队");
//        deptList.add("水上分局");
//        deptList.add("公共交通治安分局");
//        deptList.add("反恐怖工作支队");
//        deptList.add("防范和处理邪教问题工作支队");
//        deptList.add("维稳办");
//        deptList.add("四项办");
//        deptList.add("警务效能监察支队（作风办）");
//        deptList.add("食品药品和环境犯罪侦查支队");
//        deptList.add("消防支队");
//        deptList.add("武警支队");
//        deptList.add("行政审批服务处");
//        deptList.add("特勤局");
//
//        deptList.add("江北新区分局");
//        deptList.add("玄武分局");
//        deptList.add("秦淮分局");
//        deptList.add("建邺分局");
//        deptList.add("鼓楼分局");
//        deptList.add("栖霞分局");
//        deptList.add("雨花台分局");
//        deptList.add("浦口分局");
//        deptList.add("江宁分局");
//        deptList.add("六合分局");
//        deptList.add("经济技术开发区分局");
//        deptList.add("地铁公安分局");
//        deptList.add("溧水分局");
//        deptList.add("高淳分局");
//    }

    public static Map<String, String> getSourceMap() {
        if (sourceMap == null) {
            sourceMap = new HashMap<>(16);
            sourceMap.put("从农村录用的人员", "社会招考");
            sourceMap.put("从城镇录入的人员", "社会招考");
            sourceMap.put("国家统一分配的研究生", "社会招考");
            sourceMap.put("国有事企业工人考录", "社会招考");
            sourceMap.put("国有事企业干部考录", "社会招考");
            sourceMap.put("录用的中学毕业生", "社会招考");
            sourceMap.put("录用的职业高中毕业生", "社会招考");
            sourceMap.put("统一分配中专毕业生", "社会招考");
            sourceMap.put("统一分配大学毕业生", "社会招考");
            sourceMap.put("非国有单位人员考录", "社会招考");
            sourceMap.put("从相同所有制单位调入", "实施公务员法机关调入");
            sourceMap.put("党群等机关调入", "实施公务员法机关调入");
            sourceMap.put("政府机关调入", "实施公务员法机关调入");
            sourceMap.put("复员军人", "转业及复员军人");
            sourceMap.put("转业军人", "转业及复员军人");
        }
        return sourceMap;
    }

    /**
     * 根据请求类型获取来源类型
     *
     * @param type
     * @return
     */
    public static List<String> getSourceDetailByType(String type) {
        if (source == null) {
            Map<String, String> sourceMap = getSourceMap();
            source = sourceMap.entrySet().stream().collect(Collectors.groupingBy(entry -> entry.getValue(), Collectors.mapping(a -> a.getKey(), Collectors.toList())));
        }

        return source.get(type);
    }


    /**
     * 转化给前端的数据
     * @param tPersonDoList
     * @return
     */
    public static List<UserInResultVo> changeToEntity(List<TPersonDo> tPersonDoList) {
        //转换给前端的实体
        List<UserInResultVo> listResult = new ArrayList<>();
        if (tPersonDoList.size() > 0) {
            tPersonDoList.forEach(v -> {
                UserInResultVo userInfoSubset = new UserInResultVo();
                String orgName = StringUtils.isNotEmpty(v.getOrgSimpleName()) ? v.getOrgSimpleName() : "";
                String deptName = StringUtils.isNotEmpty(v.getOfficeSimpleName()) ? v.getOfficeSimpleName() : "";
                userInfoSubset.setDeptName(orgName+deptName);
                userInfoSubset.setUserName(v.getPersonName());
                userInfoSubset.setSex("1".equals(v.getSex()) ? "男" : "女");
                if (StringUtils.isNotEmpty(v.getAge())) {
                    userInfoSubset.setAge(Integer.parseInt(v.getAge()));
                }
                if (StringUtils.isNotEmpty(v.getNation())) {
                    SgQueryCategoryDo sgQueryCategory = sgQueryCategoryDao.findByCvTypeAndCvFlag("AE", v.getNation());
                    userInfoSubset.setNation(sgQueryCategory.getCvName());
                }
                if (StringUtils.isNotEmpty(v.getPoliticalStatus())) {
                    SgQueryCategoryDo sgQueryCategory = sgQueryCategoryDao.findByCvTypeAndCvFlag("AT", v.getPoliticalStatus());
                    userInfoSubset.setParty(sgQueryCategory.getCvName());
                }
                if (StringUtils.isNotEmpty(v.getPoliceJobGrade())) {
                    SgQueryCategoryDo sgQueryCategory = sgQueryCategoryDao.findByCvTypeAndCvFlag("XG", v.getPoliceJobGrade());
                    userInfoSubset.setDuty(sgQueryCategory.getCvName());
                }
                if (StringUtils.isNotEmpty(v.getPoliceRank())) {
                    SgQueryCategoryDo sgQueryCategory = sgQueryCategoryDao.findByCvTypeAndCvFlag("XA", v.getPoliceRank());
                    userInfoSubset.setPoliceRank(sgQueryCategory.getCvName());
                }
                if (StringUtils.isNotEmpty(v.getJobGrade())) {
                    SgQueryCategoryDo sgQueryCategory = sgQueryCategoryDao.findByCvTypeAndCvFlag("AK", v.getJobGrade());
                    userInfoSubset.setJobGrade(sgQueryCategory.getCvName());
                }
                TEducationDo tEducationDo = tEducationDao.getFirst(v.getIdNo(), "0");
                if(tEducationDo==null){
                    return;
                }
                SgQueryCategoryDo sgQueryCategory2 = sgQueryCategoryDao.findByCvTypeAndCvFlag("AM", tEducationDo.getEducationalTitle());
                userInfoSubset.setEducation(sgQueryCategory2.getCvName());
                userInfoSubset.setSchool(v.getGraduatedFrom());
                listResult.add(userInfoSubset);
            });
        }
        return listResult;
    }

    /**
     * 拼接sql，解决sql语句in中数据超过1000的问题
     * @param column
     * @param values
     * @return
     */
    public static String convertValues2Sql(String column, List<String> values) {
        StringBuilder sqlBuilder = new StringBuilder(" AND (1=0 ");
        int remains = values.size() % 1000;
        int quotient = values.size() / 1000;
        for (int i = 0; i < quotient; i++) {
            String join = StringUtils.join(values.subList(i * 1000, (i + 1) * 1000), "','");
            sqlBuilder.append("  OR " + column + " IN ('" + join + "') ");
        }
        sqlBuilder.append("  OR " + column + " IN ('" + StringUtils.join(values.subList(quotient * 1000, quotient * 1000 + remains), "','") + "') ");
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    /**
     * 拼接sql，解决sql语句in中数据超过1000的问题
     * @param column
     * @param values
     * @return
     */
    public static String convertValues2OrSql(String column, List<String> values,Boolean equal) {
        StringBuilder sqlBuilder = new StringBuilder("(");
        for (String value : values) {
            if(equal){
                sqlBuilder.append(" "+column+"='"+value+"' OR");
            }else{
                sqlBuilder.append(" "+column+"!='"+value+"' AND");
            }
        }
        if(equal){
            sqlBuilder.delete(sqlBuilder.lastIndexOf("OR"),sqlBuilder.length());
        }else{
            sqlBuilder.delete(sqlBuilder.lastIndexOf("AND"),sqlBuilder.length());
        }
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    private static Map<String, String> deptNameMap = new ConcurrentHashMap<>();

    @Scheduled(initialDelay = 0,fixedDelay = 1000*60*2)
    public void updateDeptNameMap(){
        List<Map<String, Object>> maps = tertiaryJdbcTemplate.queryForList("SELECT * FROM DEPT_NAME_MAP");
        deptNameMap.clear();
        deptNameMap.putAll(maps.stream().collect(Collectors.toMap(a->String.valueOf(a.get("DEPT_NAME")),a->String.valueOf(a.get("SHORT_NAME")))));
    }

    public static String getUnitName(String name) {
        String temp = deptNameMap.get(name);
        if (StringUtils.isNotEmpty(temp)) {
            name = temp;
        }
        return name;
    }

    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


}
