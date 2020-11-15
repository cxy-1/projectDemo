package com.sugon.analysis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.param.PersonAndType;
import com.sugon.analysis.domain.view.persondraw.*;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.PersonComparisonMapper;
import com.sugon.analysis.repository.oracle.SgQueryCategoryDao;
import com.sugon.analysis.repository.oracle.TEducationDao;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.util.DateUtil;
import com.sugon.analysis.util.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @program: analysis->PersonComparisonService
 * @description:
 * @author: gaowh
 * @create: 2019-11-28 15:34
 **/
@Service
public class PersonComparisonService {
    @Resource
    private TPersonDao tPersonDao;
    @Resource
    private TEducationDao tEducationDao;
    @Resource
    private PersonComparisonMapper personComparisonMapper;
    @Resource
    private SgQueryCategoryDao sgQueryCategoryDao;

    @Resource
    private SatisfiedDataSevice satisfiedDataSevice;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**职务列表*/
    private static  List<String> dutyList = Stream.of(
            "正厅领导职",
            "副厅领导职",
            "副局职", "副区长",
            "副区级", "南京副巡视员",
            "正处职",
            "正处级", "副处职",
            "副处级", "正科职",
            "正科级", "副科职", "副科..."
    ).collect(toList());

    /**综合管理职级列表*/
    private static  List<String> jobLevelOneList = Stream.of(
            "一级巡视员", "二级巡视员",
            "一级调研员", "二级调研员", "三级调研员", "四级调研员",
            "一级主任科员", "二级主任科员", "三级主任科员", "四级主任科员",
            "一级科员", "二级科员"
    ).collect(toList());

    /**综合管理职级列表*/
    private static  List<String> jobLevelTwoList = Stream.of(
            "一级警务专员", "二级警务专员",
            "一级高级警长", "二级高级警长", "三级高级警长", "四级高级警长",
            "一级警长", "二级警长", "三级警长", "四级警长",
            "一级警员", "二级警员"
    ).collect(toList());

    /**综合管理职级列表*/
    private static  List<String> jobLevelThreeList = Stream.of(
            "警务技术一级总监", "警务技术二级总监",
            "警务技术一级主任", "警务技术二级主任", "警务技术三级主任", "警务技术四级主任",
            "警务技术一级主管", "警务技术二级主管", "警务技术三级主管", "警务技术四级主管",
            "警务技术员"
    ).collect(toList());

    /**职级-厅级列表*/
    private static  List<String> jobLevelSortOneList = Stream.of(
            "一级巡视员", "二级巡视员",
            "一级警务专员", "二级警务专员",
            "警务技术一级总监", "警务技术二级总监"
    ).collect(toList());

    /**职级-处级列表*/
    private static  List<String> jobLevelSortTwoList = Stream.of(
            "一级调研员", "二级调研员", "三级调研员", "四级调研员",
            "一级高级警长", "二级高级警长", "三级高级警长", "四级高级警长",
            "警务技术一级主任", "警务技术二级主任", "警务技术三级主任", "警务技术四级主任"
    ).collect(toList());

    /**职级-科级列表*/
    private static  List<String> jobLevelSortThreeList = Stream.of(
            "一级主任科员", "二级主任科员", "三级主任科员", "四级主任科员",
            "一级警长", "二级警长", "三级警长", "四级警长",
            "警务技术一级主管", "警务技术二级主管", "警务技术三级主管", "警务技术四级主管"
    ).collect(toList());

    /**职级-科员列表*/
    private static  List<String> jobLevelSortFourList = Stream.of(
            "一级科员", "二级科员",
            "一级警员", "二级警员",
            "警务技术员"
    ).collect(toList());

    /**用户在对比人中所处位置*/
    private static final String HIGH = "high";

    private static final String LOW = "low";


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

    /**
     * 民警精准画像职务对比
     */
    public ResultModel getPersonCount(PersonAndType personAndType) {
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        resultModel.setResult(idNoList.size());
        return resultModel;
    }

