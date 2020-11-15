package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/10/31
 */
@Data
public class UnitSourceVo {
    private String deptId;
    /**
     * @部门名称
     */
    private String deptName;

    /**
     * @警察院校毕业生
     */
    private int policeSchool;

    /**
     * @专业及复原军人
     */
    private int soldier;

    /**
     * @社会招考
     */
    private int social;

    /**
     * @实施公务员法机关调入
     */
    private int fold;

    /**
     * @其它
     */
    private int elseSource;

    /**
     * @类型
     */
    private String type;

}
