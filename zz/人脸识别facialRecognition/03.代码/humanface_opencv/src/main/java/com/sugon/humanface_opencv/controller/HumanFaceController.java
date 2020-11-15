package com.sugon.humanface_opencv.controller;

import com.sugon.humanface_opencv.domain.entity.THumanInfo;
import com.sugon.humanface_opencv.domain.entity.THumanTrajectory;
import com.sugon.humanface_opencv.service.HumanFaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 17:48 2018/11/7
 */
@RestController
@RequestMapping("/humanFace")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HumanFaceController {

    private final HumanFaceService humanFaceService;
    /*
    @Autowired
    private HumanFaceService humanFaceService;
    ||
    private final HumanFaceService humanFaceService;
    @Autowired
    public HumanFaceController(HumanFaceService humanFaceService) {
        this.HumanFaceService = humanFaceService;
    }
    ||
    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    public class HumanFaceController {

    private final HumanFaceService humanFaceService;
    ...
    自动装配的简化写法
     */

    @RequestMapping("/saveFaceWithImage")
    public boolean saveFaceWithImage(@RequestParam("imagebytes")byte[] imagebytes ,
                                     @RequestParam("idNum")String idNum) {
        return humanFaceService.saveFaceWithImage(imagebytes,idNum);
    }

    @RequestMapping("/searchIdNumByFace")
    public Map<String,String> searchIdNumByFace(@RequestParam("imagebyte")byte[] imagebytes) {
        return humanFaceService.searchIdNumByFace(imagebytes);

    }

    @RequestMapping("/getFaceByIdNum")
    public byte[] getFaceByIdNum(@RequestParam("idNum")String idNum) {
        return humanFaceService.getFaceByIdNum(idNum);
    }

    @RequestMapping("/getHumanInfoByIdNum")
    public THumanInfo getHumanInfoByIdNum(@RequestParam("idNum")String idNum) {
        return humanFaceService.getHumanInfoByIdNum(idNum);
    }

    @RequestMapping("/getHumanTrajectoryByIdNum")
    public List<THumanTrajectory> getHumanTrajectoryByIdNum(@RequestParam(value="idNum") String idNum,
                                                            @RequestParam(value="startTime",required = false) String startTime,
                                                            @RequestParam(value="endTime",required = false) String endTime) {
        return humanFaceService.getHumanTrajectoryByIdNum(idNum,startTime,endTime);
    }
}
