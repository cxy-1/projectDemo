package com.sugon.analysis.domain.view.report;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/27
 */
@Data
public class TeamReport {
	/**
	 * 单位名称
	 */
	private String deptName1;
	/**
	 * 单位名称
	 */
	private String deptName2;
	/**
	 * 当前年份
	 */
	private int currentYear;
	/**
	 * 去年年份
	 */
	private String lastYear;
	/**
	 * 当前年月
	 */
	private String currentMonth;
	/**
	 * 单位别称
	 */
	private String deptAlias;
	/**
	 * 总民警数量
	 */
	private String personCount;

	/**
	 * 男民警
	 */
	private String manCount;

	private String womanCount;

	/**
	 * 男民警比例
	 */
	private String manPercentage;

	/**
	 * 女民警比例
	 */
	private String womanPercentage;

	/**
	 * 男女比例
	 */
	private String proportion;

	/**
	 * 领导班子成员
	 */
	private String lead;

	/**
	 * 平均年龄
	 */
	private String avgAge;

	/**
	 * 执法勤务民警
	 */
	private String enforcePolice;

	/**
	 * 执法勤务民警占比
	 */
	private String enforcePercentage;

	/**
	 * 警务技术
	 */
	private String affairsPolice;

	/**
	 * 警务技术占比
	 */
	private String affairsPercentage;

	/**
	 * 管理类民警
	 */
	private String managementPolice;

	/**
	 * 管理类民警占比
	 */
	private String managementPercentage;

	/**
	 * 分局数量
	 */
	private String branchCount;

	/**
	 * 属地分局
	 */
	private String earthlyBranch;

	/**
	 * 专业分局
	 */
	private String specializedBranch;

	/**
	 * 派出所
	 */
	private String policeStation;



	private String dutyContent;
	private String nonLeaderDutyContent;
	/**
	 * 正厅领导职人数
	 */
	private String mainPosition;

	/**
	 * 副厅领导职人数
	 */
	private String deputyOffice;

	/**
	 * 副局领导职务
	 */
	private String bureau;

	/**
	 * 正处职领导人数
	 */
	private String thePost;

	/**
	 * 副处职领导人数
	 */
	private String thePair;

	/**
	 * 正科领导职人数
	 */
	private String theFamilies;

	/**
	 * 副科职领导人数
	 */
	private String otherJob;

	/**
	 * 35岁以下人数
	 */
	private String age1;

	/**
	 * 35岁以下占比
	 */
	private String agePercentage1;

	/**
	 * 35-50人数
	 */
	private String age2;

	/**
	 * 35-50占比
	 */
	private String agePercentage2;

	/**
	 * 50岁以上
	 */
	private String age3;

	/**
	 * 50岁以上占比
	 */
	private String agePercentage3;

	/**
	 * 主要学历
	 */
	private String education;

	/**
	 * 主要学历占比
	 */
	private String educationPercent;
	/**
	 * 工作时间描述
	 */
	private String workTimeContent;

	/**
	 * 年龄图片
	 */
	private PictureRenderData ageImage;

	/**
	 * 性别图片
	 */
	private PictureRenderData columnSex;

	/**
	 * 工作时间图片
	 */
	private PictureRenderData workTime;

	/**
	 * 文化程度图片
	 */
	private PictureRenderData educationImg;

	/**
	 * 多少项个人荣誉
	 */
	private String honor1;

	/**
	 * 多少个人一等功
	 */
	private String honor2;

	/**
	 * 多少个人二等功
	 */
	private String honor3;

	/**
	 * 个人三等功
	 */
	private String honor4;

	/**
	 * 较市局平均个人荣誉多少项
	 */
	private String honor5;

	/**
	 * 高或低百分之多少
	 */
	private String honor6;


	/**
	 * 荣誉图片
	 */
	private PictureRenderData honorImage;

	/**
	 * 违纪开始年份
	 */
	private String rule1;

	/**
	 * 多少人违纪
	 */
	private String rule2;

	/**
	 * 多少人涉及
	 */
	private String rule3;

	/**
	 * 纪检案件表格
	 */
	private MiniTableRenderData ruleTable;

	/**
	 * 绩效表格
	 */
	private List<Map<String,Object >> kpiTable;

	/**
	 * 任职时间最长的人员表
	 */
	private MiniTableRenderData postTable;

	/**
	 * 当前单位领导班子中入警时间最长的人员表
	 */
	private MiniTableRenderData superLeaderEntryTable;

	/**
	 * 当前单位下设单位领导班子中入警时间最长的人员表
	 */
	private MiniTableRenderData subLeaderEntryTable;

	/**
	 * 工作时间最长的人员表
	 */
	private MiniTableRenderData workTimeTable;

	/**
	 *教育自多少年开始
	 */
	private String training1;

