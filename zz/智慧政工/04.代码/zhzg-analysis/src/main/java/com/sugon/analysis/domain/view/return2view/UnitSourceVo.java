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
     * @��������
     */
    private String deptName;

    /**
     * @����ԺУ��ҵ��
     */
    private int policeSchool;

    /**
     * @רҵ����ԭ����
     */
    private int soldier;

    /**
     * @����п�
     */
    private int social;

    /**
     * @ʵʩ����Ա�����ص���
     */
    private int fold;

    /**
     * @����
     */
    private int elseSource;

    /**
     * @����
     */
    private String type;

}
