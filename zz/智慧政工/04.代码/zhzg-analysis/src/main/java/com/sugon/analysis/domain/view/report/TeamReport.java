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
	 * ��λ����
	 */
	private String deptName1;
	/**
	 * ��λ����
	 */
	private String deptName2;
	/**
	 * ��ǰ���
	 */
	private int currentYear;
	/**
	 * ȥ�����
	 */
	private String lastYear;
	/**
	 * ��ǰ����
	 */
	private String currentMonth;
	/**
	 * ��λ���
	 */
	private String deptAlias;
	/**
	 * ��������
	 */
	private String personCount;

	/**
	 * ����
	 */
	private String manCount;

	private String womanCount;

	/**
	 * ���񾯱���
	 */
	private String manPercentage;

	/**
	 * Ů�񾯱���
	 */
	private String womanPercentage;

	/**
	 * ��Ů����
	 */
	private String proportion;

	/**
	 * �쵼���ӳ�Ա
	 */
	private String lead;

	/**
	 * ƽ������
	 */
	private String avgAge;

	/**
	 * ִ��������
	 */
	private String enforcePolice;

	/**
	 * ִ��������ռ��
	 */
	private String enforcePercentage;

	/**
	 * ������
	 */
	private String affairsPolice;

	/**
	 * ������ռ��
	 */
	private String affairsPercentage;

	/**
	 * ��������
	 */
	private String managementPolice;

	/**
	 * ��������ռ��
	 */
	private String managementPercentage;

	/**
	 * �־�����
	 */
	private String branchCount;

	/**
	 * ���ط־�
	 */
	private String earthlyBranch;

	/**
	 * רҵ�־�
	 */
	private String specializedBranch;

	/**
	 * �ɳ���
	 */
	private String policeStation;



	private String dutyContent;
	private String nonLeaderDutyContent;
	/**
	 * �����쵼ְ����
	 */
	private String mainPosition;

	/**
	 * �����쵼ְ����
	 */
	private String deputyOffice;

	/**
	 * �����쵼ְ��
	 */
	private String bureau;

	/**
	 * ����ְ�쵼����
	 */
	private String thePost;

	/**
	 * ����ְ�쵼����
	 */
	private String thePair;

	/**
	 * �����쵼ְ����
	 */
	private String theFamilies;

	/**
	 * ����ְ�쵼����
	 */
	private String otherJob;

	/**
	 * 35����������
	 */
	private String age1;

	/**
	 * 35������ռ��
	 */
	private String agePercentage1;

	/**
	 * 35-50����
	 */
	private String age2;

	/**
	 * 35-50ռ��
	 */
	private String agePercentage2;

	/**
	 * 50������
	 */
	private String age3;

	/**
	 * 50������ռ��
	 */
	private String agePercentage3;

	/**
	 * ��Ҫѧ��
	 */
	private String education;

	/**
	 * ��Ҫѧ��ռ��
	 */
	private String educationPercent;
	/**
	 * ����ʱ������
	 */
	private String workTimeContent;

	/**
	 * ����ͼƬ
	 */
	private PictureRenderData ageImage;

	/**
	 * �Ա�ͼƬ
	 */
	private PictureRenderData columnSex;

	/**
	 * ����ʱ��ͼƬ
	 */
	private PictureRenderData workTime;

	/**
	 * �Ļ��̶�ͼƬ
	 */
	private PictureRenderData educationImg;

	/**
	 * �������������
	 */
	private String honor1;

	/**
	 * ���ٸ���һ�ȹ�
	 */
	private String honor2;

	/**
	 * ���ٸ��˶��ȹ�
	 */
	private String honor3;

	/**
	 * �������ȹ�
	 */
	private String honor4;

	/**
	 * ���о�ƽ����������������
	 */
	private String honor5;

	/**
	 * �߻�Ͱٷ�֮����
	 */
	private String honor6;


	/**
	 * ����ͼƬ
	 */
	private PictureRenderData honorImage;

	/**
	 * Υ�Ϳ�ʼ���
	 */
	private String rule1;

	/**
	 * ������Υ��
	 */
	private String rule2;

	/**
	 * �������漰
	 */
	private String rule3;

	/**
	 * �ͼ참�����
	 */
	private MiniTableRenderData ruleTable;

	/**
	 * ��Ч���
	 */
	private List<Map<String,Object >> kpiTable;

	/**
	 * ��ְʱ�������Ա��
	 */
	private MiniTableRenderData postTable;

	/**
	 * ��ǰ��λ�쵼�������뾯ʱ�������Ա��
	 */
	private MiniTableRenderData superLeaderEntryTable;

	/**
	 * ��ǰ��λ���赥λ�쵼�������뾯ʱ�������Ա��
	 */
	private MiniTableRenderData subLeaderEntryTable;

	/**
	 * ����ʱ�������Ա��
	 */
	private MiniTableRenderData workTimeTable;

	/**
	 *�����Զ����꿪ʼ
	 */
	private String training1;

	/**
	 * ���μ���ѵ�����˴�
	 */
	private String training2;

	/**
	 * ��һ�����
	 */
	private String training3;

	/**
	 * ����������
	 */
	private String training4;


	/**
	 * ѵ��ͼƬ
	 */
	private PictureRenderData trainingImage;


	//�¼ӵ��쵼���ӷ���============================================================================

	/**
	 * ȫ���쵼���ӳ�Ա��{{overAllLeaderCount}}��
	 */
	private String overAllLeaderCount;
	/**
	 * ��ǰ��λ���ӳ�Ա��{{overAllLeaderCount}}��
	 */
	private String superLeaderCount;

	/**
	 * ��ǰ��λ���赥λ���ӳ�Ա��{{subLeaderCount}}��
	 */
	private String subLeaderCount;

	/**
	 * {{overAllName}}
	 */
	private String overAllName;
	/**
	 * ��ǰ��λ{{superName}}
	 */
	private String superName;

	/**
	 * ��ǰ��λ���赥λ{{subName}}
	 */
	private String subName;

	/**
	 * ռ�����񾯵�{{overAllLeaderCountPercent}}
	 */
	private String overAllLeaderCountPercent;


	/**
	 * ��ǰ��λְλ�ֲ�{{superLeaderDutyContent}}
	 */
	private String superLeaderDutyContent;
	/**
	 * ��ǰ��λ���赥λְλ�ֲ�{{subLeaderDutyContent}}
	 */
	private String subLeaderDutyContent;
	/**
	 * ����ְ�쵼{{zhengtingLeaderCount}}��
	 */
	private String zhengtingLeaderCount;

	/**
	 * ����ְ�쵼{{futingLeaderCount}}��
	 */
	private String  futingLeaderCount;

	/**
	 * ���֣�������ְ�������쵼{{fujuLeaderCount}}��
	 */
	private String fujuLeaderCount;

	/**
	 * ����ְ�쵼{{zhengchuLeaderCount}}��
	 */
	private String zhengchuLeaderCount;

	/**
	 * ����ְ�쵼{{fuchuLeaderCount}}��
	 */
	private String fuchuLeaderCount;

	/**
	 * ����ְ�쵼{{zhengkeLeaderCount}}��
	 */
	private String zhengkeLeaderCount;

	/**
	 * ����ְ�쵼{{fukeLeaderCount}}��
	 */
	private String fukeLeaderCount;


	/**
	 * ����ְ�쵼{{neixiashezhengtingCount}}��
	 */
	private String neixiashezhengtingCount;

	/**
	 * ����ְ�쵼{{neixiashefutingCount}}��
	 */
	private String neixiashefutingCount;

	/**
	 * ���֣�������ְ�������쵼{{neixiashefujuCount}}��
	 */
	private String neixiashefujuCount;

	/**
	 * ����ְ�쵼{{neixiashezhengchuCount}}��
	 */
	private String neixiashezhengchuCount;

	/**
	 * ����ְ�쵼{{neixiashefuchuCount}}��
	 */
	private String neixiashefuchuCount;

	/**
	 * ����ְ�쵼{{neixiashezhengkeCount}}��
	 */
	private String neixiashezhengkeCount;

	/**
	 * ����ְ�쵼{{neixiashefukeCount}}��
	 */
	private String neixiashefukeCount;


	/**
	 * �쵼���ӳ�Ա��35������
	 */
	private String youngLeaderCount;

	/**
	 * �쵼���ӳ�Ա��35������ռ��������
	 */
	private String youngLeaderPercent;

	/**
	 * 35��������50��
	 */
	private String middleLeaderCount;

	/**
	 * 35��������50��ռ��������
	 */
	private String middleLeaderPercent;

	/**
	 * 50������������
	 */
	private String oldLeaderCount;

	/**
	 * 50������������ռ��������
	 */
	private String oldLeaderPercent;

	/**
	 * ƽ������
	 */
	private String avgLeaderAge;

	/**
	 * ��ǰ��λ�쵼���ӳ�Ա��35������
	 */
	private String youngLeaderSuperCount;
	/**
	 * ռ��������
	 */
	private String youngLeaderSuperPercent;
	/**
	 * 35��������50��
	 */
	private String middleLeaderSuperCount;
	/**
	 * ռ�������ȵ�
	 */
	private String middleLeaderSuperPercent;
	/**
	 * 50������������
	 */
	private String oldLeaderSuperCount;
	/**
	 * ռ�������ȵ�
	 */
	private String oldLeaderSuperPercent;
	/**
	 * ������
	 */
	private String superLeaderMaxAge;
	/**
	 * ��С
	 */
	private String superLeaderMinAge;
	/**
	 * ƽ������
	 */
	private String superLeaderAvgAge;

	/**
	 * ��ǰ��λ���赥λ�쵼���ӳ�Ա��35������
	 */
	private String youngLeaderSubCount;
	/**
	 * ռ��������
	 */
	private String youngLeaderSubPercent;
	/**
	 * 35��������50��
	 */
	private String middleLeaderSubCount;
	/**
	 * ռ�������ȵ�
	 */
	private String middleLeaderSubPercent;
	/**
	 * 50������������
	 */
	private String oldLeaderSubCount;
	/**
	 * ռ�������ȵ�
	 */
	private String oldLeaderSubPercent;
	/**
	 * ������
	 */
	private String subLeaderMaxAge;
	/**
	 * ��С
	 */
	private String subLeaderMinAge;
	/**
	 * ƽ������
	 */
	private String subLeaderAvgAge;

	/**
	 * �־��쵼���ӳ�Ա��35������
	 */
	private String youngLeaderFjCount;
	/**
	 * ռ��������
	 */
	private String youngLeaderFjPercent;
	/**
	 * 35��������50��
	 */
	private String middleLeaderFjCount;
	/**
	 * ռ�������ȵ�
	 */
	private String middleLeaderFjPercent;
	/**
	 * 50������������
	 */
	private String oldLeaderFjCount;
	/**
	 * ռ�������ȵ�
	 */
	private String oldLeaderFjPercent;
	/**
	 * ������
	 */
	private String fjLeaderMaxAge;
	/**
	 * ��С
	 */
	private String fjLeaderMinAge;
	/**
	 * ƽ������
	 */
	private String fjLeaderAvgAge;


	/**
	 * �о��� �����쵼���ӳ�Ա��35������
	 */
	private String youngLeaderBmCount;
	/**
	 * ռ��������
	 */
	private String youngLeaderBmPercent;
	/**
	 * 35��������50��
	 */
	private String middleLeaderBmCount;
	/**
	 * ռ�������ȵ�
	 */
	private String middleLeaderBmPercent;
	/**
	 * 50������������
	 */
	private String oldLeaderBmCount;
	/**
	 * ռ�������ȵ�
	 */
	private String oldLeaderBmPercent;
	/**
	 * ������
	 */
	private String bmLeaderMaxAge;
	/**
	 * ��С
	 */
	private String bmLeaderMinAge;

	/**
	 * ƽ������
	 */
	private String bmLeaderAvgAge;


	/**
	 * �쵼���ӳ�Ա������
	 */
	private String leaderMaleCount;
	/**
	 * Ů��
	 */
	private String leaderFemaleCount;
	/**
	 * ��Ů����Ϊ
	 */
	private String leaderMfPercent;

	/**
	 * ��ǰ��λ��������
	 */
	private String leaderMaleSuperCount;
	/**
	 * ��ǰ��λŮ��
	 */
	private String leaderFemaleSuperCount;
	/**
	 * ��ǰ��λ��Ů����Ϊ
	 */
	private String leaderMfSuperPercent;

	/**
	 * ��ǰ��λ������������
	 */
	private String leaderMaleSubCount;
	/**
	 * ��ǰ��λ����Ů��
	 */
	private String leaderFemaleSubCount;
	/**
	 * ��ǰ��λ������Ů����Ϊ
	 */
	private String leaderMfSubPercent;

	/**
	 * ���з־�������
	 */
	private String leaderMaleFjCount;
	/**
	 * Ů��
	 */
	private String leaderFemaleFjCount;
	/**
	 * ��Ů����Ϊ
	 */
	private String leaderMfFjPercent;
	/**
	 * �о��ڲ���������
	 */
	private String leaderMaleBmCount;
	/**
	 * Ů��
	 */
	private String leaderFemaleBmCount;
	/**
	 * ��Ů����Ϊ
	 */
	private String leaderMfBmPercent;


	/**
	 * �쵼���ӳ�Ա����Ҫ��
	 */
	private String leaderMainEduName;
	/**
	 * ռ����Ա��
	 */
	private String leaderMainEduPercent;

	/**
	 * ��ǰ��λ�쵼���ӳ�Ա����Ҫ��
	 */
	private String superLeaderMainEduName;
	/**
	 * ռ����Ա��
	 */
	private String superLeaderMainEduPercent;

	/**
	 * ��ǰ��λ���赥λ�쵼���ӳ�Ա����Ҫ��
	 */
	private String subLeaderMainEduName;
	/**
	 * ռ����Ա��
	 */
	private String subLeaderMainEduPercent;


	/**
	 * ���з־��쵼���ӳ�Ա����Ҫ��
	 */
	private String fjLeaderMainEduName;
	/**
	 * ռ����Ա��
	 */
	private String fjLeaderMainEduPercent;
	/**
	 * �о��������������Ҫ��
	 */
	private String bmLeaderMainEduName;
	/**
	 * ռ����Ա��
	 */
	private String bmLeaderMainEduPercent;


	/**
	 * ��ǰ��λ�쵼���ӳ�Ա�뾯��������
	 */
	private String superEntryPeriodContent;
	/**
	 * ��ǰ��λ���赥λ�쵼���ӳ�Ա�뾯��������
	 */
	private String subEntryPeriodContent;

	/**
	 * �쵼���ӳ�Ա���뾯������5�꣨�������µ�
	 */
	private String leaderSize5;
	/**
	 * ռ��������
	 */
	private String leaderSize5Percent;
	/**
	 * 5-10�꣨������
	 */
	private String leaderSize5t10;
	/**
	 * ռ��������
	 */
	private String leaderSize5t10Percent;
	/**
	 * 10-15�꣨������
	 */
	private String leaderSize10t15;
	/**
	 * ռ��������
	 */
	private String leaderSize10t15Percent;
	/**
	 * 15-20�꣨������
	 */
	private String leaderSize15t20;
	/**
	 * ռ��������
	 */
	private String leaderSize15t20Percent;
	/**
	 * 20-25�꣨������
	 */
	private String leaderSize20t25;
	/**
	 * ռ��������
	 */
	private String leaderSize20t25Percent;
	/**
	 * 25-30�꣨������
	 */
	private String leaderSize25t30;
	/**
	 * ռ��������
	 */
	private String leaderSize25t30Percent;
	/**
	 * 30�꼰���ϵ�
	 */
	private String leaderSize30;
	/**
	 * ռ��������
	 */
	private String leaderSize30Percent;


	/**
	 * �쵼���ӳ�Ա����Ҫ�� ��Ϊ��
	 */
	private String LeaderMainNationName;
	/**
	 * ռ����Ա��
	 */
	private String LeaderMainNationPercent;
	/**
	 * ��ǰ��λ�쵼���ӳ�Ա����Ҫ��
	 */
	private String LeaderMainNationNameSuper;
	/**
	 * ռ����Ա��
	 */
	private String LeaderMainNationPercentSuper;
	/**
	 * ��ǰ��λ���赥λ�쵼���ӳ�Ա����Ҫ��
	 */
	private String LeaderMainNationNameSub;
	/**
	 * ռ����Ա��
	 */
	private String LeaderMainNationPercentSub;

	/**
	 * ���з־��쵼���ӳ�Ա����Ҫ��
	 */
	private String LeaderMainNationNameFj;
	/**
	 * ռ����Ա��
	 */
	private String LeaderMainNationPercentFj;
	/**
	 * �о��ڲ�������Ҫ��
	 */
	private String LeaderMainNationNameBm;
	/**
	 * ռ����Ա��
	 */
	private String LeaderMainNationPercentBm;


	/**
	 *��һ�� �쵼����ְλ����ͼƬ����״ͼ��
	 */

	private PictureRenderData superLeaderDuty;
	private PictureRenderData subLeaderDuty;
	/**
	 *�ڶ��� ����ṹͼƬ����ͼ��
	 */
	private PictureRenderData leaderPieAge;
	/**
	 *������ ����ṹͼƬ����״ͼ��
	 */
	private PictureRenderData leaderColumnAge;
	/**
	 *���ĸ� ��Ů����ͼƬ����ͼ��
	 */
	private PictureRenderData leaderSex;

	private PictureRenderData leaderColumnSex;

	/**
	 *����� �Ļ��̶ȣ���״ͼ��
	 */
	private PictureRenderData superLeaderEducation;
	private PictureRenderData subLeaderEducation;
	/**
	 *������ ����ֲ�����״ͼ��
	 */
	private PictureRenderData superLeaderNation;
	private PictureRenderData subLeaderNation;
	/**
	 *���߸� ��ְʱ�䣨��״ͼ��
	 */
	private PictureRenderData leaderWorkPSB;
	/**
	 *�ڰ˸� �뾯���ޣ���ͼ��
	 */
	private PictureRenderData superLeaderEntryPeriod;
	private PictureRenderData subLeaderEntryPeriod;

	/**
	 * ��ְ������������
	 */
	private String mainPostNum;
	/**
	 * ��ְ��������������
	 */
	private String mainPostNumOppositie;
	/**
	 * ��ְ������������
	 */
	private String assistantPostNum;
	/**
	 * ��ְ��������������
	 */
	private String assistantPostNumOppositie;

	// ��ְ�쵼ʱ�����
	private PictureRenderData leaderMainPost;

	//��ְ�쵼ʱ�����
	private PictureRenderData leaderAssistantPost;

	private String mydContent;

	private List<RowRenderData> mydTable;


	/****************** �쵼����Υ�Ͱ���  ******************/
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


	/*** ����Ա  */
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