	/**
	 * 共参加培训多少人次
	 */
	private String training2;

	/**
	 * 那一年最多
	 */
	private String training3;

	/**
	 * 最多年的人数
	 */
	private String training4;


	/**
	 * 训练图片
	 */
	private PictureRenderData trainingImage;


	//新加的领导班子分析============================================================================

	/**
	 * 全局领导班子成员共{{overAllLeaderCount}}人
	 */
	private String overAllLeaderCount;
	/**
	 * 当前单位班子成员共{{overAllLeaderCount}}人
	 */
	private String superLeaderCount;

	/**
	 * 当前单位下设单位班子成员共{{subLeaderCount}}人
	 */
	private String subLeaderCount;

	/**
	 * {{overAllName}}
	 */
	private String overAllName;
	/**
	 * 当前单位{{superName}}
	 */
	private String superName;

	/**
	 * 当前单位下设单位{{subName}}
	 */
	private String subName;

	/**
	 * 占总体民警的{{overAllLeaderCountPercent}}
	 */
	private String overAllLeaderCountPercent;


	/**
	 * 当前单位职位分布{{superLeaderDutyContent}}
	 */
	private String superLeaderDutyContent;
	/**
	 * 当前单位下设单位职位分布{{subLeaderDutyContent}}
	 */
	private String subLeaderDutyContent;
	/**
	 * 正厅职领导{{zhengtingLeaderCount}}人
	 */
	private String zhengtingLeaderCount;

	/**
	 * 副厅职领导{{futingLeaderCount}}人
	 */
	private String  futingLeaderCount;

	/**
	 * 副局（副区）职（级）领导{{fujuLeaderCount}}人
	 */
	private String fujuLeaderCount;

	/**
	 * 正处职领导{{zhengchuLeaderCount}}人
	 */
	private String zhengchuLeaderCount;

	/**
	 * 副处职领导{{fuchuLeaderCount}}人
	 */
	private String fuchuLeaderCount;

	/**
	 * 正科职领导{{zhengkeLeaderCount}}人
	 */
	private String zhengkeLeaderCount;

	/**
	 * 副科职领导{{fukeLeaderCount}}人
	 */
	private String fukeLeaderCount;


	/**
	 * 正厅职领导{{neixiashezhengtingCount}}人
	 */
	private String neixiashezhengtingCount;

	/**
	 * 副厅职领导{{neixiashefutingCount}}人
	 */
	private String neixiashefutingCount;

	/**
	 * 副局（副区）职（级）领导{{neixiashefujuCount}}人
	 */
	private String neixiashefujuCount;

	/**
	 * 正处职领导{{neixiashezhengchuCount}}人
	 */
	private String neixiashezhengchuCount;

	/**
	 * 副处职领导{{neixiashefuchuCount}}人
	 */
	private String neixiashefuchuCount;

	/**
	 * 正科职领导{{neixiashezhengkeCount}}人
	 */
	private String neixiashezhengkeCount;

	/**
	 * 副科职领导{{neixiashefukeCount}}人
	 */
	private String neixiashefukeCount;


	/**
	 * 领导班子成员中35岁以下
	 */
	private String youngLeaderCount;

	/**
	 * 领导班子成员中35岁以下占总人数的
	 */
	private String youngLeaderPercent;

	/**
	 * 35（含）到50岁
	 */
	private String middleLeaderCount;

	/**
	 * 35（含）到50岁占总人数比
	 */
	private String middleLeaderPercent;

	/**
	 * 50（含）岁以上
	 */
	private String oldLeaderCount;

	/**
	 * 50（含）岁以上占总人数比
	 */
	private String oldLeaderPercent;

	/**
	 * 平均年龄
	 */
	private String avgLeaderAge;

	/**
	 * 当前单位领导班子成员中35岁以下
	 */
	private String youngLeaderSuperCount;
	/**
	 * 占总人数的
	 */
	private String youngLeaderSuperPercent;
	/**
	 * 35（含）到50岁
	 */
	private String middleLeaderSuperCount;
	/**
	 * 占总人数比的
	 */
	private String middleLeaderSuperPercent;
	/**
	 * 50（含）岁以上
	 */
	private String oldLeaderSuperCount;
	/**
	 * 占总人数比的
	 */
	private String oldLeaderSuperPercent;
	/**
	 * 年纪最大
	 */
	private String superLeaderMaxAge;
	/**
	 * 最小
	 */
	private String superLeaderMinAge;
	/**
	 * 平均年龄
	 */
	private String superLeaderAvgAge;

