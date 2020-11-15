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
     * ���β���������
     */
    public static List<String> zzbSubList = Stream.of(
            "2014111018472484800000000567", "2014111018472484800000000566", "2014111018472522300000002932", "2014111018472481700000000388",
            "2014111018472487900000000748", "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"
    ).collect(toList());

    /**
     * ���β���������
     */
    public static List<String> specialDeptList = Stream.of(
            "2014111018472516100000002607", "2014111018472497300000001287", "2014111018472497300000001308"
    ).collect(toList());

    /**
     * ���β�id
     */
    public static String ZZB_ID = "2014111018472522300000002937";
    public static List<String> duties = Arrays.asList("����ְ","����ְ","����ְ","����ְ","����ְ","����ְ","����ְ");
    /**
     * ���쵼ְ��
     */
    public static List<Pair<String,String>> nonLeaderDuties = Arrays.asList(Pair.of("������","0525"),Pair.of("Ѳ��Ա","0530"),Pair.of("������","0625"),Pair.of("��Ѳ��Ա","0630"),Pair.of("������","0717"),Pair.of("�Ͼ���Ѳ��Ա","0718"),Pair.of("������","0730"),Pair.of("����Ա","0740"),Pair.of("������","0830"),Pair.of("������Ա","0840"));
    public static List<String> education = Arrays.asList("��ʿ", "˶ʿ", "����", "��ר", "���м�����");

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
            return "����";
        }
        return temp;
    }

    public static List<String> deptList = new CopyOnWriteArrayList<>();

    //----------------����Ա�õ�λ��--------------------//
    //�����أ��ֿڡ����������ϡ���ˮ���ߴ�
    public static List<String> oldFiveDeptIds = Arrays.asList(new String[]{"2014111018472511400000002322","2014111018472516100000002593","2014111018472517600000002930","2014111018472509800000002206","2014111018472505100000001734"});
    //�о֡�����
    public static List<String> bmIds = Arrays.asList(new String[]{"2014110416460086100000002942","2014111018472522300000002937","2014111018472481700000000388","2014111018472484800000000512","2014111018472497300000001246","2014111018472486400000000648","2014111018472522300000002938","2014111018472522300000002940","1808061651531350000000009569","2014111018472497300000001250","2014111018472495800000001145","2014111018472484800000000531","2014111018472480100000000351","2014111018472481700000000359","2014111018472484800000000550","2014111018472478600000000217","2014111018472484800000000540","2014111018472484800000000578","2014111018472484800000000511","2014111018472484800000000526","2014111018472480100000000330","2014111018472492600000000966","2014111018472491100000000862","2014111018472486400000000599","2014111018472480100000000339","2014111018472489500000000793","2014111018472516100000002607","2014111018472497300000001287","2014111018472484800000000517","2014111018472477000000000192","1506241826242730000000000272","1603211549598110000000023053","1908200929504940000000000466","2014111018472487900000000747"});
    //�־֣����������أ�
    public static List<String> fjIds = Arrays.asList(new String[]{ "2014111018472512900000002390","2014111018472522300000002936","2014111018472508300000001991","2014111018472517600000002837","2014111018472505100000001665","2014111018472516100000002727","2014111018472500400000001420","2014111018472497300000001265","2014111018472497300000001308"});
