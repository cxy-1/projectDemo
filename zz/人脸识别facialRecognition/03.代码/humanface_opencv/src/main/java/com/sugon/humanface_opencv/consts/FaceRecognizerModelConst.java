package com.sugon.humanface_opencv.consts;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 14:33 2018/11/15
 */
public class FaceRecognizerModelConst {

    public interface ModelTrainFlag {

        Integer FACE_MODEL_UNTRAINED = -1;//δѵ��״̬����������������Ŀ������
        Integer FACE_MODEL_TRAINING = 0;//����ѵ����ѵ������ִ����
        Integer FACE_MODEL_TRAINED = 1;//ѵ�����

    }

    public interface FaceAlgorithm {

        Integer EIGEN_FACE_ALGORITHM = 1;
        Integer FISHER_FACE_ALGORITHM = 2;
        Integer LBPH_FACE_ALGORITHM = 3;

    }
}
