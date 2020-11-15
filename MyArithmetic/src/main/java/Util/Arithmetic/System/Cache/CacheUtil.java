package Util.Arithmetic.System.Cache;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.poi.util.StringUtil;

/** cpu�Ļ�����
 * Cache�������
 * @author wuyl
 *
 */
public final class CacheUtil {

    private static CacheUtil instance;
    private static CacheManager cacheManager;

    private CacheUtil() {
    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            synchronized(CacheUtil.class){
                if (instance == null) {
                    instance = new CacheUtil();
                    URL url = CacheUtil.class.getClassLoader().getResource("ehcache.xml");
                    cacheManager = new CacheManager(url);
                }
            }
        }
        return instance;
    }

    /**
     * ��ȡ����
     * @param cacheName
     * @return Cache
     */
    public Cache getCache(String cacheName){
        return cacheManager.getCache(cacheName);
    }

    /**
     * ���Ԫ����Cache
     * @param cache
     * @param map
     */
    public void addElement(String cacheName,Map<String,String> map){
        String mapKey="";
        Element element=null;
        for(int i=0;i<map.size();i++){
            mapKey= StringUtil.getMapKey(map).get(i);
            element = new Element(mapKey, map.get(mapKey).toString());
            this.getCache(cacheName).put(element);
        }
    }

    /**
     * ��ȡ�����л�Ԫ��ֵ
     * @param cache
     * @param key
     * @return
     */
    public Object getObjectValue(String cacheName,String key){
        Element element = this.getCache(cacheName).get(key);
        if(null!=element){
            return element.getObjectValue();
        }else{
            return null;
        }
    }

    /**
     * ��ȡ�����л�Ԫ��ֵ
     * @param cache
     * @param key
     * @return
     */
    public String getStringValue(String cacheName,String key){
        Element element = this.getCache(cacheName).get(key);
        if(null!=element){
            return element.getObjectValue().toString();
        }else{
            return "";
        }
    }

    /**
     * ��ȡ���л�Ԫ��ֵ
     * @param cache
     * @param key
     * @return
     */
    public Serializable getSerializableValue(String cacheName,String key){
        Element element = this.getCache(cacheName).get(key);
        Serializable value = element.getValue();
        return value;
    }

    /**
     * ɾ��Ԫ��
     * @param cache
     * @param key
     */
    public void delElement(String cacheName,String key){
        this.getCache(cacheName).remove(key);
    }

    /**
     * ����Ԫ��
     * @param cache
     * @param key
     * @param value
     */
    public void updElement(String cacheName,String key,String value){
        this.getCache(cacheName).put(new Element(key,value));
    }


    /**
     * ��ȡ���л�������
     * @return
     */
    public String[] getCacheNames(){
        return cacheManager.getCacheNames();
    }

    /**
     * ��ӻ���
     * @param cacheName
     */
    public void addCache(String cacheName){
        cacheManager.addCache(cacheName);
    }

    /**
     * ɾ������
     * @param cacheName
     */
    public void delCache(String cacheName){
        cacheManager.removeCache(cacheName);
    }

    /**
     * ɾ�����л���
     */
    public void delCache(){
        cacheManager.removalAll();
    }

    /**
     * ��ȡ�����С
     * @param cache
     * @return
     */
    public int getCacheSize(String cacheName){
        return this.getCache(cacheName).getSize();
    }

    /**
     * ��ȡ�ڴ��л���Ԫ������
     * @param cache
     * @return
     */
    public long getMemoryStoreSize(String cacheName){
        return this.getCache(cacheName).getMemoryStoreSize();
    }

    /**
     * ��ȡӲ���л���Ԫ������
     * @param cache
     * @return
     */
    public long getDiskStoreSize(String cacheName){
        return this.getCache(cacheName).getDiskStoreSize();
    }

    /**
     * ж��CacheManager���ر�Cache
     */
    public void closeManager(){
        cacheManager.shutdown();
        instance=null;
    }


}
