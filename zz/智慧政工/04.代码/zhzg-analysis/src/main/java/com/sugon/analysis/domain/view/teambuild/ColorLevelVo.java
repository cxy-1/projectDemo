package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->ColorLevelVo
 * @description:
 * @author: gaowh
 * @create: 2019-12-11 13:29
 **/
@Data
public class ColorLevelVo {
    /**单位id*/
    private String deptId;
    /**单位名称*/
    private String deptName;
    /**单位简称*/
    private String simpleName;
    /**颜色等级*/
    private int colorLevel;
    /**颜色等级*/
    private int level1;
    /**颜色等级*/
    private int level2;
    /**颜色等级*/
    private int level3;
    /**颜色等级*/
    private int level4;
}
