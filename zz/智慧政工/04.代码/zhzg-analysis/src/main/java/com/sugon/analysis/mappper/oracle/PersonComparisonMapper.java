package com.sugon.analysis.mappper.oracle;

import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.param.PersonAndType;
import com.sugon.analysis.domain.view.persondraw.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: analysis->PersonComparisonMapper
 * @description:
 * @author: gaowh
 * @create: 2019-11-28 17:39
 **/
@Mapper
public interface PersonComparisonMapper {
    /**
     * 查询符合条件的人员信息
     * @param tPersonDo
     * @param personAndType
     * @return List<TPersonDo>
     */
    List<TPersonDo> findPersonByType(@Param("tPersonDo")TPersonDo tPersonDo,
                                     @Param("personAndType") PersonAndType personAndType);

    /**
     * 查询符合条件的人员的身份证号
     * @param tPersonDo
     * @param personAndType
     * @return List<String>
     */
    List<String> findIdNoByType(@Param("tPersonDo")TPersonDo tPersonDo,
                                @Param("personAndType") PersonAndType personAndType);

    /**
     * 根据人员的身份证号查询表彰奖励信息
     * @param idNoList
     * @param personAndType
     * @return List<HonorComparisonVo>
     */
    List<HonorComparisonVo> findHonorByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);

    /**
     * 根据人员的身份证号查询民意满意度信息
     * @param idNoList
     * @param personAndType
     * @return List<SatisfiedComparisonVo>
     */
    List<SatisfiedComparisonVo> findSatisfiedByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);

    /**
     * 根据人员的身份证号查询警衔信息
     * @param idNoList
     * @param personAndType
     * @return List<RankComparisonVo>
     */
    List<RankComparisonVo> findRankByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType")PersonAndType personAndType);

    /**
     * 根据人员的身份证号查询职级信息
     * @param personIdList
     * @param personAndType
     * @return List<JobLevelComparisonVo>
     */
    List<JobLevelComparisonVo> findJobLevelByPersonId(@Param("personIdList")List<String> personIdList, @Param("personAndType") PersonAndType personAndType);

    /**
     * 根据人员的身份证号查询职务信息
     * @param idNoList
     * @param personAndType
     * @return List<PostComparisonVo>
     */
    List<PostComparisonVo> findPostByPersonId(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);
}
