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
     * ���
     */
    private String year;

    /**
     *�Ӵ���
     */
    private int jcj;

    /**
     * ����
     */
    private int aj;

    /**
     * �Ӵ���ռ����
     */
    private String accountedFoJcj;

    /**
     * ����ռ����
     */
    private String accountedForAj;

    private double opinion;
}
