package Util.Arithmetic.Enum;

/**
 *
 * @author zhuwz
 */
public enum CodeEnums {

    /**
     * 人员类型
     */
    USER_STATUS0("0","未返回江宁本地常住居民"),
    USER_STATUS1("1","持续在江宁人员"),
    USER_STATUS2("2","一月初返回江宁居民"),
    USER_STATUS3("3","一月初来江宁人员"),
    USER_STATUS4("4","一月中返回江宁居民"),
    USER_STATUS5("5","一月中来江宁人员"),
    USER_STATUS6("6","居民医学观察人员"),
    USER_STATUS7("7","集中医学观察人员"),

    /**
     * 症状类型
     */
    ZHENGZHUANG_TYPE0("0","自觉正常"),
    ZHENGZHUANG_TYPE1("1","发热"),
    ZHENGZHUANG_TYPE2("2","干咳"),
    ZHENGZHUANG_TYPE3("3","咳痰"),
    ZHENGZHUANG_TYPE4("4","乏力"),
    ZHENGZHUANG_TYPE5("5","腹泻"),
    ZHENGZHUANG_TYPE6("6","呼吸困难"),
    ZHENGZHUANG_TYPE7("7","气促"),
    ZHENGZHUANG_TYPE8("8","未知"),

    /**
     * 证件类型
     */
    ZHENGJIAN_TYPE0("0","身份证"),
    ZHENGJIAN_TYPE1("1","护照"),
    ZHENGJIAN_TYPE2("2","军官证"),
    ZHENGJIAN_TYPE3("3","台胞证"),
    ZHENGJIAN_TYPE4("4","港澳居民居住证"),
    ZHENGJIAN_TYPE5("5","台湾居民居住证");




    private String endCode;
    private String codeInfo;


    private CodeEnums(String endCode, String codeInfo) {
        this.endCode = endCode;
        this.codeInfo = codeInfo;
    }

    public String getEndCode() {
        return endCode;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

}
