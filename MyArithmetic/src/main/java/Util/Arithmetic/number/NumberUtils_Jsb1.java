package Util.Arithmetic.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @ClassName NumberUtils
 * @Author liu
 * @Description 功能描述
 * @Date 2019/5/6 8:58
 * @Version 1.0
 **/
public class NumberUtils_Jsb1 {
    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";

    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     *
     * @param amount 报销金额
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月18日
     */
    public static String amountConversion(double amount) {
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        //金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;

            value = formatNumber(tempValue, 2, true);
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION) + BILLION_UNIT;
            } else {
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;

            value = formatNumber(tempValue, 2, true);
            result = zeroFill(value) + BILLION_UNIT;
        } else {
            result = String.valueOf(amount);
            result = result.substring(0, result.indexOf('.'));
        }
        return result;
    }


    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number   要四舍五入的数字
     * @param decimal  保留的小数点数
     * @param rounding 是否四舍五入
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月18日
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月23日
     */
    public static String zeroFill(double number) {
        String value = String.valueOf(number);
        String tmp1=".";
        int size =2;
        if (value.indexOf(tmp1) < 0) {
            value = value + ".00";
        } else {
            String decimalValue = value.substring(value.indexOf(".") + 1);

            if (decimalValue.length() < size) {
                value = value + "0";
            }
        }
        return value;
    }

    /**
     * 生成四位随机数
     * @return
     */
    public  static int randomFour(){
        return  (int)((Math.random()*9+1)*1000);
    }
    /**
     *  计算a除以b的百分比
     * @param a
     * @param b
     * @return
     */
    public  static  String percentData(int a,int b){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)a/(float)b*100);
        return result+"%";
    }

    /**
     *  计算a除以b 二位小数
     * @param a
     * @param b
     * @return
     */
    public  static  Double percentData1(int a,int b){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(4);
        String result = numberFormat.format((float)a/(float)b);
        return Double.valueOf(result);
    }
}
