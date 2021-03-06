package Util.Arithmetic.Excel;

import javax.security.auth.login.FailedLoginException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 读取助手
 */
public class XlsReader {
    // 文件时间的记录
    private static LinkedHashMap<String, Long> m_timeMap = new LinkedHashMap<String, Long>();

    /**
     * 如果改变才会返回 没变化会抛出异常。外面应该捕捉 并不影响
     */
    public static <T extends XlsProperty> ArrayList<T> loadToArrayWhenChange(String filename, Class<T> class1)
            throws Exception {
        // 对单独的文件进行加锁
        long lasttime = 0;
        if (m_timeMap.get(filename) != null) {
            lasttime = m_timeMap.get(filename);
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new Exception("配置文件错误" + filename);
        }
        long time = file.lastModified();
        if (time == lasttime) {
            throw new FailedLoginException(filename + "文件并未改变");
        }
        return loadToArray(filename, class1);
    }

    /**
     * 直接读取配置文件
     *
     * @param filename
     * @param class1
     * @return
     * @throws Exception
     */
    public static <T extends XlsProperty> ArrayList<T> loadToArray(String filename, Class<T> class1) throws Exception {
        // 对单独的文件进行加锁
        File file = new File(filename);
        ArrayList<T> list = new ArrayList<T>();
        XlsRead.parseFileToArray(filename, class1, list);
        long time = file.lastModified();
        m_timeMap.put(filename, time);
        return list;
    }

    /**
     * 如果改变才会返回 没变化会抛出异常。外面应该捕捉 并不影响
     *
     * @param filename
     * @param class1
     * @return
     * @throws Exception
     */
    public static <T extends XlsProperty> List<T> loadToHashMapWhenChange(String filename, Class<T> class1)
            throws Exception {
        // 对单独的文件进行加锁
        long lasttime = 0;
        if (m_timeMap.get(filename) != null) {
            lasttime = m_timeMap.get(filename);
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new Exception("配置文件错误" + filename);
        }
        long time = file.lastModified();
        if (time == lasttime) {
            throw new FailedLoginException(filename + "文件并未改变");
        }
        return loadToHashMap(filename, class1);
    }

    /**
     * 如果改变才会返回 没变化会抛出异常。外面应该捕捉 并不影响
     */
    public static <K extends XlsPage<V>, V extends XlsProperty> HashMap<String, K> loadPageWhenChange(String filename,
                                                                                                      Class<K> clazzk, Class<V> clazz) throws Exception {
        // 对单独的文件进行加锁
        long lasttime = 0;
        if (m_timeMap.get(filename) != null) {
            lasttime = m_timeMap.get(filename);
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new Exception("配置文件错误" + filename);
        }
        long time = file.lastModified();
        if (time == lasttime) {
            throw new FailedLoginException(filename + "文件并未改变");
        }

        return XlsRead.parseFileToPage(filename, clazzk, clazz);
    }

    /**
     * 直接读取配置文件
     */
    public static <T extends XlsProperty> List<T> loadToHashMap(String filename, Class<T> class1)
            throws Exception {
        File file = new File(filename);
        ArrayList<T> list = new ArrayList<T>();
        XlsRead.parseFileToArray(filename, class1, list);
        m_timeMap.put(filename, file.lastModified());
        return list;
    }
}
