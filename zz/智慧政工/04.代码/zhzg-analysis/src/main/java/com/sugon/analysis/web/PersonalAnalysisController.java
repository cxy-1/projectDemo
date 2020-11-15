package com.sugon.analysis.web;

import com.sugon.analysis.domain.data.query.LocationQo;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.service.PersonalAnalysisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import static com.sugon.analysis.enums.ResponsesStatus.PARAMERROR;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/28
 */
@RestController
@RequestMapping("/perComparison")
@Slf4j
public class PersonalAnalysisController {
    @Resource
    private PersonalAnalysisService personalAnalysisService;

    /**
     * @param idNumbers
     * @return
     * 名叫初映像描述
     */
    @CrossOrigin
    @ApiOperation(value = "初映像", notes = "民警精准画像初映像")
    @RequestMapping(value = "/firstImpression", method = RequestMethod.GET)
    public ResultModel firstImpression(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getFirstImpression(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * @param idNumbers
     * @return
     * 初映像标签
     */
    @CrossOrigin
    @ApiOperation(value = "初映像标签", notes = "民警精准画像初映像标签描述")
    @RequestMapping(value = "/firstLabelImpression", method = RequestMethod.GET)
    public ResultModel getfirstLabelImpression(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getFirstLabelImpression(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * @param locationQo
     * @return
     * 找位置图表统计
     */
    @ApiOperation(value = "找位置", notes = "民警在本部门和市局的位置")
    @RequestMapping(value = "/location", method = RequestMethod.POST)
    @CrossOrigin
    public ResultModel location(@RequestBody LocationQo locationQo) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(locationQo.getIdNumber())) {
            resultModel.setResult(personalAnalysisService.getLocation(locationQo));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * @param idNumbers
     * @return
     * 根据身份证号判断是否有职务
     */
    @ApiOperation(value = "判断是否有职务", notes = "判断该民警是否有职务")
    @RequestMapping(value = "/isDuty", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel isDuty(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getIsDuty(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }


    /**
     * @param idNumbers
     * @return
     * 民警找位置描述
     */
    @ApiOperation(value = "右侧的找位置描述", notes = "找位置描述")
    @RequestMapping(value = "/locationImpression", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel locationImpression(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getLocationImpression(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * @param idNumbers
     * @return
     * 判断民警是男是女
     */
    @ApiOperation(value = "判断男女", notes = "判断该人是男是女")
    @RequestMapping(value = "/getSex", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel getSex(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getSex(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * 民警职位分析
     * @param locationQo
     * @return
     */
    @ApiOperation(value = "职位分析", notes = "分析民警的职位变动、培训次数、奖励次数")
    @RequestMapping(value = "/jobAnalysis", method = RequestMethod.POST)
    @CrossOrigin
    public ResultModel jobAnalysis(@RequestBody LocationQo locationQo) {
        ResultModel resultModel = new ResultModel();
        if (locationQo !=null) {
            resultModel.setResult(personalAnalysisService.getJobAnalysis(locationQo));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * 民警勇争先
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "勇争先", notes = "分析民警的职位变动、培训次数、奖励次数")
    @RequestMapping(value = "/beTheBest", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel beTheBest(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getToBeTheBest(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * @description: 分析民警的不同单位的获奖程度
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "勇争先标签", notes = "分析民警的不同单位的获奖程度")
    @RequestMapping(value = "/beTheBestLabel", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel beTheBestLabel(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getToBeTheBestLabel(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }


    /**
     * 民警接处警和案件
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "接警量和案件量", notes = "接警量和案件量")
    @RequestMapping(value = "/getJcj", method = RequestMethod.GET)
    @CrossOrigin
    public ResultModel getJcj(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getJcj(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }

    /**
     * 民意分析
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "民意分析", notes = "民意分析")
    @CrossOrigin
    @RequestMapping(value = "/getPersonOpinion", method = RequestMethod.GET)
    public ResultModel getPersonOpinion(@RequestParam("idNumbers") String idNumbers) {
        ResultModel resultModel = new ResultModel();
        if (StringUtils.isNotBlank(idNumbers)) {
            resultModel.setResult(personalAnalysisService.getPersonOpinion(idNumbers));
        } else {
            resultModel.setResult(PARAMERROR);
        }
        return resultModel;
    }



}
