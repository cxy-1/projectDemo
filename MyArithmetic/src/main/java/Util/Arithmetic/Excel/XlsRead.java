package Util.Arithmetic.Excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

//lib jxl-2.6.12.jar

public class XlsRead {
    public static final byte CHAR_VARS_START = ';';
    public static final byte CHAR_COMMENT_START = '/';
    public static final byte CHAR_NOT_INPUT = '#';

    /**
     * /** 解析Excel数据
     */
    @SuppressWarnings("unchecked")
    public static <T extends XlsProperty> void parseFileToArray(String fileName, Class<T> clazz,
                                                                ArrayList<T> list) throws Exception {
        WorkbookSettings wbs = new WorkbookSettings();
        wbs.setSuppressWarnings(true);
        Workbook book = Workbook.getWorkbook(new File(fileName), wbs);
        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        Field[] fields = null;
        for (int i = 0; i < rows; i++) {
            String[] strs = new String[cols];
            for (int j = 0; j < cols; j++) {
                Cell c = sheet.getCell(j, i);
                strs[j] = c.getContents();
            }
            if (strs[0].length() == 0) {
                continue;
            }
            if (strs[0].charAt(0) == CHAR_COMMENT_START) {
                continue;
            } else if (strs[0].charAt(0) == CHAR_VARS_START) {
                fields = getFields(clazz, strs);
            } else {
                list.add((T) parseLineToObj(strs, fields, clazz));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <K extends XlsPage<V>, V extends XlsProperty> HashMap<String, K> parseFileToPage(
            String fileName, Class<K> clazzk, Class<V> clazz) throws Exception {
        HashMap<String, K> list = new HashMap<String, K>();
        WorkbookSettings wbs = new WorkbookSettings();
        wbs.setSuppressWarnings(true);
        Workbook book = Workbook.getWorkbook(new File(fileName), wbs);
        int num = book.getNumberOfSheets();
        for (int page = 0; page < num; page++) {
            Sheet sheet = book.getSheet(page);
            K k = clazzk.newInstance();
            k.setName(sheet.getName());
            int rows = sheet.getRows();
            int cols = sheet.getColumns();
            Field[] fields = null;
            for (int i = 0; i < rows; i++) {
                String[] strs = new String[cols];
                for (int j = 0; j < cols; j++) {
                    Cell c = sheet.getCell(j, i);
                    strs[j] = c.getContents();
                }
                if (strs[0].length() == 0) {
                    continue;
                }
                if (strs[0].charAt(0) == CHAR_COMMENT_START) {
                    continue;
                } else if (strs[0].charAt(0) == CHAR_VARS_START) {
                    fields = getFields(clazz, strs);
                } else {
                    k.addItem((V) parseLineToObj(strs, fields, clazz));
                }
            }
            list.put(k.getName(), k);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <V extends XlsProperty> ArrayList<V> parsePage(String fileName, Class<V> clazz,
                                                                 int page) throws Exception {
        ArrayList<V> list = new ArrayList<V>();
        WorkbookSettings wbs = new WorkbookSettings();
        wbs.setSuppressWarnings(true);
        Workbook book = Workbook.getWorkbook(new File(fileName), wbs);
        Sheet sheet = book.getSheet(page);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        Field[] fields = null;
        for (int i = 0; i < rows; i++) {
            String[] strs = new String[cols];
            for (int j = 0; j < cols; j++) {
                Cell c = sheet.getCell(j, i);
                strs[j] = c.getContents();
            }
            if (strs[0].length() == 0) {
                continue;
            }
            if (strs[0].charAt(0) == CHAR_COMMENT_START) {
                continue;
            } else if (strs[0].charAt(0) == CHAR_VARS_START) {
                fields = getFields(clazz, strs);
            } else {
                list.add((V) parseLineToObj(strs, fields, clazz));
            }
        }
        return list;
    }

    // 把一行解析成一个对象
    private static Object parseLineToObj(String[] value, Field[] fields,
                                         Class<? extends XlsProperty> clazz)
            throws InstantiationException, IllegalAccessException {
        Object o = clazz.newInstance();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (f == null) {
                continue;
            }
            if (f.getType().equals(byte.class)) {
                f.setByte(o, decodeByte(value[i]));
            } else if (f.getType().equals(short.class)) {
                f.setShort(o, decodeShort(value[i]));
            } else if (f.getType().equals(int.class)) {
                f.setInt(o, decodeInt(value[i]));
            } else if (f.getType().equals(float.class)) {
                f.setFloat(o, decodeFloat(value[i]));
            } else if (f.getType().equals(long.class)) {
                f.setLong(o, decodeLong(value[i]));
            } else if (f.getType().equals(double.class)) {
                f.setDouble(o, decodeDouble(value[i]));
            } else if (f.getType().equals(int[].class)) {
                int[] dst = (int[]) f.get(o);
                if (dst == null) {
                    int leight = getArrayLeight(fields, value, i);
                    dst = new int[leight];
                    f.set(o, dst);
                }
                parseIntArray(value, fields, i, dst);
            } else if (f.getType().equals(float[].class)) {
                float[] dst = (float[]) f.get(o);
                if (dst == null) {
                    int leight = getArrayLeight(fields, value, i);
                    dst = new float[leight];
                    f.set(o, dst);
                }
                parseFloatArray(value, fields, i, dst);
            } else if (f.getType().equals(long[].class)) {
                long[] dst = (long[]) f.get(o);
                if (dst == null) {
                    int leight = getArrayLeight(fields, value, i);
                    dst = new long[leight];
                    f.set(o, dst);
                }
                parseLongArray(value, fields, i, dst);
            } else if (f.getType().equals(String[].class)) {
                String[] dst = (String[]) f.get(o);
                if (dst == null) {
                    int leight = getArrayLeight(fields, value, i);
                    dst = new String[leight];
                    f.set(o, dst);
                }
                parseStringArray(value, fields, i, dst);
            } else if (f.getType().equals(Class.class)) {
                f.set(o, decodeClass(value[i]));
            } else if (f.getType().equals(double[].class)) {
                double[] dst = (double[]) f.get(o);
                if (dst == null) {
                    int leight = getArrayLeight(fields, value, i);
                    dst = new double[leight];
                    f.set(o, dst);
                }
                parseDoubleArray(value, fields, i, dst);
            } else {
                f.set(o, value[i]);
            }
        }
        return o;
    }

    private static Class<?> decodeClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串数组从指定索引转化成字符串数组 字符串数组将不包含指定索引
     *
     * @param value
     * @param startIndex
     * @return
     */
    private static void parseStringArray(String[] value, Field[] fields, int startIndex,
                                         String[] r) {
        for (int i = 0; i < r.length; i++) {
            if (startIndex + i >= value.length) {
                r[i] = "";
            } else {
                r[i] = value[startIndex + i];
            }
        }
    }

    public static boolean isCanConverToInt(String s) {
        try {
            Integer.decode(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将字符串数组从指定索引转化成int数组 int数组将不包含指定索引
     *
     * @param value
     * @param startIndex
     * @return
     */
    private static void parseIntArray(String[] value, Field[] fields, int startIndex, int[] r) {
        for (int i = 0; i < r.length; i++) {
            if (startIndex + i >= value.length) {
                r[i] = 0;
            } else {
                r[i] = decodeInt(value[startIndex + i]);
            }
        }
    }

    /**
     * 将字符串数组从指定索引转化成int数组 int数组将不包含指定索引
     *
     * @param value
     * @param startIndex
     * @return
     */
    private static void parseFloatArray(String[] value, Field[] fields, int startIndex, float[] r) {
        for (int i = 0; i < r.length; i++) {
            if (startIndex + i >= value.length) {
                r[i] = 0;
            } else {
                r[i] = decodeFloat(value[startIndex + i]);
            }
        }
    }

    /**
     * 将字符串数组从指定索引转化成int数组 int数组将不包含指定索引
     *
     * @param value
     * @param startIndex
     * @return
     */
    private static void parseLongArray(String[] value, Field[] fields, int startIndex, long[] r) {
        for (int i = 0; i < r.length; i++) {
            if (startIndex + i >= value.length) {
                r[i] = 0;
            } else {
                r[i] = decodeLong(value[startIndex + i]);
            }
        }
    }

    /**
     * 将字符串数组从指定索引转化成int数组 int数组将不包含指定索引
     *
     * @param value
     * @param startIndex
     * @return
     */
    private static void parseDoubleArray(String[] value, Field[] fields, int startIndex,
                                         double[] r) {
        for (int i = 0; i < r.length; i++) {
            if (startIndex + i >= value.length) {
                r[i] = 0;
            } else {
                r[i] = decodeDouble(value[startIndex + i]);
            }
        }
    }

    /**
     * 将字符串转化成数字
     *
     * @param str
     * @return
     */
    private static float decodeFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private static short decodeShort(String str) {
        try {
            return Short.decode(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private static byte decodeByte(String str) {
        try {
            return Byte.decode(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将字符串转化成数字
     *
     * @param str
     * @return
     */
    private static int decodeInt(String str) {
        try {
            return Integer.decode(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将字符串转化成0 字(long)
     *
     * @param str
     * @return
     */
    private static long decodeLong(String str) {
        try {
            return Long.decode(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将字符串转化成0 字(Double)
     *
     * @param str
     * @return
     */
    private static double decodeDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据变量名的字符串取得变量对象
     *
     * @param clazz
     * @param strs
     * @return
     * @throws Exception
     */
    private static Field[] getFields(Class<? extends XlsProperty> clazz, String[] strs)
            throws Exception {
        Field[] fields = new Field[strs.length];
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            if (s.length() > 0) {
                if (s.charAt(0) == CHAR_VARS_START) {
                    s = s.substring(1, s.length());
                }
                if (s.charAt(0) == CHAR_NOT_INPUT) {
                    // 不读取的标签
                    fields[i] = null;
                    continue;
                }
                Field field = getField(clazz, s);
                fields[i] = field;
                if (field == null) {
                    continue;
                }
                fields[i].setAccessible(true);
            }
        }
        return fields;
    }

    private static Field getField(Class<?> clazz, String s) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(s);
        } catch (NoSuchFieldException e) {
            return getField(clazz.getSuperclass(), s);
        } catch (NullPointerException e) {
        }
        return field;
    }

    // 计算数组的长度
    private static int getArrayLeight(Field[] fields, String[] values, int startIndex) {
        if (values[startIndex].length() == 0) {
            return 0;
        }
        int leight = 1;
        for (int i = startIndex + 1; i < fields.length; i++) {
            if (fields[i] != null || values[i].length() == 0) {
                break;
            }
            leight++;
        }
        return leight;
    }
}
