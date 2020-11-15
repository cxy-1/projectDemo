package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.TCommendInfoDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author gaowh
 */
public interface TCommendInfoDao extends JpaRepository<TCommendInfoDo, Long> {
    /**
     * 查询三等功以上的奖励
     * @param rewardName
     * @param idNo
     * @return
     */
    @Query(value = "select * from NJGAZNL.T_COMMEND_INFO t where IDNO =?1  and (award_name in(?2) OR award_name LIKE '%一等功%' OR award_name LIKE '%二等功%' OR award_name LIKE '%三等功%')", nativeQuery = true)
    List<TCommendInfoDo> findAllByAwardNameAndIdno(String idNo, List<String> rewardName);

}
