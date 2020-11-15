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
     * ְ��
     */
    private DutyAndYearVo dutyAndYearVo;

    /**
     * ����
     */
    private PoliceRankAndYearVo policeRankAndYearVo;

    /**
     * ����
     */
    private com.sugon.analysis.domain.view.return2view.AssessAndYearVo assessAndYearVo;

    /**
     * �����
     */
    private MyAndMonthVo myAndMonthVo;

    /**
     * ������
     */
    List<String> years;
}