//--------------------����Ա�õ�λ��-------------------------------//


    //�־ֲ���id����֯�ܹ�����
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
//        deptList.add("�Ͼ��й�����");
//        deptList.add("���β�");
//        deptList.add("���´�");
//        deptList.add("������");
//        deptList.add("��֯������");
//        deptList.add("���񾯲�ѵ��ѧУ");
//        deptList.add("�����ݸɲ���");
//        deptList.add("����ίԱ��");
//        deptList.add("��ί������ң�");
//        deptList.add("������");
//        deptList.add("�칫��");
//        deptList.add("�����ϲ�");
//        deptList.add("��ƴ�");
//        deptList.add("�Ƽ���Ϣ����");
//        deptList.add("����������");
//        deptList.add("�ŷô�");
//        deptList.add("ָ������");
//        deptList.add("���񶽲�֧��");
//        deptList.add("���ڰ�ȫ����֧��");
//        deptList.add("��λ�ڲ���ȫ����֧��");
//        deptList.add("�ΰ�����֧��");
//        deptList.add("���¾���֧�ӣ�������鴦��");
//        deptList.add("��������֧��");
//        deptList.add("�������֧��");
//        deptList.add("��ͨ����֧��");
//        deptList.add("���뾳����֧��");
//        deptList.add("���簲ȫ����֧��");
//        deptList.add("����֧��");
//        deptList.add("�ؾ�֧�ӣ�Ѳ��֧�ӣ�");
//        deptList.add("�˿ڹ���֧��");
//        deptList.add("���÷������֧��");
//        deptList.add("����֧��");
//        deptList.add("����֧��");
//        deptList.add("ˮ�Ϸ־�");
//        deptList.add("������ͨ�ΰ��־�");
//        deptList.add("���ֲ�����֧��");
//        deptList.add("�����ʹ���а�����⹤��֧��");
//        deptList.add("ά�Ȱ�");
//        deptList.add("�����");
//        deptList.add("����Ч�ܼ��֧�ӣ�����죩");
//        deptList.add("ʳƷҩƷ�ͻ����������֧��");
//        deptList.add("����֧��");
//        deptList.add("�侯֧��");
//        deptList.add("������������");
//        deptList.add("���ھ�");
//
//        deptList.add("���������־�");
//        deptList.add("����־�");
//        deptList.add("�ػ��־�");
//        deptList.add("�����־�");
//        deptList.add("��¥�־�");
//        deptList.add("��ϼ�־�");
//        deptList.add("�껨̨�־�");
//        deptList.add("�ֿڷ־�");
//        deptList.add("�����־�");
//        deptList.add("���Ϸ־�");
//        deptList.add("���ü����������־�");
//        deptList.add("���������־�");
//        deptList.add("��ˮ�־�");
//        deptList.add("�ߴ��־�");
//    }

    public static Map<String, String> getSourceMap() {
        if (sourceMap == null) {
            sourceMap = new HashMap<>(16);
            sourceMap.put("��ũ��¼�õ���Ա", "����п�");
            sourceMap.put("�ӳ���¼�����Ա", "����п�");
            sourceMap.put("����ͳһ������о���", "����п�");
            sourceMap.put("��������ҵ���˿�¼", "����п�");
            sourceMap.put("��������ҵ�ɲ���¼", "����п�");
            sourceMap.put("¼�õ���ѧ��ҵ��", "����п�");
            sourceMap.put("¼�õ�ְҵ���б�ҵ��", "����п�");
            sourceMap.put("ͳһ������ר��ҵ��", "����п�");
            sourceMap.put("ͳһ�����ѧ��ҵ��", "����п�");
            sourceMap.put("�ǹ��е�λ��Ա��¼", "����п�");
            sourceMap.put("����ͬ�����Ƶ�λ����", "ʵʩ����Ա�����ص���");
            sourceMap.put("��Ⱥ�Ȼ��ص���", "ʵʩ����Ա�����ص���");
            sourceMap.put("�������ص���", "ʵʩ����Ա�����ص���");
            sourceMap.put("��Ա����", "תҵ����Ա����");
            sourceMap.put("תҵ����", "תҵ����Ա����");
        }
        return sourceMap;
    }

    /**
     * �����������ͻ�ȡ��Դ����
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
     * ת����ǰ�˵�����
     * @param tPersonDoList
     * @return
     */
    public static List<UserInResultVo> changeToEntity(List<TPersonDo> tPersonDoList) {
        //ת����ǰ�˵�ʵ��
        List<UserInResultVo> listResult = new ArrayList<>();
        if (tPersonDoList.size() > 0) {
            tPersonDoList.forEach(v -> {
                UserInResultVo userInfoSubset = new UserInResultVo();
                String orgName = StringUtils.isNotEmpty(v.getOrgSimpleName()) ? v.getOrgSimpleName() : "";
                String deptName = StringUtils.isNotEmpty(v.getOfficeSimpleName()) ? v.getOfficeSimpleName() : "";
                userInfoSubset.setDeptName(orgName+deptName);
                userInfoSubset.setUserName(v.getPersonName());
                userInfoSubset.setSex("1".equals(v.getSex()) ? "��" : "Ů");
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
     * ƴ��sql�����sql���in�����ݳ���1000������
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
     * ƴ��sql�����sql���in�����ݳ���1000������
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
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


}
