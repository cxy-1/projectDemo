package com.sugon.analysis.domain.data.query;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/3
 */
@Data
public class DutyAndYear {
    /**
     * @职务代码
     */
    private String dutyCode;

    /**
     * @年份
     */
    private String year;

    /**
     * @名称
     */
    private String cvName;
}
