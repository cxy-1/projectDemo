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
     * 考核主键
     */
    @Column(name = "PERFORM_ID")
    private String performId;

    /**
     * 分组名称
     */
    @Column(name = "GROUP_NAME")
    private String groupName;

    /**
     * 上级分组组件
     */
    @Column(name = "P_GROUP_ID")
    private String pGroupId;

    /**
     * 分组顺序
     */
    @Column(name = "GROUP_SORT")
    private String groupSort;

    @Column(name = "DELSTATUS")
    private int delStatus;

    /**
     * 添加时间
     */
    @Column(name = "ADDTIME")
    private Date addTime;

    /**
     * 修改时间
     */
    @Column(name = "MODTIME")
    private Date modTime;
}
