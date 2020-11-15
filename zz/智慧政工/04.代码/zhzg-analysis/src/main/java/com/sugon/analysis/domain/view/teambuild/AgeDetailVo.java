package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->AgeDetailVo
 * @description:
 * @author: gaowh
 * @create: 2019-10-31 16:55
 **/
@Data
public class AgeDetailVo {

    /**
     * 姓名
     */
    private String personName;

    /**
     * 警号
     */
    private String policeNo;

    /**
     * 性别
     */
    private String sex;

    /**
     * 工作单位
     */
    private String unitName;

    /**
     * 年龄
     */
    private int age;

}
