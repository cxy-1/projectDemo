package Util.Arithmetic.plat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*import com.google.gson.JsonObject;*/
import com.xiaokang.screen.entity.Point;

import java.io.*;

import static java.lang.Math.PI;
import static org.apache.juli.FileHandler.DEFAULT_BUFFER_SIZE;

/**
 * 坐标转换工具类
 * 火星坐标(GCJ02) 百度坐标(BD09) 大地坐标(WGS84) 之间转换
 * WGS84坐标: 大地坐标, 国际上通用的坐标, 国外地图均使用WGS84坐标
 * GCJ02坐标: 火星坐标, WGS84坐标加密后的坐标, 高德地图, 腾讯地图, 谷歌中国地图使用GCJ02坐标
 * BD09坐标: 百度坐标, GCJ02坐标加密后的坐标, 百度地图使用BD09坐标
 *
 * @author piaopiao87
 */
public class CoordTransformUtil {
	private static double xPI = PI * 3000.0 / 180.0;
	private static double a = 6378245.0;
	private static double ee = 0.00669342162296594323;

	private CoordTransformUtil() {
	}

	/**
	 * 判断是否在国内, 不在国内则不做偏移
	 *
	 * @param point
	 * @return
	 */
	public static boolean outOfChina(Point point) {
		if (point.getLng() < 72.004 || point.getLng() > 137.8347) {
			return true;
		}
		if (point.getLat() < 0.8293 || point.getLat() > 55.8271) {
			return true;
		}
		return false;
	}

