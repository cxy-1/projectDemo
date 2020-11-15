package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/28
 */
@Data
@Entity
@Table(name = "T_RESUME_INFO",schema = "NJGAZNL")
public class TResumeInfoDo {
    /**
     * @ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * @履历类别(公司的经历、部队的经历，后期在公安的经历)
     */
    @Column(name = "RESUME_SORT")
    private String resumeSort;

    /**
     * @履历类型（工作履行/学习履历）
     */
    @Column(name = "RESUME_TYPE")
    private String resumeType;

    /**
     * @起始日期
     */
    @Column(name = "STARTING_DATE")
    private Date startingDate;

    /**
     * @截止日期
     */
    @Column(name = "CLOSING_DATE")
    private Date closingDate;

    /**
     * @所在单位
     */
    @Column(name = "ORG_NAME")
    private String orgName;

    /**
     * @身份或职务
     */
    @Column(name = "POSITION")
    private String position;

    /**
     * @数据来源
     */
    @Column(name = "DATA_SOURCE")
    private String dateSource;

    /**
     * @排序字段
     */
    @Column(name = "DATA_SOURCE_ID")
    private String dataSourceId;

    /**
     * @身份证号
     */
    @Column(name = "IDNO")
    private String idNo;

    /**
     * @是否删除标志
     */
    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * @描述
     */
    @Column(name = "RELATEID")
    private String relateId;








}
