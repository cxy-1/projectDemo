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
     * ���óƺ�
     */
    private String honorName;
    /**
     * ��������
     */
    private String deptName;
    /**
     * �ڽ�ʱ��
     */
    private String date;
    /**
     * ���ò���
     */
    private String awardDept;
}
