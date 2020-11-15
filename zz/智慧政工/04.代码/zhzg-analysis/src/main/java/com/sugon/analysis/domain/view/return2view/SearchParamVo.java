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
     * ְ��
     */
    private List<String> duty;

    /**
     * ְ��
     */
    private Map<String,List<String>> dutyRank;
    /**
     * ����
     */
    private List<String> policeRank;
    /**
     * ����
     */
    private List<String> education;
    /**
     * ��Դ
     */
    private List<String> source;

    /**
     * �����
     */
    private List<String> ageScope;

    /**
     * �뾯����
     */
    private List<String> timeWork;
}
