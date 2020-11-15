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
    /**����*/
    private String name;
    /**��λ����*/
    private String deptName;
    /**ְ�����ƴ���*/
    private String jobLevelCode;
    /**ְ������*/
    private String jobLevelName;
    /**ְ��ʱ��*/
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
