package Util.Arithmetic.plat;

import com.xiaokang.screen.entity.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 坐标纠正工具类
 * @author gf
 */
public class LocationUtil {
    /**带坐标纠正*/
    public static List<Map<String,Object>> getDatas(List<Map<String,Object>> maps){
        List<Map<String,Object>> data=new ArrayList<>();
        if(maps !=null){
            for(int i =0;i<maps.size();i++){
                Point pointcan = new Point();
                Map<String,Object> map=new HashMap<>();
                List<String> list = new ArrayList<>();
                map.put("type",maps.get(i).get("type"));
                pointcan.setLng(Double.parseDouble((String) maps.get(i).get("gisx")));
                pointcan.setLat(Double.parseDouble((String) maps.get(i).get("gisy")));
                Point pointresult=CoordTransformUtil.WGS84ToGCJ02(pointcan);
                list.add(String.valueOf(pointresult.getLng()));
                list.add(String.valueOf(pointresult.getLat()));
                map.put("latlng",list);
                if(maps.get(i).get("id")!=null){
                    map.put("id",maps.get(i).get("id"));
                }
                data.add(map);
            }
        }
        return data;
    }
    /**不带坐标纠正*/
    public static List<Map<String,Object>> getDatas2(List<Map<String,Object>> maps){
        List<Map<String,Object>> data=new ArrayList<>();
        if(maps !=null){
            for(int i =0;i<maps.size();i++){
                Map<String,Object> map=new HashMap<>();
                List<String> list = new ArrayList<>();
                map.put("type",maps.get(i).get("type"));
                list.add((String) maps.get(i).get("gisx"));
                list.add((String) maps.get(i).get("gisy"));
                map.put("latlng",list);
                data.add(map);
            }
        }
        return data;
    }
    /**摄像机点位坐标的纠正*/
    public static List<Map<String,Object>> getCameraDatas(List<Map<String,Object>> maps){
        List<Map<String,Object>> data=new ArrayList<>();
        if(maps !=null){
            for(int i =0;i<maps.size();i++){
                Point pointcan = new Point();
                Map<String,Object> map=new HashMap<>();
                List<String> list = new ArrayList<>();
                map.put("type",maps.get(i).get("type"));
                map.put("id",maps.get(i).get("id"));
                map.put("name",maps.get(i).get("name"));
                pointcan.setLng(Double.parseDouble((String) maps.get(i).get("gisx")));
                pointcan.setLat(Double.parseDouble((String) maps.get(i).get("gisy")));
                Point pointresult=CoordTransformUtil.WGS84ToGCJ02(pointcan);
                list.add(String.valueOf(pointresult.getLng()));
                list.add(String.valueOf(pointresult.getLat()));
                map.put("latlng",list);
                data.add(map);
            }
        }
        return data;
    }

}
