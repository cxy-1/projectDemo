package com.sugon.analysis.domain.data.query;

import lombok.Data;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/10
 */
@Data
public class QuerySearchVo {
    /**
     * ����
     */
    private String param;

    /**
     * ����
     */
    private String type;

    /**
     * ���֤��
     */
    private String idNumber;

    /**
     * ���id
     */
    private String performId;

    /**
     * ����Id
     */
    private String groupId;


    /**
     * �����ֶ�
     */
    private String sort;
}
