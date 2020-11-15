package com.sugon.analysis.domain.view.teambuild;

import lombok.Data;

/**
 * @program: analysis->PersonHonorVo
 * @description:
 * @author: gaowh
 * @create: 2019-11-01 16:36
 **/
@Data
public class PersonHonorVo {
	/**
	 * 姓名
	 */
	private String personName;
	/**
	 * 警号
	 */
	private String policeNo;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 奖励名称
	 */
	private String awardName;
	/**
	 * 授奖时间
	 */
	private String date;
	/**
	 * 授奖单位
	 */
	private String awardDept;
}
