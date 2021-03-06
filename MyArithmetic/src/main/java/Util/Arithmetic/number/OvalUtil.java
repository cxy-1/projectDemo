package Util.Arithmetic.number;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:   OvalUtil
 * Description: 椭圆坐标计算工具类
 * @author Dawn.Ji
 * @date Created in 11:19 2019/3/25
 */
public class OvalUtil {

    private Double a;
    private Double b;


    public OvalUtil(Double lx,Double ly){
        a = Math.abs(lx)/2.0;
        b = Math.abs(ly)/2.0;
    }

    public List<Double> getSite(Double theta){

        List<Double> res = new ArrayList<>();

        Double x = a * Math.cos(theta) + a;
        Double y = b * Math.sin(theta) + b;

        res.add(x);
        res.add(y);

        return res;
    }
}
