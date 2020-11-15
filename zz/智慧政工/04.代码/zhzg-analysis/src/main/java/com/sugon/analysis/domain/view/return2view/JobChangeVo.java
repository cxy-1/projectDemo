package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/5
 */
@Data
public class JobChangeVo {
    private String idNumber;
    List<NameAndTimeInfo> jobAndTime;
}
