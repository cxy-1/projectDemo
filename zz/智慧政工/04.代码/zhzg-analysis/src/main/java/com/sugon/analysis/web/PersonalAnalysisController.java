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
     * ���г�ӳ������
     */
    @CrossOrigin
    @ApiOperation(value = "��ӳ��", notes = "�񾯾�׼�����ӳ��")
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
     * ��ӳ���ǩ
     */
    @CrossOrigin
    @ApiOperation(value = "��ӳ���ǩ", notes = "�񾯾�׼�����ӳ���ǩ����")
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
     * ��λ��ͼ��ͳ��
     */
    @ApiOperation(value = "��λ��", notes = "���ڱ����ź��оֵ�λ��")
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
     * �������֤���ж��Ƿ���ְ��
     */
    @ApiOperation(value = "�ж��Ƿ���ְ��", notes = "�жϸ����Ƿ���ְ��")
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
     * ����λ������
     */
    @ApiOperation(value = "�Ҳ����λ������", notes = "��λ������")
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
     * �ж���������Ů
     */
    @ApiOperation(value = "�ж���Ů", notes = "�жϸ���������Ů")
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
     * ��ְλ����
     * @param locationQo
     * @return
     */
    @ApiOperation(value = "ְλ����", notes = "�����񾯵�ְλ�䶯����ѵ��������������")
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
     * ��������
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "������", notes = "�����񾯵�ְλ�䶯����ѵ��������������")
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
     * @description: �����񾯵Ĳ�ͬ��λ�Ļ񽱳̶�
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "�����ȱ�ǩ", notes = "�����񾯵Ĳ�ͬ��λ�Ļ񽱳̶�")
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
     * �񾯽Ӵ����Ͱ���
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "�Ӿ����Ͱ�����", notes = "�Ӿ����Ͱ�����")
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
     * �������
     * @param idNumbers
     * @return
     */
    @ApiOperation(value = "�������", notes = "�������")
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
