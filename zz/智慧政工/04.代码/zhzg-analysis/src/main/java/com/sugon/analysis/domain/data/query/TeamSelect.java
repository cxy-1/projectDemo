package com.sugon.analysis.domain.data.query;


import lombok.Data;

import java.util.List;

/**
 * @program: analysis->TeamSelect
 * @description:
 * @author: gaowh
 * @create: 2019-10-28 15:33
 **/
@Data
public class TeamSelect {
    /**
     * �־�&֧��
     */
    private String type;
    /**
     * ��λid
     */
    private List<String> unitId;
}
