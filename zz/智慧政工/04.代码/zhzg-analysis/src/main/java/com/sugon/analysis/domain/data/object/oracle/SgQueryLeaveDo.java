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
     * 用户名称
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * 身份证号
     */
    @Column(name = "USER_IDNO")
    private String userIdNo;
    /**
     * 部门名称
     */
    @Column(name = "DEPT_NAME")
    private String deptName;
    /**
     * 部门编码
     */
    @Column(name = "DEPT_CODE")
    private String deptCode;
    /**
     * 请假类型
     */
    @Column(name = "LEAVE_TYPE")
    private String leaveType;
    /**
     * 请假开始时间
     */
    @Column(name = "START_TIME")
    private String startTime;
    /**
     * 请假结束时间
     */
    @Column(name = "END_TIME")
    private String endTime;
    /**
     * 申请时间
     */
    @Column(name = "APPLY_TIME")
    private String applyTime;
}
