package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/1
 */
@Data
public class UnitEducationCount {
    private String deptId;
    /**
     * @��λ����
     */
    private String deptName;

    /**
     * @����
     */
    private String type;

    /**
     * @��ʿ����
     */
    private int doctor;

    /**
     * @˶ʿ����
     */
    private int master;

    /**
     * @��������
     */
    private int college;

    /**
     * @��ר����
     */
    private int juniorCollege;

    /**
     * @���м�����
     */
    private int senior;
}
