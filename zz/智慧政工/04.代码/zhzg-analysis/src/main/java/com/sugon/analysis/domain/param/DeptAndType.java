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
     * @分类
     */
    @ApiParam(required=true)
    @ApiModelProperty(value="类型",name="type")
    private String type;

    /**
     * 身份证值
     */
    private String idNumber;

    /**
     * 下转的companyId
     */
    private String companyId;
}
