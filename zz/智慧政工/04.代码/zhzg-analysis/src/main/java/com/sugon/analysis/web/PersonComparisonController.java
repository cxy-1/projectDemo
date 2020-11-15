package com.sugon.analysis.web;

import com.sugon.analysis.domain.param.PersonAndType;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.service.PersonComparisonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: analysis->PersonComparisonController
 * @description:
 * @author: gaowh
 * @create: 2019-11-28 15:28
 **/
@RestController
@RequestMapping("/personComparison")
@Slf4j
public class PersonComparisonController {
	@Resource
	private PersonComparisonService personComparisonService;

	/**
	 * 民警精准画像符合维度的人数
	 */
	@RequestMapping(value = "/getPersonCount", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getPersonCount(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getPersonCount(personAndType);
	}

	/**
	 * 民警精准画像职务对比
	 */
	@RequestMapping(value = "/getPostComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getPostComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getPostComparison(personAndType);
	}

	/**
	 * 民警精准画像职级对比
	 */
	@RequestMapping(value = "/getJobLevelComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getJobLevelComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getJobLevelComparison(personAndType);
	}

	/**
	 * 民警精准画像警衔晋升对比
	 */
	@RequestMapping(value = "/getRankComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getRankComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getRankComparison(personAndType);
	}

	/**
	 * 民警精准画像表彰奖励对比
	 */
	@RequestMapping(value = "/getHonorComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getHonorComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getHonorComparison(personAndType);
	}

	/**
	 * 民警精准画像民意满意度对比
	 */
	@RequestMapping(value = "/getSatisfiedComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getSatisfiedComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getSatisfiedComparison(personAndType);
	}

	/**
	 * 民警精准画像教育培训对比
	 */
	@RequestMapping(value = "/getTrainComparison", method = RequestMethod.POST)
	@CrossOrigin
	public ResultModel getTrainComparison(@RequestBody PersonAndType personAndType) {
		return personComparisonService.getTrainComparison(personAndType);
	}
}
