package com.sugon.analysis.domain.view.persondraw;

import lombok.Data;

/**
 * @program: analysis->SatisfiedComparisonVo
 * @description:
 * @author: gaowh
 * @create: 2019-12-02 21:34
 **/
@Data
public class SatisfiedComparisonVo {
    /**���֤��*/
    private String idNo;
    /**����*/
    private String name;
    /**��λ����*/
    private String deptName;
    /**���������*/
    private String satisfiedScore;
    /**��ƽ�����������*/
    private String avgSatisfiedScore;
}
