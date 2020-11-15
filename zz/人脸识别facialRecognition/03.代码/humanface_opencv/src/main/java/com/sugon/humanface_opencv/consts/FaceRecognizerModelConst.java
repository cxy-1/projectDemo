package com.sugon.humanface_opencv.consts;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 14:33 2018/11/15
 */
public class FaceRecognizerModelConst {

    public interface ModelTrainFlag {

        Integer FACE_MODEL_UNTRAINED = -1;//未训练状态，如新增人脸、项目刚启动
        Integer FACE_MODEL_TRAINING = 0;//正在训练，训练方法执行中
        Integer FACE_MODEL_TRAINED = 1;//训练完成

    }

    public interface FaceAlgorithm {

        Integer EIGEN_FACE_ALGORITHM = 1;
        Integer FISHER_FACE_ALGORITHM = 2;
        Integer LBPH_FACE_ALGORITHM = 3;

    }
}
