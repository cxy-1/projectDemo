package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/9
 */
@Data
public class PersonVo {
    /**
     * id
     */
    private String id;

    /**
     * 警号
     */
    private String policeNo;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门名称
     */
    private String deptName;
}
