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
     * @�Լ�������ʲô
     */
    private String mySelf;
    /**
     * @ͳ������ѧ��
     */
    private Map<String, Map<String, Integer>> education;

    /**
     * @ͳ������
     */
    private Map<String, Map<String, Integer>> age;

    /**
     * @����ͳ��
     */
    private Map<String, Map<String, Long>> policeRank;

    /**
     * @��Դ
     */
    private Map<String, Map<String, Integer>> source;

    /**
     * @ְ��
     */
    private Map<String, Map<String, Integer>> duty;

    /**
     * @ְ��
     */
    private  Map<String,Map<String, Integer>> dutyRank;



}
