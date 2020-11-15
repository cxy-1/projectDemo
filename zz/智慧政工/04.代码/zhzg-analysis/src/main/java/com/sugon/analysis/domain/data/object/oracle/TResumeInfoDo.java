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
     * @�������(��˾�ľ��������ӵľ����������ڹ����ľ���)
     */
    @Column(name = "RESUME_SORT")
    private String resumeSort;

    /**
     * @�������ͣ���������/ѧϰ������
     */
    @Column(name = "RESUME_TYPE")
    private String resumeType;

    /**
     * @��ʼ����
     */
    @Column(name = "STARTING_DATE")
    private Date startingDate;

    /**
     * @��ֹ����
     */
    @Column(name = "CLOSING_DATE")
    private Date closingDate;

    /**
     * @���ڵ�λ
     */
    @Column(name = "ORG_NAME")
    private String orgName;

    /**
     * @��ݻ�ְ��
     */
    @Column(name = "POSITION")
    private String position;

    /**
     * @������Դ
     */
    @Column(name = "DATA_SOURCE")
    private String dateSource;

    /**
     * @�����ֶ�
     */
    @Column(name = "DATA_SOURCE_ID")
    private String dataSourceId;

    /**
     * @���֤��
     */
    @Column(name = "IDNO")
    private String idNo;

    /**
     * @�Ƿ�ɾ����־
     */
    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * @����
     */
    @Column(name = "RELATEID")
    private String relateId;








}
