package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/1
 */
@Data
public class UnitEducationCount {
    private String deptId;
    /**
     * @单位名称
     */
    private String deptName;

    /**
     * @类型
     */
    private String type;

    /**
     * @博士人数
     */
    private int doctor;

    /**
     * @硕士人数
     */
    private int master;

    /**
     * @本科人数
     */
    private int college;

    /**
     * @大专人数
     */
    private int juniorCollege;

    /**
     * @高中及以下
     */
    private int senior;
}
