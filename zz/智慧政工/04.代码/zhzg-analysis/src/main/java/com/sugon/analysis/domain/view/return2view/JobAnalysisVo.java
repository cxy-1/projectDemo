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
     * @��������
     */
    private Map<String,Integer> reward;

    /**
     * @��ѵ
     */
    private Map<String,Integer> training;

    /**
     * @ְ��
     */
    private Map<String,String> duty;

    /**
     * @��
     */
    private List<String> column;

    /**
     * @���������
     */
    private List<String> row;
}
