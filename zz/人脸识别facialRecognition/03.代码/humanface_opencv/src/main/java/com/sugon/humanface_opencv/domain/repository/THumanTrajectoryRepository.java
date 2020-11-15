package com.sugon.humanface_opencv.domain.repository;

import com.sugon.humanface_opencv.domain.entity.THumanTrajectory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 17:16 2018/11/7
 */
public interface THumanTrajectoryRepository extends JpaRepository<THumanTrajectory,String> {

    List<THumanTrajectory> findByIdNum(String idNum);

    List<THumanTrajectory> findByIdNumAndTimeGreaterThanEqual(String idNum,String startTime);

    List<THumanTrajectory> findByIdNumAndTimeLessThanEqual(String idNum,String endTime);

    List<THumanTrajectory> findByIdNumAndTimeGreaterThanEqualAndTimeLessThanEqual(String idNum,String startTime,String endTime);


}
