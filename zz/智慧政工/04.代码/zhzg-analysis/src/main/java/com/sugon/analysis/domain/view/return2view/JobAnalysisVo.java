package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/3
 */
@Data
public class JobAnalysisVo {
    /**
     * @个人荣誉
     */
    private Map<String,Integer> reward;

    /**
     * @培训
     */
    private Map<String,Integer> training;

    /**
     * @职务
     */
    private Map<String,String> duty;

    /**
     * @列
     */
    private List<String> column;

    /**
     * @横坐标年份
     */
    private List<String> row;
}
