package com.sugon.analysis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sugon.analysis.domain.view.persondraw.JcjAndOpinionVo;
import com.sugon.analysis.domain.view.persondraw.OpinionAndJcjVo;
import com.sugon.analysis.domain.view.persondraw.PersonAndOpinionVo;
import com.sugon.analysis.domain.view.return2view.JcjVo;
import com.sugon.analysis.domain.view.return2view.OpinionVo;
import com.sugon.analysis.domain.view.teambuild.UnitSatisfiedVo;
import com.sugon.analysis.repository.oracle.SysDeptDao;
import com.sugon.analysis.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sugon.analysis.util.CommonUnit.convertValues2Sql;
import static java.util.stream.Collectors.toList;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/25
 */
@Slf4j
@Service
public class SatisfiedDataSevice {
    @Value("${mymyd.webPath}")
    private String webPath;

    @Value("${spring.profiles.active}")
    private String active;


    @Resource(name = "secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;
    @Resource
    private SysDeptDao sysDeptDao;

    /**
     * 第一单元部门
     */
    private static List<String> oneUnitList = Stream.of(
            "2014111018472516100000002727", "2014111018472508300000001991", "2014111018472505100000001665",
            "2014111018472517600000002837", "2014111018472500400000001420", "2014111018472522300000002936"
    ).collect(toList());

    /**
     * 第二单元部门
     */
    private static List<String> twoUnitList = Stream.of(
            "2014111018472509800000002206", "2014111018472505100000001734", "2014111018472511400000002322",
            "2014111018472512900000002390", "2014111018472516100000002593", "2014111018472517600000002930"
    ).collect(toList());

    /**
     * 第三单元部门
     */
    private static List<String> threeUnitList = Stream.of(
            "2014111018472497300000001265", "2014111018472497300000001287", "2014111018472497300000001308", "2014111018472516100000002607"
    ).collect(toList());

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * 获取每年的接处警数据
     *
     * @param idNo
     * @return
     */
    public List<JcjVo> getJcjAndMylDataByYear(String idNo) {
        int allJcjCount = 0;
        int allAjCount = 0;
        String url = webPath + "/getSinglePerson/";
        Map<String, String> param = new HashMap<>(10);
        param.put("idNo", idNo);
        String data = null;
        try {
            data = HttpClientUtil
                    .doGet(url, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<JcjVo> jcjVoList = new ArrayList<>();
        if (!StringUtils.isEmpty(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            if (jsonObject.get("data") == null) {
                return jcjVoList;
            }
            JSONArray jsonArray = JSONObject.parseArray(jsonObject.get("data").toString());
            List<JcjAndOpinionVo> jcjAndOpinionVoList = new ArrayList<>();
            if (jsonArray.size() > 0) {
                jsonArray.forEach(v -> {
                    JSONObject jsonObject1 = (JSONObject) v;
                    JcjAndOpinionVo jcjAndOpinionVo = JSONObject.parseObject(JSON.toJSONString(jsonObject1), JcjAndOpinionVo.class);
                    jcjAndOpinionVoList.add(jcjAndOpinionVo);
                });
            }
            List<OpinionVo> opinionVoList = new ArrayList<>();
            if (jcjAndOpinionVoList.size() > 0) {
                allJcjCount = jcjAndOpinionVoList.stream().mapToInt(JcjAndOpinionVo::getJcj).sum();
                allAjCount = jcjAndOpinionVoList.stream().mapToInt(JcjAndOpinionVo::getAj).sum();
                jcjAndOpinionVoList.forEach(f -> {
                    OpinionVo opinionVo = new OpinionVo();
                    String yearAndMonth = String.valueOf(f.getYyyymm());
                    opinionVo.setYear(yearAndMonth.substring(0, 4));
                    opinionVo.setMonth(yearAndMonth.substring(4));
                    opinionVo.setJcj(f.getJcj());
                    opinionVo.setAj(f.getAj());
                    if (f.getJcjmyl() != null && f.getAjmyl() != null) {
                        double myAvgMyd = (f.getJcjmyl() + f.getAjmyl()) / 2;
                        opinionVo.setOpinion(myAvgMyd);
                    } else {
                        if (f.getJcjmyl() != null) {
                            opinionVo.setOpinion(f.getJcjmyl());
                        }
                        if (f.getAjmyl() != null) {
                            opinionVo.setOpinion(f.getAjmyl());
                        }
                    }
                    opinionVoList.add(opinionVo);

                });
            }
            if (opinionVoList.size() > 0) {
                Map<String, List<OpinionVo>> result2 = opinionVoList.stream().collect(
                        Collectors.groupingBy(OpinionVo::getYear
                        )
                );
                if (result2.size() > 0) {
                    for (Map.Entry<String, List<OpinionVo>> entry : result2.entrySet()) {
                        String mapKey = entry.getKey();
                        List<OpinionVo> mapValue = entry.getValue();
                        JcjVo jcjVo = new JcjVo();
                        jcjVo.setYear(mapKey);
                        if (mapValue.size() > 0) {
                            int jcjCount = mapValue.stream().mapToInt(OpinionVo::getJcj).sum();
                            jcjVo.setJcj(jcjCount);
                            int ajCount = mapValue.stream().mapToInt(OpinionVo::getAj).sum();
                            jcjVo.setAj(ajCount);
                            double mydCount = mapValue.stream().mapToDouble(OpinionVo::getOpinion).average().getAsDouble();
                            jcjVo.setOpinion(mydCount);
                            if (allJcjCount != 0) {
                                double jcjAccount = (double) jcjCount / (double) allJcjCount;
                                jcjVo.setAccountedFoJcj(String.valueOf(jcjAccount * 100));
                            } else {
                                double jcjAccount = 0;
                                jcjVo.setAccountedFoJcj(String.valueOf(jcjAccount));
                            }

                            if (allAjCount != 0) {
                                double ajAccount = (double) ajCount / (double) allAjCount;
                                jcjVo.setAccountedForAj(String.valueOf(ajAccount * 100));
                            } else {
                                double ajAccount = 0;
                                jcjVo.setAccountedForAj(String.valueOf(ajAccount));
                            }
                        }
                        jcjVoList.add(jcjVo);
                    }
                }
            }
        }

        return jcjVoList;
    }


    /**
     * jcjmyl数据
     *
     * @param idNo
     * @return
     */
    OpinionAndJcjVo getJcjAndMylData(String idNo, int queryStart, int queryEnd) {
        String url = webPath + "/getSinglePerson/";
        Map<String, String> param = new HashMap<>(10);
        param.put("idNo", idNo);
        String data = HttpClientUtil
                .doGet(url, param);
        if (StringUtils.isNotBlank(data)) {
            System.out.println("data:" + data);
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONArray jsonArray = JSONObject.parseArray(jsonObject.get("data").toString());
            List<JcjAndOpinionVo> jcjAndOpinionVoList = new ArrayList<>();
            List<JcjAndOpinionVo> jcjAndOpinionVoList1 = new ArrayList<>();
            OpinionAndJcjVo opinionAndJcjVo = new OpinionAndJcjVo();
            if (jsonArray.size() > 0) {
                jsonArray.forEach(v -> {
                    JSONObject jsonObject1 = (JSONObject) v;
                    JcjAndOpinionVo jcjAndOpinionVo = JSONObject.parseObject(JSON.toJSONString(jsonObject1), JcjAndOpinionVo.class);
                    jcjAndOpinionVoList.add(jcjAndOpinionVo);
                });
            }
            if (jcjAndOpinionVoList.size() > 0) {
                int allJcjCount = jcjAndOpinionVoList.stream().mapToInt(JcjAndOpinionVo::getJcj).sum();
                opinionAndJcjVo.setAllJcj(allJcjCount);
                int allAjCount = jcjAndOpinionVoList.stream().mapToInt(JcjAndOpinionVo::getAj).sum();
                opinionAndJcjVo.setAllAj(allAjCount);
                jcjAndOpinionVoList1 = jcjAndOpinionVoList.
                        stream().
                        filter(f -> f.getYyyymm() >= queryStart && f.getYyyymm() <= queryEnd).
                        sorted(Comparator.comparing(JcjAndOpinionVo::getYyyymm)).
                        collect(Collectors.toList());
                if (jcjAndOpinionVoList1.size() > 0) {
                    int jcjCount = jcjAndOpinionVoList1.stream().mapToInt(JcjAndOpinionVo::getJcj).sum();
                    int ajCount = jcjAndOpinionVoList1.stream().mapToInt(JcjAndOpinionVo::getAj).sum();
                    int xsAjCount = jcjAndOpinionVoList1.stream().mapToInt(JcjAndOpinionVo::getXsaj).sum();
                    opinionAndJcjVo.setAj(ajCount);
                    opinionAndJcjVo.setJcj(jcjCount);
                    opinionAndJcjVo.setXsAj(xsAjCount);
                    double mydCount = 0;
                    double ajMydCount = 0;
                    if (jcjAndOpinionVoList.size() > 0) {
                        mydCount = jcjAndOpinionVoList.stream().filter(f -> f.getJcjmyl() != null).mapToDouble(JcjAndOpinionVo::getJcjmyl).average().orElse(0D);
                        ajMydCount = jcjAndOpinionVoList.stream().filter(f -> f.getAjmyl() != null).mapToDouble(JcjAndOpinionVo::getAjmyl).average().orElse(0D);
                    }
                    if (mydCount > 0 && ajMydCount > 0) {
                        double myd = (mydCount + ajMydCount) / 2;
                        String mydAvgStr = decimalFormat.format(myd);
                        opinionAndJcjVo.setMyd(mydAvgStr);
                    } else {
                        if (mydCount > 0) {
                            String mydAvgStr = decimalFormat.format(mydCount);
                            opinionAndJcjVo.setMyd(mydAvgStr);
                        }
                        if (ajMydCount > 0) {
                            String mydAvgStr = decimalFormat.format(ajMydCount);
                            opinionAndJcjVo.setMyd(mydAvgStr);
                        }
                    }
                    List<OpinionVo> opinionVos = new ArrayList<>();
                    jcjAndOpinionVoList1.forEach(j -> {
                        OpinionVo opinionVo = new OpinionVo();
                        String yearAndMonth = String.valueOf(j.getYyyymm());
                        opinionVo.setYear(yearAndMonth.substring(0, 4));
                        opinionVo.setMonth(yearAndMonth.substring(4));
                        if (j.getJcjmyl() != null && j.getAjmyl() != null) {
                            double myAvgMyd = (j.getJcjmyl() + j.getAjmyl()) / 2;
                            opinionVo.setMymyd(String.format("%.2f", Double.parseDouble(String.valueOf(myAvgMyd))));

                        } else {
                            if (j.getAjmyl() != null) {
                                opinionVo.setMymyd(String.format("%.2f", j.getAjmyl()));
                            }
                            if (j.getJcjmyl() != null) {
                                opinionVo.setMymyd(String.format("%.2f", j.getJcjmyl()));
                            }
                        }

                        opinionVos.add(opinionVo);
                    });
                    opinionAndJcjVo.setOpinionVoList(opinionVos);
                    return opinionAndJcjVo;

                } else {
                    return null;
                }

            }
            return null;

        } else {
            return null;
        }

    }

    /**
     * 民意110数据
     *
     * @param idNos
     * @param year
     * @return
     */
    List<PersonAndOpinionVo> getMydByIdNos(List<String> idNos, String year) {
        String url = webPath + "getBatchPerson/";
        Map<String, String> param = new HashMap<>();
        param.put("idNoList", "[\"" + StringUtils.join(idNos, "\",\"") + "\"]");
//        jsonObject1.put("idNoList",idNos);
        param.put("year", year);
        System.out.println("idNoList：" + idNos);
        System.out.println("year：" + year);

        String data = HttpClientUtil
                .doPost(url, param);
        System.out.println("data:" + data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray jsonArray = JSONObject.parseArray(jsonObject.get("data").toString());
        List<PersonAndOpinionVo> personAndOpinionVoArrayList = new ArrayList<>();
        if (jsonArray.size() > 0) {
            jsonArray.forEach(v -> {
                JSONObject jsonObject2 = (JSONObject) v;
                PersonAndOpinionVo personAndOpinionVo = JSONObject.
                        parseObject(JSON.toJSONString(jsonObject2), PersonAndOpinionVo.class);
                personAndOpinionVoArrayList.add(personAndOpinionVo);
            });
        }
        return personAndOpinionVoArrayList;
    }

    /**
     * 获取队伍民意110队伍满意度数据,并根据不同单元的不同权重计算综合满意度
     */
    String getUnitSatisfied(String unitId) {
        String url = webPath + "getUnit?unitId=" + unitId;
        String msg = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(msg);
        JSONArray jsonArray = JSONObject.parseArray(jsonObject.get("data").toString());
        List<UnitSatisfiedVo> list = new ArrayList<>();
        if (jsonArray.size() > 0) {
            jsonArray.forEach(v -> {
                JSONObject jsonObject1 = (JSONObject) v;
                UnitSatisfiedVo unitSatisfiedVo = JSONObject.parseObject(JSON.toJSONString(jsonObject1), UnitSatisfiedVo.class);
                list.add(unitSatisfiedVo);
            });
        }
        list.sort((o1, o2) -> {
            Integer date1 = Integer.parseInt(o1.getYYYYMM());
            Integer date2 = Integer.parseInt(o2.getYYYYMM());
            return date2.compareTo(date1);
        });
        return satisfiedCount(list, unitId);
    }

    /**
     * 单位满意度(民意110数据)
     */
    public List<Map<String, String>> getUnitSatisfiedBySql(List<String> unitId) {

        //拼接sql，查询所选部门人员信息
        String sql;
        if("dev".equals(active)||"local".equals(active)){
            sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY DWID ORDER BY YYYYMM DESC) RN, T.* FROM ( " +
                    "SELECT DWID, YYYYMM, " +
                    "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
                    "QUESTION3DF, " +
                    "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
                    "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
                    "SUGON.IF_V4_DWDATABYMONTH_TABLE) T) WHERE RN = 1" + convertValues2Sql("DWID", unitId);
        }else{
            sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY DWID ORDER BY YYYYMM DESC) RN, T.* FROM ( " +
                    "SELECT DWID, YYYYMM, " +
                    "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
                    "QUESTION3DF, " +
                    "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
                    "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
                    "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571) T) WHERE RN = 1" + convertValues2Sql("DWID", unitId);
        }
//生产环境的sql
//        String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY DWID ORDER BY YYYYMM DESC) RN, T.* FROM ( " +
//                "SELECT DWID, YYYYMM, " +
//                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
//                "QUESTION3DF, " +
//                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
//                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
//                "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571) T) WHERE RN = 1" + convertValues2Sql("DWID", unitId);

        //开发环境的sql
//        拼接sql，查询所选部门人员信息
//        String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY DWID ORDER BY YYYYMM DESC) RN, T.* FROM ( " +
//                "SELECT DWID, YYYYMM, " +
//                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
//                "QUESTION3DF, " +
//                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
//                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
//                "SUGON.IF_V4_DWDATABYMONTH_TABLE) T) WHERE RN = 1" + convertValues2Sql("DWID", unitId);

        List<Map<String, Object>> queryList = secondaryJdbcTemplate.queryForList(sql);
        List<UnitSatisfiedVo> list = new ArrayList<>();
        if (queryList.size() > 0) {
            queryList.forEach(v -> {
                UnitSatisfiedVo unitSatisfiedVo = new UnitSatisfiedVo();
                unitSatisfiedVo.setDWID(v.get("DWID") == null ? null : v.get("DWID").toString());
                unitSatisfiedVo.setYYYYMM(v.get("YYYYMM") == null ? null : v.get("YYYYMM").toString());
                unitSatisfiedVo.setCKYWMYL(v.get("CKYWMYL") == null ? null : v.get("CKYWMYL").toString());//窗口服务满意率
                unitSatisfiedVo.setQUESTION3DF(v.get("QUESTION3DF") == null ? null : v.get("QUESTION3DF").toString());  //社区民警熟悉率
                unitSatisfiedVo.setJCJMYL(v.get("JCJMYL") == null ? null : v.get("JCJMYL").toString());//接处警满意率
                unitSatisfiedVo.setZHAJMYL(v.get("ZHAJMYL") == null ? null : v.get("ZHAJMYL").toString()); //综合案件满意率
                unitSatisfiedVo.setZHJTSGMYL(v.get("ZHJTSGMYL") == null ? null : v.get("ZHJTSGMYL").toString());//综合交通事故满意率
                unitSatisfiedVo.setQUESTION1DF(v.get("QUESTION1DF") == null ? null : v.get("QUESTION1DF").toString());  //社会治安满意率
                unitSatisfiedVo.setQUESTION2DF(v.get("QUESTION2DF") == null ? null : v.get("QUESTION2DF").toString()); //公安队伍满意度
                unitSatisfiedVo.setMYD12345(v.get("MYD12345") == null ? null : v.get("MYD12345").toString()); //
                list.add(unitSatisfiedVo);
            });
        }
        return satisfiedBySqlCount(list);
    }

    /**
     * 单位综合满意度计算
     */
    private String satisfiedCount(List<UnitSatisfiedVo> list, String unitId) {
        int i = 0;
        double count = 0d;
        if (oneUnitList.contains(unitId)) {
            if (list.size() > 0) {
                //窗口业务满意度
                if (StringUtils.isNotEmpty(list.get(0).getCKYWMYL())) {
                    count = count + Double.parseDouble(list.get(0).getCKYWMYL());
                    i = i + 1;
                }
                //社区民警熟悉度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION3DF())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getQUESTION3DF()));
                    i = i + 2;
                }
                //110接处警满意度
                if (StringUtils.isNotEmpty(list.get(0).getJCJMYL())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getJCJMYL()));
                    i = i + 2;
                }
                //案件办理满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHAJMYL())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getZHAJMYL()));
                    i = i + 2;
                }
                //社会治安满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION1DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION1DF());
                    i = i + 1;
                }
                //公安队伍满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION2DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION2DF());
                    i = i + 1;
                }
                //12345工单办结满意度
                if (StringUtils.isNotEmpty(list.get(0).getMYD12345())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getMYD12345()));
                    i = i + 2;
                }
            }
        } else if (twoUnitList.contains(unitId)) {
            if (list.size() > 0) {
                //窗口业务满意度
                if (StringUtils.isNotEmpty(list.get(0).getCKYWMYL())) {
                    count = count + Double.parseDouble(list.get(0).getCKYWMYL());
                    i = i + 1;
                }
                //社区民警熟悉度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION3DF())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getQUESTION3DF()));
                    i = i + 2;
                }
                //110接处警满意度
                if (StringUtils.isNotEmpty(list.get(0).getJCJMYL())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getJCJMYL()));
                    i = i + 2;
                }
                //案件办理满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHAJMYL())) {
                    count = count + Double.parseDouble(list.get(0).getZHAJMYL());
                    i = i + 1;
                }
                //交通事故满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHJTSGMYL())) {
                    count = count + Double.parseDouble(list.get(0).getZHJTSGMYL());
                    i = i + 1;
                }
                //社会治安满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION1DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION1DF());
                    i = i + 1;
                }
                //公安队伍满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION2DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION2DF());
                    i = i + 1;
                }
                //12345工单办结满意度
                if (StringUtils.isNotEmpty(list.get(0).getMYD12345())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getMYD12345()));
                    i = i + 2;
                }
            }
        } else if (threeUnitList.contains(unitId)) {
            if (list.size() > 0) {
                //窗口业务满意度
                if (StringUtils.isNotEmpty(list.get(0).getCKYWMYL())) {
                    count = count + Double.parseDouble(list.get(0).getCKYWMYL());
                    i = i + 1;
                }
                //社区民警熟悉度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION3DF())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getQUESTION3DF()));
                    i = i + 2;
                }
                //110接处警满意度
                if (StringUtils.isNotEmpty(list.get(0).getJCJMYL())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getJCJMYL()));
                    i = i + 2;
                }
                //案件办理满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHAJMYL())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getZHAJMYL()));
                    i = i + 2;
                }
                //12345工单办结满意度
                if (StringUtils.isNotEmpty(list.get(0).getMYD12345())) {
                    count = count + (2d * Double.parseDouble(list.get(0).getMYD12345()));
                    i = i + 2;
                }
            }
        } else {
            if (list.size() > 0) {
                //窗口业务满意度
                if (StringUtils.isNotEmpty(list.get(0).getCKYWMYL())) {
                    count = count + Double.parseDouble(list.get(0).getCKYWMYL());
                    i = i + 1;
                }
                //社区民警熟悉度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION3DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION3DF());
                    i = i + 1;
                }
                //110接处警满意度
                if (StringUtils.isNotEmpty(list.get(0).getJCJMYL())) {
                    count = count + Double.parseDouble(list.get(0).getJCJMYL());
                    i = i + 1;
                }
                //案件办理满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHAJMYL())) {
                    count = count + Double.parseDouble(list.get(0).getZHAJMYL());
                    i = i + 1;
                }
                //交通事故满意度
                if (StringUtils.isNotEmpty(list.get(0).getZHJTSGMYL())) {
                    count = count + Double.parseDouble(list.get(0).getZHJTSGMYL());
                    i = i + 1;
                }
                //社会治安满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION1DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION1DF());
                    i = i + 1;
                }
                //公安队伍满意度
                if (StringUtils.isNotEmpty(list.get(0).getQUESTION2DF())) {
                    count = count + Double.parseDouble(list.get(0).getQUESTION2DF());
                    i = i + 1;
                }
                //12345工单办结满意度
                if (StringUtils.isNotEmpty(list.get(0).getMYD12345())) {
                    count = count + Double.parseDouble(list.get(0).getMYD12345());
                    i = i + 1;
                }
            }
        }

        if (i > 0) {
            return String.valueOf(count / (double) i);
        } else {
            return null;
        }
    }

    /**
     * sql查询出的单位综合满意度计算
     */
    private List<Map<String, String>> satisfiedBySqlCount(List<UnitSatisfiedVo> list) {
        List<Map<String, String>> resultList = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(v -> {
                //单位名称
                String name = sysDeptDao.findByIdAndDelStatus(v.getDWID(), 0).getUnitName();
                Map<String, String> map = new HashMap<>();
                int i = 0;
                double count = 0d;
                if (oneUnitList.contains(v.getDWID())) {
                    //窗口业务满意度
                    if (StringUtils.isNotEmpty(v.getCKYWMYL())) {
                        count = count + Double.parseDouble(v.getCKYWMYL());
                        i = i + 1;
                    }
                    //社区民警熟悉度
                    if (StringUtils.isNotEmpty(v.getQUESTION3DF())) {
                        count = count + (2d * Double.parseDouble(v.getQUESTION3DF()));
                        i = i + 2;
                    }
                    //110接处警满意度
                    if (StringUtils.isNotEmpty(v.getJCJMYL())) {
                        count = count + (2d * Double.parseDouble(v.getJCJMYL()));
                        i = i + 2;
                    }
                    //案件办理满意度
                    if (StringUtils.isNotEmpty(v.getZHAJMYL())) {
                        count = count + (2d * Double.parseDouble(v.getZHAJMYL()));
                        i = i + 2;
                    }
                    //社会治安满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION1DF())) {
                        count = count + Double.parseDouble(v.getQUESTION1DF());
                        i = i + 1;
                    }
                    //公安队伍满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION2DF())) {
                        count = count + Double.parseDouble(v.getQUESTION2DF());
                        i = i + 1;
                    }
                    //12345工单办结满意度
                    if (StringUtils.isNotEmpty(v.getMYD12345())) {
                        count = count + (2d * Double.parseDouble(v.getMYD12345()));
                        i = i + 2;
                    }
                } else if (twoUnitList.contains(v.getDWID())) {
                    //窗口业务满意度
                    if (StringUtils.isNotEmpty(v.getCKYWMYL())) {
                        count = count + Double.parseDouble(v.getCKYWMYL());
                        i = i + 1;
                    }
                    //社区民警熟悉度
                    if (StringUtils.isNotEmpty(v.getQUESTION3DF())) {
                        count = count + (2d * Double.parseDouble(v.getQUESTION3DF()));
                        i = i + 2;
                    }
                    //110接处警满意度
                    if (StringUtils.isNotEmpty(v.getJCJMYL())) {
                        count = count + (2d * Double.parseDouble(v.getJCJMYL()));
                        i = i + 2;
                    }
                    //案件办理满意度
                    if (StringUtils.isNotEmpty(v.getZHAJMYL())) {
                        count = count + Double.parseDouble(v.getZHAJMYL());
                        i = i + 1;
                    }
                    //交通事故满意度
                    if (StringUtils.isNotEmpty(v.getZHJTSGMYL())) {
                        count = count + Double.parseDouble(v.getZHJTSGMYL());
                        i = i + 1;
                    }
                    //社会治安满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION1DF())) {
                        count = count + Double.parseDouble(v.getQUESTION1DF());
                        i = i + 1;
                    }
                    //公安队伍满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION2DF())) {
                        count = count + Double.parseDouble(v.getQUESTION2DF());
                        i = i + 1;
                    }
                    //12345工单办结满意度
                    if (StringUtils.isNotEmpty(v.getMYD12345())) {
                        count = count + (2d * Double.parseDouble(v.getMYD12345()));
                        i = i + 2;
                    }
                } else if (threeUnitList.contains(v.getDWID())) {
                    //窗口业务满意度
                    if (StringUtils.isNotEmpty(v.getCKYWMYL())) {
                        count = count + Double.parseDouble(v.getCKYWMYL());
                        i = i + 1;
                    }
                    //社区民警熟悉度
                    if (StringUtils.isNotEmpty(v.getQUESTION3DF())) {
                        count = count + (2d * Double.parseDouble(v.getQUESTION3DF()));
                        i = i + 2;
                    }
                    //110接处警满意度
                    if (StringUtils.isNotEmpty(v.getJCJMYL())) {
                        count = count + (2d * Double.parseDouble(v.getJCJMYL()));
                        i = i + 2;
                    }
                    //案件办理满意度
                    if (StringUtils.isNotEmpty(v.getZHAJMYL())) {
                        count = count + (2d * Double.parseDouble(v.getZHAJMYL()));
                        i = i + 2;
                    }
                    //12345工单办结满意度
                    if (StringUtils.isNotEmpty(v.getMYD12345())) {
                        count = count + (2d * Double.parseDouble(v.getMYD12345()));
                        i = i + 2;
                    }
                } else {
                    //窗口业务满意度
                    if (StringUtils.isNotEmpty(v.getCKYWMYL())) {
                        count = count + Double.parseDouble(v.getCKYWMYL());
                        i = i + 1;
                    }
                    //社区民警熟悉度
                    if (StringUtils.isNotEmpty(v.getQUESTION3DF())) {
                        count = count + Double.parseDouble(v.getQUESTION3DF());
                        i = i + 1;
                    }
                    //110接处警满意度
                    if (StringUtils.isNotEmpty(v.getJCJMYL())) {
                        count = count + Double.parseDouble(v.getJCJMYL());
                        i = i + 1;
                    }
                    //案件办理满意度
                    if (StringUtils.isNotEmpty(v.getZHAJMYL())) {
                        count = count + Double.parseDouble(v.getZHAJMYL());
                        i = i + 1;
                    }
                    //交通事故满意度
                    if (StringUtils.isNotEmpty(v.getZHJTSGMYL())) {
                        count = count + Double.parseDouble(v.getZHJTSGMYL());
                        i = i + 1;
                    }
                    //社会治安满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION1DF())) {
                        count = count + Double.parseDouble(v.getQUESTION1DF());
                        i = i + 1;
                    }
                    //公安队伍满意度
                    if (StringUtils.isNotEmpty(v.getQUESTION2DF())) {
                        count = count + Double.parseDouble(v.getQUESTION2DF());
                        i = i + 1;
                    }
                    //12345工单办结满意度
                    if (StringUtils.isNotEmpty(v.getMYD12345())) {
                        count = count + Double.parseDouble(v.getMYD12345());
                        i = i + 1;
                    }
                }

                if (i > 0) {
                    String value = String.valueOf(count / (double) i);
                    map.put("name", name);
                    map.put("unitId", v.getDWID());
                    map.put("value", StringUtils.isNotEmpty(value) ?
                            String.format("%.2f", Double.parseDouble(value)) : null);
                    map.put("date", v.getYYYYMM());
                } else {
                    map.put("name", name);
                    map.put("unitId", v.getDWID());
                    map.put("value", null);
                    map.put("date", v.getYYYYMM());
                }
                resultList.add(map);
            });
        }
        //去除无满意度队伍，并按满意度高低排序
        return resultList.stream().filter(v -> StringUtils.isNotEmpty(v.get("value"))).sorted(((o1, o2) -> {
            Double date1 = Double.parseDouble(o1.get("value"));
            Double date2 = Double.parseDouble(o2.get("value"));
            return date2.compareTo(date1);
        })).collect(toList());
    }

