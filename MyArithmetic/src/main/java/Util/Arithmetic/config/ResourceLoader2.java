package Util.Arithmetic.config;

import com.sugon.job.core.log.JobLogger;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author xyl
 *
 * 依赖任务服务实现
 */
public class ResourceLoader2 {

    public static String ES_TYPE = "ELASTICSEARCH";

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

    public static String getExecutorProperties2(String key) {
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

//	public static String getDBColTypeProperties(String key) {
//		String getValue = "";
//		if (StringUtils.isNotBlank(key)) {
//			ResourceBundle bundle = ResourceBundle.getBundle("dbcoltype");
//			if(bundle!=null){
//				getValue = bundle.getString(key);
//			}
//
//		}
//		return getValue;
//	}

//	public static String getHiveDateType(String prefix,String key) {
//		String value = ResourceLoader.getHiveTypeProperties(prefix+"_"+key.toUpperCase());
//		if (StringUtils.isBlank(value)) {
//			value = "STRING";
//		}
//		return value;
//	}

    public static String getDBColType(String prefix, String key, String postfix) {
        String dbColKey = prefix + "_" + key.toUpperCase() + "_" + postfix;
        String getValue = "";
        if (StringUtils.isNotBlank(dbColKey)) {
            ResourceBundle bundle = ResourceBundle.getBundle("dbcoltype");
            if (bundle != null) {
                if(ResourceLoader.ES_TYPE.equals(prefix)){
                    getValue = "VARCHAR" ;
                }else{
                    getValue = bundle.getString(dbColKey);
                }

            } else {
                getValue = "VARCHAR";
            }
        }
        return getValue;
    }

    public static String getDBColType(String dbColKey) {

        String getValue = "";
        if (StringUtils.isNotBlank(dbColKey)) {
            ResourceBundle bundle = ResourceBundle.getBundle("dbcoltype");
            if (bundle != null) {
                getValue = bundle.getString(dbColKey);
            } else {
                getValue = "VARCHAR";
            }
        }
        return getValue;
    }

    public static String getEsColType(String prefix, String key) {
        String dbColKey = prefix + "_" + key.toUpperCase();
        String getValue = "";
        if (StringUtils.isNotBlank(dbColKey)) {
            ResourceBundle bundle = ResourceBundle.getBundle("estype");
            if (bundle != null) {
                getValue = bundle.getString(dbColKey);
            } else {
                getValue = "string";
            }
        }
        return getValue;
    }

    public static String getHiveDateType(String prefix, String key) {
        String dbColKey = prefix + "_" + key.toUpperCase();
        String getValue = "";
        if (StringUtils.isNotBlank(dbColKey)) {
            ResourceBundle bundle = ResourceBundle.getBundle("hivetype");
            if (bundle != null) {
                try{
                    getValue = bundle.getString(dbColKey);
                }catch (Exception e){
                    JobLogger.log(dbColKey+"==="+"is not exists");
                    getValue = "STRING";
                }
            } else {
                getValue = "STRING";
            }
        }
        return getValue;
    }

    public static String getMppKeyColType(String key) {
        String dbColKey =  key.toUpperCase();
        String getValue = "";
        if (StringUtils.isNotBlank(dbColKey)) {
            ResourceBundle bundle = ResourceBundle.getBundle("mppkey");
            if (bundle != null) {
                try {
                    getValue = bundle.getString(dbColKey);
                }catch (Exception e){
                    getValue = key;
                }
            } else {
                getValue =key;
            }
        }
        return getValue;
    }

    public static void main(String[] args) {

        String test = ResourceLoader.getMppKeyColType("id");
        System.out.println(test);
    }
}
