package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.MPersonOpinionDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/5
 */
public interface MpersonOpinionDao extends JpaRepository<MPersonOpinionDo, Long> {
    /**
     * 根据身份证号和年份查询
     * @param idNo
     * @param month
     * @return
     */
    @Query(value = "select nvl(sum(jcj),0) from NJGAZNL.M_PERSON_OPINION t WHERE sfzh = ?1 and MONTH LIKE ?2", nativeQuery = true)
    int findJcjByIdNoAndMonthLike(String idNo,String month);

    /**
     * 根据身份证号统计案件
     * @param idNo
     * @param month
     * @return
     */
    @Query(value = "select nvl(sum(aj),0) from NJGAZNL.M_PERSON_OPINION t WHERE sfzh = ?1 and MONTH LIKE ?2", nativeQuery = true)
    int findAjByIdNoAndMonthLike(String idNo,String month);

    /**
     * 查询所有的接处警
     * @param month
     * @return
     */
    @Query(value = "select nvl(sum(jcj),0) from NJGAZNL.M_PERSON_OPINION t WHERE MONTH LIKE ?1", nativeQuery = true)
    int findAllJcjByIdNoAndMonthLike(String month);

    /**
     * 查询所有的案件
     * @param month
     * @return
     */
    @Query(value = "select nvl(sum(aj),0) from NJGAZNL.M_PERSON_OPINION t WHERE MONTH LIKE ?1", nativeQuery = true)
    int findAllAjByIdNoAndMonthLike(String month);

    /**
     * 查询民意满意度
     * @param idNo
     * @param month
     * @return
     */
    @Query(value = "select nvl(avg(mymyd),0) from NJGAZNL.M_PERSON_OPINION t WHERE sfzh = ?1 and MONTH LIKE ?2", nativeQuery = true)
    Double findByMymyd(String idNo,String month);

    /**
     * 查询月份的名义满意度
     * @param idNo
     * @param month
     * @return
     */
    @Query(value = "select SFZH,substr(month,5,6) as MONTH,MYMYD from NJGAZNL.M_PERSON_OPINION t where sfzh = ?1 and month like ?2", nativeQuery = true)
    List<MPersonOpinionDo> findAllByIdNoAndMonthLike(String idNo ,String month);

}
