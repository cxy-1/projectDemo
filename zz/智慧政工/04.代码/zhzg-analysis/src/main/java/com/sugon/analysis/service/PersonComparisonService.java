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

    /**ְ���б�*/
    private static  List<String> dutyList = Stream.of(
            "�����쵼ְ",
            "�����쵼ְ",
            "����ְ", "������",
            "������", "�Ͼ���Ѳ��Ա",
            "����ְ",
            "������", "����ְ",
            "������", "����ְ",
            "���Ƽ�", "����ְ", "����..."
    ).collect(toList());

    /**�ۺϹ���ְ���б�*/
    private static  List<String> jobLevelOneList = Stream.of(
            "һ��Ѳ��Ա", "����Ѳ��Ա",
            "һ������Ա", "��������Ա", "��������Ա", "�ļ�����Ա",
            "һ�����ο�Ա", "�������ο�Ա", "�������ο�Ա", "�ļ����ο�Ա",
            "һ����Ա", "������Ա"
    ).collect(toList());

    /**�ۺϹ���ְ���б�*/
    private static  List<String> jobLevelTwoList = Stream.of(
            "һ������רԱ", "��������רԱ",
            "һ���߼�����", "�����߼�����", "�����߼�����", "�ļ��߼�����",
            "һ������", "��������", "��������", "�ļ�����",
            "һ����Ա", "������Ա"
    ).collect(toList());

    /**�ۺϹ���ְ���б�*/
    private static  List<String> jobLevelThreeList = Stream.of(
            "������һ���ܼ�", "�����������ܼ�",
            "������һ������", "��������������", "��������������", "�������ļ�����",
            "������һ������", "��������������", "��������������", "�������ļ�����",
            "������Ա"
    ).collect(toList());

    /**ְ��-�����б�*/
    private static  List<String> jobLevelSortOneList = Stream.of(
            "һ��Ѳ��Ա", "����Ѳ��Ա",
            "һ������רԱ", "��������רԱ",
            "������һ���ܼ�", "�����������ܼ�"
    ).collect(toList());

    /**ְ��-�����б�*/
    private static  List<String> jobLevelSortTwoList = Stream.of(
            "һ������Ա", "��������Ա", "��������Ա", "�ļ�����Ա",
            "һ���߼�����", "�����߼�����", "�����߼�����", "�ļ��߼�����",
            "������һ������", "��������������", "��������������", "�������ļ�����"
    ).collect(toList());

    /**ְ��-�Ƽ��б�*/
    private static  List<String> jobLevelSortThreeList = Stream.of(
            "һ�����ο�Ա", "�������ο�Ա", "�������ο�Ա", "�ļ����ο�Ա",
            "һ������", "��������", "��������", "�ļ�����",
            "������һ������", "��������������", "��������������", "�������ļ�����"
    ).collect(toList());

    /**ְ��-��Ա�б�*/
    private static  List<String> jobLevelSortFourList = Stream.of(
            "һ����Ա", "������Ա",
            "һ����Ա", "������Ա",
            "������Ա"
    ).collect(toList());

    /**�û��ڶԱ���������λ��*/
    private static final String HIGH = "high";

    private static final String LOW = "low";


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

    /**
     * �񾯾�׼����ְ��Ա�
     */
    public ResultModel getPersonCount(PersonAndType personAndType) {
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        resultModel.setResult(idNoList.size());
        return resultModel;
    }

    /**
     * �񾯾�׼����ְ��Ա�
     */
    public ResultModel getPostComparison(PersonAndType personAndType) {
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //�������֤�Ų�ѯְ����Ϣ
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
        //����ְ��
        String myPoliceJobGrade;
        List<PostComparisonVo> myPostVoList = postComparisonVoList.stream().filter(v -> v.getIdNo().equals(personAndType.getIdNo())).collect(toList());
        if (myPostVoList.size() > 0) {
            myPoliceJobGrade = myPostVoList.get(0).getPostName();
        } else {
            myPoliceJobGrade = "";
        }
        //ְ����Ϣmap
        Map<String, Map<String, Object>> postMap = new LinkedHashMap<>(10);
        //��map������ְ����Ϣͳ�Ƽ�����ְ��
        Map<String, Object> resultMap = new HashMap<>(10);
        //ְ��ͳ��list
        List<List<PostComparisonVo>> postList = new ArrayList<>();
        //����8����ְͬ���list
        for (int i = 0; i < 8; i++) {
            postList.add(new ArrayList<>());
        }
        postComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getPostName())) {
                switch (v.getPostName()) {
                    case "���Ƽ�":
                    case "����ְ":
                    case "����...":
                        postList.get(7).add(v);
                        break;
                    case "������":
                    case "����ְ":
                        postList.get(6).add(v);
                        break;
                    case "������":
                    case "����ְ":
                        postList.get(5).add(v);
                        break;
                    case "����ְ":
                        postList.get(4).add(v);
                        break;
                    case "������":
                    case "�Ͼ���Ѳ��Ա":
                        postList.get(3).add(v);
                        break;
                    case "����ְ":
                    case "������":
                        postList.get(2).add(v);
                        break;
                    case "�����쵼ְ":
                        postList.get(1).add(v);
                        break;
                    case "�����쵼ְ":
                        postList.get(0).add(v);
                        break;
                    default:
                        break;
                }
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(0).size());
//                    put("detail", postList.get(0));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(1).size());
//                    put("detail", postList.get(1));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(2).size());
//                    put("detail", postList.get(2));
            }
        });
        postMap.put("����ְ���쵼", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(3).size());
