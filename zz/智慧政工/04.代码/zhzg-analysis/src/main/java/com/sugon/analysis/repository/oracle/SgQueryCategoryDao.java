package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.SgQueryCategoryDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: zhzg-statistics->SgQueryCategoryDao
 * @description: �ֵ�Dao��ӿ�
 * @author: gaowh
 * @create: 2019-09-17 14:32
 **/

public interface SgQueryCategoryDao extends JpaRepository<SgQueryCategoryDo, Long> {
    /**
     * �������Ͳ�ѯ
     * @param cvType
     * @return
     */
    List<SgQueryCategoryDo> findAllByCvTypeOrderByCvFlagDesc(String cvType);

    /**
     * �����ֵ�������Ͳ�ѯ�ֵ�ֵ
     * @param cvType
     * @param cvFlag
     * @return
     */
    SgQueryCategoryDo findByCvTypeAndCvFlag(String cvType, String cvFlag);

    /**
     * �������Ʋ�ѯ
     * @param cvType
     * @param name
     * @return
     */
    SgQueryCategoryDo findByCvTypeAndCvName(String cvType, String name);

    /**
     * �������Ʋ�ѯ
     * @param cvType
     * @param name
     * @return
     */
    List<SgQueryCategoryDo> findByCvTypeAndCvNameIn(String cvType, List<String> name);

    /**
     * ��ѯ���еı��ƴ���
     * @param cvType
     * @return
     */
    List<SgQueryCategoryDo> findAllByCvTypeAndCvFlagLike(String cvType,String cvflag);

    /**
     * ��ѯ��������
     * @return
     */
    @Query(value = "select * from NJGAZNL.SG_QUERY_CATEGORY t where cvtype = 'AM' and substr(cvflag,0,1) >= '4'", nativeQuery = true)
    List<SgQueryCategoryDo> findAllUnderHigh();


    List<SgQueryCategoryDo> findByCvType(String cvType);
}
