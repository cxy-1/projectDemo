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
     * @��Դ����
     */
    private String sourceName;

    /**
     * @��ҵԺУ����
     */
    private String schoolType;

    /**
     * @����
     */
    private int count;
}
