package Util.Arithmetic.http;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HttpConnectionUtil {
    /**
     * 测试网络是否能连接通畅
     *
     * @param serviceURL
     * @return
     */
    public static boolean isConnection(String serviceURL) {
        try {
            URL url = new URL(serviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);// 2秒则超时
            conn.setReadTimeout(2000);
            int state = conn.getResponseCode();
            if (state == 200) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 判断ip是否在网段中
     * @param ipSection 网段
     * @param ip
     * @return
     */
    public static boolean ipIsValid(String ipSection, String ip) {
        if (ipSection == null)
            throw new NullPointerException("IP段不能为空！");
        if (ip == null)
            throw new NullPointerException("IP不能为空！");
        ipSection = ipSection.trim();
        ip = ip.trim();
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
        if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))
            return false;
        int idx = ipSection.indexOf('-');
        String[] sips = ipSection.substring(0, idx).split("\\.");
        String[] sipe = ipSection.substring(idx + 1).split("\\.");
        String[] sipt = ip.split("\\.");
        long ips = 0L, ipe = 0L, ipt = 0L;
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(sips[i]);
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);
        }
        if (ips > ipe) {
            long t = ips;
            ips = ipe;
            ipe = t;
        }
        return ips <= ipt && ipt <= ipe;
    }

    public static void main(String[] args) {
        if (ipIsValid("127.0.0.1-127.0.0.1", "127.0.0.1")) {
            System.out.println("ip属于该网段");
        } else{
            System.out.println("ip不属于该网段");
        }
    }


    /**
     * 获取配置文件信息
     *
     * @return
     */
    public static Properties getServiceUrlMap() {
        Properties props = new Properties();
        try {
            props.load(HttpConnectionUtil.class.getClassLoader().getResourceAsStream("cas-service.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}
