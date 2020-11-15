package Util.Arithmetic.System.Cache;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.poi.util.StringUtil;

/** cpu的缓冲区
 * Cache缓存操作
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
     * 获取缓存
     * @param cacheName
     * @return Cache
     */
    public Cache getCache(String cacheName){
        return cacheManager.getCache(cacheName);
    }

    /**
     * 添加元素至Cache
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
     * 获取非序列化元素值
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
     * 获取非序列化元素值
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
     * 获取序列化元素值
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
     * 删除元素
     * @param cache
     * @param key
     */
    public void delElement(String cacheName,String key){
        this.getCache(cacheName).remove(key);
    }

    /**
     * 更新元素
     * @param cache
     * @param key
     * @param value
     */
    public void updElement(String cacheName,String key,String value){
        this.getCache(cacheName).put(new Element(key,value));
    }


    /**
     * 获取所有缓存名称
     * @return
     */
    public String[] getCacheNames(){
        return cacheManager.getCacheNames();
    }

    /**
     * 添加缓存
     * @param cacheName
     */
    public void addCache(String cacheName){
        cacheManager.addCache(cacheName);
    }

    /**
     * 删除缓存
     * @param cacheName
     */
    public void delCache(String cacheName){
        cacheManager.removeCache(cacheName);
    }

    /**
     * 删除所有缓存
     */
    public void delCache(){
        cacheManager.removalAll();
    }

    /**
     * 获取缓存大小
     * @param cache
     * @return
     */
    public int getCacheSize(String cacheName){
        return this.getCache(cacheName).getSize();
    }

    /**
     * 获取内存中缓存元素数量
     * @param cache
     * @return
     */
    public long getMemoryStoreSize(String cacheName){
        return this.getCache(cacheName).getMemoryStoreSize();
    }

    /**
     * 获取硬盘中缓存元素数量
     * @param cache
     * @return
     */
    public long getDiskStoreSize(String cacheName){
        return this.getCache(cacheName).getDiskStoreSize();
    }

    /**
     * 卸载CacheManager，关闭Cache
     */
    public void closeManager(){
        cacheManager.shutdown();
        instance=null;
    }


}
