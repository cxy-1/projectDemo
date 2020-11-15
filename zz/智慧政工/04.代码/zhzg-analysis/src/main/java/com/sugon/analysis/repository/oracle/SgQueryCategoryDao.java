package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.SgQueryCategoryDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: zhzg-statistics->SgQueryCategoryDao
 * @description: 字典Dao层接口
 * @author: gaowh
 * @create: 2019-09-17 14:32
 **/

public interface SgQueryCategoryDao extends JpaRepository<SgQueryCategoryDo, Long> {
    /**
     * 根据类型查询
     * @param cvType
     * @return
     */
    List<SgQueryCategoryDo> findAllByCvTypeOrderByCvFlagDesc(String cvType);

    /**
     * 根据字典项和类型查询字典值
     * @param cvType
     * @param cvFlag
     * @return
     */
    SgQueryCategoryDo findByCvTypeAndCvFlag(String cvType, String cvFlag);

    /**
     * 根据名称查询
     * @param cvType
     * @param name
     * @return
     */
    SgQueryCategoryDo findByCvTypeAndCvName(String cvType, String name);

    /**
     * 根据名称查询
     * @param cvType
     * @param name
     * @return
     */
    List<SgQueryCategoryDo> findByCvTypeAndCvNameIn(String cvType, List<String> name);

    /**
     * 查询所有的本科代码
     * @param cvType
     * @return
     */
    List<SgQueryCategoryDo> findAllByCvTypeAndCvFlagLike(String cvType,String cvflag);

    /**
     * 查询高中以下
     * @return
     */
    @Query(value = "select * from NJGAZNL.SG_QUERY_CATEGORY t where cvtype = 'AM' and substr(cvflag,0,1) >= '4'", nativeQuery = true)
    List<SgQueryCategoryDo> findAllUnderHigh();


    List<SgQueryCategoryDo> findByCvType(String cvType);
}
