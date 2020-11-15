package com.sugon.analysis.domain.data.query;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * description 描述 多条件查询的查询参数
 *
 * @author young
 * @time 10:35
 */

@Data
public class MultiConditionQueryPojo {
    private List<Map<String,Object>> param;
}
