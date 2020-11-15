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
     * ����id
     */
    private String unitId;


    private String deptLevel;


    private String delStatus;


    private String sortIndex;


    private String searchCode;


    private String perString;

    /**
     * ��ƴ
     */
    private String simpleName;


    private String unitName;


    private String parentId;

    private String companyId;

    private String phoneTic;

    /**
     * �Ӳ���
     */
    private List<CompanyNew> childCompany;

}
