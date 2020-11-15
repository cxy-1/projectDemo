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
     * @�ڣ�������ʱ��
     */
    @Column(name = "TITLE_TIME")
    private Date titleTime;

    /**
     * @�ڽ���ԭ��
     */
    @Column(name = "TITLE_REASON")
    private String titleReason;

    /**
     * @�ڣ����������
     */
    @Column(name = "TITLE_ORDER")
    private String titleOrder;

    /**
     * @�γ�
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * @��������
     */
    @Column(name = "TITLE_SORT")
    private String titleSort;

    /**
     * @�γ���������
     */
    @Column(name = "TITLE_START_DATE")
    private Date titleStartDate;

    /**
     * @�γ���ֹ����
     */
    @Column(name = "TITLE_END_DATE")
    private Date titleEndDate;

    /**
     * @���֤����
     */
    @Column(name = "IDNO")
    private String idNo;

    /**
     * @������Դ
     */
    @Column(name = "DATA_SOURCE")
    private String dataSource;

    /**
     * @������ԴID
     */
    @Column(name = "DATA_SOURCE_ID")
    private String dataSourceId;

    /**
     * @0����1ɾ��
     */
    @Column(name = "DELSTATUS")
    private int delStatus;








}
