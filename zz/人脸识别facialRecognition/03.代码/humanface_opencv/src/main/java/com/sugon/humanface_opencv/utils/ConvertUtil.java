package com.sugon.humanface_opencv.utils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Description:
 * Author     : Dawn.Ji
 * Date       : Created in 9:31 2018/11/8
 */
public final class ConvertUtil {

    public static BufferedImage ByteToBufferedImage(byte[] inBytes){

        ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;

    }

    public static byte[] BufferedImageToByte(BufferedImage inBufferedImage,String format){

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(inBufferedImage,format,out);
        }catch (IOException e){
            e.printStackTrace();
        }
        return out.toByteArray();

    }

    public static Mat ByteToMat(byte[] inBytes){

        BufferedImage bufferedImage = ByteToBufferedImage(inBytes);
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();

        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);

        return mat;

    }

    public static byte[] MatToByte(Mat inMat,String imageFileExtension){
        MatOfByte mob = new MatOfByte();

        Imgcodecs.imencode(imageFileExtension, inMat, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        return byteArray;
    }


}