	/**
	 * 当前单位下设单位领导班子成员中35岁以下
	 */
	private String youngLeaderSubCount;
	/**
	 * 占总人数的
	 */
	private String youngLeaderSubPercent;
	/**
	 * 35（含）到50岁
	 */
	private String middleLeaderSubCount;
	/**
	 * 占总人数比的
	 */
	private String middleLeaderSubPercent;
	/**
	 * 50（含）岁以上
	 */
	private String oldLeaderSubCount;
	/**
	 * 占总人数比的
	 */
	private String oldLeaderSubPercent;
	/**
	 * 年纪最大
	 */
	private String subLeaderMaxAge;
	/**
	 * 最小
	 */
	private String subLeaderMinAge;
	/**
	 * 平均年龄
	 */
	private String subLeaderAvgAge;

	/**
	 * 分局领导班子成员中35岁以下
	 */
	private String youngLeaderFjCount;
	/**
	 * 占总人数的
	 */
	private String youngLeaderFjPercent;
	/**
	 * 35（含）到50岁
	 */
	private String middleLeaderFjCount;
	/**
	 * 占总人数比的
	 */
	private String middleLeaderFjPercent;
	/**
	 * 50（含）岁以上
	 */
	private String oldLeaderFjCount;
	/**
	 * 占总人数比的
	 */
	private String oldLeaderFjPercent;
	/**
	 * 年纪最大
	 */
	private String fjLeaderMaxAge;
	/**
	 * 最小
	 */
	private String fjLeaderMinAge;
	/**
	 * 平均年龄
	 */
	private String fjLeaderAvgAge;


	/**
	 * 市局内 部门领导班子成员中35岁以下
	 */
	private String youngLeaderBmCount;
	/**
	 * 占总人数的
	 */
	private String youngLeaderBmPercent;
	/**
	 * 35（含）到50岁
	 */
	private String middleLeaderBmCount;
	/**
	 * 占总人数比的
	 */
	private String middleLeaderBmPercent;
	/**
	 * 50（含）岁以上
	 */
	private String oldLeaderBmCount;
	/**
	 * 占总人数比的
	 */
	private String oldLeaderBmPercent;
	/**
	 * 年纪最大
	 */
	private String bmLeaderMaxAge;
	/**
	 * 最小
	 */
	private String bmLeaderMinAge;

	/**
	 * 平均年龄
	 */
	private String bmLeaderAvgAge;


	/**
	 * 领导班子成员中男性
	 */
	private String leaderMaleCount;
	/**
	 * 女性
	 */
	private String leaderFemaleCount;
	/**
	 * 男女比例为
	 */
	private String leaderMfPercent;

	/**
	 * 当前单位男性数量
	 */
	private String leaderMaleSuperCount;
	/**
	 * 当前单位女性
	 */
	private String leaderFemaleSuperCount;
	/**
	 * 当前单位男女比例为
	 */
	private String leaderMfSuperPercent;

	/**
	 * 当前单位下设男性数量
	 */
	private String leaderMaleSubCount;
	/**
	 * 当前单位下设女性
	 */
	private String leaderFemaleSubCount;
	/**
	 * 当前单位下设男女比例为
	 */
	private String leaderMfSubPercent;

	/**
	 * 其中分局中男性
	 */
	private String leaderMaleFjCount;
	/**
	 * 女性
	 */
	private String leaderFemaleFjCount;
	/**
	 * 男女比例为
	 */
	private String leaderMfFjPercent;
	/**
	 * 市局内部门中男性
	 */
	private String leaderMaleBmCount;
	/**
	 * 女性
	 */
	private String leaderFemaleBmCount;
	/**
	 * 男女比例为
	 */
	private String leaderMfBmPercent;


	/**
	 * 领导班子成员中主要以
	 */
	private String leaderMainEduName;
	/**
	 * 占总人员的
	 */
	private String leaderMainEduPercent;

	/**
	 * 当前单位领导班子成员中主要以
	 */
	private String superLeaderMainEduName;
	/**
	 * 占总人员的
	 */
	private String superLeaderMainEduPercent;

	/**
	 * 当前单位下设单位领导班子成员中主要以
	 */
	private String subLeaderMainEduName;
	/**
	 * 占总人员的
	 */
	private String subLeaderMainEduPercent;


	/**
	 * 其中分局领导班子成员中主要以
	 */
	private String fjLeaderMainEduName;
	/**
	 * 占总人员的
	 */
	private String fjLeaderMainEduPercent;
	/**
	 * 市局内下设机构中主要以
	 */
	private String bmLeaderMainEduName;
	/**
	 * 占总人员的
	 */
	private String bmLeaderMainEduPercent;


	/**
	 * 当前单位领导班子成员入警年限描述
	 */
	private String superEntryPeriodContent;
	/**
	 * 当前单位下设单位领导班子成员入警年限描述
	 */
	private String subEntryPeriodContent;