    /**
     * 民警精准画像职务对比
     */
    public ResultModel getPostComparison(PersonAndType personAndType) {
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //根据身份证号查询职务信息
        List<PostComparisonVo> postComparisonVoList = new ArrayList<>();
        if (idNoList.size() > 1000) {
            int listSize = idNoList.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                postComparisonVoList.addAll(personComparisonMapper.findPostByPersonId(idNoList.subList(1000 * i, 1000 * (i + 1)), personAndType));
            }
            postComparisonVoList.addAll(personComparisonMapper.findPostByPersonId(idNoList.subList(1000 * listSize, idNoList.size()), personAndType));
        } else if (idNoList.size() > 0) {
            postComparisonVoList.addAll(personComparisonMapper.findPostByPersonId(idNoList, personAndType));
        }
        //本人职务
        String myPoliceJobGrade;
        List<PostComparisonVo> myPostVoList = postComparisonVoList.stream().filter(v -> v.getIdNo().equals(personAndType.getIdNo())).collect(toList());
        if (myPostVoList.size() > 0) {
            myPoliceJobGrade = myPostVoList.get(0).getPostName();
        } else {
            myPoliceJobGrade = "";
        }
        //职务信息map
        Map<String, Map<String, Object>> postMap = new LinkedHashMap<>(10);
        //总map（包括职务信息统计及本人职务）
        Map<String, Object> resultMap = new HashMap<>(10);
        //职务统计list
        List<List<PostComparisonVo>> postList = new ArrayList<>();
        //增加8个不同职务的list
        for (int i = 0; i < 8; i++) {
            postList.add(new ArrayList<>());
        }
        postComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getPostName())) {
                switch (v.getPostName()) {
                    case "正科级":
                    case "副科职":
                    case "副科...":
                        postList.get(7).add(v);
                        break;
                    case "副处级":
                    case "正科职":
                        postList.get(6).add(v);
                        break;
                    case "正处级":
                    case "副处职":
                        postList.get(5).add(v);
                        break;
                    case "正处职":
                        postList.get(4).add(v);
                        break;
                    case "副区级":
                    case "南京副巡视员":
                        postList.get(3).add(v);
                        break;
                    case "副局职":
                    case "副区长":
                        postList.get(2).add(v);
                        break;
                    case "副厅领导职":
                        postList.get(1).add(v);
                        break;
                    case "正厅领导职":
                        postList.get(0).add(v);
                        break;
                    default:
                        break;
                }
            }
        });
        postMap.put("正厅职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(0).size());
//                    put("detail", postList.get(0));
            }
        });
        postMap.put("副厅职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(1).size());
//                    put("detail", postList.get(1));
            }
        });
        postMap.put("副局职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(2).size());
//                    put("detail", postList.get(2));
            }
        });
        postMap.put("副局职非领导", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(3).size());
//                    put("detail", postList.get(3));
            }
        });
        postMap.put("正处职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(4).size());
//                    put("detail", postList.get(4));
            }
        });
        postMap.put("副处职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(5).size());
//                    put("detail", postList.get(5));
            }
        });
        postMap.put("正科职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(6).size());
//                    put("detail", postList.get(6));
            }
        });
        postMap.put("副科职", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(7).size());
