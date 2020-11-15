package com.sugon.analysis.platform.repository;

import com.sugon.analysis.platform.entity.BeTheBestInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @description: 勇争先数据维护持久层
 * @author: liuwei
 * @create: 2020-05-27 15:54
 **/
public interface BeTheBestInfoRepository extends JpaRepository<BeTheBestInfoEntity,String>,JpaSpecificationExecutor<BeTheBestInfoEntity> {
    BeTheBestInfoEntity findByIdNoAndDelstatusAndActive(String idNo, String delstatus,String active);
    List<BeTheBestInfoEntity> findByIdNoInAndDelstatusAndActive(List<String> idNos, String delstatus,String active);
}
