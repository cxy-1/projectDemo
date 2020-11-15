package com.sugon.analysis.domain.data.query;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class PersonQo {

    private String id;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 出生日期
     */
    private String birthDay;

    /**
     * 出生地
     */
    private String birthPlace;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 入党时间
     */
    private String joinPartyTime;

    /**
     * 毕业院校
     */
    private String graduatedFrom;

    /**
     * 参加工作时间
     */
    private String timeToWork;

    /**
     * 参加公安工作日期
     */
    private String timeToWorkPsb;

    /**
     * 婚姻状况
     */
    private String maritalStatus;

    /**
     * 健康状况
     */
    private String healthStatus;

    /**
     * 个人专长
     */
    private String personalExpertise;

    /**
     * 职务
     */
    private String post;

    /**
     * 警衔
     */
    private String policeRank;

    /**
     * 警号
     */
    private String policeNo;

    /**
     * 是否涉密
     */
    private String secertInvolved;

    /**
     * 所属单位id
     */
    private String orgId;

    /**
     * 新增人
     */
    private String adder;

    /**
     * 新增时间
     */
    private String addTime;

    /**
     * 修改人
     */
    private String moder;

    /**
     * 修改时间
     */
    private String modTime;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 数据来源id
     */
    private String dataSourceId;

    /**
     * 0正常 1删除
     */
    private String delStatus;

    /**
     * 户籍地址
     */
    private String permanentAddress;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 科室简称
     */
    private String officeSimpleName;

    /**
     * 单位简称
     */
    private String orgSimpleName;

    /**
     *
     */
    private String personSort;

    /**
     * 部门id
     */
    private String orgDeptId;

    /**
     *
     */
    private String idNo15;

    /**
     * 年龄
     */
    private String age;

    /**
     * 进入来源
     */
    private String source;

    /**
     * 职级
     */
    private String jobGrade;

    /**
     * 公安职务级别
     */
    private String policeJobGrade;


    /**拼接单位部门*/
    @Transient
    private String deptName;

    public String getDeptName() {
        return this.orgSimpleName+(this.officeSimpleName==null?"":this.officeSimpleName);
    }
}
