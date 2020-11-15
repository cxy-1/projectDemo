package com.sugon.analysis.domain.data.query;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0 中间实体单位id 部门id
 * @description:
 * @date:2019/10/28
 */
@Data
public class DeptIdsQo {
    /**
     * @单位id
     */
    private String id;
    /**
     * @下级部门id
     */
    List<DeptIdsQo> childId;
}