	private static double transformLng(double lng, double lat) {
		double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLat(double lng, double lat) {
		double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	/**
	 * 百度坐标(BD09) 转 火星坐标(GCJ02)
	 *
	 * @param bdPoint
	 * @return
	 */
	public static Point BD09ToGCJ02(Point bdPoint) {
		double x = bdPoint.getLng() - 0.0065;
		double y = bdPoint.getLat() - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * xPI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * xPI);
		return new Point(MapUtil.formatDecimal(z * Math.cos(theta), 6), MapUtil.formatDecimal(z * Math.sin(theta), 6));
	}

	/**
	 * 火星坐标(GCJ02) 转 百度坐标(BD09)
	 *
	 * @param gcjPoint
	 * @return
	 */
	public static Point GCJ02ToBD09(Point gcjPoint) {
		double z = Math.sqrt(gcjPoint.getLng() * gcjPoint.getLng() + gcjPoint.getLat() * gcjPoint.getLat()) + 0.00002 * Math.sin(gcjPoint.getLat() * xPI);
		double theta = Math.atan2(gcjPoint.getLat(), gcjPoint.getLng()) + 0.000003 * Math.cos(gcjPoint.getLng() * xPI);
		return new Point(MapUtil.formatDecimal(z * Math.cos(theta) + 0.0065, 6), MapUtil.formatDecimal(z * Math.sin(theta) + 0.006, 6));
	}

	/**
	 * 大地坐标(WGS84) 转 火星坐标(GCJ02)
	 *
	 * @param wgsPoint
	 * @return
	 */
	public static Point WGS84ToGCJ02(Point wgsPoint) {
		if (outOfChina(wgsPoint)) {
			return wgsPoint;
		}

		double dlng = transformLng(wgsPoint.getLng() - 105.0, wgsPoint.getLat() - 35.0);
		double dlat = transformLat(wgsPoint.getLng() - 105.0, wgsPoint.getLat() - 35.0);
		double radlat = wgsPoint.getLat() / 180.0 * PI;
		double magic = Math.sin(radlat);
		magic = 1 - ee * magic * magic;
		double sqrtmagic = Math.sqrt(magic);
		dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
		dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
		return new Point(MapUtil.formatDecimal(wgsPoint.getLng() + dlng, 6), MapUtil.formatDecimal(wgsPoint.getLat() + dlat, 6));
	}

	/**
	 * 火星坐标(GCJ02) 转 大地坐标(WGS84)
	 *
	 * @param gcjPoint
	 * @return
	 */
	public static Point GCJ02ToWGS84(Point gcjPoint) {
		if (outOfChina(gcjPoint)) {
			return gcjPoint;
		}

		double dlng = transformLng(gcjPoint.getLng() - 105.0, gcjPoint.getLat() - 35.0);
		double dlat = transformLat(gcjPoint.getLng() - 105.0, gcjPoint.getLat() - 35.0);
		double radlat = gcjPoint.getLat() / 180.0 * PI;
		double magic = Math.sin(radlat);
		magic = 1 - ee * magic * magic;
		double sqrtmagic = Math.sqrt(magic);
		dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
		dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
		double mglng = gcjPoint.getLng() + dlng;
		double mglat = gcjPoint.getLat() + dlat;
		return new Point(MapUtil.formatDecimal(gcjPoint.getLng() * 2 - mglng, 6), MapUtil.formatDecimal(gcjPoint.getLat() * 2 - mglat, 6));
	}

//    public static void main(String[] args) throws  Exception {
//        readFileByLines("G:\\111.txt");
//        String  jsonStr = fileToString("G:\\1111.txt");
//
//        JSONObject jsonObject = JSON.parseObject(jsonStr);
//        JSONArray jsonArray = jsonObject.getJSONArray("latlng");
//                Point point = new Point();
//                point.setLng(Double.parseDouble(jsonArray.getString(0)));
//                point.setLat(Double.parseDouble(jsonArray.getString(1)));
//                Point point1 = GCJ02ToWGS84(point);
//            jsonArray.set(0,point1.getLng());
//            jsonArray.set(1,point1.getLat());
//        jsonObject.put("latlng",jsonArray);
//        //===
//         jsonArray = jsonObject.getJSONArray("center");
//        point = new Point();
//        point.setLng(Double.parseDouble(jsonArray.getString(0)));
//        point.setLat(Double.parseDouble(jsonArray.getString(1)));
//         point1 = GCJ02ToWGS84(point);
//        jsonArray.set(0,point1.getLng());
//        jsonArray.set(1,point1.getLat());
//        jsonObject.put("center",jsonArray);
//        //===
//        jsonArray = jsonObject.getJSONArray("bounds");
//        for(int i=0;i<jsonArray.size();i++){
//            point = new Point();
//            point.setLng(Double.parseDouble( jsonArray.getJSONArray(i).getString(0)));
//            point.setLat(Double.parseDouble( jsonArray.getJSONArray(i).getString(1)));
//            point1 = GCJ02ToWGS84(point);
//            jsonArray.getJSONArray(i).set(0,point1.getLng());
//            jsonArray.getJSONArray(i).set(1,point1.getLat());
//        }
//        jsonObject.put("bounds",jsonArray);

	// ---- 其他 格式
//        JSONArray jsonArray = jsonObject.getJSONArray("data");
//        for(int i=0;i<jsonArray.size();i++){
//            Point point = new Point();
//            point.setLng(Double.parseDouble( jsonArray.getJSONObject(i).getString("lng")));
//            point.setLat(Double.parseDouble( jsonArray.getJSONObject(i).getString("lat")));
//            Point point1 = GCJ02ToWGS84(point);
//            jsonArray.getJSONObject(i).put("lng",point1.getLng());
//            jsonArray.getJSONObject(i).put("lat",point1.getLat());
//        }
//        jsonObject.put("data",jsonArray);
//        System.out.println(jsonObject.toString());
//    }


//    public static void readFileByLines(String fileName) {
//        File file = new File(fileName);
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            String tempString = null;
//            int line = 1;
//            // 一次读一行，读入null时文件结束
//            while ((tempString = reader.readLine()) != null) {
//                Thread.sleep(500);
//                String[] strArr = tempString.split(",");
//                Point point = new Point();
//                point.setLng(Double.parseDouble(strArr[0]));
//                point.setLat(Double.parseDouble(strArr[1]));
//                Point point1 = GCJ02ToWGS84(point);
//
//                System.out.println(line+":["+point1.getLng()+","+point1.getLat()+"],");
//                line++;
//            }
//            reader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }


//    /**
//     * 将流转换成字符串
//     *
//     * @return
//     * @throws IOException
//     */
//    public static String fileToString(String filePath) throws IOException {
//        File file = new File(filePath);
//        FileInputStream is = new FileInputStream(file);
//        String fileStr = streamToString(is);
//        return fileStr;
//    }
//
//    /**
//     * 将流转换成字符串 使用Base64加密
//     *
//     * @return
//     * @throws IOException
//     */
//    public static String streamToString(InputStream inputStream) throws IOException {
//        byte[] bt = toByteArray(inputStream);
//        inputStream.close();
////        String out = new sun.misc.BASE64Encoder().encodeBuffer(bt);
//        String out = new String(bt);
//        return out;
//    }
//
//    /**
//     * summary:将流转化为字节数组
//     *
//     * @param inputStream
//     * @return
//     * @throws IOException
//     */
//    public static byte[] toByteArray(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024 * 4];
//        byte[] result = null;
//        try {
//            int n = 0;
//            while ((n = inputStream.read(buffer)) != -1) {
//                out.write(buffer, 0, n);
//            }
//            result = out.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            out.close();
//        }
//        return result;
//
//    }

}
