package com.sugon.analysis.repository.sugon.oracle;

import com.sugon.analysis.domain.data.object.sugon.oracle.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: create by huangxb
 * @version: v1.0
 * @description:
 * @date:2019/12/24
 */
public interface UserLogDao  extends JpaRepository<UserLog, Long> ,JpaSpecificationExecutor<UserLog>{
}
