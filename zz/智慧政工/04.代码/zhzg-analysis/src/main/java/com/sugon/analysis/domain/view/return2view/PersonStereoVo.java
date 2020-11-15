package com.sugon.analysis.domain.view.return2view;

import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/10/28
 */
@Data
public class PersonStereoVo {
    private String companyId;

    private String deptName;
    /**
     * @部门分局领导信息
     */
    List<com.sugon.analysis.domain.view.return2view.HeadInfo> headInfoList;
    /**
     * @平均年龄
     */
    private String aveAge;
    /**
     * @人员总数
     */
    private int personSum;
    /**
     * 正处职含以上
     */
    private int inThePost;
    /**
     * @副处职务含以上
     */
    private int uprightPosition;
    /**
     * @副处职
     */
    private int deputyDuties;
    /**
     * @正科职
     */
    private int professionalDuties;
    /**
     * @副科职
     */
    private int subsidiaryDuties;

    /**
     * 是否下转
     */
    private String isSub;

    /**
     * 是否可点击
     */
    private String isClick;

    /**
     * 判断是第一级还是第二级别
     */
    private String isWhite;
}
