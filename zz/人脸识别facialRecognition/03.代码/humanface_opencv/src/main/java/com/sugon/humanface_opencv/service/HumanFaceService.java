package com.sugon.humanface_opencv.service;

import com.sugon.humanface_opencv.domain.entity.THumanInfo;
import com.sugon.humanface_opencv.domain.entity.THumanTrajectory;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 18:13 2018/11/7
 */
public interface HumanFaceService {

    boolean saveFaceWithImage(byte[] imagebytes,String idNum);

    Map<String,String> searchIdNumByFace(byte[] imagebytes);

    byte[] getFaceByIdNum(String idNum);

    THumanInfo getHumanInfoByIdNum(String idNum);

    List<THumanTrajectory> getHumanTrajectoryByIdNum(String idNum,String startTime,String endTime);
}
