package com.sugon.analysis.domain.data.query;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * description ���� ��������ѯ�Ĳ�ѯ����
 *
 * @author young
 * @time 10:35
 */

@Data
public class MultiConditionQueryPojo {
    private List<Map<String,Object>> param;
}
