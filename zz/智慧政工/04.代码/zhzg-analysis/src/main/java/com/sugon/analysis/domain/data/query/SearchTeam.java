package com.sugon.analysis.domain.data.query;

import lombok.Data;

/**
 * @program: analysis->SearchTeam
 * @description:
 * @author: gaowh
 * @create: 2019-11-05 17:04
 **/
@Data
public class SearchTeam extends TeamSelect {
    /**
     * �����ؼ���
     */
    private String key;
}
