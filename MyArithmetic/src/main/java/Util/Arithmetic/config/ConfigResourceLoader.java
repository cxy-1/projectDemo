package Util.Arithmetic.config;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author xyl
 *
 * 加载外部文件配置类
 */
public class ConfigResourceLoader {


    /**
     * 获取用户定义参数
     *
     * @param key
     * @return
     */
    public static String getCustomProperties(String key) {
        String getValue = "";
        if (StringUtils.isNotBlank(key)) {
            ResourceBundle bundle = ResourceBundle.getBundle("datax");
            try {
                getValue = bundle.getString(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getValue;
    }


    /**
     * 读取外部配置文件
     * @param key
     * @return
     */
    public static String getExecutorProperties(String key) {
        String getValue = "";
        String applicationProperties = System.getProperty("spring.config.location");
        FileInputStream fileInputStream =null;
        if (StringUtils.isNotBlank(key)) {
            try {
                if(StringUtils.isNotBlank(applicationProperties)){
                    Properties pps = new Properties();
                    fileInputStream = new FileInputStream(applicationProperties);
                    pps.load(fileInputStream);
                    getValue = (String) pps.get(key);
                }else{
                    ResourceBundle bundle = ResourceBundle.getBundle("application");
                    getValue = bundle.getString(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fileInputStream!=null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return getValue;
    }

    /**
     * 读取内部配置文件
     * @param key
     * @return
     */
    public static String getExecutorPropertiesInner(String key) {
        String getValue = "";
        if (StringUtils.isNotBlank(key)) {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            try {
                getValue = bundle.getString(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getValue;
    }

    public static String getHiveTypeProperties(String key) {
        String getValue = "";
        if (StringUtils.isNotBlank(key)) {
            ResourceBundle bundle = ResourceBundle.getBundle("hivetype");
            getValue = bundle.getString(key);
        }
        return getValue;
    }

    public static String getHiveDateType(String prefix,String key) {
        String value = ResourceLoader.getHiveTypeProperties(prefix+"_"+key.toUpperCase());
        if (StringUtils.isBlank(value)) {
            value = "STRING";
        }
        return value;
    }

    public static void main(String[] args) {

        ResourceLoader.getCustomProperties("");

    }

}
