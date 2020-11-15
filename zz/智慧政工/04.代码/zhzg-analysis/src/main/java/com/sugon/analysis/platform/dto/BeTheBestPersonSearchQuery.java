package com.sugon.analysis.platform.dto;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @description: ������ģ����Ա����
 * @author: liuwei
 * @create: 2020-06-08 11:32
 **/
@Data
public class BeTheBestPersonSearchQuery extends PageQuery {
    private String idNumber;
    private String policeNo;
    private String personName;
    private List<String> deptIds;
    private Boolean editable;

    public String getPersonName(){
        return StringUtils.isNotBlank(personName)?("%"+personName+"%"):null;
    }

}
