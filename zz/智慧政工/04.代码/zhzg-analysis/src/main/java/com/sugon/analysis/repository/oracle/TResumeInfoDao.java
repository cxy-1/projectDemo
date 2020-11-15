package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.TResumeInfoDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/11/28
 */
public interface TResumeInfoDao extends JpaRepository<TResumeInfoDo, Long> {
    /**
     * 根据身份证号查询
     * @param idNo
     * @param delStatus
     * @return
     */
    List<TResumeInfoDo> findAllByIdNoAndDelStatus(String idNo, int delStatus);

    /**
     * 根据身份证号和时间查询
     * @param idNo
     * @param delStatus
     * @param startingDate
     * @return
     */
    @Query(value = "select org_name from NJGAZNL.T_RESUME_INFO t where idno = ?1 and delstatus = ?2 and starting_date > to_date(?3,'YYYY/MM/DD')  group by org_name", nativeQuery = true)
    List<String> findAllByIdNoAndDelStatusAndStartingDateAfter(String idNo, int delStatus, String startingDate);

    /**
     *  根据部门名称查询
     * @param orgName
     * @param delStatus
     * @param idNo
     * @return
     */
    @Query(value = "SELECT * FROM NJGAZNL.T_RESUME_INFO t WHERE org_name = ?1 AND delstatus = ?2  AND starting_date is not null  and idNo = ?3 order by TO_NUMBER(SUBSTR(DATA_SOURCE_ID,12,13))", nativeQuery = true)
    List<TResumeInfoDo> findByOrgNameAndDelStatusAndIdNoOrderByStartingDateAsc(String orgName, int delStatus, String idNo);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    TResumeInfoDo findById(String id);

    /**
     * 根据身份证号和时间查询
     *
     * @param idNo
     * @param delStatus
     * @param idNo
     * @return
     */
    @Query(value = " SELECT * FROM NJGAZNL.T_RESUME_INFO t WHERE idno = ?1 AND delstatus = ?2 AND starting_date >= to_date(?3,'YYYYMMDD') AND starting_date is not null ORDER BY  TO_NUMBER(SUBSTR(DATA_SOURCE_ID,12,13))", nativeQuery = true)
    List<TResumeInfoDo> findAllByIdNoAndDelStatus(String idNo, int delStatus, String dateTime);
}
