package com.sugon.humanface_opencv.service.impl;

import com.sugon.humanface_opencv.cvmodels.FaceRecognizerModel;
import com.sugon.humanface_opencv.domain.entity.THumanFace;
import com.sugon.humanface_opencv.domain.entity.THumanInfo;
import com.sugon.humanface_opencv.domain.entity.THumanTrajectory;
import com.sugon.humanface_opencv.domain.repository.TFaceCompareLogRepository;
import com.sugon.humanface_opencv.domain.repository.THumanFaceRepository;
import com.sugon.humanface_opencv.domain.repository.THumanInfoRepository;
import com.sugon.humanface_opencv.domain.repository.THumanTrajectoryRepository;
import com.sugon.humanface_opencv.service.HumanFaceService;
import com.sugon.humanface_opencv.utils.ConvertUtil;
import com.sugon.humanface_opencv.utils.ImageUtil;
import com.sugon.humanface_opencv.utils.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sugon.humanface_opencv.consts.FaceRecognizerModelConst.FaceAlgorithm.LBPH_FACE_ALGORITHM;
import static com.sugon.humanface_opencv.consts.FaceRecognizerModelConst.ModelTrainFlag.FACE_MODEL_TRAINING;
import static com.sugon.humanface_opencv.consts.FaceRecognizerModelConst.ModelTrainFlag.FACE_MODEL_UNTRAINED;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 18:14 2018/11/7
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HumanFaceServiceImpl implements HumanFaceService {

    private final TFaceCompareLogRepository tFaceCompareLogRepository;

    private final THumanFaceRepository tHumanFaceRepository;

    private final THumanInfoRepository tHumanInfoRepository;

    private final THumanTrajectoryRepository tHumanTrajectoryRepository;

    @Override
    public boolean saveFaceWithImage(byte[] imagebytes, String idNum) {

        //�ж�ͼƬ�Ƿ�����ҽ�����һ������
        Integer facesconut = ImageUtil.getFacesCount(ConvertUtil.ByteToMat(imagebytes));
        if ( facesconut != 1) {
            return false;//δʶ����������ʶ�𵽶�������
        }

        THumanFace tHumanFace = new THumanFace();

        //�жϸ����Ƿ����
        List<THumanFace> ifexists = tHumanFaceRepository.findByIdNum(idNum);
        if (ifexists.size() == 0) {

            Integer maxLable = tHumanFaceRepository.getMaxLabel();

            if (maxLable == null) {
                tHumanFace.setLabel(1);
            } else {
                tHumanFace.setLabel(maxLable + 1);
            }

        } else {
            tHumanFace.setLabel(ifexists.get(0).getLabel());
        }

        tHumanFace.setId(KeyUtil.genUniqueKey());
        tHumanFace.setIdNum(idNum);
        tHumanFace.setFaceImage(imagebytes);

        tHumanFaceRepository.save(tHumanFace);

        //�����������ݣ�������ģ�ʹ���δѵ����
        FaceRecognizerModel.flag = FACE_MODEL_UNTRAINED;

        return true;
    }

    @Override
    public Map<String,String> searchIdNumByFace(byte[] imagebytes) {

        //���������������������ѵ��ģ��
        if (FaceRecognizerModel.flag == FACE_MODEL_UNTRAINED) {
            try {
                FaceRecognizerModel.TrainModel(LBPH_FACE_ALGORITHM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //���ģ������ѵ�����ȴ�ѵ�����
        while (FaceRecognizerModel.flag == FACE_MODEL_TRAINING) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] label = {0};
        double[] confidence = {0.0};
        FaceRecognizerModel.model.predict(ImageUtil.imageGrayEqualizeHist(ImageUtil.getFace(ConvertUtil.ByteToMat(imagebytes))), label, confidence);

        Map<String,String> resMap = new HashMap();
        resMap.put("label",label[0]+"");
        resMap.put("confidence",confidence[0]+"");
        return resMap;

//        int predictId = FaceRecognizerModel.model.predict_label(ImageUtil.imageGrayEqualizeHist(ImageUtil.getFace(ConvertUtil.ByteToMat(imagebytes))));

//        return tHumanFaceRepository.findByLabel(predictId).get(0).getIdNum();

    }

    @Override
    public byte[] getFaceByIdNum(String idNum) {

        return tHumanFaceRepository.findByIdNum(idNum).get(0).getFaceImage();

    }

    @Override
    public THumanInfo getHumanInfoByIdNum(String idNum) {

        return tHumanInfoRepository.findByIdNum(idNum);

    }

    /**
     * �������֤�����ȡ��Ա�켣
     *
     * @param idNum
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<THumanTrajectory> getHumanTrajectoryByIdNum(String idNum, String startTime, String endTime) {

        if (startTime != null && endTime != null) {
            return tHumanTrajectoryRepository.findByIdNumAndTimeGreaterThanEqualAndTimeLessThanEqual(idNum, startTime, endTime);
        } else if (startTime == null && endTime != null) {
            return tHumanTrajectoryRepository.findByIdNumAndTimeLessThanEqual(idNum, endTime);
        } else if (startTime != null) {
            return tHumanTrajectoryRepository.findByIdNumAndTimeGreaterThanEqual(idNum, startTime);
        } else {
            return tHumanTrajectoryRepository.findByIdNum(idNum);
        }

    }
}
