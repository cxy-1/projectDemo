package com.sugon.analysis.domain.data.query;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0 �м�ʵ�嵥λid ����id
 * @description:
 * @date:2019/10/28
 */
@Data
public class DeptIdsQo {
    /**
     * @��λid
     */
    private String id;
    /**
     * @�¼�����id
     */
    List<DeptIdsQo> childId;
}
