package com.sugon.analysis.domain.view.personstereo;

import com.sugon.analysis.domain.view.return2view.UnitSchoolCountVo;
import lombok.Data;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/20
 */
@Data
public class ResultUnitSchoolVo {
    private List<UnitSchoolCountVo> unitSchoolCountVoList;
    private List<String> column;
}
