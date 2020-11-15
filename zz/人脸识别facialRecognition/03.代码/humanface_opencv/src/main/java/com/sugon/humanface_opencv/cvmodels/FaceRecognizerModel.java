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

    public static Integer flag;//��ModelConst.ModelTrainFlag

    @PostConstruct
    public void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceRecognizerModel = this;
        //ģ��δѵ��
        flag = FACE_MODEL_UNTRAINED;
        faceRecognizerModel.tHumanFaceRepository = this.tHumanFaceRepository;

        /**
         * ��������ʶ��ģ��ѵ��
         * Ŀǰ������ʹ��LBPH��ԭ����EigenFace��FisherFace��ѵ��ͼ��Ͳ���ͼ�񶼱����ǻҶ�ͼ�������Ǿ�����һ���ü����ģ�
         * ��LBPH����Ҫ�Ҷ�ͼ���Գߴ��һ�����ϸ�Ҫ��
         */
        try {
            FaceRecognizerModel.TrainModel(LBPH_FACE_ALGORITHM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void TrainModel(Integer faceAlgorithmSelect) throws Exception {

        //ģ�Ϳ�ʼѵ��
        flag = FACE_MODEL_TRAINING;

        /**
         * ѡ���㷨��������ʶ��ģ��ѵ��
         * Ŀǰ������ʹ��LBPH��ԭ����EigenFace��FisherFace��ѵ��ͼ��Ͳ���ͼ�񶼱����ǻҶ�ͼ�������Ǿ�����һ���ü����ģ�
         * ��LBPH����Ҫ�Ҷ�ͼ���Գߴ��һ�����ϸ�Ҫ��
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

        //��ȡ������������
        List<THumanFace> tHumanFaceList = faceRecognizerModel.tHumanFaceRepository.findAll();

        List<Mat> facesList = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        for (int i = 0; i < tHumanFaceList.size(); i++) {
            THumanFace obj = tHumanFaceList.get(i);
            facesList.add(ImageUtil.imageGrayEqualizeHist(ImageUtil.getFace(ConvertUtil.ByteToMat(obj.getFaceImage()))));
            labels.add(obj.getLabel());
        }

        Mat labelsMat = Converters.vector_int_to_Mat(labels);

        //ģ��ѵ��
        model.train(facesList, labelsMat);

        //ģ��ѵ�����
        flag = FACE_MODEL_TRAINED;

    }

}
