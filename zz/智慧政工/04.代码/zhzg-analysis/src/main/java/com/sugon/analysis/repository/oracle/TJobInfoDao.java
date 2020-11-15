package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.TJobInfoDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/6
 */
public interface TJobInfoDao extends JpaRepository<TJobInfoDo, Long> {
    /**
     * 查询职务变动
     * @param idNo
     * @param delStatus
     * @return
     */
    @Query(value = "select * from NJGAZNL.t_job_info t where idno = ?1 and delstatus = ?2 order by TO_NUMBER(SUBSTR(DATA_SOURCE_ID,12,13)) desc ", nativeQuery = true)
    List<TJobInfoDo> findAllByIdNoAndDelStatusOrderByDataSourceId(String idNo,int delStatus);
}
