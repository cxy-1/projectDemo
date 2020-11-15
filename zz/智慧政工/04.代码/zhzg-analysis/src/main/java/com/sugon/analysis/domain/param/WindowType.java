package com.sugon.analysis.domain.param;

import lombok.Data;

import java.util.Map;

/**
 * @program: analysis->WindowType
 * @description:
 * @author: gaowh
 * @create: 2019-12-12 13:16
 **/
@Data
public class WindowType {
    /**类型 地图是0，树是1*/
    private String type;
    /**单位id*/
    private String unitId;
    /**绩效考核范围*/
    private Map<String, Double> jxkh;
    /**综合满意度范围*/
    private Map<String, Double> zhmyd;
    /**纪检案件范围*/
    private Map<String, Double> jjaj;
    /**集体荣誉范围*/
    private Map<String, Double> jtry;
}
