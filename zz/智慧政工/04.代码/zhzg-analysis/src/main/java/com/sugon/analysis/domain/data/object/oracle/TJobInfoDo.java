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
 * @date:2019/12/6
 */
@Data
@Entity
@Table(name = "T_JOB_INFO",schema = "NJGAZNL")
public class TJobInfoDo {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "JOB_GRADE")
    private String jobGrade;

    @Column(name = "DURATION_OF_SERVICE")
    private Date durationOfService;

    @Column(name = "DATA_SOURCE")
    private String dataSource;

    @Column(name = "DATA_SOURCE_ID")
    private String dataSourceId;

    @Column(name = "IDNO")
    private String idNo;

    @Column(name = "DELSTATUS")
    private int delStatus;

    @Column(name = "JOB_TIME")
    private Date jobTime;




}
