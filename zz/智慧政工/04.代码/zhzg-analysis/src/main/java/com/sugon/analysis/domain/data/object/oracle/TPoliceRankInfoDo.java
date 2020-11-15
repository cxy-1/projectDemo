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
 * @date:2019/11/29
 */
@Data
@Entity
@Table(name = "T_POLICE_RANK_INFO",schema = "NJGAZNL")
public class TPoliceRankInfoDo {
    /**
     * @ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * @授（晋）衔时间
     */
    @Column(name = "TITLE_TIME")
    private Date titleTime;

    /**
     * @授晋衔原因
     */
    @Column(name = "TITLE_REASON")
    private String titleReason;

    /**
     * @授（晋）衔令号
     */
    @Column(name = "TITLE_ORDER")
    private String titleOrder;

    /**
     * @衔称
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * @授衔种类
     */
    @Column(name = "TITLE_SORT")
    private String titleSort;

    /**
     * @衔称起算日期
     */
    @Column(name = "TITLE_START_DATE")
    private Date titleStartDate;

    /**
     * @衔称终止日期
     */
    @Column(name = "TITLE_END_DATE")
    private Date titleEndDate;

    /**
     * @身份证号码
     */
    @Column(name = "IDNO")
    private String idNo;

    /**
     * @数据来源
     */
    @Column(name = "DATA_SOURCE")
    private String dataSource;

    /**
     * @数据来源ID
     */
    @Column(name = "DATA_SOURCE_ID")
    private String dataSourceId;

    /**
     * @0正常1删除
     */
    @Column(name = "DELSTATUS")
    private int delStatus;








}
