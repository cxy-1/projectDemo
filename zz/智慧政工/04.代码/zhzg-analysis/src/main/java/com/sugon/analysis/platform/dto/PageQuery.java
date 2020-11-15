package com.sugon.analysis.platform.dto;

import lombok.Data;

/**
 * @description: 分页查询公共类
 * @author: liuwei
 * @create: 2020-06-04 11:58
 **/
@Data
public class PageQuery {
    private int pageSize;

    private int pageNo;

    private int start;

    public int getStart() {
        return (pageNo - 1) * pageSize + 1;
    }

    private int end;

    public int getEnd() {
        return pageNo * pageSize;
    }
}
