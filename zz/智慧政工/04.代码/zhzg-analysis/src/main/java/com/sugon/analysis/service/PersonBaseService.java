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

    private static final String MAIN_HALL_LEADERSHIP = "�����쵼ְ";

    static final String MAIN_HALL_SHIP = "����ְ";

    private static final String DEPUTY_OFFICE_LEADERSHIP = "�����쵼ְ";

    static final String DEPUTY_OFFICE_SHIP = "����ְ";

    static final String IN_THE_POST = "����ְ";

    static final String DEPUTY_BUREAU = "����ְ";

    private static final String DEPUTY_PREFECTS= "������";

    private static final String LEADERSHIP_DEPUTY_BUREAU= "�����쵼ְ";

    private static final String DEPUTY_DISTRICT= "������";

    private static final String NJ_DEPUTY= "�Ͼ���Ѳ��Ա";

    static final String DEPUTY_BUREAU_NON_LEADERSHIP= "����ְ���쵼";

    private static final String AT_THE_LEVEL= "������";

    static final String PLACE_PAIR_PLACE_POSITION= "����ְ";

    private static final String PLACE_CLASS= "������";

    static final String AS_IS_FAMILY= "����ְ";

    private static final String SECTION= "���Ƽ�";

    static final String OTHER_JOB= "����ְ";

    static final String ONE = "1";

    static final String DUTY = "ְ��";

    static final String DUTY_RANK = "ְ��";

    static final String SOCIAL = "����п�";
    static final String OTHER = "����";

    static final String AUTHORITY_TO = "ʵʩ����Ա�����ص���";


    static final String POLICE_COLLEGE = "����ԺУ��ҵ��";

    static final String CON_AND_DEMOBILIZATION = "תҵ����Ա����";

    static final String DOCTOR = "��ʿ";

    static final String MASTER = "˶ʿ";

    static final String COLLEGE = "����";

    static final String JUNIOR_COLLEGE = "��ר";

    static final String UNDER_HIGH_SCHOOL = "���м�����";

    static final String DOCTOR_CODE = "11001";

    static final int ONE_THOUSAND = 1000;

    List<String> dutyName =Arrays.asList(" ", "����ְ", "����ְ", "����ְ", "����ְ", "����ְ���쵼", "����ְ", "����ְ",
            "����ְ");

    String[] dutys = new String[]{"0925", "102", "10", "0830", "0920", "0730", "0820", "0720", "0717", "0718",
            "0715", "0716", "0620", "0520"};

    String[] dutyRanks1 = new String[]{" ", "������Ա", "һ����Ա", "�ļ����ο�Ա", "�������ο�Ա", "�������ο�Ա",
            "һ�����ο�Ա", "�ļ�����Ա", "��������Ա", "��������Ա", "һ������Ա", "����Ѳ��Ա", "һ��Ѳ��Ա"};

    String[] dutyRanks2 = new String[]{" ", "������Ա", "һ����Ա", "�ļ�����", "��������", "��������",
            "һ������", "�ļ��߼�����", "�����߼�����", "�����߼�����", "һ���߼�����", "��������רԱ", "һ������רԱ"};

    String[] dutyRanks3 = new String[]{" ", "������Ա", "�������ļ�����", "��������������", "��������������",
            "������һ������", "�������ļ�����", "��������������", "��������������", "������һ������",
            "�����������ܼ�", "������һ���ܼ�", "һ������רԱ"};


    /**
     * ���κϲ�
     * @param identify
     * @return
     */
    String mergeDuty(String identify) {
        if (MAIN_HALL_LEADERSHIP.equals(identify)) {
            identify = "����ְ";
        } else if (DEPUTY_OFFICE_LEADERSHIP.equals(identify)) {
            identify = "����ְ";
        } else if (IN_THE_POST.equals(identify)) {
            identify = "����ְ";
        } else if (DEPUTY_BUREAU.equals(identify) || DEPUTY_PREFECTS.equals(identify) ||
                LEADERSHIP_DEPUTY_BUREAU.equals(identify)) {
            identify = "����ְ";
        } else if (DEPUTY_DISTRICT.equals(identify) || NJ_DEPUTY.equals(identify) ||
                DEPUTY_BUREAU_NON_LEADERSHIP.equals(identify)) {
            identify = "����ְ���쵼";
        } else if (AT_THE_LEVEL.equals(identify) || PLACE_PAIR_PLACE_POSITION.equals(identify)) {
            identify = "����ְ";
        } else if (PLACE_CLASS.equals(identify) || AS_IS_FAMILY.equals(identify)) {
            identify = "����ְ";
        } else if (SECTION.equals(identify) || OTHER_JOB.equals(identify)) {
            identify = "����ְ";
        }
        return identify;
    }

    /**
     * @param idNumber
     * @return �жϸ����Ƿ���ְ����ְ��
     */
    public String getIsDuty(String idNumber) {
        TPersonDo tPersonDo = tPersonDao.findByIdNoAndDelStatus(idNumber, "0");
        String name = "ְ��";
        List<String> dutyStr = Arrays.asList(dutys);
        if (tPersonDo != null) {
            String duty = tPersonDo.getPoliceJobGrade();
            if (duty != null) {
                if (dutyStr.contains(duty)) {
                    name = "ְ��";
                } else {
                    //���Ƿ���ְ��
                    if (tPersonDo.getJobGrade() != null) {
                        name = "ְ��";
                    } else {
                        name = "ְ��";
                    }
                }
            } else {
                if (tPersonDo.getJobGrade() != null) {
                    name = "ְ��";
                } else {
                    name = "ְ��";
                }
            }
        }
        return name;
    }
}