//                    put("detail", postList.get(7));
            }
        });
        //移除count为0的职务
        postMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        String myPostName = StringUtils.isNotEmpty(getMyPostType(myPoliceJobGrade)) ? myPoliceJobGrade : "无";
        resultMap.put("我的职务", myPostName);
        resultMap.put("chartData", postMap);
        int i = 0;
        final int[] allCount = {0};
        if (postList.size() > 0) {
            postList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (StringUtils.isNotEmpty(myPoliceJobGrade) && !"无".equals(myPostName)) {
            String myPostType = getMyPostType(myPoliceJobGrade);
            for (Map.Entry<String, Map<String, Object>> a : postMap.entrySet()) {
                if (a.getKey().equals(myPostType)) {
                    i = i + ((int) a.getValue().get("count") / 2);
                    break;
                }
                i = i + (int) a.getValue().get("count");
            }
            double percent = (double) i / (double) allCount[0];
            if (postMap.size() > 1) {
                if (percent <= 0.3d) {
                    resultMap.put("level", HIGH);
                } else if (percent >= 0.7d) {
                    resultMap.put("level", LOW);
                }
            }
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**获得我的职务*/
    private String getMyPostType(String myPoliceJobGrade){
        String myPostType = null;
        switch (myPoliceJobGrade) {
            case "正科级":
            case "副科职":
            case "副科...":
                myPostType = "副科职";
                break;
            case "副处级":
            case "正科职":
                myPostType = "正科职";
                break;
            case "正处级":
            case "副处职":
                myPostType = "副处职";
                break;
            case "正处职":
                myPostType = "正处职";
                break;
            case "副区级":
            case "南京副巡视员":
                myPostType = "副局职非领导";
                break;
            case "副局职":
            case "副区长":
                myPostType = "副局职";
                break;
            case "副厅领导职":
                myPostType = "副厅职";
                break;
            case "正厅领导职":
                myPostType = "正厅职";
                break;
            default:break;
        }
        return myPostType;
    }

    /**
     * 民警精准画像职级对比
     */
    public ResultModel getJobLevelComparison(PersonAndType personAndType) {
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //根据身份证号找到personid
        List<String> personIdList = new ArrayList<>();
        idNoList.forEach(v -> {
            String personId;
            TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(v, "0");
            if(tPersonDo !=null) {
                personId =tPersonDo.getDataSourceId();
                if (!StringUtils.isEmpty(personId)) {
                    personIdList.add(personId);
                }
            }
        });
        //根据personid查询职级信息
        List<JobLevelComparisonVo> jobLevelComparisonVoList = new ArrayList<>();
        if (personIdList.size() > 1000) {
            int listSize = personIdList.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                jobLevelComparisonVoList.addAll(personComparisonMapper.findJobLevelByPersonId(personIdList.subList(1000 * i, 1000 * (i + 1)), personAndType));
            }
            jobLevelComparisonVoList.addAll(personComparisonMapper.findJobLevelByPersonId(personIdList.subList(1000 * listSize, personIdList.size()), personAndType));
        } else if (personIdList.size() > 0){
            jobLevelComparisonVoList.addAll(personComparisonMapper.findJobLevelByPersonId(personIdList, personAndType));
        }
        //本人职级
        String myPersonId = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0").getDataSourceId();
        String myJobLevel;
        List<JobLevelComparisonVo> myjobLevelVoList = jobLevelComparisonVoList.stream().filter(v -> myPersonId.equals(v.getPersonId())).collect(toList());
        if (myjobLevelVoList.size() > 0){
            myJobLevel = myjobLevelVoList.get(0).getJobLevelCode();
        }else {
            myJobLevel = null;
        }
        //职级信息map
        Map<String, Map<String, Object>> jobLevelMap = new LinkedHashMap<>(10);
        //总map（包括职级信息统计及本人职级）
        Map<String, Object> resultMap = new HashMap<>(10);
        //职级统计list
        List<List<JobLevelComparisonVo>> jobLevelList = new ArrayList<>();
        if (StringUtils.isNotEmpty(myJobLevel)){
            if (myJobLevel.contains("00602")){
                jobLevelComparisonVoList.removeIf(v -> !(jobLevelOneList.contains(v.getJobLevelName())));
                getJobLevelGroup(jobLevelComparisonVoList, jobLevelList, jobLevelMap);
            }else if (myJobLevel.contains("00604")){
                jobLevelComparisonVoList.removeIf(v -> !(jobLevelTwoList.contains(v.getJobLevelName())));
                getJobLevelGroup(jobLevelComparisonVoList, jobLevelList, jobLevelMap);
            }else if (myJobLevel.contains("00606")){
                jobLevelComparisonVoList.removeIf(v -> !(jobLevelThreeList.contains(v.getJobLevelName())));
                getJobLevelGroup(jobLevelComparisonVoList, jobLevelList, jobLevelMap);
            }
        }else {
            jobLevelComparisonVoList.removeIf(v -> !(jobLevelOneList.contains(v.getJobLevelName())
                    ||jobLevelTwoList.contains(v.getJobLevelName())
                    ||jobLevelThreeList.contains(v.getJobLevelName())));
            getJobLevelGroup(jobLevelComparisonVoList, jobLevelList, jobLevelMap);
            List<Map<String, Object>> sortList = new ArrayList<>();
            int sortOne = 0, sortTwo = 0, sortThree = 0, sortFour = 0;
            for(Map.Entry<String, Map<String, Object>> entry : jobLevelMap.entrySet()){
                if (jobLevelSortOneList.contains(entry.getKey())){
                    sortOne = sortOne + Integer.parseInt(entry.getValue().get("count").toString());
                }else if (jobLevelSortTwoList.contains(entry.getKey())){
                    sortTwo = sortTwo + Integer.parseInt(entry.getValue().get("count").toString());
                }else if (jobLevelSortThreeList.contains(entry.getKey())){
                    sortThree = sortThree + Integer.parseInt(entry.getValue().get("count").toString());
                }else if (jobLevelSortFourList.contains(entry.getKey())){
                    sortFour = sortFour + Integer.parseInt(entry.getValue().get("count").toString());
                }
            }
            if (sortOne > 0){
                int finalSortOne = sortOne;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "厅级");
                    put("value", finalSortOne);
                }});
            }
            if (sortTwo > 0){
                int finalSortTwo = sortTwo;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "处级");
                    put("value", finalSortTwo);
                }});
            }
            if (sortThree > 0){
                int finalSortThree = sortThree;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "科级");
                    put("value", finalSortThree);
                }});
            }
            if (sortFour > 0){
                int finalSortFour = sortFour;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "科员");
                    put("value", finalSortFour);
                }});
            }
            resultMap.put("sortData", sortList);
        }
        String myJobLevelName = jobLevelMap.size()>0?(StringUtils.isNotEmpty(myJobLevel) ? sgQueryCategoryDao.findByCvTypeAndCvFlag("AK", myJobLevel).getCvName() : "无"):"无";
        resultMap.put("我的职级", myJobLevelName);
        resultMap.put("chartData", jobLevelMap);
        int i = 0;
        final int[] allCount = {0};
        if (jobLevelList.size() > 0){
            jobLevelList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (!("".equals(myJobLevelName) || "无".equals(myJobLevelName))) {
            for(Map.Entry<String, Map<String, Object>> a:jobLevelMap.entrySet()){
                if (myJobLevelName.equals(a.getKey())){
                    i = i + ((int)a.getValue().get("count") / 2);
                    break;
                }
                i = i + (int)a.getValue().get("count");
            }
            double percent = (double) i / (double) allCount[0];
            if (jobLevelMap.size() > 1){
                if (percent <= 0.3d) {
                    resultMap.put("level", HIGH);
                } else if (percent >= 0.7d){
                    resultMap.put("level", LOW);
                }
            }
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**
     * 民警精准画像警衔晋升对比
     */
    public ResultModel getRankComparison(PersonAndType personAndType) {
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //根据身份证号查询警衔信息
        List<RankComparisonVo> allRankComparisonVoList = new ArrayList<>();
        if (idNoList.size() > 1000) {
            int listSize = idNoList.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                allRankComparisonVoList.addAll(personComparisonMapper.findRankByIdNo(idNoList.subList(1000 * i, 1000 * (i + 1)), personAndType));
            }
            allRankComparisonVoList.addAll(personComparisonMapper.findRankByIdNo(idNoList.subList(1000 * listSize, idNoList.size()), personAndType));
        } else if (idNoList.size() > 0){
            allRankComparisonVoList.addAll(personComparisonMapper.findRankByIdNo(idNoList, personAndType));
        }
        //警衔信息map
        Map<String, Map<String, Object>> rankMap = new LinkedHashMap<>(16);
        //总map（包括警衔信息统计及本人警衔）
        Map<String, Object> resultMap = new HashMap<>(16);
        //警衔统计list
        List<List<RankComparisonVo>> rankList = new ArrayList<>();
        //增加11个不同警衔的list
        for (int i=0; i<11; i++){
            rankList.add(new ArrayList<>());
        }
        allRankComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getPoliceRank())) {
                switch (v.getPoliceRank()) {
                    case "二级警员":
                        rankList.get(10).add(v);
                        break;
                    case "一级警员":
                        rankList.get(9).add(v);
                        break;
                    case "三级警司":
                        rankList.get(8).add(v);
                        break;
                    case "二级警司":
                        rankList.get(7).add(v);
                        break;
                    case "一级警司":
                        rankList.get(6).add(v);
                        break;
                    case "三级警督":
                        rankList.get(5).add(v);
                        break;
                    case "二级警督":
                        rankList.get(4).add(v);
                        break;
                    case "一级警督":
                        rankList.get(3).add(v);
                        break;
                    case "三级警监":
                        rankList.get(2).add(v);
                        break;
                    case "二级警监":
                        rankList.get(1).add(v);
                        break;
                    case "一级警监":
                        rankList.get(0).add(v);
                        break;
                    default:break;
                }
            }
        });
        rankMap.put("一级警监", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(0).size());
