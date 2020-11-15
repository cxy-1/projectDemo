package com.sugon.analysis.domain.data.query;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class QuerySearchVo {
    /**
     * 参数
     */
    private String param;

    /**
     * 类型
     */
    private String type;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 年份id
     */
    private String performId;

    /**
     * 分组Id
     */
    private String groupId;


    /**
     * 排序字段
     */
    private String sort;
}
