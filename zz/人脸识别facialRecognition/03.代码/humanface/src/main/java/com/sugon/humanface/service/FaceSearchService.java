package com.sugon.humanface.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sugon.humanface.utils.AuthService;
import com.sugon.humanface.utils.Base64Util;
import com.sugon.humanface.utils.FileUtil;
import com.sugon.humanface.utils.GsonUtils;
import com.sugon.humanface.utils.HttpUtil;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 人脸搜索
 */
@Slf4j
public class FaceSearchService {

    public static final  String QUERY_URL="https://aip.baidubce.com/rest/2.0/face/v3/search";

//    static {
//        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        //注意程序运行的时候需要在VM option添加该行 指明opencv的dll文件所在路径
//        //-Djava.library.path=$PROJECT_DIR$\opencv\x64
//        System.out.println(System.getProperty("java.library.path")); 
//        System.load( "C:\\Windows\\System32\\opencv_java341.dll" );
//    }

    /**
     *  从百度人脸库中查找对比,测试类
     */
    public static String search() {
        byte[] bytes2 = new byte[0];
        try {
            bytes2 = FileUtil.readFileByBytes("C:\\Users\\lenovo\\Desktop\\6.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String image2 = Base64Util.encode(bytes2);
        return search(image2);
    }

    /**
     *  从百度人脸库中查找对比
     *  @param base64ImageStr : 对比图片base64编码的字符串
     */
    public static  String search(String base64ImageStr) {
//    	long startTime = System.currentTimeMillis();
//    	//System.load("C:\\Users\\wuyl\\Desktop\\winopencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
//    	boolean writeFlag = GenerateImage(base64ImageStr);
//    	System.out.println("=====写入是否成功======："+writeFlag);
//    	String result = null;
//    	Mat image = Imgcodecs.imread("D:\\picture\\new.jpg");    	
//    	Mat gray = new Mat();
//    	Mat hist = new Mat();
//    	Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
//    	Imgproc.equalizeHist(gray,hist);
//    	System.out.println("=====图片灰度化======");
//    	CascadeClassifier objDetector=new CascadeClassifier("lbpcascade_frontalface_improved");
//		
//		MatOfRect faces=new MatOfRect();
//		objDetector.detectMultiScale(hist, faces);
//		System.out.println("=====图片人脸数量======"+faces.toList().size());
//		long endtime = System.currentTimeMillis();
//		System.out.println("=====比对之前图片处理耗时======："+(endtime-startTime));
//		if(faces!=null && faces.toList().size()>0) {
//			for (Rect rect : faces.toArray()) {  
//				Imgproc.rectangle(hist, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255,0, 0,255));  
//				int x = rect.x-25;
//				 int y = rect.y-55;
//
//				 if((rect.x-25)<0 || (rect.y-55)<0){
//					continue;
//				}
//				
//				Rect rects = new Rect(x, y, rect.width+50, rect.height+100);
//				Mat dst = new Mat(image,rects);
//				System.out.println("=====转换完成开始写入文件======");
//				Imgcodecs.imwrite("D:\\picture\\new1.jpg", dst);
//				String imageStr = GetImageStr();
//				System.out.println("=====完成写入文件！======");
//				Map<String, Object> map = new HashMap<>();
//				System.out.println("=====开始比对======");
//		        map.put("image", imageStr);
//		        map.put("liveness_control", "LOW");  // 活体检测要求
//		        map.put("group_id_list", "111");  // 人脸库组
//		        map.put("image_type", "BASE64");
//		        map.put("quality_control", "LOW");
//
//		        String param = GsonUtils.toJson(map);
//		        String accessToken = AuthService.getAuth();
//		        
//		        try {
//		            result = HttpUtil.post(QUERY_URL, accessToken, "application/json", param);
//		            System.out.println("=====结束比对======");
//		        } catch (Exception e) {
//		              System.out.println(e.getMessage());
//		          //  log.error(e.getMessage(),e);
//		        }
//		        System.out.println("result+++:"+result);
//			}
//		}
        String result = null;
        Map<String, Object> map = new HashMap<>();
        map.put("image", base64ImageStr);
        map.put("liveness_control", "LOW");  // 活体检测要求
        map.put("group_id_list", "111");  // 人脸库组
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");

        String param = GsonUtils.toJson(map);
        String accessToken = AuthService.getAuth();

        try {
            result = HttpUtil.post(QUERY_URL, accessToken, "application/json", param);
            //System.out.println("=====结束比对======");
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //  log.error(e.getMessage(),e);
        }
        //System.out.println("result+++:"+result);
        //long startTime1 = System.currentTimeMillis();
        //System.out.println("=====比对处理耗时======："+(startTime1-endtime));
        return result;
    }

    public static boolean GenerateImage(String imgStr)
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据  
                    b[i]+=256;
                }
            }
            //生成jpeg图片  
            File f=new File("D:\\picture");
            f.setWritable(true, false);
            if(!f.exists()) {
                f.mkdirs();
            }

            String imgFilePath = "D:\\picture\\new.jpg";//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //图片转化成base64字符串
    public static String GetImageStr()
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "D:\\picture\\new1.jpg";//待处理的图片  
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组  
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }


}