//                    put("detail", postList.get(3));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(4).size());
//                    put("detail", postList.get(4));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(5).size());
//                    put("detail", postList.get(5));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(6).size());
//                    put("detail", postList.get(6));
            }
        });
        postMap.put("����ְ", new HashMap<String, Object>(10) {
            {
                put("count", postList.get(7).size());
//                    put("detail", postList.get(7));
            }
        });
        //�Ƴ�countΪ0��ְ��
        postMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        String myPostName = StringUtils.isNotEmpty(getMyPostType(myPoliceJobGrade)) ? myPoliceJobGrade : "��";
        resultMap.put("�ҵ�ְ��", myPostName);
        resultMap.put("chartData", postMap);
        int i = 0;
        final int[] allCount = {0};
        if (postList.size() > 0) {
            postList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (StringUtils.isNotEmpty(myPoliceJobGrade) && !"��".equals(myPostName)) {
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

    /**����ҵ�ְ��*/
    private String getMyPostType(String myPoliceJobGrade){
        String myPostType = null;
        switch (myPoliceJobGrade) {
            case "���Ƽ�":
            case "����ְ":
            case "����...":
                myPostType = "����ְ";
                break;
            case "������":
            case "����ְ":
                myPostType = "����ְ";
                break;
            case "������":
            case "����ְ":
                myPostType = "����ְ";
                break;
            case "����ְ":
                myPostType = "����ְ";
                break;
            case "������":
            case "�Ͼ���Ѳ��Ա":
                myPostType = "����ְ���쵼";
                break;
            case "����ְ":
            case "������":
                myPostType = "����ְ";
                break;
            case "�����쵼ְ":
                myPostType = "����ְ";
                break;
            case "�����쵼ְ":
                myPostType = "����ְ";
                break;
            default:break;
        }
        return myPostType;
    }

    /**
     * �񾯾�׼����ְ���Ա�
     */
    public ResultModel getJobLevelComparison(PersonAndType personAndType) {
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //�������֤���ҵ�personid
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
        //����personid��ѯְ����Ϣ
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
        //����ְ��
        String myPersonId = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0").getDataSourceId();
        String myJobLevel;
        List<JobLevelComparisonVo> myjobLevelVoList = jobLevelComparisonVoList.stream().filter(v -> myPersonId.equals(v.getPersonId())).collect(toList());
        if (myjobLevelVoList.size() > 0){
            myJobLevel = myjobLevelVoList.get(0).getJobLevelCode();
        }else {
            myJobLevel = null;
        }
        //ְ����Ϣmap
        Map<String, Map<String, Object>> jobLevelMap = new LinkedHashMap<>(10);
        //��map������ְ����Ϣͳ�Ƽ�����ְ����
        Map<String, Object> resultMap = new HashMap<>(10);
        //ְ��ͳ��list
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
                    put("name", "����");
                    put("value", finalSortOne);
                }});
            }
            if (sortTwo > 0){
                int finalSortTwo = sortTwo;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "����");
                    put("value", finalSortTwo);
                }});
            }
            if (sortThree > 0){
                int finalSortThree = sortThree;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "�Ƽ�");
                    put("value", finalSortThree);
                }});
            }
            if (sortFour > 0){
                int finalSortFour = sortFour;
                sortList.add(new HashMap<String, Object>(){{
                    put("name", "��Ա");
                    put("value", finalSortFour);
                }});
            }
            resultMap.put("sortData", sortList);
        }
        String myJobLevelName = jobLevelMap.size()>0?(StringUtils.isNotEmpty(myJobLevel) ? sgQueryCategoryDao.findByCvTypeAndCvFlag("AK", myJobLevel).getCvName() : "��"):"��";
        resultMap.put("�ҵ�ְ��", myJobLevelName);
        resultMap.put("chartData", jobLevelMap);
        int i = 0;
        final int[] allCount = {0};
        if (jobLevelList.size() > 0){
            jobLevelList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (!("".equals(myJobLevelName) || "��".equals(myJobLevelName))) {
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
     * �񾯾�׼�����ν����Ա�
     */
    public ResultModel getRankComparison(PersonAndType personAndType) {
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //�������֤�Ų�ѯ������Ϣ
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
        //������Ϣmap
        Map<String, Map<String, Object>> rankMap = new LinkedHashMap<>(16);
        //��map������������Ϣͳ�Ƽ����˾��Σ�
        Map<String, Object> resultMap = new HashMap<>(16);
        //����ͳ��list
        List<List<RankComparisonVo>> rankList = new ArrayList<>();
        //����11����ͬ���ε�list
        for (int i=0; i<11; i++){
            rankList.add(new ArrayList<>());
        }
        allRankComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getPoliceRank())) {
                switch (v.getPoliceRank()) {
                    case "������Ա":
                        rankList.get(10).add(v);
                        break;
                    case "һ����Ա":
                        rankList.get(9).add(v);
                        break;
                    case "������˾":
                        rankList.get(8).add(v);
                        break;
                    case "������˾":
                        rankList.get(7).add(v);
                        break;
                    case "һ����˾":
                        rankList.get(6).add(v);
                        break;
                    case "��������":
                        rankList.get(5).add(v);
                        break;
                    case "��������":
                        rankList.get(4).add(v);
                        break;
                    case "һ������":
                        rankList.get(3).add(v);
                        break;
                    case "��������":
                        rankList.get(2).add(v);
                        break;
                    case "��������":
                        rankList.get(1).add(v);
                        break;
                    case "һ������":
                        rankList.get(0).add(v);
                        break;
                    default:break;
                }
            }
        });
        rankMap.put("һ������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(0).size());
//                put("detail", rankList.get(0));
            }
        });
        rankMap.put("��������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(1).size());
//                put("detail", rankList.get(1));
            }
        });
        rankMap.put("��������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(2).size());
//                put("detail", rankList.get(2));
            }
        });
        rankMap.put("һ������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(3).size());
//                put("detail", rankList.get(3));
            }
        });
        rankMap.put("��������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(4).size());
//                put("detail", rankList.get(4));
            }
        });
        rankMap.put("��������", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(5).size());
