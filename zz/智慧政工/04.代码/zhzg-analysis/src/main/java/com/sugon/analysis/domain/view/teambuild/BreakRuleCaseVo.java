package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->BreakRuleCaseVo
 * @description:
 * @author: gaowh
 * @create: 2019-12-20 15:39
 **/
@Data
public class BreakRuleCaseVo {
    /**涉及对象*/
    private String type;
    /**身份证号码*/
    private String idNo;
    /**姓名*/
    private String personName;
    /**警号*/
    private String policeNo;
    /**部门名称*/
    private String deptName;
    /**决定时间*/
    private String date;

    /**到期时间**/
    private String date1;
    /**问题性质*/
    private String detail;
    /**问题性质*/
    private String post;
}
