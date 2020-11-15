package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class CompanyTwoMoreVo {
    /**
     * 排序
     */
    private int id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 单位
     */
    private String deptName;

    /**
     * 年龄
     */
    private String age;

    /**
     * 职务
     */
    private String duty;

    /**
     * 奖励
     */
    private String assess;

    /**
     * 警衔
     */
    private String policeRank;

    /**
     * 民意满意度
     */
    private String opinion;
}
