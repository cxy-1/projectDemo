package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description: 擎天部门表实体
 * @author: gaowh
 * @create: 2019-09-24 10:41
 **/
@Data
@Entity
@Table(name = "SYS_DEPT",schema = "NJGAZNL")
public class SysDeptDo {

    /**
     * 部门id主键ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 部门编码UNITCODE
     */
    @Column(name = "UNITCODE")
    private String unitId;

    /**
     * 部门级别 单位/部门/科室DEPTLEVEL
     */
    @Column(name = "DEPTLEVEL")
    private String deptLevel;

    /**
     * 部门负责人DEPTLEADERS
     */
    @Column(name = "DEPTLEADERS")
    private String deptLeaders;

    /**
     * 删除标识DELSTATUS
     */
    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * 排序号SORTINDEX
     */
    @Column(name = "SORTINDEX")
    private int sortIndex;

    /**
     * 快速查询编码SEARCHCODE
     */
    @Column(name = "SEARCHCODE")
    private String searchCode;

    /**
     * 部门拥有的菜单PERMSTRING
     */
    @Column(name = "PERMSTRING")
    private String permString;

    /**
     * 部门简称SIMPLECHINANAME
     */
    @Column(name = "SIMPLECHINANAME")
    private String simpleName;

    /**
     * 部门名称CHINANAME
     */
    @Column(name = "CHINANAME")
    private String unitName;

    /**
     * 上级部门PARENTID
     */
    @Column(name = "PARENTID")
    private String parentId;

    /**
     * 所属单位UNITID
     */
    @Column(name = "UNITID")
    private String companyId;

    /**
     * 传真号码FAXPHONE
     */
    @Column(name = "FAXPHONE")
    private String faxPhone;

    /**
     * 名称首字母PHONETIC
     */
    @Column(name = "PHONETIC")
    private String phoneTic;

    /**
     * 联系方式CONTACTPHONE
     */
    @Column(name = "CONTACTPHONE")
    private String contactPhone;

    @Transient
    private List<SysDeptDo> childCompany = new ArrayList<>();

}
