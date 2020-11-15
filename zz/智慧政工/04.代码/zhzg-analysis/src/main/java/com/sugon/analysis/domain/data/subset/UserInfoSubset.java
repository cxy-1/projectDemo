package com.sugon.analysis.domain.data.subset;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: EducationSubject
 * @Description:
 * @Author: 刘洋
 * @Date: 2019-07-05 20:44
 * @Version: 1.0
 **/

@Entity(name = "userInfoSubset")
@Data
public class UserInfoSubset {
    /** personID */
    @Id
    private String id;

    /** 姓名 */
    private String userName;

    /** 性别 */
    private String sex;

    /** 出生日期 */
    private String birthDate;

    /** 警号 */
    private String policeNo;

    /** 参加工作时间 */
    private String workingHours;

    /** 职务 */
    private String duty;

    /** 职级 */
    private String jobLevel;

    /** 政治面貌 */
    private String party;

    /** 毕业学校 */
    private String school;


    private String education;

    /** 姓名简拼 */
    private String nameSimpleSpelling;

    /** 警衔 */
    private String policeRank;

    /** 警衔图片名称 */
    private String policeRankImage;

    /** 机构名称 */
    private String deptName;

    /** 机构ID */
    private String deptId;

    /** 单位名称 */
    private String companyName;

    /** 单位ID */
    private String companyId;

    /** 短信号码 */
    private String tel;

    /** 家庭住址 */
    private String familyAddress;

    /** 户籍地址 */
    private String permanentAddress;

    /** 民族 */
    private String nation;

    /** 籍贯 */
    private String  nativePlace;

    /** 出生地 */
    private String  birthPlace;

    /** 入党时间 */
    private String inPartyTime;

    /** 婚姻状况 */
    private String maritalStatus;

    /** 血型 */
    private String bloodType;

    /** 身份证号码 */
    private String idNumber;

    /** 参加公安日期 */
    private String inPoliceTime;

    /** 来源 */
    private String sourceEntry;

    /** 来源 */
    private Integer age;

    /** 职级(新) */
    private String jobGrade;
}