//                put("detail", rankList.get(0));
            }
        });
        rankMap.put("二级警监", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(1).size());
//                put("detail", rankList.get(1));
            }
        });
        rankMap.put("三级警监", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(2).size());
//                put("detail", rankList.get(2));
            }
        });
        rankMap.put("一级警督", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(3).size());
//                put("detail", rankList.get(3));
            }
        });
        rankMap.put("二级警督", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(4).size());
//                put("detail", rankList.get(4));
            }
        });
        rankMap.put("三级警督", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(5).size());
//                put("detail", rankList.get(5));
            }
        });
        rankMap.put("一级警司", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(6).size());
//                put("detail", rankList.get(6));
            }
        });
        rankMap.put("二级警司", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(7).size());
//                put("detail", rankList.get(7));
            }
        });
        rankMap.put("三级警司", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(8).size());
//                put("detail", rankList.get(8));
            }
        });
        rankMap.put("一级警员", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(9).size());
//                put("detail", rankList.get(9));
            }
        });
        rankMap.put("二级警员", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(10).size());
//                put("detail", rankList.get(10));
            }
        });
        //移除count为0的警衔
        rankMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        List<RankComparisonVo> tempList = allRankComparisonVoList.stream().filter(v -> personAndType.getIdNo().equals(v.getIdNo())).collect(Collectors.toList());
        String myRank = (rankMap.size()>0&&tempList.size()>0)?tempList.get(0).getPoliceRank():"无";
        resultMap.put("我的警衔", myRank);
        resultMap.put("chartData", rankMap);
        int i = 0;
        final int[] allCount = {0};
        if (rankList.size() > 0){
            rankList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (!"无".equals(myRank)) {
            for(Map.Entry<String, Map<String, Object>> a:rankMap.entrySet()){
                if (myRank.equals(a.getKey())){
                    i = i + ((int)a.getValue().get("count") / 2);
                    break;
                }
                i = i + (int)a.getValue().get("count");
            }
            double percent = (double) i / (double) allCount[0];
            if (rankMap.size() > 1){
                if (percent <= 0.3d) {
                    resultMap.put("level", HIGH);
                } else if (percent >= 0.7d){
                    resultMap.put("level", LOW);
                }
            }
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**
     * 民警精准画像表彰奖励对比
     */
    public ResultModel getHonorComparison(PersonAndType personAndType) {
        //处理年份
        personAndType.setYear("%" + personAndType.getYear() + "%");
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号,如果不勾选维度返回空
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //根据身份证号查询表彰奖励信息
        List<HonorComparisonVo> honorComparisonVoList = new ArrayList<>();
        if (idNoList.size() > 1000) {
            int listSize = idNoList.size() / 1000;
            for (int i = 0; i < listSize; i++) {
                honorComparisonVoList.addAll(personComparisonMapper.findHonorByIdNo(idNoList.subList(1000 * i, 1000 * (i + 1)), personAndType));
            }
            honorComparisonVoList.addAll(personComparisonMapper.findHonorByIdNo(idNoList.subList(1000 * listSize, idNoList.size()), personAndType));
        } else if (idNoList.size() > 0){
            honorComparisonVoList.addAll(personComparisonMapper.findHonorByIdNo(idNoList, personAndType));
        }
        //表彰奖励信息map
        Map<String, Map<String, Object>> honorMap = new LinkedHashMap<>(20);
        //总map（包括表彰奖励信息统计及本人表彰奖励次数）
        Map<String, Object> resultMap = new HashMap<>(10);
        //表彰奖励统计list
        List<List<HonorComparisonVo>> honorList = new ArrayList<>();
        //增加18个不同表彰奖励的list
        for (int i=0; i<18; i++){
            honorList.add(new ArrayList<>());
        }
        honorComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getAwardName())) {
                if ("革命烈士".equals(v.getAwardName())) {
                    honorList.get(0).add(v);
                } else if ("全国公安系统一级英雄模范".equals(v.getAwardName())){
                    honorList.get(1).add(v);
                } else if ("全国公安系统二级英雄模范".equals(v.getAwardName())){
                    honorList.get(2).add(v);
                } else if ("全国劳模".equals(v.getAwardName())){
                    honorList.get(3).add(v);
                } else if ("全国特级优秀人民警察".equals(v.getAwardName())){
                    honorList.get(4).add(v);
                } else if ("全国优秀人民警察".equals(v.getAwardName())){
                    honorList.get(5).add(v);
                } else if ("全国三八红旗手".equals(v.getAwardName())){
                    honorList.get(6).add(v);
                } else if (v.getAwardName().contains("一等功")){
                    honorList.get(7).add(v);
                } else if ("省劳模".equals(v.getAwardName())){
                    honorList.get(8).add(v);
                } else if ("省三八红旗手".equals(v.getAwardName())){
                    honorList.get(9).add(v);
                } else if ("省五四青年奖章".equals(v.getAwardName())){
                    honorList.get(10).add(v);
                } else if (v.getAwardName().contains("二等功")){
                    honorList.get(11).add(v);
                } else if ("市劳动模范".equals(v.getAwardName())){
                    honorList.get(12).add(v);
                } else if ("市三八红旗手".equals(v.getAwardName())){
                    honorList.get(13).add(v);
                } else if (v.getAwardName().contains("三等功")){
                    honorList.get(14).add(v);
                } else if (v.getAwardName().contains("通报表扬")){
                    honorList.get(15).add(v);
                } else if (v.getAwardName().contains("嘉奖")){
                    honorList.get(16).add(v);
                } else {
                    honorList.get(17).add(v);
                }
            }
        });
        honorMap.put("革命烈士", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(0).size());
