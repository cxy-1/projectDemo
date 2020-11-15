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
     * 姓名
     */
    private String personName;

    /**
     * 警号
     */
    private String policeNo;

    /**
     * 工作单位
     */
    private String unitName;

    /**
     * 培训开始时间
     */
    private String startTime;

    /**
     * 培训结束时间
     */
    private String endTime;

    /**
     * 培训班名称
     */
    private String trainingCourse;

    /**
     * 培训结果
     */
    private String trainingResult;

}
