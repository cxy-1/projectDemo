package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class SearchParamVo {

    /**
     * 职务
     */
    private List<String> duty;

    /**
     * 职级
     */
    private Map<String,List<String>> dutyRank;
    /**
     * 警衔
     */
    private List<String> policeRank;
    /**
     * 教育
     */
    private List<String> education;
    /**
     * 来源
     */
    private List<String> source;

    /**
     * 年龄段
     */
    private List<String> ageScope;

    /**
     * 入警年限
     */
    private List<String> timeWork;
}
