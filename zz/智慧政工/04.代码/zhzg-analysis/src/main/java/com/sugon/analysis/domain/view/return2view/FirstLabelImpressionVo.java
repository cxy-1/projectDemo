package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/28
 */
@Data
public class FirstLabelImpressionVo {
    /**
     * @加入公安描述
     */
    private String policeContent;

    /**
     * @加入共产党描述
     */
    private String politicalContent;

    /**
     * @最近职务晋升描述
     */
    private String dutyContent;

    /**
     * @最近一次警衔晋升描述
     */
    private String policeRankContent;

    /**
     * @333专业人才描述
     */
    private String talentsContent;

    /**
     * @兼职教官
     */
    private String jzContent;

    /**
     * 司法证书
     */
    private String fxContent;

    /**
     * @荣誉描述
     */
    private String honorContent;

}
