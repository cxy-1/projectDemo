package com.sugon.analysis.domain.data.subset;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/9/25
 */
@Data
public class CompanyNew {
    private String id;
    /**
     * 部门id
     */
    private String unitId;


    private String deptLevel;


    private String delStatus;


    private String sortIndex;


    private String searchCode;


    private String perString;

    /**
     * 简拼
     */
    private String simpleName;


    private String unitName;


    private String parentId;

    private String companyId;

    private String phoneTic;

    /**
     * 子部门
     */
    private List<CompanyNew> childCompany;

}
