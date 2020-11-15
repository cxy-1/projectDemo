package Util.Arithmetic.config;

import java.io.InputStream;
import java.util.Map;

/**    �ļ���ȡ+�ļ�����
 * �����ļ�����
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
     * ��ȡ��������
     */
    public Map getAllProperty() throws Exception{
        if(null!=this.properties){
            return this.properties.getAllProperty();
        }else{
            return null;
        }
    }

    /**
     * ����Key��ȡ����
     */
    public String getProperty(String key) throws Exception{
        if(null!=this.properties){
            return this.properties.getProperty(key);
        }else{
            return null;
        }
    }

}
