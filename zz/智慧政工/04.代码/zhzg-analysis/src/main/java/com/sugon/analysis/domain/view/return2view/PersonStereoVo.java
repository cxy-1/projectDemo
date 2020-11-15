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
     * @���ŷ־��쵼��Ϣ
     */
    List<com.sugon.analysis.domain.view.return2view.HeadInfo> headInfoList;
    /**
     * @ƽ������
     */
    private String aveAge;
    /**
     * @��Ա����
     */
    private int personSum;
    /**
     * ����ְ������
     */
    private int inThePost;
    /**
     * @����ְ������
     */
    private int uprightPosition;
    /**
     * @����ְ
     */
    private int deputyDuties;
    /**
     * @����ְ
     */
    private int professionalDuties;
    /**
     * @����ְ
     */
    private int subsidiaryDuties;

    /**
     * �Ƿ���ת
     */
    private String isSub;

    /**
     * �Ƿ�ɵ��
     */
    private String isClick;

    /**
     * �ж��ǵ�һ�����ǵڶ�����
     */
    private String isWhite;
}
