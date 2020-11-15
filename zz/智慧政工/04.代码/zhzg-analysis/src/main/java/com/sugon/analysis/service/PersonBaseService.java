package com.sugon.analysis.service;

import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.repository.oracle.TPersonDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/11
 */
@Slf4j
@Service
public class PersonBaseService {

    @Resource
    private TPersonDao tPersonDao;

    private static final String MAIN_HALL_LEADERSHIP = "正厅领导职";

    static final String MAIN_HALL_SHIP = "正厅职";

    private static final String DEPUTY_OFFICE_LEADERSHIP = "副厅领导职";

    static final String DEPUTY_OFFICE_SHIP = "副厅职";

    static final String IN_THE_POST = "正处职";

    static final String DEPUTY_BUREAU = "副局职";

    private static final String DEPUTY_PREFECTS= "副区长";

    private static final String LEADERSHIP_DEPUTY_BUREAU= "副局领导职";

    private static final String DEPUTY_DISTRICT= "副区级";

    private static final String NJ_DEPUTY= "南京副巡视员";

    static final String DEPUTY_BUREAU_NON_LEADERSHIP= "副局职非领导";

    private static final String AT_THE_LEVEL= "正处级";

    static final String PLACE_PAIR_PLACE_POSITION= "副处职";

    private static final String PLACE_CLASS= "副处级";

    static final String AS_IS_FAMILY= "正科职";

    private static final String SECTION= "正科级";

    static final String OTHER_JOB= "副科职";

    static final String ONE = "1";

    static final String DUTY = "职务";

    static final String DUTY_RANK = "职级";

    static final String SOCIAL = "社会招考";
    static final String OTHER = "其他";

    static final String AUTHORITY_TO = "实施公务员法机关调入";


    static final String POLICE_COLLEGE = "警察院校毕业生";

    static final String CON_AND_DEMOBILIZATION = "转业及复员军人";

    static final String DOCTOR = "博士";

    static final String MASTER = "硕士";

    static final String COLLEGE = "本科";

    static final String JUNIOR_COLLEGE = "大专";

    static final String UNDER_HIGH_SCHOOL = "高中及以下";

    static final String DOCTOR_CODE = "11001";

    static final int ONE_THOUSAND = 1000;

    List<String> dutyName =Arrays.asList(" ", "副科职", "正科职", "副处职", "正处职", "副局职非领导", "副局职", "副厅职",
            "正厅职");

    String[] dutys = new String[]{"0925", "102", "10", "0830", "0920", "0730", "0820", "0720", "0717", "0718",
            "0715", "0716", "0620", "0520"};

    String[] dutyRanks1 = new String[]{" ", "二级科员", "一级科员", "四级主任科员", "三级主任科员", "二级主任科员",
            "一级主任科员", "四级调研员", "三级调研员", "二级调研员", "一级调研员", "二级巡视员", "一级巡视员"};

    String[] dutyRanks2 = new String[]{" ", "二级警员", "一级警员", "四级警长", "三级警长", "二级警长",
            "一级警长", "四级高级警长", "三级高级警长", "二级高级警长", "一级高级警长", "二级警务专员", "一级警务专员"};

    String[] dutyRanks3 = new String[]{" ", "警务技术员", "警务技术四级主管", "警务技术三级主管", "警务技术二级主管",
            "警务技术一级主管", "警务技术四级主任", "警务技术三级主任", "警务技术二级主任", "警务技术一级主任",
            "警务技术二级总监", "警务技术一级总监", "一级警务专员"};


    /**
     * 警衔合并
     * @param identify
     * @return
     */
    String mergeDuty(String identify) {
        if (MAIN_HALL_LEADERSHIP.equals(identify)) {
            identify = "正厅职";
        } else if (DEPUTY_OFFICE_LEADERSHIP.equals(identify)) {
            identify = "副厅职";
        } else if (IN_THE_POST.equals(identify)) {
            identify = "正处职";
        } else if (DEPUTY_BUREAU.equals(identify) || DEPUTY_PREFECTS.equals(identify) ||
                LEADERSHIP_DEPUTY_BUREAU.equals(identify)) {
            identify = "副局职";
        } else if (DEPUTY_DISTRICT.equals(identify) || NJ_DEPUTY.equals(identify) ||
                DEPUTY_BUREAU_NON_LEADERSHIP.equals(identify)) {
            identify = "副局职非领导";
        } else if (AT_THE_LEVEL.equals(identify) || PLACE_PAIR_PLACE_POSITION.equals(identify)) {
            identify = "副处职";
        } else if (PLACE_CLASS.equals(identify) || AS_IS_FAMILY.equals(identify)) {
            identify = "正科职";
        } else if (SECTION.equals(identify) || OTHER_JOB.equals(identify)) {
            identify = "副科职";
        }
        return identify;
    }

    /**
     * @param idNumber
     * @return 判断该民警是否有职务还是职级
     */
    public String getIsDuty(String idNumber) {
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(idNumber, "0");
        String name = "职务";
        List<String> dutyStr = Arrays.asList(dutys);
        if (tPersonDo != null) {
            String duty = tPersonDo.getPoliceJobGrade();
            if (duty != null) {
                if (dutyStr.contains(duty)) {
                    name = "职务";
                } else {
                    //看是否有职级
                    if (tPersonDo.getJobGrade() != null) {
                        name = "职级";
                    } else {
                        name = "职务";
                    }
                }
            } else {
                if (tPersonDo.getJobGrade() != null) {
                    name = "职级";
                } else {
                    name = "职务";
                }
            }
        }
        return name;
    }
}
