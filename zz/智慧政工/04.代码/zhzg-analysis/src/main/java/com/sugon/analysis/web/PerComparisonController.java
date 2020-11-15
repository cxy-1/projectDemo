package com.sugon.analysis.web;

import com.sugon.analysis.domain.data.query.CompareAndType;
import com.sugon.analysis.domain.data.query.MultiConditionQueryPojo;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.service.PerComparisonService;
import com.sugon.analysis.util.CommonUnit;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.sugon.analysis.enums.ResponsesStatus.PARAMERROR;
import static com.sugon.analysis.enums.ResponsesStatus.PARAMSPECIAL;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description: 人员对比
 * @date:2019/11/5
 */
@RestController
@RequestMapping("/perComparison")
@Slf4j
public class PerComparisonController {
    @Resource
    private PerComparisonService perComparisonService;

    /**
     * 查询所有的人员
     *
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "查询人员", notes = "查询所有的人员")
    @RequestMapping(value = "/getPeople", method = RequestMethod.GET)
    public ResultModel getPeople(@RequestParam("query") String query,@RequestParam("idNumber") String idNumber) {
        ResultModel resultModel = new ResultModel();
        if(CommonUnit.isSpecialChar(query)) {
            resultModel.setResult(PARAMSPECIAL);
        }else {
            resultModel.setResult(perComparisonService.getPeopleByParams(idNumber,query));
        }
        return resultModel;
    }


    /**
     * @param compareAndType
     * @return
     * @description: 职务变动
     */
    @CrossOrigin
    @ApiOperation(value = "对比两个民警", notes = "民警精准画像对比")
    @RequestMapping(value = "/companyTwoPeople", method = RequestMethod.POST)
    public ResultModel companyTwoPeople(@RequestBody CompareAndType compareAndType) {
        ResultModel resultModel = new ResultModel();
        if (compareAndType != null) {
            resultModel.setResult(perComparisonService.getCompanyTwoPeople(compareAndType));
        } else {
            return new ResultModel();
        }
        return resultModel;
    }


    /**
     * @param type
     * @return
     * @description: 查询搜索条件
     */
    @CrossOrigin
    @ApiOperation(value = "查询搜索条件", notes = "查询搜索条件")
    @RequestMapping(value = "/getSearchParam", method = RequestMethod.GET)
    public ResultModel getSearchParam(@RequestParam("type") String type) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(type)) {
            resultModel.setResult(perComparisonService.getSearchParam(type));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    @CrossOrigin
    @ApiOperation(value = "对比多个民警", notes = "对比三个以上的民警")
    @RequestMapping(value = "/companyTwoMore", method = RequestMethod.POST)
    public ResultModel companyTwoMore(@RequestBody CompareAndType compareAndType) {
        ResultModel resultModel = new ResultModel();
        if (compareAndType != null) {
            try {
                resultModel.setResult(perComparisonService.getCompanyTwoMore(compareAndType));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * 快速查询
     *
     * @param querySearchVo
     * @return
     */
    /*@CrossOrigin
    @ApiOperation(value = "快速查询", notes = "快速查询")
    @RequestMapping(value = "/quickSearch", method = RequestMethod.POST)
    public ResultModel quickSearch(@RequestBody QuerySearchVo querySearchVo) {
        ResultModel resultModel = new ResultModel();
        if (querySearchVo != null) {
            resultModel.setResult(perComparisonService.getQuickSearch(querySearchVo));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }*/

    /**
     * 快速查询（young）多条件
     *
     * @param
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "快速查询", notes = "快速查询")
    @RequestMapping(value = "/quickSearch", method = RequestMethod.POST)
    public ResultModel quickSearch(@RequestBody MultiConditionQueryPojo multiConditionQueryPojo,@RequestParam("idNumber") String idNumber) {
        ResultModel resultModel = new ResultModel();
        if (multiConditionQueryPojo != null) {
            resultModel.setResult(perComparisonService.getQuickSearch(idNumber,multiConditionQueryPojo));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }
    @Value("${local.url}")
    private String webPath;

    /**
     * 获取图片
     *
     * @param idNumber
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "获取图片", notes = "获取人员照片信息")
    @RequestMapping(value = "/getImgPath", method = RequestMethod.GET)
    public ResultModel getImgPath(@RequestParam("idNumber") String idNumber) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumber)) {
            resultModel.setResult(webPath + "teamPerson/getFtpImgPath?idNumber=" + idNumber);
        } else {
            resultModel.setResult(PARAMERROR);
        }

        return resultModel;
    }


}
