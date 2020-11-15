package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description: ���첿�ű�ʵ��
 * @author: gaowh
 * @create: 2019-09-24 10:41
 **/
@Data
@Entity
@Table(name = "SYS_DEPT",schema = "NJGAZNL")
public class SysDeptDo {

    /**
     * ����id����ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ���ű���UNITCODE
     */
    @Column(name = "UNITCODE")
    private String unitId;

    /**
     * ���ż��� ��λ/����/����DEPTLEVEL
     */
    @Column(name = "DEPTLEVEL")
    private String deptLevel;

    /**
     * ���Ÿ�����DEPTLEADERS
     */
    @Column(name = "DEPTLEADERS")
    private String deptLeaders;

    /**
     * ɾ����ʶDELSTATUS
     */
    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * �����SORTINDEX
     */
    @Column(name = "SORTINDEX")
    private int sortIndex;

    /**
     * ���ٲ�ѯ����SEARCHCODE
     */
    @Column(name = "SEARCHCODE")
    private String searchCode;

    /**
     * ����ӵ�еĲ˵�PERMSTRING
     */
    @Column(name = "PERMSTRING")
    private String permString;

    /**
     * ���ż��SIMPLECHINANAME
     */
    @Column(name = "SIMPLECHINANAME")
    private String simpleName;

    /**
     * ��������CHINANAME
     */
    @Column(name = "CHINANAME")
    private String unitName;

    /**
     * �ϼ�����PARENTID
     */
    @Column(name = "PARENTID")
    private String parentId;

    /**
     * ������λUNITID
     */
    @Column(name = "UNITID")
    private String companyId;

    /**
     * �������FAXPHONE
     */
    @Column(name = "FAXPHONE")
    private String faxPhone;

    /**
     * ��������ĸPHONETIC
     */
    @Column(name = "PHONETIC")
    private String phoneTic;

    /**
     * ��ϵ��ʽCONTACTPHONE
     */
    @Column(name = "CONTACTPHONE")
    private String contactPhone;

    @Transient
    private List<SysDeptDo> childCompany = new ArrayList<>();

}
