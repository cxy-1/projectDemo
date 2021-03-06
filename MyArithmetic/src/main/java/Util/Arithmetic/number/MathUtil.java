package Util.Arithmetic.number;

import java.math.BigDecimal;

/**
 * 数学函数工具类
 * @author lizu
 *
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        }else {
            return false;
        }
    }

    public static BigDecimal add(long v1,long v2){
        //调用String构造器
        BigDecimal b1=new BigDecimal(Long.toString(v1));
        BigDecimal b2=new BigDecimal(Long.toString(v2));
        return b1.add(b2);
    }







}