//                put("detail", honorList.get(0));
            }
        });
        honorMap.put("全国公安系统一级英雄模范", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(1).size());
//                put("detail", honorList.get(1));
            }
        });
        honorMap.put("全国公安系统二级英雄模范", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(2).size());
//                put("detail", honorList.get(2));
            }
        });
        honorMap.put("全国劳模", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(3).size());
//                put("detail", honorList.get(3));
            }
        });
        honorMap.put("全国特级优秀人民警察", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(4).size());
//                put("detail", honorList.get(4));
            }
        });
        honorMap.put("全国优秀人民警察", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(5).size());
//                put("detail", honorList.get(5));
            }
        });
        honorMap.put("全国三八红旗手", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(6).size());
//                put("detail", honorList.get(6));
            }
        });
        honorMap.put("一等功", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(7).size());
//                put("detail", honorList.get(7));
            }
        });
        honorMap.put("省劳模", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(8).size());
//                put("detail", honorList.get(8));
            }
        });
        honorMap.put("省三八红旗手", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(9).size());
//                put("detail", honorList.get(9));
            }
        });
        honorMap.put("省五四青年奖章", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(10).size());
//                put("detail", honorList.get(10));
            }
        });
        honorMap.put("二等功", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(11).size());
//                put("detail", honorList.get(11));
            }
        });
        honorMap.put("市劳动模范", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(12).size());
//                put("detail", honorList.get(12));
            }
        });
        honorMap.put("市三八红旗手", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(13).size());
//                put("detail", honorList.get(13));
            }
        });
        honorMap.put("三等功", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(14).size());
//                put("detail", honorList.get(14));
            }
        });
        honorMap.put("通报表扬", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(15).size());
//                put("detail", honorList.get(15));
            }
        });
        honorMap.put("嘉奖", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(16).size());
//                put("detail", honorList.get(16));
            }
        });
        honorMap.put("其他奖励", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(17).size());
