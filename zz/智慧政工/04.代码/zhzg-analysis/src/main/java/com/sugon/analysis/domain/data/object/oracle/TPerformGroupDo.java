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
 * @date:2019/12/19
 */
@Data
@Entity
@Table(name = "T_PERFORM_GROUP",schema = "NJGAZNL")
public class TPerformGroupDo {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ��������
     */
    @Column(name = "PERFORM_ID")
    private String performId;

    /**
     * ��������
     */
    @Column(name = "GROUP_NAME")
    private String groupName;

    /**
     * �ϼ��������
     */
    @Column(name = "P_GROUP_ID")
    private String pGroupId;

    /**
     * ����˳��
     */
    @Column(name = "GROUP_SORT")
    private String groupSort;

    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * ���ʱ��
     */
    @Column(name = "ADDTIME")
    private Date addTime;

    /**
     * �޸�ʱ��
     */
    @Column(name = "MODTIME")
    private Date modTime;
}
