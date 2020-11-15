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
	 * 教育培训按年份区间统计次数
	 * @param idNoList
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findTrainCountByYear(@Param("idNoList")List<String> idNoList, @Param("teamSelect")TeamSelect teamSelect);
	/**
	 * 教育培训按年份区间统计次数 详情
	 * @param idNoList
	 * @param
	 * @return
	 */
	List<SelectDetailTrainVo>findTrainDetailByYear(@Param("idNoList")List<String> idNoList, @Param("detailParamPojo") DetailParamPojo detailParamPojo);

	/**
	 * 按年份区间查询队伍集体荣誉数量
	 * @param name 队伍名称
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findOrgHonorCountByYear(@Param("name")String name, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * 按年份区间查询队伍集体荣誉数量 详情
	 * @param name 队伍名称
	 * @param
	 * @return
	 */
	List<SelectHonorDetailVo> findOrgHonorDetailByYear(@Param("name")String name, @Param("detailParamPojo")DetailParamPojo detailParamPojo);

	/**
	 * 按年份区间查询队伍个人荣誉数量
	 * @param idNoList
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo> findPersonHonorCountByIdNoAndYear(@Param("idNoList")List<String> idNoList, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * 按年份区间查询队伍个人荣誉数量 详情
	 * @param idNoList
	 * @param
	 * @return
	 */
	List<SelectPersonHonorDetailVo> findPersonHonorDetailByIdNoAndYear(@Param("idNoList")List<String> idNoList, @Param("detailParamPojo")DetailParamPojo detailParamPojo);

	/**
	 * 按年份区间查询队伍纪检案件数量
	 * @param name 队伍名称
	 * @param teamSelect
	 * @return
	 */
	List<TrainCountVo>  findBreakRuleCaseCountByNameAndYear(@Param("name")String name, @Param("teamSelect")TeamSelect teamSelect);

	/**
	 * 按年份区间查询队伍纪检案件数量详情
	 * @param name 队伍名称
	 * @param
	 * @return
	 */
	List<SelectBreakRuleDetailPojo>  findBreakRuleCaseCountByNameAndYearDetail(@Param("name")String name, @Param("detailParamPojo")DetailParamPojo detailParamPojo);
}
