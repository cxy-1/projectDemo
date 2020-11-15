package com.sugon.analysis.domain.data.query;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/2
 */
@Data
public class SourceAndCount {
    /**
     * @来源名称
     */
    private String sourceName;

    /**
     * @毕业院校类型
     */
    private String schoolType;

    /**
     * @数量
     */
    private int count;
}
