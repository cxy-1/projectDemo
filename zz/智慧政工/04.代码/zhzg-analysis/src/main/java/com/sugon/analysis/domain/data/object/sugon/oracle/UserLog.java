package com.sugon.analysis.domain.data.object.sugon.oracle;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/24
 */
@Entity(name = "USER_LOG")
@Data
public class UserLog {
    @Id
    @GeneratedValue(generator="system-guid")//关联system-uuid
    @GenericGenerator(name="system-guid", strategy = "guid")
    @Column(name = "ID", nullable = false, length = 50)
    private String id;

    /*才
     * 用户
     */
    @Column(name = "ID_NO")
    private String idNo;

    /**
     * 大标题
     */
    @Column(name = "FLAG_NAME")
    private String flagName;

    /**
     * 页面名称
     */
    @Column(name = "PAGE_NAME")
    private String pageName;

    /**
     * 时间
     */
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;


    @Column(name = "ACTION")
    private String action;
    /**
     * 所属部门id（直接父）
     */
    @Column(name = "DEPT_ID")
    private String deptId;
    /**
     * 所属单位id
     */
    @Column(name = "UNIT_ID")
    private String unitId;
    /**
     * 姓名
     */
    @Column(name = "PERSON_NAME")
    private String personName;
    /**
     * 单位简称
     */
    @Column(name = "ORG_SIMPLE_NAME")
    private String orgSimpleName;
    /**
     * 科室简称
     */
    @Column(name = "OFFICE_SIMPLE_NAME")
    private String officeSimpleName;
    /**
     * 警号
     */
    @Column(name = "POLICE_NO")
    private String policeNo;
    /**
     * 运行环境
     */
    @Column(name = "ACTIVE")
    private String active;

    @Transient
    private String deptName;

    public String getDeptName() {
        return this.orgSimpleName+(this.officeSimpleName==null?"":this.officeSimpleName);
    }
}
