package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/5
 */
@Data
public class JcjVo {
    /**
     * 年份
     */
    private String year;

    /**
     *接处警
     */
    private int jcj;

    /**
     * 案件
     */
    private int aj;

    /**
     * 接处警占比量
     */
    private String accountedFoJcj;

    /**
     * 案件占比量
     */
    private String accountedForAj;

    private double opinion;
}
