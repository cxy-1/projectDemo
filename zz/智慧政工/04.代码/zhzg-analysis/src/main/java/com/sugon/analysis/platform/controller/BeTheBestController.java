package com.sugon.analysis.platform.controller;

import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.platform.dto.BeTheBestDTO;
import com.sugon.analysis.platform.dto.BeTheBestPersonSearchQuery;
import com.sugon.analysis.platform.service.BeTheBestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 勇争先数据维护
 * @author: liuwei
 * @create: 2020-06-08 10:54
 **/
@RestController
@RequestMapping("/beTheBest")
@CrossOrigin
public class BeTheBestController {

	@Resource
	private BeTheBestService beTheBestService;

	@RequestMapping(value = "/searchPerson",method = RequestMethod.POST)
	public ResultModel searchPerson(@RequestBody BeTheBestPersonSearchQuery query){
		return beTheBestService.searchPerson(query);
	}

	@RequestMapping(value = "/searchEditablePerson",method = RequestMethod.POST)
	public ResultModel searchEditablePerson(@RequestBody BeTheBestPersonSearchQuery query){
		return beTheBestService.searchEditablePerson(query);
	}
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public ResultModel update(@RequestBody BeTheBestDTO theBestDTO){
		return beTheBestService.update(theBestDTO);
	}

	@RequestMapping(value = "/deleteInfo",method = RequestMethod.POST)
	public ResultModel deleteInfo(@RequestBody List<String> idNos){
		return beTheBestService.deleteInfo(idNos);
	}

	@RequestMapping(value = "/dateRange",method = RequestMethod.GET)
	public ResultModel dateRange(@RequestParam("idNumber") String idNumber){
		return beTheBestService.dateRange(idNumber);
	}

}