//                put("detail", rankList.get(5));
            }
        });
        rankMap.put("һ����˾", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(6).size());
//                put("detail", rankList.get(6));
            }
        });
        rankMap.put("������˾", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(7).size());
//                put("detail", rankList.get(7));
            }
        });
        rankMap.put("������˾", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(8).size());
//                put("detail", rankList.get(8));
            }
        });
        rankMap.put("һ����Ա", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(9).size());
//                put("detail", rankList.get(9));
            }
        });
        rankMap.put("������Ա", new HashMap<String, Object>(16) {
            {
                put("count", rankList.get(10).size());
//                put("detail", rankList.get(10));
            }
        });
        //�Ƴ�countΪ0�ľ���
        rankMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        List<RankComparisonVo> tempList = allRankComparisonVoList.stream().filter(v -> personAndType.getIdNo().equals(v.getIdNo())).collect(Collectors.toList());
        String myRank = (rankMap.size()>0&&tempList.size()>0)?tempList.get(0).getPoliceRank():"��";
        resultMap.put("�ҵľ���", myRank);
        resultMap.put("chartData", rankMap);
        int i = 0;
        final int[] allCount = {0};
        if (rankList.size() > 0){
            rankList.forEach(v -> allCount[0] = allCount[0] + v.size());
        }
        if (!"��".equals(myRank)) {
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
     * �񾯾�׼������ý����Ա�
     */
    public ResultModel getHonorComparison(PersonAndType personAndType) {
        //�������
        personAndType.setYear("%" + personAndType.getYear() + "%");
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��,�������ѡά�ȷ��ؿ�
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //�������֤�Ų�ѯ���ý�����Ϣ
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
        //���ý�����Ϣmap
        Map<String, Map<String, Object>> honorMap = new LinkedHashMap<>(20);
        //��map���������ý�����Ϣͳ�Ƽ����˱��ý���������
        Map<String, Object> resultMap = new HashMap<>(10);
        //���ý���ͳ��list
        List<List<HonorComparisonVo>> honorList = new ArrayList<>();
        //����18����ͬ���ý�����list
        for (int i=0; i<18; i++){
            honorList.add(new ArrayList<>());
        }
        honorComparisonVoList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getAwardName())) {
                if ("������ʿ".equals(v.getAwardName())) {
                    honorList.get(0).add(v);
                } else if ("ȫ������ϵͳһ��Ӣ��ģ��".equals(v.getAwardName())){
                    honorList.get(1).add(v);
                } else if ("ȫ������ϵͳ����Ӣ��ģ��".equals(v.getAwardName())){
                    honorList.get(2).add(v);
                } else if ("ȫ����ģ".equals(v.getAwardName())){
                    honorList.get(3).add(v);
                } else if ("ȫ���ؼ��������񾯲�".equals(v.getAwardName())){
                    honorList.get(4).add(v);
                } else if ("ȫ���������񾯲�".equals(v.getAwardName())){
                    honorList.get(5).add(v);
                } else if ("ȫ�����˺�����".equals(v.getAwardName())){
                    honorList.get(6).add(v);
                } else if (v.getAwardName().contains("һ�ȹ�")){
                    honorList.get(7).add(v);
                } else if ("ʡ��ģ".equals(v.getAwardName())){
                    honorList.get(8).add(v);
                } else if ("ʡ���˺�����".equals(v.getAwardName())){
                    honorList.get(9).add(v);
                } else if ("ʡ�������꽱��".equals(v.getAwardName())){
                    honorList.get(10).add(v);
                } else if (v.getAwardName().contains("���ȹ�")){
                    honorList.get(11).add(v);
                } else if ("���Ͷ�ģ��".equals(v.getAwardName())){
                    honorList.get(12).add(v);
                } else if ("�����˺�����".equals(v.getAwardName())){
                    honorList.get(13).add(v);
                } else if (v.getAwardName().contains("���ȹ�")){
                    honorList.get(14).add(v);
                } else if (v.getAwardName().contains("ͨ������")){
                    honorList.get(15).add(v);
                } else if (v.getAwardName().contains("�ν�")){
                    honorList.get(16).add(v);
                } else {
                    honorList.get(17).add(v);
                }
            }
        });
        honorMap.put("������ʿ", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(0).size());
