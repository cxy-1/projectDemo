package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/9
 */
@Data
public class MyAndMonthVo {
    /**
     * �·�
     */
    private List<String> months;

    /**
     * ���������
     */
    private Map<String,Map<String,String>> mymydMap;
}
