package com.sugon.humanface_opencv.cvmodels;

import com.sugon.humanface_opencv.domain.entity.THumanFace;
import com.sugon.humanface_opencv.domain.repository.THumanFaceRepository;
import com.sugon.humanface_opencv.utils.ConvertUtil;
import com.sugon.humanface_opencv.utils.ImageUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.utils.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.sugon.humanface_opencv.consts.FaceRecognizerModelConst.FaceAlgorithm.*;
import static com.sugon.humanface_opencv.consts.FaceRecognizerModelConst.ModelTrainFlag.*;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 10:51 2018/11/10
 */
@Component
public class FaceRecognizerModel {

    @Autowired
    private THumanFaceRepository tHumanFaceRepository;

    private static FaceRecognizerModel faceRecognizerModel;

    public static FaceRecognizer model;

    public static Integer flag;//见ModelConst.ModelTrainFlag

    @PostConstruct
    public void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceRecognizerModel = this;
        //模型未训练
        flag = FACE_MODEL_UNTRAINED;
        faceRecognizerModel.tHumanFaceRepository = this.tHumanFaceRepository;

        /**
         * 进行人脸识别模型训练
         * 目前仅建议使用LBPH，原因是EigenFace和FisherFace的训练图像和测试图像都必须是灰度图，而且是经过归一化裁剪过的，
         * 而LBPH仅需要灰度图，对尺寸归一化无严格要求。
         */
        try {
            FaceRecognizerModel.TrainModel(LBPH_FACE_ALGORITHM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void TrainModel(Integer faceAlgorithmSelect) throws Exception {

        //模型开始训练
        flag = FACE_MODEL_TRAINING;

        /**
         * 选择算法进行人脸识别模型训练
         * 目前仅建议使用LBPH，原因是EigenFace和FisherFace的训练图像和测试图像都必须是灰度图，而且是经过归一化裁剪过的，
         * 而LBPH仅需要灰度图，对尺寸归一化无严格要求。
         */
        if (faceAlgorithmSelect == EIGEN_FACE_ALGORITHM) {
            model = EigenFaceRecognizer.create();
        } else if (faceAlgorithmSelect == FISHER_FACE_ALGORITHM) {
            model = FisherFaceRecognizer.create();
        } else if (faceAlgorithmSelect == LBPH_FACE_ALGORITHM) {
            model = LBPHFaceRecognizer.create(1, 8, 8, 8, 123.0);
        } else {
            throw new Exception("Face algorithm select error." +
                    " The currently available algorithms are: 1-Eigen 2-Fisher 3-LBPH");
        }

        //获取库里人脸数据
        List<THumanFace> tHumanFaceList = faceRecognizerModel.tHumanFaceRepository.findAll();

        List<Mat> facesList = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        for (int i = 0; i < tHumanFaceList.size(); i++) {
            THumanFace obj = tHumanFaceList.get(i);
            facesList.add(ImageUtil.imageGrayEqualizeHist(ImageUtil.getFace(ConvertUtil.ByteToMat(obj.getFaceImage()))));
            labels.add(obj.getLabel());
        }

        Mat labelsMat = Converters.vector_int_to_Mat(labels);

        //模型训练
        model.train(facesList, labelsMat);

        //模型训练完成
        flag = FACE_MODEL_TRAINED;

    }

}