//                put("detail", honorList.get(0));
            }
        });
        honorMap.put("ȫ������ϵͳһ��Ӣ��ģ��", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(1).size());
//                put("detail", honorList.get(1));
            }
        });
        honorMap.put("ȫ������ϵͳ����Ӣ��ģ��", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(2).size());
//                put("detail", honorList.get(2));
            }
        });
        honorMap.put("ȫ����ģ", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(3).size());
//                put("detail", honorList.get(3));
            }
        });
        honorMap.put("ȫ���ؼ��������񾯲�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(4).size());
//                put("detail", honorList.get(4));
            }
        });
        honorMap.put("ȫ���������񾯲�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(5).size());
//                put("detail", honorList.get(5));
            }
        });
        honorMap.put("ȫ�����˺�����", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(6).size());
//                put("detail", honorList.get(6));
            }
        });
        honorMap.put("һ�ȹ�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(7).size());
//                put("detail", honorList.get(7));
            }
        });
        honorMap.put("ʡ��ģ", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(8).size());
//                put("detail", honorList.get(8));
            }
        });
        honorMap.put("ʡ���˺�����", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(9).size());
//                put("detail", honorList.get(9));
            }
        });
        honorMap.put("ʡ�������꽱��", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(10).size());
//                put("detail", honorList.get(10));
            }
        });
        honorMap.put("���ȹ�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(11).size());
//                put("detail", honorList.get(11));
            }
        });
        honorMap.put("���Ͷ�ģ��", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(12).size());
