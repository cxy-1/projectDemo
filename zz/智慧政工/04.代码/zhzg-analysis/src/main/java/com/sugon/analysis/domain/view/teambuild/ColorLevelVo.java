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
    /**��λid*/
    private String deptId;
    /**��λ����*/
    private String deptName;
    /**��λ���*/
    private String simpleName;
    /**��ɫ�ȼ�*/
    private int colorLevel;
    /**��ɫ�ȼ�*/
    private int level1;
    /**��ɫ�ȼ�*/
    private int level2;
    /**��ɫ�ȼ�*/
    private int level3;
    /**��ɫ�ȼ�*/
    private int level4;
}
