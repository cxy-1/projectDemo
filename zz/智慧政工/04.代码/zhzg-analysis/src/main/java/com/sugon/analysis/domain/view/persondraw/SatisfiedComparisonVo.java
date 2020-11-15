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
    /**身份证号*/
    private String idNo;
    /**姓名*/
    private String name;
    /**单位名称*/
    private String deptName;
    /**民意满意度*/
    private String satisfiedScore;
    /**年平均民意满意度*/
    private String avgSatisfiedScore;
}
