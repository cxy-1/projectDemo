package Util.Arithmetic.a0.license;

import com.sugon.jsdk.utils.SpringUtils;
import com.sugon.sdm.system.service.TSystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sugon.sdm.system.vo.LicensePojo;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LicenseUtil { //license 授权

    private static final Logger LOG = LoggerFactory
            .getLogger(LicenseUtil.class);

    private static final SimpleDateFormat sf = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static String path;

    static {

        ResourceBundle rb = ResourceBundle.getBundle("config/resources");

        if (File.separator.equals("\\")) {
            path = rb.getString("lic.default.path.widows");
        } else {
            path = rb.getString("lic.default.path.linux");
        }
    }

    /**
     *
     * 初始化license
     *
     * @param expiryTime
     *            失效时间
     */
    public static void generateLicense(String expiryTime) {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            String ipsrt = ip.getHostAddress();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder macStr = new StringBuilder();
            if (mac != null) {

                for (int i = 0; i < mac.length; i++) {
                    macStr.append(String.format("%02X%s", mac[i],
                            (i < mac.length - 1) ? ":" : ""));
                }
            }
            LicensePojo licensePojo = new LicensePojo();
            licensePojo.setIp(encryptLicenseString(ipsrt));
            licensePojo.setMac(encryptLicenseString(macStr.toString()));
            licensePojo.setExpiryTime(encryptLicenseString(expiryTime));
            licensePojo.setValidTime(encryptLicenseString(getCurrentTimeString()));
            writeLicense(licensePojo);
        } catch (Exception e) {
            // license 写入，异常不做处理
            // LOG.error("LicenseUtil generateLicense Exception" +
            // e.getMessage());
        }
    }

    /**
     *
     * 校验license是否生效
     *
     * @return
     */
    public static Map<String, Object> checkLicense() {

        Boolean result = false;
        long resultDay = 0;

        LicensePojo licensePojo = readLicense();

        if (null != licensePojo) {
            String currentTime = getCurrentTimeString();
            TSystemUserService tSystemUserService = SpringUtils.getBean(TSystemUserService.class);
            String maxLoginTime = tSystemUserService.getMaxLastLoginTime();
            if (changeTimeToLong(maxLoginTime) <= changeTimeToLong(decryptLicenseString(licensePojo.getExpiryTime()))) {
                if (changeTimeToLong(currentTime) <= changeTimeToLong(decryptLicenseString(licensePojo.getExpiryTime()))) {
                    InetAddress ip;
                    try {
                        ip = InetAddress.getLocalHost();
                        String ipsrt = ip.getHostAddress();
                        NetworkInterface network = NetworkInterface
                                .getByInetAddress(ip);
                        byte[] mac = network.getHardwareAddress();
                        StringBuilder macStr = new StringBuilder();
                        if (mac != null) {

                            for (int i = 0; i < mac.length; i++) {
                                macStr.append(String.format("%02X%s", mac[i],
                                        (i < mac.length - 1) ? ":" : ""));
                            }
                        }

                        if (ipsrt.equals(decryptLicenseString(licensePojo.getIp()))
                                && macStr.toString().equals(decryptLicenseString(licensePojo.getMac()))) {
                            result = true;

//						if (!currentTime.equals(licensePojo.getValidTime())) {
//							licensePojo.setValidTime(currentTime);
//							writeLicense(licensePojo);
//						}

                            resultDay = surplusTime(currentTime,
                                    decryptLicenseString(licensePojo.getExpiryTime()));
                        }

                    } catch (Exception e) {
                        LOG.error("LicenseUtil checkLicense Exception"
                                + e.getMessage());
                    }

                }
            }
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("result", result);
        resultMap.put("resultDay", resultDay);

        return resultMap;
    }

    /**
     *
     * 读取license
     *
     * @return
     */
    public static LicensePojo readLicense() {

        ObjectInputStream oin = null;
        LicensePojo pojo = null;

        try {
            oin = new ObjectInputStream(new FileInputStream(path + "valid.lic"));
            pojo = (LicensePojo) oin.readObject();

        } catch (Exception e) {
            // license 读取，异常不做处理
            // LOG.error("LicenseUtil readLicense Exception" + e.getMessage());
        } finally {
            if (null != oin) {
                try {
                    oin.close();
                } catch (IOException e) {

                    // license 写入，异常不做处理
                    // LOG.error("LicenseUtil writeLicense close io Exception"
                    // + e.getMessage());
                }
            }
        }

        return pojo;
    }

    /**
     *
     * 写入license
     *
     * @param licensePojo
     *            license对象
     */
    public static void writeLicense(LicensePojo licensePojo) {

        File parentFile = new File(path);

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        File childFile = new File(parentFile, "valid.lic");

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(childFile));
            oos.writeObject(licensePojo);
            oos.flush();
        } catch (Exception e) {
            // license 写入，异常不做处理
            // LOG.error("LicenseUtil writeLicense Exception" + e.getMessage());
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {

                    // license 写入，异常不做处理
                    // LOG.error("LicenseUtil writeLicense close io Exception"
                    // + e.getMessage());
                }
            }
        }
    }

    /**
     *
     * 将时间转成long，方便比较大小
     *
     * @param time
     *            时间
     * @return
     */
    public static Long changeTimeToLong(String time) {

        return Long.valueOf(time.replaceAll("-", ""));
    }

    /**
     *
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getCurrentTimeString() {

        return sf.format(new Date(System.currentTimeMillis()));
    }

    public static long surplusTime(String validTime, String expiryTime) {

        long result = 0;

        try {
            Date endTime = sf.parse(expiryTime);

            Date beginTime = sf.parse(validTime);

            result = ((endTime.getTime() - beginTime.getTime()) / (1000 * 60 * 60 * 24)) + 1;

        } catch (ParseException e) {
            LOG.error("LicenseUtil surplusTime Exception" + e.getMessage());
        }

        return result;
    }

    private static String encryptLicenseString(String str) {
        String result = "";
        if (StringUtils.isNotBlank(str)) {
            try {
//				byte[] key = AESUtils.initKey("sugon_ark_3");
//				byte[] data = str.getBytes();
//				byte[] redata = AESUtils.encrypt(data, key);
//				result = new String(AESUtils.parseByte2HexStr(redata));
                result = encoder(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String decryptLicenseString(String str) {
        String result = "";
        if (StringUtils.isNotBlank(str)) {
            try {
//				byte[] key = AESUtils.initKey("sugon_ark_3");
//				byte[] encryptData = AESUtils.parseHexStr2Byte(str);
//				result = new String(AESUtils.decrypt(encryptData,key));
                result = decoder(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64 加密
     * @param str
     * @return
     * @throws Exception
     */
    private static String encoder(String str) throws Exception {
        final Base64.Encoder encoder = Base64.getEncoder();
        final String encodedText = encoder.encodeToString(str.getBytes("UTF-8"));
        return encodedText;
    }

    /**
     * base64 解密
     * @param encodedText
     * @return
     * @throws Exception
     */
    private static String decoder(String encodedText) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        final String result = new String(decoder.decode(encodedText), "UTF-8");
        return result;
    }
}