//    /**
//     * 按年份对比单位平均满意度(民意110数据)
//     */
//    public List<Map<String, String>> getSatisfiedComparisonBySql(List<String> unitId, String year) {
//        //拼接sql，查询所选部门人员信息
//        String sql = "SELECT DWID, YYYYMM, " +
//                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
//                "QUESTION3DF, " +
//                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
//                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
//                "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571 WHERE SUBSTR(YYYYMM, 1, 4) = '" + year + "'" + convertValues2Sql("DWID", unitId);
//
//        //拼接sql，查询所选部门人员信息
////        String sql = "SELECT DWID, YYYYMM, " +
////                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
////                "QUESTION3DF, " +
////                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
////                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
////                "SUGON.IF_V4_DWDATABYMONTH_TABLE WHERE SUBSTR(YYYYMM, 1, 4) = '" + year + "'" + convertValues2Sql("DWID", unitId);
//
//        List<Map<String, Object>> queryList = secondaryJdbcTemplate.queryForList(sql);
//        List<UnitSatisfiedVo> list = new ArrayList<>();
//        if (queryList.size() > 0) {
//            queryList.forEach(v -> {
//                UnitSatisfiedVo unitSatisfiedVo = new UnitSatisfiedVo();
//                unitSatisfiedVo.setDWID(v.get("DWID") == null ? null : v.get("DWID").toString());
//                unitSatisfiedVo.setYYYYMM(v.get("YYYYMM") == null ? null : v.get("YYYYMM").toString());
//                unitSatisfiedVo.setCKYWMYL(v.get("CKYWMYL") == null ? null : v.get("CKYWMYL").toString());
//                unitSatisfiedVo.setQUESTION3DF(v.get("QUESTION3DF") == null ? null : v.get("QUESTION3DF").toString());
//                unitSatisfiedVo.setJCJMYL(v.get("JCJMYL") == null ? null : v.get("JCJMYL").toString());
//                unitSatisfiedVo.setZHAJMYL(v.get("ZHAJMYL") == null ? null : v.get("ZHAJMYL").toString());
//                unitSatisfiedVo.setZHJTSGMYL(v.get("ZHJTSGMYL") == null ? null : v.get("ZHJTSGMYL").toString());
//                unitSatisfiedVo.setQUESTION1DF(v.get("QUESTION1DF") == null ? null : v.get("QUESTION1DF").toString());
//                unitSatisfiedVo.setQUESTION2DF(v.get("QUESTION2DF") == null ? null : v.get("QUESTION2DF").toString());
//                unitSatisfiedVo.setMYD12345(v.get("MYD12345") == null ? null : v.get("MYD12345").toString());
//                list.add(unitSatisfiedVo);
//            });
//        }
//        return satisfiedBySqlCount(list);
//    }

    /**
     * 按年份对比单位平均满意度(民意110数据)
     */
    public List<Map<String, String>> getSatisfiedComparisonBySql(List<String> unitId, List<String> years) {
        String column = "SUBSTR(YYYYMM, 1, 4)";

        String sql="";
        if("dev".equals(active)||"local".equals(active)){
            sql = "SELECT DWID, YYYYMM, " +
                    "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
                    "QUESTION3DF, " +
                    "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
                    "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
                    "SUGON.IF_V4_DWDATABYMONTH_TABLE WHERE  1=1 " +  convertValues2Sql(column, years) + convertValues2Sql("DWID", unitId);
        }else{
            sql = "SELECT DWID, YYYYMM, " +
                    "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
                    "QUESTION3DF, " +
                    "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
                    "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
                    "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571 WHERE 1=1 " + convertValues2Sql(column, years) + convertValues2Sql("DWID", unitId);
        }
        //生产环境的sql
        //拼接sql，查询所选部门人员信息
//        String sql = "SELECT DWID, YYYYMM, " +
//                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
//                "QUESTION3DF, " +
//                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
//                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
//                "SUGON.IF_V4_DWDATABYMONTH_TABLE201912201140571 WHERE 1=1 " + convertValues2Sql(column, years) + convertValues2Sql("DWID", unitId);

        //开发环境的sql
        //拼接sql，查询所选部门人员信息
//        String sql = "SELECT DWID, YYYYMM, " +
//                "CASE WHEN CKYWJZHMYL IS NOT NULL THEN CKYWJZHMYL ELSE CKYWMYL - 7.4 END CKYWMYL, " +
//                "QUESTION3DF, " +
//                "CASE WHEN JCJJZHMYL IS NOT NULL THEN JCJJZHMYL ELSE JCJMYL - 9.3 END JCJMYL, " +
//                "ZHAJMYL, ZHJTSGMYL, QUESTION1DF, QUESTION2DF, MYD12345 FROM " +
//                "SUGON.IF_V4_DWDATABYMONTH_TABLE WHERE  1=1 " +  convertValues2Sql(column, years) + convertValues2Sql("DWID", unitId);

        List<Map<String, Object>> queryList = secondaryJdbcTemplate.queryForList(sql);
        List<UnitSatisfiedVo> list = new ArrayList<>();
        if (queryList.size() > 0) {
            queryList.forEach(v -> {
                UnitSatisfiedVo unitSatisfiedVo = new UnitSatisfiedVo();
                unitSatisfiedVo.setDWID(v.get("DWID") == null ? null : v.get("DWID").toString());
                unitSatisfiedVo.setYYYYMM(v.get("YYYYMM") == null ? null : v.get("YYYYMM").toString());
                unitSatisfiedVo.setCKYWMYL(v.get("CKYWMYL") == null ? null : v.get("CKYWMYL").toString());
                unitSatisfiedVo.setQUESTION3DF(v.get("QUESTION3DF") == null ? null : v.get("QUESTION3DF").toString());
                unitSatisfiedVo.setJCJMYL(v.get("JCJMYL") == null ? null : v.get("JCJMYL").toString());
                unitSatisfiedVo.setZHAJMYL(v.get("ZHAJMYL") == null ? null : v.get("ZHAJMYL").toString());
                unitSatisfiedVo.setZHJTSGMYL(v.get("ZHJTSGMYL") == null ? null : v.get("ZHJTSGMYL").toString());
                unitSatisfiedVo.setQUESTION1DF(v.get("QUESTION1DF") == null ? null : v.get("QUESTION1DF").toString());
                unitSatisfiedVo.setQUESTION2DF(v.get("QUESTION2DF") == null ? null : v.get("QUESTION2DF").toString());
                unitSatisfiedVo.setMYD12345(v.get("MYD12345") == null ? null : v.get("MYD12345").toString());
                list.add(unitSatisfiedVo);
            });
        }
        return satisfiedBySqlCount(list);
    }

}
