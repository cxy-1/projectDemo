package Util.Arithmetic.number;



public class AgeConvert {

    public static String ageConvert(Object ageType){
        int ageT = Integer.parseInt(ageType+"");
        String res = null;
        switch(ageT){
            case 1:
                res= "18岁以下";
                break;
            case 2:
                res= "18-25岁";
                break;
            case 3:
                res ="25-30岁";
                break;
            case 4:
                res = "30-40岁";
                break;
            case 5:
                res = "40-50岁";
                break;
            case 6:
                res =  "50-65岁";
                break;
            case 7:
                res = "65岁以上";
                break;
            case 8:
                res = "未知";
                break;
            default:
                res = "年龄类型传输参数有误";
                break;
        }
        return res;
    }
}
