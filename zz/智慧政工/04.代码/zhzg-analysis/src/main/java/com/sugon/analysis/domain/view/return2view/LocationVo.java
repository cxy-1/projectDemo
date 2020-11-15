package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.Map;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/2
 */
@Data
public class LocationVo {
    /**
     * @自己本身是什么
     */
    private String mySelf;
    /**
     * @统计数据学历
     */
    private Map<String, Map<String, Integer>> education;

    /**
     * @统计年龄
     */
    private Map<String, Map<String, Integer>> age;

    /**
     * @警衔统计
     */
    private Map<String, Map<String, Long>> policeRank;

    /**
     * @来源
     */
    private Map<String, Map<String, Integer>> source;

    /**
     * @职务
     */
    private Map<String, Map<String, Integer>> duty;

    /**
     * @职级
     */
    private  Map<String,Map<String, Integer>> dutyRank;



}
