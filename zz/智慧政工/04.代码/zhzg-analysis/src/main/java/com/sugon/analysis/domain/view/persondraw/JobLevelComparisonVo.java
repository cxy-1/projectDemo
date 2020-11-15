package com.sugon.analysis.domain.view.persondraw;

import lombok.Data;

/**
 * @program: analysis->JobLevelComparisonVo
 * @description:
 * @author: gaowh
 * @create: 2019-12-06 17:15
 **/
@Data
public class JobLevelComparisonVo {
    /**personId*/
    private String personId;
    /**姓名*/
    private String name;
    /**单位名称*/
    private String deptName;
    /**职级名称代码*/
    private String jobLevelCode;
    /**职级名称*/
    private String jobLevelName;
    /**职级时间*/
    private String jobLevelTime;

    @Override
    public boolean equals(Object obj) {
        JobLevelComparisonVo s=(JobLevelComparisonVo)obj;
        return personId.equals(s.personId);
    }

    @Override
    public int hashCode() {
        String in = personId;
        return in.hashCode();
    }
}