//                put("detail", honorList.get(17));
            }
        });
        //移除count为0的奖项
        honorMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        //处理当前用户的表彰奖励信息
        List<HonorComparisonVo> myHonorDetailList = personComparisonMapper.findHonorByIdNo(Collections.singletonList(personAndType.getIdNo()), personAndType);
        Map<String, Long> myHonorMap = myHonorDetailList.stream().collect(Collectors.groupingBy(HonorComparisonVo::getAwardName, Collectors.counting()));
        List<String> myHonorList = new ArrayList<>();
        for(Map.Entry<String, Long> a:myHonorMap.entrySet()){
            myHonorList.add(a.getValue() + "次" + a.getKey());
        }
        resultMap.put("我的表彰", honorMap.size()>0?(myHonorList.size() > 0 ? getPersonHonorSort(myHonorList) : "无"):"无");
        resultMap.put("chartData", honorMap);
        double avgCount = (double) honorComparisonVoList.size()/(double) idNoList.size();
        if ((double) myHonorDetailList.size() >= avgCount){
            resultMap.put("level", HIGH);
        }else {
            resultMap.put("level", LOW);
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**
     * 民警精准画像民意满意度对比
     * 数据未接入
     */
    public ResultModel getSatisfiedComparison(PersonAndType personAndType) {
        //处理年份
//        personAndType.setYear("%" + personAndType.getYear() + "%");
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人的身份证号,如果不勾选维度返回空
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //根据身份证号查询民意满意度信息
        List<SatisfiedComparisonVo> satisfiedComparisonVoList = new ArrayList<>();
        String year;
        if("全部".equals(personAndType.getYear())) {
            year = DateUtil.getCurrentYear();
        }else {
            year = personAndType.getYear();
        }
        List<PersonAndOpinionVo> personAndOpinionVoList = satisfiedDataSevice.getMydByIdNos(idNoList,year);

        if(personAndOpinionVoList.size() >0) {
            personAndOpinionVoList.forEach(p->{
                SatisfiedComparisonVo satisfiedComparisonVo = new SatisfiedComparisonVo();
                double myd = (p.getAjmyl() + p.getJcjmyl())/2;
                String mydStr =decimalFormat.format(myd);
                satisfiedComparisonVo.setAvgSatisfiedScore(mydStr);
                satisfiedComparisonVoList.add(satisfiedComparisonVo);

            });
        }
        //民意满意度信息map
        Map<String, Map<String, Object>> satisfiedMap = new LinkedHashMap<>(10);
        //总map（包括民意满意度信息统计及本人民意满意度）
        Map<String, Object> resultMap = new HashMap<>(10);
        //民意满意度统计list
        List<List<SatisfiedComparisonVo>> satisfiedList = new ArrayList<>();
        //增加6个不同民意满意度的list
        for (int i=0; i<6; i++){
            satisfiedList.add(new ArrayList<>());
        }
        if (satisfiedComparisonVoList.size() > 0){
            satisfiedComparisonVoList.forEach(v -> {
                if (v != null){
                    if (StringUtils.isNotEmpty(v.getAvgSatisfiedScore())) {
                        if (Double.valueOf(v.getAvgSatisfiedScore()) >= 98) {
                            satisfiedList.get(0).add(v);
                        } else if (Double.valueOf(v.getAvgSatisfiedScore()) >= 96 && Double.valueOf(v.getAvgSatisfiedScore()) < 98) {
                            satisfiedList.get(1).add(v);
                        } else if (Double.valueOf(v.getAvgSatisfiedScore()) >= 94 && Double.valueOf(v.getAvgSatisfiedScore()) < 96) {
                            satisfiedList.get(2).add(v);
                        } else if (Double.valueOf(v.getAvgSatisfiedScore()) >= 92 && Double.valueOf(v.getAvgSatisfiedScore()) < 94) {
                            satisfiedList.get(3).add(v);
                        } else if (Double.valueOf(v.getAvgSatisfiedScore()) >= 90 && Double.valueOf(v.getAvgSatisfiedScore()) < 92) {
                            satisfiedList.get(4).add(v);
                        } else if (Double.valueOf(v.getAvgSatisfiedScore()) < 90) {
                            satisfiedList.get(5).add(v);
                        }
                    }
                }
            });
        }
        satisfiedMap.put("98%以上", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(0).size());
//                put("detail", satisfiedList.get(0));
            }
        });
        satisfiedMap.put("96%-98%", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(1).size());
//                put("detail", satisfiedList.get(1));
            }
        });
        satisfiedMap.put("94%-96%", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(2).size());
//                put("detail", satisfiedList.get(2));
            }
        });
        satisfiedMap.put("92%-94%", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(3).size());
//                put("detail", satisfiedList.get(3));
            }
        });
        satisfiedMap.put("90%-92%", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(4).size());
//                put("detail", satisfiedList.get(4));
            }
        });
        satisfiedMap.put("90%以下", new HashMap<String, Object>(10){
            {
                put("count", satisfiedList.get(5).size());
//                put("detail", satisfiedList.get(5));
            }
        });
        List<SatisfiedComparisonVo> mySatisfiedInfo = new ArrayList<>();
        List<PersonAndOpinionVo> personAndOpinionVoList1 = satisfiedDataSevice.getMydByIdNos(Collections.singletonList(personAndType.getIdNo()),year);
        if(personAndOpinionVoList1.size() >0) {
            personAndOpinionVoList1.forEach(e->{
                SatisfiedComparisonVo satisfiedComparisonVo = new SatisfiedComparisonVo();
                double myd = (e.getAjmyl() + e.getJcjmyl())/2;
                String mydStr =decimalFormat.format(myd);
                satisfiedComparisonVo.setAvgSatisfiedScore(mydStr);
                mySatisfiedInfo.add(satisfiedComparisonVo);
            });
        }
