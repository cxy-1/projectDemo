package com.sugon.analysis.domain.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/10/15
 */
@Data
public class DeptAndType {
    /**
     * @����
     */
    @ApiParam(required=true)
    @ApiModelProperty(value="����",name="type")
    private String type;

    /**
     * ���ֵ֤
     */
    private String idNumber;

    /**
     * ��ת��companyId
     */
    private String companyId;
}
