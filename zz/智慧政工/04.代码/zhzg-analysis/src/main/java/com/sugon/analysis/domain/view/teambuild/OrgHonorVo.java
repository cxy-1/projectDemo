package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->OrgHonorVo
 * @description:
 * @author: gaowh
 * @create: 2019-11-01 14:33
 **/
@Data
public class OrgHonorVo {
    /**
     * 表彰称号
     */
    private String honorName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 授奖时间
     */
    private String date;
    /**
     * 表彰部门
     */
    private String awardDept;
}