//        List<SatisfiedComparisonVo> mySatisfiedInfo = personComparisonMapper.findSatisfiedByIdNo(Collections.singletonList(personAndType.getIdNo()), personAndType);
        String mySatisfied = mySatisfiedInfo.size() > 0 ?
                String.format("%.2f", Double.parseDouble(mySatisfiedInfo.get(0).getAvgSatisfiedScore())) + "%" : "无";
        resultMap.put("我的民意满意度", mySatisfied);
        resultMap.put("chartData", satisfiedMap);
        if (!"无".equals(mySatisfied)) {
            //移除count为0的民意满意度分类
//            satisfiedMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
            double myCount = Double.parseDouble(mySatisfiedInfo.get(0).getAvgSatisfiedScore());
            String mySatisfiedType = null;
            if (myCount >= 98) {
                mySatisfiedType = "98%以上";
            } else if (myCount >= 96 && myCount < 98) {
                mySatisfiedType = "96%-98%";
            } else if (myCount >= 94 && myCount < 96) {
                mySatisfiedType = "94%-96%";
            } else if (myCount >= 92 && myCount < 94) {
                mySatisfiedType = "92%-94%";
            } else if (myCount >= 90 && myCount < 92) {
                mySatisfiedType = "90%-92%";
            } else if (myCount < 90) {
                mySatisfiedType = "90%以下";
            }
            int i = 0;
            final int[] allCount = {0};
            if (satisfiedList.size() > 0) {
                satisfiedList.forEach(v -> allCount[0] = allCount[0] + v.size());
            }
            for (Map.Entry<String, Map<String, Object>> a : satisfiedMap.entrySet()) {
                if (mySatisfiedType != null && mySatisfiedType.equals(a.getKey())) {
                    i = i + ((int) a.getValue().get("count") / 2);
                    break;
                }
                i = i + (int) a.getValue().get("count");
            }
            double percent = (double) i / (double) allCount[0];
            if (satisfiedMap.size() > 1) {
                if (percent <= 0.3d) {
                    resultMap.put("level", HIGH);
                } else if (percent >= 0.7d) {
                    resultMap.put("level", LOW);
                }
            }
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**
     * 民警精准画像教育培训对比
     */
    public ResultModel getTrainComparison(PersonAndType personAndType) {
        //返回给前端的数据
        ResultModel resultModel = new ResultModel();
        //根据勾选维度查询符合条件的所有人
        List<TPersonDo> tPersonDoList = getComparisonPeople(personAndType);
        //取出前端展示的字段
        List<TrainComparisonVo> trainComparisonVoList = new ArrayList<>();
        if (tPersonDoList.size() > 0){
            tPersonDoList.forEach(v -> {
                TrainComparisonVo trainComparisonVo = new TrainComparisonVo();
                trainComparisonVo.setName(v.getPersonName());
                trainComparisonVo.setDeptName(v.getDeptName());
                trainComparisonVo.setTrainingCount(v.getTrainingCount());
                trainComparisonVoList.add(trainComparisonVo);
            });
        }
        //教育培训信息map
        Map<String, Map<String, Object>> trainMap = new LinkedHashMap<>(10);
        //总map（包括教育培训信息统计及本人教育培训次数）
        Map<String, Object> resultMap = new HashMap<>(10);
        //教育培训统计list
        List<List<TrainComparisonVo>> trainList = new ArrayList<>();
        //增加3个教育培训次数分类的list
        for (int i=0; i<3; i++){
            trainList.add(new ArrayList<>());
        }
        trainComparisonVoList.forEach(v -> {
            if (v.getTrainingCount() < 1) {
                trainList.get(2).add(v);
            } else if (v.getTrainingCount() > 5){
                trainList.get(0).add(v);
            } else {
                trainList.get(1).add(v);
            }
        });
        trainMap.put("5次以上", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(0).size());
//                put("detail", trainList.get(0));
            }
        });
        trainMap.put("1-5次", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(1).size());
//                put("detail", trainList.get(1));
            }
        });
        trainMap.put("未参加培训", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(2).size());
