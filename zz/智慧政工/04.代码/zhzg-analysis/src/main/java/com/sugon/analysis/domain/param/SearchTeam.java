package com.sugon.analysis.domain.param;

import lombok.Data;

/**
 * @program: analysis->SearchTeam
 * @description:
 * @author: gaowh
 * @create: 2019-11-05 17:04
 **/
@Data
public class SearchTeam extends com.sugon.analysis.domain.data.query.TeamSelect {
    /**
     * ËÑË÷¹Ø¼ü×Ö
     */
    private String key;
}
