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
 * ��������
 */
@Slf4j
public class FaceSearchService {

    public static final  String QUERY_URL="https://aip.baidubce.com/rest/2.0/face/v3/search";

//    static {
//        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        //ע��������е�ʱ����Ҫ��VM option��Ӹ��� ָ��opencv��dll�ļ�����·��
//        //-Djava.library.path=$PROJECT_DIR$\opencv\x64
//        System.out.println(System.getProperty("java.library.path")); 
//        System.load( "C:\\Windows\\System32\\opencv_java341.dll" );
//    }

    /**
     *  �Ӱٶ��������в��ҶԱ�,������
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
     *  �Ӱٶ��������в��ҶԱ�
     *  @param base64ImageStr : �Ա�ͼƬbase64������ַ���
     */
    public static  String search(String base64ImageStr) {
//    	long startTime = System.currentTimeMillis();
//    	//System.load("C:\\Users\\wuyl\\Desktop\\winopencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
//    	boolean writeFlag = GenerateImage(base64ImageStr);
//    	System.out.println("=====д���Ƿ�ɹ�======��"+writeFlag);
//    	String result = null;
//    	Mat image = Imgcodecs.imread("D:\\picture\\new.jpg");    	
//    	Mat gray = new Mat();
//    	Mat hist = new Mat();
//    	Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
//    	Imgproc.equalizeHist(gray,hist);
//    	System.out.println("=====ͼƬ�ҶȻ�======");
//    	CascadeClassifier objDetector=new CascadeClassifier("lbpcascade_frontalface_improved");
//		
//		MatOfRect faces=new MatOfRect();
//		objDetector.detectMultiScale(hist, faces);
//		System.out.println("=====ͼƬ��������======"+faces.toList().size());
//		long endtime = System.currentTimeMillis();
//		System.out.println("=====�ȶ�֮ǰͼƬ�����ʱ======��"+(endtime-startTime));
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
//				System.out.println("=====ת����ɿ�ʼд���ļ�======");
//				Imgcodecs.imwrite("D:\\picture\\new1.jpg", dst);
//				String imageStr = GetImageStr();
//				System.out.println("=====���д���ļ���======");
//				Map<String, Object> map = new HashMap<>();
//				System.out.println("=====��ʼ�ȶ�======");
//		        map.put("image", imageStr);
//		        map.put("liveness_control", "LOW");  // ������Ҫ��
//		        map.put("group_id_list", "111");  // ��������
//		        map.put("image_type", "BASE64");
//		        map.put("quality_control", "LOW");
//
//		        String param = GsonUtils.toJson(map);
//		        String accessToken = AuthService.getAuth();
//		        
//		        try {
//		            result = HttpUtil.post(QUERY_URL, accessToken, "application/json", param);
//		            System.out.println("=====�����ȶ�======");
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
        map.put("liveness_control", "LOW");  // ������Ҫ��
        map.put("group_id_list", "111");  // ��������
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");

        String param = GsonUtils.toJson(map);
        String accessToken = AuthService.getAuth();

        try {
            result = HttpUtil.post(QUERY_URL, accessToken, "application/json", param);
            //System.out.println("=====�����ȶ�======");
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //  log.error(e.getMessage(),e);
        }
        //System.out.println("result+++:"+result);
        //long startTime1 = System.currentTimeMillis();
        //System.out.println("=====�ȶԴ����ʱ======��"+(startTime1-endtime));
        return result;
    }

    public static boolean GenerateImage(String imgStr)
    {   //���ֽ������ַ�������Base64���벢����ͼƬ  
        if (imgStr == null) //ͼ������Ϊ��  
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64����  
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//�����쳣����  
                    b[i]+=256;
                }
            }
            //����jpegͼƬ  
            File f=new File("D:\\picture");
            f.setWritable(true, false);
            if(!f.exists()) {
                f.mkdirs();
            }

            String imgFilePath = "D:\\picture\\new.jpg";//�����ɵ�ͼƬ  
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

    //ͼƬת����base64�ַ���
    public static String GetImageStr()
    {//��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        String imgFile = "D:\\picture\\new1.jpg";//�������ͼƬ  
        InputStream in = null;
        byte[] data = null;
        //��ȡͼƬ�ֽ�����  
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
        //���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//����Base64��������ֽ������ַ���  
    }


}