	/**
	 * 领导班子成员中入警年限在5年（含）以下的
	 */
	private String leaderSize5;
	/**
	 * 占总人数的
	 */
	private String leaderSize5Percent;
	/**
	 * 5-10年（含）的
	 */
	private String leaderSize5t10;
	/**
	 * 占总人数的
	 */
	private String leaderSize5t10Percent;
	/**
	 * 10-15年（含）的
	 */
	private String leaderSize10t15;
	/**
	 * 占总人数的
	 */
	private String leaderSize10t15Percent;
	/**
	 * 15-20年（含）的
	 */
	private String leaderSize15t20;
	/**
	 * 占总人数的
	 */
	private String leaderSize15t20Percent;
	/**
	 * 20-25年（含）的
	 */
	private String leaderSize20t25;
	/**
	 * 占总人数的
	 */
	private String leaderSize20t25Percent;
	/**
	 * 25-30年（含）的
	 */
	private String leaderSize25t30;
	/**
	 * 占总人数的
	 */
	private String leaderSize25t30Percent;
	/**
	 * 30年及以上的
	 */
	private String leaderSize30;
	/**
	 * 占总人数的
	 */
	private String leaderSize30Percent;


	/**
	 * 领导班子成员中主要以 族为主
	 */
	private String LeaderMainNationName;
	/**
	 * 占总人员的
	 */
	private String LeaderMainNationPercent;
	/**
	 * 当前单位领导班子成员中主要以
	 */
	private String LeaderMainNationNameSuper;
	/**
	 * 占总人员的
	 */
	private String LeaderMainNationPercentSuper;
	/**
	 * 当前单位下设单位领导班子成员中主要以
	 */
	private String LeaderMainNationNameSub;
	/**
	 * 占总人员的
	 */
	private String LeaderMainNationPercentSub;

	/**
	 * 其中分局领导班子成员中主要以
	 */
	private String LeaderMainNationNameFj;
	/**
	 * 占总人员的
	 */
	private String LeaderMainNationPercentFj;
	/**
	 * 市局内部门中主要以
	 */
	private String LeaderMainNationNameBm;
	/**
	 * 占总人员的
	 */
	private String LeaderMainNationPercentBm;


	/**
	 *第一个 领导班子职位分析图片（柱状图）
	 */

	private PictureRenderData superLeaderDuty;
	private PictureRenderData subLeaderDuty;
	/**
	 *第二个 年龄结构图片（饼图）
	 */
	private PictureRenderData leaderPieAge;
	/**
	 *第三个 年龄结构图片（柱状图）
	 */
	private PictureRenderData leaderColumnAge;
	/**
	 *第四个 男女比例图片（饼图）
	 */
	private PictureRenderData leaderSex;

	private PictureRenderData leaderColumnSex;

	/**
	 *第五个 文化程度（柱状图）
	 */
	private PictureRenderData superLeaderEducation;
	private PictureRenderData subLeaderEducation;
	/**
	 *第六个 民族分布（柱状图）
	 */
	private PictureRenderData superLeaderNation;
	private PictureRenderData subLeaderNation;
	/**
	 *第七个 任职时间（柱状图）
	 */
	private PictureRenderData leaderWorkPSB;
	/**
	 *第八个 入警年限（饼图）
	 */
	private PictureRenderData superLeaderEntryPeriod;
	private PictureRenderData subLeaderEntryPeriod;

	/**
	 * 正职超过三年人数
	 */
	private String mainPostNum;
	/**
	 * 正职不超过三年人数
	 */
	private String mainPostNumOppositie;
	/**
	 * 副职超过两年人数
	 */
	private String assistantPostNum;
	/**
	 * 副职不超过两年人数
	 */
	private String assistantPostNumOppositie;

	// 正职领导时间分析
	private PictureRenderData leaderMainPost;

	//副职领导时间分析
	private PictureRenderData leaderAssistantPost;

	private String mydContent;

	private List<RowRenderData> mydTable;


	/****************** 领导班子违纪案件  ******************/
	private String leaderRuleTitle;
	private String leaderRuleContent;
	private String superLeaderRuleContent;
	private String subLeaderRuleContent;
	private PictureRenderData leaderRuleTrend;
	private String leaderRuleTrendTitle;
	private MiniTableRenderData leaderRuleTable;


	private PictureRenderData ruleTrend;
	private String ruleTrendTitle;
	private MiniTableRenderData ruleStatisticsTable;
	private MiniTableRenderData ruleTop5Table;
	private String ruleContent;
	private MiniTableRenderData trainingTable;


	/*** 增减员  */
	private List<RowRenderData>  ptTable;
	private List<RowRenderData>  ptTable1;
	private List<RowRenderData>  ptTable2;
	private List<RowRenderData>  ptTable3;
	private String ptPercent;
	private int reduceCount;
	private int increaseCount;

	private String leaderAgeContent;
	private String leaderAgeSuperContent;
	private String leaderAgeSubContent;
	private String ageContent;
	private String kpiContent;
}
