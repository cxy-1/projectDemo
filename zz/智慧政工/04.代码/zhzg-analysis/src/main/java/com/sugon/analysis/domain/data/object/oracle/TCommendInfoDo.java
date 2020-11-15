package com.sugon.analysis.domain.data.object.oracle;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author gaowh
 */
@Data
@Entity
@Table(name = "T_COMMEND_INFO",schema = "NJGAZNL")
public class TCommendInfoDo {
	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "AWARD_NAME")
	private String awardName;
	@Column(name = "AWARD_WINNING_UNIT")
	private String awardWinningunit;
	@Column(name = "APPROVAL_DATE")
	private String approvalDate;
	@Column(name = "DATA_SOURCE")
	private String dataSource;
	@Column(name = "DATA_SOURCE_ID")
	private String dataSourceid;
	@Column(name = "IDNO")
	private String idno;
	@Column(name = "ADDER")
	private String adder;
	@Column(name = "ADD_TIME")
	private String addTime;
	@Column(name = "MODER")
	private String moder;
	@Column(name = "MOD_TIME")
	private String modTime;
	@Column(name = "DELSTATUS")
	private String delstaus;
	@Column(name = "RELATEID")
	private String relateid;
	@Column(name = "APPROVAL_SORT")
	private String approvalSort;
	@Column(name = "CANCEL_DATE")
	private String cancelDate;
	@Column(name = "UNIT_GRADE")
	private String unitGrade;

}
