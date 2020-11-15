package com.sugon.analysis.domain.data.subset;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: EducationSubject
 * @Description:
 * @Author: ����
 * @Date: 2019-07-05 20:44
 * @Version: 1.0
 **/

@Entity(name = "userInfoSubset")
@Data
public class UserInfoSubset {
    /** personID */
    @Id
    private String id;

    /** ���� */
    private String userName;

    /** �Ա� */
    private String sex;

    /** �������� */
    private String birthDate;

    /** ���� */
    private String policeNo;

    /** �μӹ���ʱ�� */
    private String workingHours;

    /** ְ�� */
    private String duty;

    /** ְ�� */
    private String jobLevel;

    /** ������ò */
    private String party;

    /** ��ҵѧУ */
    private String school;


    private String education;

    /** ������ƴ */
    private String nameSimpleSpelling;

    /** ���� */
    private String policeRank;

    /** ����ͼƬ���� */
    private String policeRankImage;

    /** �������� */
    private String deptName;

    /** ����ID */
    private String deptId;

    /** ��λ���� */
    private String companyName;

    /** ��λID */
    private String companyId;

    /** ���ź��� */
    private String tel;

    /** ��ͥסַ */
    private String familyAddress;

    /** ������ַ */
    private String permanentAddress;

    /** ���� */
    private String nation;

    /** ���� */
    private String  nativePlace;

    /** ������ */
    private String  birthPlace;

    /** �뵳ʱ�� */
    private String inPartyTime;

    /** ����״�� */
    private String maritalStatus;

    /** Ѫ�� */
    private String bloodType;

    /** ���֤���� */
    private String idNumber;

    /** �μӹ������� */
    private String inPoliceTime;

    /** ��Դ */
    private String sourceEntry;

    /** ��Դ */
    private Integer age;

    /** ְ��(��) */
    private String jobGrade;
}
