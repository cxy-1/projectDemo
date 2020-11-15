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
    /**���� ��ͼ��0������1*/
    private String type;
    /**��λid*/
    private String unitId;
    /**��Ч���˷�Χ*/
    private Map<String, Double> jxkh;
    /**�ۺ�����ȷ�Χ*/
    private Map<String, Double> zhmyd;
    /**�ͼ참����Χ*/
    private Map<String, Double> jjaj;
    /**����������Χ*/
    private Map<String, Double> jtry;
}
