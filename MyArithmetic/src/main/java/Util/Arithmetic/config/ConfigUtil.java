package Util.Arithmetic.config;

import java.io.InputStream;
import java.util.Map;

/**    文件读取+文件操作
 * 配置文件操作
 * @author wuyl
 *
 */
public class ConfigUtil {
    private PropertiesUtil properties=null;

    public ConfigUtil(String fileName){
        this.initProperties(fileName);
    }

    private void initProperties(String fileName){
        InputStream is = null;
        try {
            is = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
            if(null!=is){
                this.properties=new PropertiesUtil(is);
            }
        }catch ( Exception e ) {
            throw new RuntimeException(e.getMessage(),e);
        }finally{
            try{
                if(null!=is){
                    is.close();
                    is=null;
                }
            }catch(Exception e){
                throw new RuntimeException(e.getMessage(),e);
            }
        }
    }

    /**
     * 获取所有属性
     */
    public Map getAllProperty() throws Exception{
        if(null!=this.properties){
            return this.properties.getAllProperty();
        }else{
            return null;
        }
    }

    /**
     * 根据Key获取属性
     */
    public String getProperty(String key) throws Exception{
        if(null!=this.properties){
            return this.properties.getProperty(key);
        }else{
            return null;
        }
    }

}
