package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->TrainDetailVo
 * @description:
 * @author: gaowh
 * @create: 2019-11-01 10:51
 **/
@Data
public class TrainDetailVo {

    /**
     * ����
     */
    private String personName;

    /**
     * ����
     */
    private String policeNo;

    /**
     * ������λ
     */
    private String unitName;

    /**
     * ��ѵ��ʼʱ��
     */
    private String startTime;

    /**
     * ��ѵ����ʱ��
     */
    private String endTime;

    /**
     * ��ѵ������
     */
    private String trainingCourse;

    /**
     * ��ѵ���
     */
    private String trainingResult;

}
