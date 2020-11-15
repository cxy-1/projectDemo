package com.sugon.analysis.domain.param;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/18
 *
 */
@Data
public class PersonstereoDetailVo {
    private String isDownload;
    private List<String> deptIds;
    private String type;
}
