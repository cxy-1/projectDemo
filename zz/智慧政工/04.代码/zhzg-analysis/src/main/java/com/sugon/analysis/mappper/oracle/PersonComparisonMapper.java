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
     * ��ѯ������������Ա��Ϣ
     * @param tPersonDo
     * @param personAndType
     * @return List<TPersonDo>
     */
    List<TPersonDo> findPersonByType(@Param("tPersonDo")TPersonDo tPersonDo,
                                     @Param("personAndType") PersonAndType personAndType);

    /**
     * ��ѯ������������Ա�����֤��
     * @param tPersonDo
     * @param personAndType
     * @return List<String>
     */
    List<String> findIdNoByType(@Param("tPersonDo")TPersonDo tPersonDo,
                                @Param("personAndType") PersonAndType personAndType);

    /**
     * ������Ա�����֤�Ų�ѯ���ý�����Ϣ
     * @param idNoList
     * @param personAndType
     * @return List<HonorComparisonVo>
     */
    List<HonorComparisonVo> findHonorByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);

    /**
     * ������Ա�����֤�Ų�ѯ�����������Ϣ
     * @param idNoList
     * @param personAndType
     * @return List<SatisfiedComparisonVo>
     */
    List<SatisfiedComparisonVo> findSatisfiedByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);

    /**
     * ������Ա�����֤�Ų�ѯ������Ϣ
     * @param idNoList
     * @param personAndType
     * @return List<RankComparisonVo>
     */
    List<RankComparisonVo> findRankByIdNo(@Param("idNoList")List<String> idNoList, @Param("personAndType")PersonAndType personAndType);

    /**
     * ������Ա�����֤�Ų�ѯְ����Ϣ
     * @param personIdList
     * @param personAndType
     * @return List<JobLevelComparisonVo>
     */
    List<JobLevelComparisonVo> findJobLevelByPersonId(@Param("personIdList")List<String> personIdList, @Param("personAndType") PersonAndType personAndType);

    /**
     * ������Ա�����֤�Ų�ѯְ����Ϣ
     * @param idNoList
     * @param personAndType
     * @return List<PostComparisonVo>
     */
    List<PostComparisonVo> findPostByPersonId(@Param("idNoList")List<String> idNoList, @Param("personAndType") PersonAndType personAndType);
}
