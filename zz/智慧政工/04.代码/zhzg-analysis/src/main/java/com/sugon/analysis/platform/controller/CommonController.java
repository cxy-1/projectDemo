package com.sugon.analysis.platform.controller;

import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.util.ChildUnit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: Õ®”√øÿ÷∆≤„
 * @author: liuwei
 * @create: 2020-06-04 11:06
 **/
@RestController
@RequestMapping("/common")
@CrossOrigin
public class CommonController {

    @Resource
    private ChildUnit childUnit;

    @RequestMapping(value = "/unittreeAuto", method = RequestMethod.POST)
    public ResultModel departmentTreeAuto(@RequestParam("idNumber") String idNumber) {
        return childUnit.departmentTree(idNumber);
    }

}