//                put("detail", trainList.get(2));
            }
        });
        int myCount = tPersonDoList.stream()
                .filter(v -> v.getIdNo().equals(personAndType.getIdNo()))
                .collect(toList()).get(0).getTrainingCount();
        resultMap.put("我的教育培训", myCount + "次");
        resultMap.put("chartData", trainMap);
        //移除count为0的教育培训分类
        trainMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        String myTrainType = null;
        if (myCount > 5) {
            myTrainType = "5次以上";
        } else if (myCount >= 1) {
            myTrainType = "1-5次";
        } else {
            myTrainType = "未参加培训";
        }
        int i = 0;
        final int[] allCount = {0};
        if (trainList.size() > 0){
            trainList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        for (Map.Entry<String, Map<String, Object>> a : trainMap.entrySet()) {
            if (myTrainType.equals(a.getKey())) {
                i = i + ((int) a.getValue().get("count") / 2);
                break;
            }
            i = i + (int) a.getValue().get("count");
        }
        double percent = (double) i / (double) allCount[0];
        if (trainMap.size() > 1) {
            if (percent <= 0.3d) {
                resultMap.put("level", HIGH);
            } else if (percent >= 0.7d) {
                resultMap.put("level", LOW);
            }
        }
        resultModel.setResult(resultMap);
        return resultModel;
    }

    /**查询出符合多维度条件的所有人信息*/
    private List<TPersonDo> getComparisonPeople(PersonAndType personAndType) {
        //处理年份
        personAndType.setYear("%" + personAndType.getYear() + "%");
        //如果不勾选维度返回空
        if (!(personAndType.getIsAge() || personAndType.getIsInParty() || personAndType.getIsInPolice() ||
                personAndType.getIsSchool() || personAndType.getIsSubject() || personAndType.getIsToWork())){
            return new ArrayList<>();
        }
        //查询民警个人信息
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0");
        tPersonDo.setProfessionalName(tEducationDao.getFirst(personAndType.getIdNo(), "0").getPeofessionalName());
        //根据勾选维度查询符合条件的所有人
        return personComparisonMapper.findPersonByType(tPersonDo, personAndType);
    }

    /**查询出符合多维度条件的所有人身份证号码*/
    private List<String> getComparisonPeopleIdNo(PersonAndType personAndType) {
        //查询民警个人信息
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0");
        tPersonDo.setProfessionalName(tEducationDao.getFirst(personAndType.getIdNo(), "0").getPeofessionalName());
        if (personAndType.getIsAge() || personAndType.getIsInParty() || personAndType.getIsInPolice() ||
                personAndType.getIsSchool() || personAndType.getIsSubject() || personAndType.getIsToWork()){
            return personComparisonMapper.findIdNoByType(tPersonDo, personAndType);
        }
        return new ArrayList<>();
    }

    /**职级分组统计*/
    private void getJobLevelGroup(List<JobLevelComparisonVo> postComparisonVoList, List<List<JobLevelComparisonVo>> postList, Map<String, Map<String, Object>> postMap) {
        postComparisonVoList.stream()
                .collect(Collectors.groupingBy(JobLevelComparisonVo::getJobLevelName, Collectors.toList()))
                .forEach((x, y)-> postList.add(y));
        if (postList.size() > 0){
            postList.sort(Comparator.comparing(o -> o.get(0).getJobLevelCode()));
            postList.forEach(v -> postMap.put(v.get(0).getJobLevelName(), new HashMap<String, Object>(10){
                {
                    put("count", v.size());
//                put("detail", v);
                }
            }));
        }
    }

    /**
     * 个人荣誉排序
     *
     * @param list
     * @return
     */
    private List<String> getPersonHonorSort(List<String> list) {
        List<String> resultList = new ArrayList<>();
        //分组比较
        List<String> listOne = new ArrayList<>(), listTwo = new ArrayList<>(), listThree = new ArrayList<>();
        List<List<String>> sortOne = new ArrayList<>(), sortTwo = new ArrayList<>(), sortThree = new ArrayList<>();
        //第一组排序list
        for (int i=0; i<7; i++){
            sortOne.add(new ArrayList<>());
        }
        //第二组排序list
        for (int i=0; i<4; i++){
            sortTwo.add(new ArrayList<>());
        }
        //第三组排序list
        for (int i=0; i<8; i++){
            sortThree.add(new ArrayList<>());
        }
        //一等功、二等功、三等功
        List<String> firstLevel = new ArrayList<>(), secondLevel = new ArrayList<>(), thirdLevel = new ArrayList<>();
        //其他奖励
        List<String> otherList = new ArrayList<>();
        //比较结果
        List<String> resultOne = new ArrayList<>(), resultTwo = new ArrayList<>(), resultThree = new ArrayList<>();
        list.forEach(v -> {
            String str = v.substring(0, v.indexOf("次"));
            String honorName = v.substring(str.length() + 1);
            if (personRewordOne.contains(honorName)) {
                listOne.add(v);
            } else if (personRewordTwo.contains(honorName)) {
                listTwo.add(v);
            } else if (personRewordThree.contains(honorName)) {
                listThree.add(v);
            } else if (honorName.contains(FIRST_LEVEL)) {
                firstLevel.add(v);
            } else if (honorName.contains(SECOND_LEVEL)) {
                secondLevel.add(v);
            } else if (honorName.contains(THIRD_LEVEL)) {
                thirdLevel.add(v);
            } else {
                otherList.add(v);
            }
        });
        while (listOne.size() > 0) {
            for (String a : listOne) {
                String str = a.substring(0, a.indexOf("次"));
                String honorName = a.substring(str.length() + 1);
                if ("革命烈士".equals(honorName)) {
                    sortOne.get(0).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国公安系统一级英雄模范".equals(honorName)) {
                    sortOne.get(1).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国公安系统二级英雄模范".equals(honorName)) {
                    sortOne.get(2).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国劳模".equals(honorName)) {
                    sortOne.get(3).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国特级优秀人民警察".equals(honorName)) {
                    sortOne.get(4).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国优秀人民警察".equals(honorName)) {
                    sortOne.get(5).add(a);
                    listOne.remove(a);
                    break;
                } else if ("全国三八红旗手".equals(honorName)) {
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
            for (String a : listTwo) {
                String str = a.substring(0, a.indexOf("次"));
                String honorName = a.substring(str.length() + 1);
                if ("省劳模".equals(honorName)) {
                    sortTwo.get(0).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("省三八红旗手".equals(honorName)) {
                    sortTwo.get(1).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("省五四青年奖章".equals(honorName)) {
                    sortTwo.get(2).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("江苏好青年".equals(honorName)) {
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
            for (String a : listThree) {
                String str = a.substring(0, a.indexOf("次"));
                String honorName = a.substring(str.length() + 1);
                if ("市劳动模范".equals(honorName)) {
                    sortThree.get(0).add(a);
                    listThree.remove(a);
                    break;
                } else if ("省优秀共青团干部".equals(honorName)) {
                    sortThree.get(1).add(a);
                    listThree.remove(a);
                    break;
                } else if ("省优秀共青团员".equals(honorName)) {
                    sortThree.get(2).add(a);
                    listThree.remove(a);
                    break;
                } else if ("感动南京年度人物".equals(honorName)) {
                    sortThree.get(3).add(a);
                    listThree.remove(a);
                    break;
                } else if ("市三八红旗手".equals(honorName)) {
                    sortThree.get(4).add(a);
                    listThree.remove(a);
                    break;
                } else if ("市十大杰出青年".equals(honorName)) {
                    sortThree.get(5).add(a);
                    listThree.remove(a);
                    break;
                } else if ("市五四青年奖章".equals(honorName)) {
                    sortThree.get(6).add(a);
                    listThree.remove(a);
                    break;
                } else if ("市最美老干部".equals(honorName)) {
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




}
