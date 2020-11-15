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
    @GeneratedValue(generator="system-guid")//����system-uuid
    @GenericGenerator(name="system-guid", strategy = "guid")
    @Column(name = "ID", nullable = false, length = 50)
    private String id;

    /*��
     * �û�
     */
    @Column(name = "ID_NO")
    private String idNo;

    /**
     * �����
     */
    @Column(name = "FLAG_NAME")
    private String flagName;

    /**
     * ҳ������
     */
    @Column(name = "PAGE_NAME")
    private String pageName;

    /**
     * ʱ��
     */
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;


    @Column(name = "ACTION")
    private String action;
    /**
     * ��������id��ֱ�Ӹ���
     */
    @Column(name = "DEPT_ID")
    private String deptId;
    /**
     * ������λid
     */
    @Column(name = "UNIT_ID")
    private String unitId;
    /**
     * ����
     */
    @Column(name = "PERSON_NAME")
    private String personName;
    /**
     * ��λ���
     */
    @Column(name = "ORG_SIMPLE_NAME")
    private String orgSimpleName;
    /**
     * ���Ҽ��
     */
    @Column(name = "OFFICE_SIMPLE_NAME")
    private String officeSimpleName;
    /**
     * ����
     */
    @Column(name = "POLICE_NO")
    private String policeNo;
    /**
     * ���л���
     */
    @Column(name = "ACTIVE")
    private String active;

    @Transient
    private String deptName;

    public String getDeptName() {
        return this.orgSimpleName+(this.officeSimpleName==null?"":this.officeSimpleName);
    }
}
