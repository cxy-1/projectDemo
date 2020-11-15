package com.sugon.humanface_opencv.utils;

import com.sugon.humanface_opencv.cvmodels.FaceDetectorModel;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SURF;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.opencv.imgproc.Imgproc.COLOR_BGRA2GRAY;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 13:43 2018/11/8
 */
public class ImageUtil {

    public static String getImageType(byte[] imageBytes) {

        String type = "";
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;

        try {
            bais = new ByteArrayInputStream(imageBytes);
            mcis = new MemoryCacheImageInputStream(bais);
            Iterator itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                String imageName = reader.getClass().getSimpleName();
                if (imageName != null) {
                    if ("GIFImageReader".equals(imageName)) {
                        type = "gif";
                    } else if ("JPEGImageReader".equals(imageName)) {
                        type = "jpg";
                    } else if ("PNGImageReader".equals(imageName)) {
                        type = "png";
                    } else if ("BMPImageReader".equals(imageName)) {
                        type = "bmp";
                    } else {
                        type = "noPic";
                    }
                }
            }
        } catch (Exception e) {
            type = "noPic";
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException ioe) {
                    type = "noPic";
                }
            }
            if (mcis != null) {
                try {
                    mcis.close();
                } catch (IOException ioe) {
                    type = "noPic";
                }
            }
        }
        return type;
    }

    public static Mat resizeTo(Mat src,int w,int h){

        Mat result = src.clone();

        Imgproc.resize(result,result,new Size(w,h));

        return result;
    }

    public static Mat getFace(Mat src) {

        MatOfRect objDetections = new MatOfRect();
        //人脸提取
        FaceDetectorModel.faceDetector.detectMultiScale(src, objDetections);

        Mat face = src.submat(objDetections.toArray()[0]);

        return face;
    }

    public static Integer getFacesCount(Mat src){

        MatOfRect objDetections = new MatOfRect();
        //人脸提取
        FaceDetectorModel.faceDetector.detectMultiScale(src, objDetections);

        return objDetections.toArray().length;
    }

    public static Mat imageGrayEqualizeHist(Mat src){
        Mat matGray = new Mat();
        Imgproc.cvtColor(src,matGray,COLOR_BGRA2GRAY);
        Imgproc.equalizeHist(matGray,matGray);
        return matGray;
    }


    public static double compare2ImageSURF(Mat img1, Mat img2){

        Mat face1 = getFace(img1);
        Mat face2 = getFace(img2);

        if (face1.empty() || face2.empty()) {
            return -1.0;//未识别出某个人脸
        }

        //-- Step 1: Detect the keypoints using SURF Detector, compute the descriptors
        double hessianThreshold = 400;
        int nOctaves = 4, nOctaveLayers = 3;
        boolean extended = false, upright = false;
        SURF detector = SURF.create(hessianThreshold, nOctaves, nOctaveLayers, extended, upright);
//        SURF detector = SURF.create();
//        ORB detector = ORB.create();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint(), keypoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat(), descriptors2 = new Mat();
        detector.detectAndCompute(face1, new Mat(), keypoints1, descriptors1);
        detector.detectAndCompute(face2, new Mat(), keypoints2, descriptors2);

        //-- Step 2: Matching descriptor vectors with a FLANN based matcher
        // Since SURF is a floating-point descriptor NORM_L2 is used
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(descriptors1, descriptors2, knnMatches, 2);

        //-- Filter matches using the Lowe's ratio test
        float ratioThresh = 0.8f;
        List<DMatch> listOfGoodMatches = new ArrayList<>();

        int goodMatchCount = 0;

        for (int i = 0; i < knnMatches.size(); i++) {
            if (knnMatches.get(i).rows() > 1) {
                DMatch[] matches = knnMatches.get(i).toArray();
                if (matches[0].distance < ratioThresh * matches[1].distance) {
                    goodMatchCount++;
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }

        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(listOfGoodMatches);

        //-- Draw matches
        Mat imgMatches = new Mat();
        Features2d.drawMatches(face1, keypoints1, face2, keypoints2, goodMatches, imgMatches, Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);

        //-- Show detected matches
//        HighGui.imshow("Good Matches", imgMatches);
//        HighGui.waitKey(0);

        String filename = "D:\\faceDetection.png";
        System.out.println(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, imgMatches);

//        System.exit(0);

        return 1.0*goodMatchCount/knnMatches.size();
    }
}