//                put("detail", honorList.get(12));
            }
        });
        honorMap.put("�����˺�����", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(13).size());
//                put("detail", honorList.get(13));
            }
        });
        honorMap.put("���ȹ�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(14).size());
//                put("detail", honorList.get(14));
            }
        });
        honorMap.put("ͨ������", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(15).size());
//                put("detail", honorList.get(15));
            }
        });
        honorMap.put("�ν�", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(16).size());
//                put("detail", honorList.get(16));
            }
        });
        honorMap.put("��������", new HashMap<String, Object>(10){
            {
                put("count", honorList.get(17).size());
//                put("detail", honorList.get(17));
            }
        });
        //�Ƴ�countΪ0�Ľ���
        honorMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        //����ǰ�û��ı��ý�����Ϣ
        List<HonorComparisonVo> myHonorDetailList = personComparisonMapper.findHonorByIdNo(Collections.singletonList(personAndType.getIdNo()), personAndType);
        Map<String, Long> myHonorMap = myHonorDetailList.stream().collect(Collectors.groupingBy(HonorComparisonVo::getAwardName, Collectors.counting()));
        List<String> myHonorList = new ArrayList<>();
        for(Map.Entry<String, Long> a:myHonorMap.entrySet()){
            myHonorList.add(a.getValue() + "��" + a.getKey());
        }
        resultMap.put("�ҵı���", honorMap.size()>0?(myHonorList.size() > 0 ? getPersonHonorSort(myHonorList) : "��"):"��");
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
     * �񾯾�׼������������ȶԱ�
     * ����δ����
     */
    public ResultModel getSatisfiedComparison(PersonAndType personAndType) {
        //�������
//        personAndType.setYear("%" + personAndType.getYear() + "%");
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ���������������˵����֤��,�������ѡά�ȷ��ؿ�
        List<String> idNoList = getComparisonPeopleIdNo(personAndType);
        //�������֤�Ų�ѯ�����������Ϣ
        List<SatisfiedComparisonVo> satisfiedComparisonVoList = new ArrayList<>();
        String year;
        if("ȫ��".equals(personAndType.getYear())) {
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
        //�����������Ϣmap
        Map<String, Map<String, Object>> satisfiedMap = new LinkedHashMap<>(10);
        //��map�����������������Ϣͳ�Ƽ�������������ȣ�
        Map<String, Object> resultMap = new HashMap<>(10);
        //���������ͳ��list
        List<List<SatisfiedComparisonVo>> satisfiedList = new ArrayList<>();
        //����6����ͬ��������ȵ�list
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
        satisfiedMap.put("98%����", new HashMap<String, Object>(10){
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
        satisfiedMap.put("90%����", new HashMap<String, Object>(10){
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
                String.format("%.2f", Double.parseDouble(mySatisfiedInfo.get(0).getAvgSatisfiedScore())) + "%" : "��";
        resultMap.put("�ҵ����������", mySatisfied);
        resultMap.put("chartData", satisfiedMap);
        if (!"��".equals(mySatisfied)) {
            //�Ƴ�countΪ0����������ȷ���
//            satisfiedMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
            double myCount = Double.parseDouble(mySatisfiedInfo.get(0).getAvgSatisfiedScore());
            String mySatisfiedType = null;
            if (myCount >= 98) {
                mySatisfiedType = "98%����";
            } else if (myCount >= 96 && myCount < 98) {
                mySatisfiedType = "96%-98%";
            } else if (myCount >= 94 && myCount < 96) {
                mySatisfiedType = "94%-96%";
            } else if (myCount >= 92 && myCount < 94) {
                mySatisfiedType = "92%-94%";
            } else if (myCount >= 90 && myCount < 92) {
                mySatisfiedType = "90%-92%";
            } else if (myCount < 90) {
                mySatisfiedType = "90%����";
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
     * �񾯾�׼���������ѵ�Ա�
     */
    public ResultModel getTrainComparison(PersonAndType personAndType) {
        //���ظ�ǰ�˵�����
        ResultModel resultModel = new ResultModel();
        //���ݹ�ѡά�Ȳ�ѯ����������������
        List<TPersonDo> tPersonDoList = getComparisonPeople(personAndType);
        //ȡ��ǰ��չʾ���ֶ�
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
        //������ѵ��Ϣmap
        Map<String, Map<String, Object>> trainMap = new LinkedHashMap<>(10);
        //��map������������ѵ��Ϣͳ�Ƽ����˽�����ѵ������
        Map<String, Object> resultMap = new HashMap<>(10);
        //������ѵͳ��list
        List<List<TrainComparisonVo>> trainList = new ArrayList<>();
        //����3��������ѵ���������list
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
        trainMap.put("5������", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(0).size());
//                put("detail", trainList.get(0));
            }
        });
        trainMap.put("1-5��", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(1).size());
//                put("detail", trainList.get(1));
            }
        });
        trainMap.put("δ�μ���ѵ", new HashMap<String, Object>(10){
            {
                put("count", trainList.get(2).size());
//                put("detail", trainList.get(2));
            }
        });
        int myCount = tPersonDoList.stream()
                .filter(v -> v.getIdNo().equals(personAndType.getIdNo()))
                .collect(toList()).get(0).getTrainingCount();
        resultMap.put("�ҵĽ�����ѵ", myCount + "��");
        resultMap.put("chartData", trainMap);
        //�Ƴ�countΪ0�Ľ�����ѵ����
        trainMap.entrySet().removeIf(item -> "0".equals(String.valueOf(item.getValue().get("count"))));
        String myTrainType = null;
        if (myCount > 5) {
            myTrainType = "5������";
        } else if (myCount >= 1) {
            myTrainType = "1-5��";
        } else {
            myTrainType = "δ�μ���ѵ";
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

    /**��ѯ�����϶�ά����������������Ϣ*/
    private List<TPersonDo> getComparisonPeople(PersonAndType personAndType) {
        //�������
        personAndType.setYear("%" + personAndType.getYear() + "%");
        //�������ѡά�ȷ��ؿ�
        if (!(personAndType.getIsAge() || personAndType.getIsInParty() || personAndType.getIsInPolice() ||
                personAndType.getIsSchool() || personAndType.getIsSubject() || personAndType.getIsToWork())){
            return new ArrayList<>();
        }
        //��ѯ�񾯸�����Ϣ
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0");
        tPersonDo.setProfessionalName(tEducationDao.getFirst(personAndType.getIdNo(), "0").getPeofessionalName());
        //���ݹ�ѡά�Ȳ�ѯ����������������
        return personComparisonMapper.findPersonByType(tPersonDo, personAndType);
    }

    /**��ѯ�����϶�ά�����������������֤����*/
    private List<String> getComparisonPeopleIdNo(PersonAndType personAndType) {
        //��ѯ�񾯸�����Ϣ
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(personAndType.getIdNo(), "0");
        tPersonDo.setProfessionalName(tEducationDao.getFirst(personAndType.getIdNo(), "0").getPeofessionalName());
        if (personAndType.getIsAge() || personAndType.getIsInParty() || personAndType.getIsInPolice() ||
                personAndType.getIsSchool() || personAndType.getIsSubject() || personAndType.getIsToWork()){
            return personComparisonMapper.findIdNoByType(tPersonDo, personAndType);
        }
        return new ArrayList<>();
    }

    /**ְ������ͳ��*/
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
     * ������������
     *
     * @param list
     * @return
     */
    private List<String> getPersonHonorSort(List<String> list) {
        List<String> resultList = new ArrayList<>();
        //����Ƚ�
        List<String> listOne = new ArrayList<>(), listTwo = new ArrayList<>(), listThree = new ArrayList<>();
        List<List<String>> sortOne = new ArrayList<>(), sortTwo = new ArrayList<>(), sortThree = new ArrayList<>();
        //��һ������list
        for (int i=0; i<7; i++){
            sortOne.add(new ArrayList<>());
        }
        //�ڶ�������list
        for (int i=0; i<4; i++){
            sortTwo.add(new ArrayList<>());
        }
        //����������list
        for (int i=0; i<8; i++){
            sortThree.add(new ArrayList<>());
        }
        //һ�ȹ������ȹ������ȹ�
        List<String> firstLevel = new ArrayList<>(), secondLevel = new ArrayList<>(), thirdLevel = new ArrayList<>();
        //��������
        List<String> otherList = new ArrayList<>();
        //�ȽϽ��
        List<String> resultOne = new ArrayList<>(), resultTwo = new ArrayList<>(), resultThree = new ArrayList<>();
        list.forEach(v -> {
            String str = v.substring(0, v.indexOf("��"));
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
                String str = a.substring(0, a.indexOf("��"));
                String honorName = a.substring(str.length() + 1);
                if ("������ʿ".equals(honorName)) {
                    sortOne.get(0).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ������ϵͳһ��Ӣ��ģ��".equals(honorName)) {
                    sortOne.get(1).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ������ϵͳ����Ӣ��ģ��".equals(honorName)) {
                    sortOne.get(2).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ����ģ".equals(honorName)) {
                    sortOne.get(3).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ���ؼ��������񾯲�".equals(honorName)) {
                    sortOne.get(4).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ���������񾯲�".equals(honorName)) {
                    sortOne.get(5).add(a);
                    listOne.remove(a);
                    break;
                } else if ("ȫ�����˺�����".equals(honorName)) {
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
                String str = a.substring(0, a.indexOf("��"));
                String honorName = a.substring(str.length() + 1);
                if ("ʡ��ģ".equals(honorName)) {
                    sortTwo.get(0).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("ʡ���˺�����".equals(honorName)) {
                    sortTwo.get(1).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("ʡ�������꽱��".equals(honorName)) {
                    sortTwo.get(2).add(a);
                    listTwo.remove(a);
                    break;
                } else if ("���պ�����".equals(honorName)) {
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
                String str = a.substring(0, a.indexOf("��"));
                String honorName = a.substring(str.length() + 1);
                if ("���Ͷ�ģ��".equals(honorName)) {
                    sortThree.get(0).add(a);
                    listThree.remove(a);
                    break;
                } else if ("ʡ���㹲���Ÿɲ�".equals(honorName)) {
                    sortThree.get(1).add(a);
                    listThree.remove(a);
                    break;
                } else if ("ʡ���㹲����Ա".equals(honorName)) {
                    sortThree.get(2).add(a);
                    listThree.remove(a);
                    break;
                } else if ("�ж��Ͼ��������".equals(honorName)) {
                    sortThree.get(3).add(a);
                    listThree.remove(a);
                    break;
                } else if ("�����˺�����".equals(honorName)) {
                    sortThree.get(4).add(a);
                    listThree.remove(a);
                    break;
                } else if ("��ʮ��ܳ�����".equals(honorName)) {
                    sortThree.get(5).add(a);
                    listThree.remove(a);
                    break;
                } else if ("���������꽱��".equals(honorName)) {
                    sortThree.get(6).add(a);
                    listThree.remove(a);
                    break;
                } else if ("�������ϸɲ�".equals(honorName)) {
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
