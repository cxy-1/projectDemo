package com.sugon.analysis.repository.oracle;

import com.sugon.analysis.domain.data.object.oracle.TPersonLabelDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/5
 */
public interface TPersonLabelDao extends JpaRepository<TPersonLabelDo, Long> {

    /**
     * 查询专业人才
     * @param idNo
     * @param delStatus
     * @param type
     * @return
     */
    List<TPersonLabelDo> findByIdNoAndDelStatusAndLabelType(String idNo, int delStatus,String type);
}
