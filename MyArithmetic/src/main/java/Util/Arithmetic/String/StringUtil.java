package Util.Arithmetic.String;

import Util.Arithmetic.json.JsonUtil;
import Util.Arithmetic.number.NumberUtils;
import com.alibaba.fastjson.serializer.SerializerFeature;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.accessibility.AccessibleRole.SEPARATOR;

public class StringUtil {

    /**
     * 16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 判断是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * Base64编码解析
     * @param uKey 数字证书编码字符串
     * @return String
     * @throws IOException //
     */
    private static String decodeBase64(String uKey) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(uKey);
        return new String(bytes, "UTF-8");
    }

    /**
     * 生成文件code
     * @return
     */
    public static String createFileCode() {
        return NumberUtils.randomFour()+System.currentTimeMillis()+"";
    }
    /**
     * id生成        160532901324286
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
    /**
     * 文件名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }
    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1))
            {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c))
            {
                if (!upperCase || !nextUpperCase)
                {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            }
            else
            {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    /**
     * 说明：将字符串org_id转换成orgId的形式
     * @return String
     */
    public static final String getBeanStr(String str){
        String beanStr="";
        if(str.indexOf("_",-1)>-1){
            String[] strArr=str.toLowerCase().split("_");
            for(int i=0;i<strArr.length;i++){
                if(i==0){
                    beanStr+=strArr[0];
                }else{
                    beanStr+=(strArr[i]).substring(0,1).toUpperCase()+strArr[i].substring(1,strArr[i].length());
                }
            }
        }else{
            beanStr=str.toLowerCase();
        }
        return beanStr;
    }


    /**
     * 获取证书用户信息
     * @param uKey 数字证书编码字符串
     * @return String[]
     * @throws IOException //
     */
    public static String[] getUserInfo(String uKey) throws IOException {
     /*   String decode = decodeBase64(uKey);
        JSONObject jsonObject = JSONObject.parseObject(decode);
        String keysn = jsonObject.getString("keysn");
        return keysn.split(" ");*/
        return null;
    }
    /**1
     * 判断字符串中是否有特殊字符
     * 有返回true 没有false
     */
    public static boolean specialCharacters(String stb) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        if (stb == null) {
            stb = "";
        }
        Matcher m = p.matcher(stb);
        return m.find();
    }


    //loginName=loginName.replace(" ","");       剔除 字符串里的某个字符  如空格

    private static final SerializerFeature[] FEATURES = {
            // 设置Map字段为null时输出空值
            SerializerFeature.WriteMapNullValue,
            // 设置List字段为null时输出为[]
            SerializerFeature.WriteNullListAsEmpty,
            // 设置String字段为null时输出为空字符
            SerializerFeature.WriteNullStringAsEmpty
    };

    //2  对json字符串进行解析， 取出里面的键值对
    public static void jsonStr() {
        String str1 = str();
        Map<String, Object> map1 = (Map<String, Object>) JsonUtil.jsonToMap(str1);
        String str2 = String.valueOf(map1.get("data"));
        Map<String, Object> map2 = (Map<String, Object>) JsonUtil.jsonToMap(str2);
        List<Map<String, String>> list = (List<Map<String, String>>) map2.get("data");
        System.out.println(str2);
        System.out.println(map2);
        for (Map<String, String> str : list) {
            System.out.println(str);
        }
    }
    /**返回一个字符串*/
    private static String str() {
        String jsonString = "{\n" +
                "    \"success\": true,\n" +
                "    \"code\": 200,\n" +
                "    \"errMsg\": null,\n" +
                "    \"data\": {\n" +
                "        \"pageNum\": null,\n" +
                "        \"total\": null,\n" +
                "        \"pages\": null,\n" +
                "        \"pageSize\": null,\n" +
                "        \"list\": null,\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"code\": \"1000663\",\n" +
                "                \"deviceIp\": \"10.70.35.59\",\n" +
                "                \"deviceType\": \"1\",\n" +
                "                \"type\": \"2\",\n" +
                "                \"name\": \"1F就餐区-4\",\n" +
                "                \"status\": \"1\",\n" +
                "                \"channels\": [\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000663$1$0$0\",\n" +
                "                        \"channelName\": \"1F就餐区-4\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000663$3$0$0\",\n" +
                "                        \"channelName\": \"5J055CDPAG69DAF_0\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000663$3$0$1\",\n" +
                "                        \"channelName\": \"5J055CDPAG69DAF_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000663$4$0$0\",\n" +
                "                        \"channelName\": \"5J055CDPAG69DAF_0\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000663$4$0$1\",\n" +
                "                        \"channelName\": \"5J055CDPAG69DAF_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"1000302\",\n" +
                "                \"deviceIp\": \"10.70.33.181\",\n" +
                "                \"deviceType\": \"1\",\n" +
                "                \"type\": \"2\",\n" +
                "                \"name\": \"1F走廊南侧向北\",\n" +
                "                \"status\": \"1\",\n" +
                "                \"channels\": [\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000302$1$0$0\",\n" +
                "                        \"channelName\": \"1F走廊南侧向北\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000302$3$0$0\",\n" +
                "                        \"channelName\": \"5G08AD1PAAB6C8A_0\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000302$3$0$1\",\n" +
                "                        \"channelName\": \"5G08AD1PAAB6C8A_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000302$4$0$0\",\n" +
                "                        \"channelName\": \"5G08AD1PAAB6C8A_0\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000302$4$0$1\",\n" +
                "                        \"channelName\": \"5G08AD1PAAB6C8A_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"1000713\",\n" +
                "                \"deviceIp\": \"10.70.32.229\",\n" +
                "                \"deviceType\": \"1\",\n" +
                "                \"type\": \"2\",\n" +
                "                \"name\": \"危化品废物间\",\n" +
                "                \"status\": \"1\",\n" +
                "                \"channels\": [\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000713$1$0$0\",\n" +
                "                        \"channelName\": \"地下车库危化品废弃间\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000713$3$0$0\",\n" +
                "                        \"channelName\": \"危化品废物间_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000713$3$0$1\",\n" +
                "                        \"channelName\": \"危化品废物间_2\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000713$4$0$0\",\n" +
                "                        \"channelName\": \"危化品废物间_1\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"channelCode\": \"1000713$4$0$1\",\n" +
                "                        \"channelName\": \"危化品废物间_2\",\n" +
                "                        \"capability\": \"0\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return jsonString;
    }
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty())
        {
            // 没必要转换

            return "";
        }
        else if (!name.contains("_"))
        {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty())
            {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /*string字符串 +1 保持原来的格式 001 + 1 = 002*/
    public static String addOne(String str) {
        if ("999".equals(str)) {
            return "1000";
        }

        String num = String.valueOf(Integer.valueOf(str) + 1);
        StringBuffer s = new StringBuffer();
        if (num.length() == 1) {
            s.append("00").append(num);
            return s.toString();
        } else if (num.length() == 2) {
            s.append("0").append(num);
            return s.toString();
        } else {
            return num;
        }
    }

}
