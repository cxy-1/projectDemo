package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class ComparisonTwoVo {
    /**
     * 职务
     */
    private DutyAndYearVo dutyAndYearVo;

    /**
     * 警衔
     */
    private PoliceRankAndYearVo policeRankAndYearVo;

    /**
     * 奖励
     */
    private com.sugon.analysis.domain.view.return2view.AssessAndYearVo assessAndYearVo;

    /**
     * 满意度
     */
    private MyAndMonthVo myAndMonthVo;

    /**
     * 横坐标
     */
    List<String> years;
}
