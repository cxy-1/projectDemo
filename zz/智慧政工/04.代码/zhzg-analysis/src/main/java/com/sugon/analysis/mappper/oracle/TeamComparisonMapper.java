package com.sugon.analysis.mappper.oracle;

import com.sugon.analysis.domain.param.DetailParamPojo;
import com.sugon.analysis.domain.view.teambuild.*;
import com.sugon.analysis.domain.param.TeamSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: analysis->TeamComparisonMapper
 * @description:
 * @author: gaowh
 * @create: 2019-12-23 14:47
 **/
@Mapper
public interface TeamComparisonMapper {
	/**
	 * ������ѵ���������ͳ�ƴ���
	 * @param idNoList
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findTrainCountByYear(@Param("idNoList")List<String> idNoList, @Param("teamSelect")TeamSelect teamSelect);
	/**
	 * ������ѵ���������ͳ�ƴ��� ����
	 * @param idNoList
	 * @param
	 * @return
	 */
	List<SelectDetailTrainVo>findTrainDetailByYear(@Param("idNoList")List<String> idNoList, @Param("detailParamPojo") DetailParamPojo detailParamPojo);

	/**
	 * ����������ѯ���鼯����������
	 * @param name ��������
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findOrgHonorCountByYear(@Param("name")String name, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * ����������ѯ���鼯���������� ����
	 * @param name ��������
	 * @param
	 * @return
	 */
	List<SelectHonorDetailVo> findOrgHonorDetailByYear(@Param("name")String name, @Param("detailParamPojo")DetailParamPojo detailParamPojo);

	/**
	 * ����������ѯ���������������
	 * @param idNoList
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findPersonHonorCountByIdNoAndYear(@Param("idNoList")List<String> idNoList, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * ����������ѯ��������������� ����
	 * @param idNoList
	 * @param
	 * @return
	 */
	List<SelectPersonHonorDetailVo> findPersonHonorDetailByIdNoAndYear(@Param("idNoList")List<String> idNoList, @Param("detailParamPojo")DetailParamPojo detailParamPojo);

	/**
	 * ����������ѯ����ͼ참������
	 * @param name ��������
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo>  findBreakRuleCaseCountByNameAndYear(@Param("name")String name, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * ����������ѯ����ͼ참����������
	 * @param name ��������
	 * @param
	 * @return
	 */
	List<SelectBreakRuleDetailPojo>  findBreakRuleCaseCountByNameAndYearDetail(@Param("name")String name, @Param("detailParamPojo")DetailParamPojo detailParamPojo);
}
