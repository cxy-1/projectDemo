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
	 * ����
	 */
	private String personName;
	/**
	 * ����
	 */
	private String policeNo;
	/**
	 * ��������
	 */
	private String deptName;
	/**
	 * ��������
	 */
	private String awardName;
	/**
	 * �ڽ�ʱ��
	 */
	private String date;
	/**
	 * �ڽ���λ
	 */
	private String awardDept;
}
