package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/9/17
 */
@Entity
@Data
@Table(name = "SG_QUERY_LEAVE",schema = "NJGAZNL")
public class SgQueryLeaveDo {

    @Id
    @Column(name = "ID")
    private String id;
    /**
     * �û�����
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * ���֤��
     */
    @Column(name = "USER_IDNO")
    private String userIdNo;
    /**
     * ��������
     */
    @Column(name = "DEPT_NAME")
    private String deptName;
    /**
     * ���ű���
     */
    @Column(name = "DEPT_CODE")
    private String deptCode;
    /**
     * �������
     */
    @Column(name = "LEAVE_TYPE")
    private String leaveType;
    /**
     * ��ٿ�ʼʱ��
     */
    @Column(name = "START_TIME")
    private String startTime;
    /**
     * ��ٽ���ʱ��
     */
    @Column(name = "END_TIME")
    private String endTime;
    /**
     * ����ʱ��
     */
    @Column(name = "APPLY_TIME")
    private String applyTime;
}
