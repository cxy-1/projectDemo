package Util.Arithmetic.System;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 反射实例化工具类
 *
 * @Modify chenjzh
 * @Date 10:05 2016/12/27
 */
public class ClassUtil2 {

    public static void addClasspath(String path) {
        System.out.println("Adding path " + path + " to class path");
        File file = new File(path);

        try {
            if (file.exists()) {
                URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                Class<URLClassLoader> urlClass = URLClassLoader.class;
                Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
                method.setAccessible(true);
                method.invoke(urlClassLoader, new Object[] { file.toURI().toURL() });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final WeakHashMap<String, Class<?>> forNameCache = new WeakHashMap<>();
    private static final Map<String, String> classRenameMap;
    static {
        classRenameMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> forName(String name, Class<T> clz) throws ClassNotFoundException {
        String origName = name;

        Class<? extends T> result = (Class<? extends T>) forNameCache.get(origName);
        if (result == null) {
            result = (Class<? extends T>) Class.forName(name);
            forNameCache.put(origName, result);
        }
        return result;
    }

    public static Object newInstance(String clz) {
        try {
            return forName(clz, Object.class).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
