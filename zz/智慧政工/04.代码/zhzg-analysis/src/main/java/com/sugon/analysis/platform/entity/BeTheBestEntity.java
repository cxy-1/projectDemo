package com.sugon.analysis.platform.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description:
 * @author: liuwei
 * @create: 2020-06-08 11:38
 **/
@Entity
@Table(name = "BE_THE_BEST", schema = "ZHZG")
@Data
public class BeTheBestEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 50)
    @GeneratedValue(generator="system-guid")//¹ØÁªsystem-uuid
    @GenericGenerator(name="system-guid", strategy = "guid")
    private String id;

    @Basic
    @Column(name = "INFO_ID", nullable = true, length = 50)
    private String infoId;

    @Basic
    @Column(name = "TIME", nullable = true, length = 20)
    private String time;

    @Basic
    @Column(name = "DEPT_NAME", nullable = true, length = 255)
    private String deptName;

    @Basic
    @Column(name = "DUTY_CHANGE", nullable = true, length = 500)
    private String dutyChange;

    @Basic
    @Column(name = "ASSESS", nullable = true, length = 500)
    private String assess;

    @Basic
    @Column(name = "HONOR", nullable = true, length = 800)
    private String honor;

    @Basic
    @Column(name = "OPINION", nullable = true, length = 800)
    private String opinion;

    @Basic
    @Column(name = "EXTRA", nullable = true, length = 800)
    private String extra;

    @Basic
    @Column(name = "DELSTATUS", nullable = true, length = 800)
    private String delstatus;

    @Basic
    @Column(name = "ACTIVE", nullable = true, length = 50)
    private String active;


}
