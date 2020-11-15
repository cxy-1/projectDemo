package com.sugon.analysis.platform.repository;

import com.sugon.analysis.platform.entity.BeTheBestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @description: 勇争先数据维护持久层
 * @author: liuwei
 * @create: 2020-05-27 15:54
 **/
public interface BeTheBestRepository extends JpaRepository<BeTheBestEntity,String>,JpaSpecificationExecutor<BeTheBestEntity> {
    BeTheBestEntity findByInfoIdAndTimeAndActive(String infoId, String time,String active);
}
