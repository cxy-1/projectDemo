package com.sugon.analysis.domain.data.query;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class PersonQo {

    private String id;

    /**
     * ���֤����
     */
    private String idNo;

    /**
     * ����
     */
    private String personName;

    /**
     * �Ա�
     */
    private String sex;

    /**
     * ����
     */
    private String nation;

    /**
     * Ѫ��
     */
    private String bloodType;

    /**
     * ��������
     */
    private String birthDay;

    /**
     * ������
     */
    private String birthPlace;

    /**
     * ����
     */
    private String nativePlace;

    /**
     * ������ò
     */
    private String politicalStatus;

    /**
     * �뵳ʱ��
     */
    private String joinPartyTime;

    /**
     * ��ҵԺУ
     */
    private String graduatedFrom;

    /**
     * �μӹ���ʱ��
     */
    private String timeToWork;

    /**
     * �μӹ�����������
     */
    private String timeToWorkPsb;

    /**
     * ����״��
     */
    private String maritalStatus;

    /**
     * ����״��
     */
    private String healthStatus;

    /**
     * ����ר��
     */
    private String personalExpertise;

    /**
     * ְ��
     */
    private String post;

    /**
     * ����
     */
    private String policeRank;

    /**
     * ����
     */
    private String policeNo;

    /**
     * �Ƿ�����
     */
    private String secertInvolved;

    /**
     * ������λid
     */
    private String orgId;

    /**
     * ������
     */
    private String adder;

    /**
     * ����ʱ��
     */
    private String addTime;

    /**
     * �޸���
     */
    private String moder;

    /**
     * �޸�ʱ��
     */
    private String modTime;

    /**
     * ������Դ
     */
    private String dataSource;

    /**
     * ������Դid
     */
    private String dataSourceId;

    /**
     * 0���� 1ɾ��
     */
    private String delStatus;

    /**
     * ������ַ
     */
    private String permanentAddress;

    /**
     * ��ͥסַ
     */
    private String homeAddress;

    /**
     * ���Ҽ��
     */
    private String officeSimpleName;

    /**
     * ��λ���
     */
    private String orgSimpleName;

    /**
     *
     */
    private String personSort;

    /**
     * ����id
     */
    private String orgDeptId;

    /**
     *
     */
    private String idNo15;

    /**
     * ����
     */
    private String age;

    /**
     * ������Դ
     */
    private String source;

    /**
     * ְ��
     */
    private String jobGrade;

    /**
     * ����ְ�񼶱�
     */
    private String policeJobGrade;


    /**ƴ�ӵ�λ����*/
    @Transient
    private String deptName;

    public String getDeptName() {
        return this.orgSimpleName+(this.officeSimpleName==null?"":this.officeSimpleName);
    }
}
