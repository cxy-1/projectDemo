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
    /**�漰����*/
    private String type;
    /**���֤����*/
    private String idNo;
    /**����*/
    private String personName;
    /**����*/
    private String policeNo;
    /**��������*/
    private String deptName;
    /**����ʱ��*/
    private String date;

    /**����ʱ��**/
    private String date1;
    /**��������*/
    private String detail;
    /**��������*/
    private String post;
}
