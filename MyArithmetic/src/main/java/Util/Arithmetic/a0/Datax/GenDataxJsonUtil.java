package Util.Arithmetic.a0.Datax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgc on 2017/2/7 0007.
 */
public class GenDataxJsonUtil {

    /**
     * 生成datax ftp 读取的json数据
     * 支持压缩 gz、zip、gzip、bzip2 默认不压缩
     *
     * @param parameMap  常用参数
     * @param columnList 列类型、名字
     * @param childPath  子路径
     * @return
     */
    public static String generateFtpJson(Map<String, String> parameMap, List<Map<String, String>> columnList, String childPath) {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("{\n");
        resultBuffer.append("\"setting\": {},\n");
        resultBuffer.append("\"job\": {\n");
        resultBuffer.append(" \"setting\": {\n");
        resultBuffer.append("\"speed\": {\n");
        resultBuffer.append("\"channel\": 6\n");
        resultBuffer.append("}\n");
        resultBuffer.append("},\n");
        resultBuffer.append("\"content\": [\n");
        resultBuffer.append("{\n");
        resultBuffer.append("\"reader\": {\n");
        resultBuffer.append("\"name\": \"ftpreader\",\n");
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"protocol\": \"");
        resultBuffer.append(parameMap.get("protocol"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"host\": \"");
        resultBuffer.append(parameMap.get("host"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"port\":");
        resultBuffer.append(Integer.parseInt(parameMap.get("port")));
        resultBuffer.append(",\n");
        resultBuffer.append("\"username\": \"");
        resultBuffer.append(parameMap.get("username"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"password\": \"");
        resultBuffer.append(parameMap.get("password"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"path\": [\n\"");
        resultBuffer.append(parameMap.get("path"));
        resultBuffer.append("\"\n");
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"column\": [\n");
        int dataSize = columnList.size();
        int i = 0;
        for (Map<String, String> map : columnList) {
            resultBuffer.append(" {\n");
            resultBuffer.append(" \"index\":");
            resultBuffer.append(i);
            resultBuffer.append(",\n");
            resultBuffer.append(" \"type\": \"");
            String type = ResourceLoader.getDtsProperties(map.get("TYPE_NAME"));
            if ("INT".equals(type)) {
                type = "LONG";
            }
            resultBuffer.append(type);
            resultBuffer.append("\"\n  }");
            if (i < dataSize - 1) {
                resultBuffer.append(",\n");
            }
            i++;
        }
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"encoding\": \"UTF-8\",\n");
        resultBuffer.append(" \"fieldDelimiter\": \"");
        resultBuffer.append(parameMap.get("fieldDelimiter"));
        if (parameMap.containsKey("compress")) {
            resultBuffer.append("\",\n");
            resultBuffer.append(" \"compress\": \"");
            resultBuffer.append(parameMap.get("compress"));
            resultBuffer.append("\"\n");
        } else {
            resultBuffer.append("\"\n");
        }
        resultBuffer.append("}\n");
        resultBuffer.append(" },\n");
        resultBuffer.append("  \"writer\": {\n");
        resultBuffer.append("\"name\": \"hdfswriter\",\n");
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"defaultFS\": ");
        resultBuffer.append("\"");
        String hdfsUrl = ResourceLoader.getExtraProperties("hdfs.http.url");
        resultBuffer.append(hdfsUrl);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fileType\": \"Parquet\",\n");
        String tableName = parameMap.get("tablename");
        String filePath = "/apps/hive/warehouse/" + childPath;
        resultBuffer.append("\"path\": \"");
        resultBuffer.append(filePath);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fileName\": \"");
        resultBuffer.append(tableName);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fieldDelimiter\": \"|\",\n");
        resultBuffer.append("\"column\": [\n");
        int j = 0;
        for (Map<String, String> map : columnList) {
            resultBuffer.append(" {\n");
            resultBuffer.append(" \"name\":");
            resultBuffer.append("\"");
            resultBuffer.append(map.get("COLUMN_NAME"));
            resultBuffer.append("\"");
            resultBuffer.append(",\n");
            resultBuffer.append(" \"type\": \"");
            String type = ResourceLoader.getDtsProperties(map.get("TYPE_NAME"));
            resultBuffer.append(type);
            resultBuffer.append("\"\n  }");
            if (j < dataSize - 1) {
                resultBuffer.append(",\n");
            }
            j++;
        }
        resultBuffer.append("  ],\n");
        resultBuffer.append(" \"writeMode\": \"append\",\n");
        resultBuffer.append(" \"compress\": \"NONE\"\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("  ]\n");
        resultBuffer.append("  }\n");
        resultBuffer.append("  }\n");
        return resultBuffer.toString();
    }

    /**
     * 生成datax  读取hdfs json数据
     * 支持压缩 gz、zip、gzip、bzip2 默认不压缩
     *
     * @param parameMap  常用参数
     * @param columnList 列类型、名字
     * @param childPath  子路径
     * @return
     */
    public static String generateHdfsJson(Map<String, String> parameMap, List<Map<String, String>> columnList, String childPath) {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("{\n");
        resultBuffer.append("\"setting\": {},\n");
        resultBuffer.append("\"job\": {\n");
        resultBuffer.append(" \"setting\": {\n");
        resultBuffer.append("\"speed\": {\n");
        resultBuffer.append("\"channel\": 6\n");
        resultBuffer.append("}\n");
        resultBuffer.append("},\n");
        resultBuffer.append("\"content\": [\n");
        resultBuffer.append("{\n");
        resultBuffer.append("\"reader\": {\n");
        resultBuffer.append("\"name\": \"hdfsreader\",\n");
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"defaultFS\": \"");
        resultBuffer.append(ResourceLoader.getExtraProperties("hdfs.http.url"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"path\": [\n\"");
        resultBuffer.append(parameMap.get("path"));
        resultBuffer.append("\"\n");
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"column\": [\n");
        int dataSize = columnList.size();
        int i = 0;
        for (Map<String, String> map : columnList) {
            resultBuffer.append(" {\n");
            resultBuffer.append(" \"index\":");
            resultBuffer.append(i);
            resultBuffer.append(",\n");
            resultBuffer.append(" \"type\": \"");
            String type = ResourceLoader.getDtsProperties(map.get("TYPE_NAME"));
            if ("INT".equals(type)) {
                type = "LONG";
            }
            resultBuffer.append(type);
            resultBuffer.append("\"\n  }");
            if (i < dataSize - 1) {
                resultBuffer.append(",\n");
            }
            i++;
        }
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"encoding\": \"UTF-8\",\n");
        resultBuffer.append(" \"fileType\": \"text\",\n");
        resultBuffer.append(" \"fieldDelimiter\": \"");
        resultBuffer.append(parameMap.get("fieldDelimiter"));
        resultBuffer.append("\"\n");
        resultBuffer.append("}\n");
        resultBuffer.append(" },\n");
        resultBuffer.append("  \"writer\": {\n");
        resultBuffer.append("\"name\": \"hdfswriter\",\n");
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"defaultFS\": ");
        resultBuffer.append("\"");
        String hdfsUrl = ResourceLoader.getExtraProperties("hdfs.http.url");
        resultBuffer.append(hdfsUrl);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fileType\": \"Parquet\",\n");
        String tableName = parameMap.get("tablename");
        String filePath = "/apps/hive/warehouse/" + childPath;
        resultBuffer.append("\"path\": \"");
        resultBuffer.append(filePath);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fileName\": \"");
        resultBuffer.append(tableName);
        resultBuffer.append("\",\n");
        resultBuffer.append("\"fieldDelimiter\": \"|\",\n");
        resultBuffer.append("\"column\": [\n");
        int j = 0;
        for (Map<String, String> map : columnList) {
            resultBuffer.append(" {\n");
            resultBuffer.append(" \"name\":");
            resultBuffer.append("\"");
            resultBuffer.append(map.get("COLUMN_NAME"));
            resultBuffer.append("\"");
            resultBuffer.append(",\n");
            resultBuffer.append(" \"type\": \"");
            String type = ResourceLoader.getDtsProperties(map.get("TYPE_NAME"));
            resultBuffer.append(type);
            resultBuffer.append("\"\n  }");
            if (j < dataSize - 1) {
                resultBuffer.append(",\n");
            }
            j++;
        }
        resultBuffer.append("  ],\n");
        resultBuffer.append(" \"writeMode\": \"append\",\n");
        resultBuffer.append(" \"compress\": \"NONE\"\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("  ]\n");
        resultBuffer.append("  }\n");
        resultBuffer.append("  }\n");
        return resultBuffer.toString();
    }

    /**
     * 生成datax  读取hdfs json数据
     * 支持压缩 gz、zip、gzip、bzip2 默认不压缩
     *
     * @param parameMap  常用参数
     * @param columnList 列类型、名字
     * @param childPath  子路径
     * @return
     */
    public static String generateHdfsToDbJson(Map<String, String> parameMap, List<Map<String, String>> columnList) {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("{\n");
        resultBuffer.append("\"setting\": {},\n");
        resultBuffer.append("\"job\": {\n");
        resultBuffer.append(" \"setting\": {\n");
        resultBuffer.append("\"speed\": {\n");
        resultBuffer.append("\"channel\": 6\n");
        resultBuffer.append("}\n");
        resultBuffer.append("},\n");
        resultBuffer.append("\"content\": [\n");
        resultBuffer.append("{\n");
        resultBuffer.append("\"reader\": {\n");
        resultBuffer.append("\"name\": \"hdfsreader\",\n");
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"defaultFS\": \"");
        resultBuffer.append(ResourceLoader.getExtraProperties("hdfs.http.url"));
        resultBuffer.append("\",\n");
        resultBuffer.append("\"path\": [\n\"");
        resultBuffer.append(parameMap.get("path"));
        resultBuffer.append("\"\n");
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"column\": [\n");
        int dataSize = columnList.size();
        int i = 0;
        for (Map<String, String> map : columnList) {
            resultBuffer.append(" {\n");
            resultBuffer.append(" \"index\":");
            resultBuffer.append(i);
            resultBuffer.append(",\n");
            resultBuffer.append(" \"type\": \"");
            String type = ResourceLoader.getDtsProperties(map.get("TYPE_NAME"));
            if ("INT".equals(type)) {
                type = "LONG";
            }
            resultBuffer.append(type);
            resultBuffer.append("\"\n  }");
            if (i < dataSize - 1) {
                resultBuffer.append(",\n");
            }
            i++;
        }
        resultBuffer.append(" ],\n");
        resultBuffer.append(" \"encoding\": \"UTF-8\",\n");
        resultBuffer.append(" \"fileType\": \"text\",\n");
        resultBuffer.append(" \"fieldDelimiter\": \"");
        resultBuffer.append(parameMap.get("fieldDelimiter"));
        resultBuffer.append("\"\n");
        resultBuffer.append("}\n");
        resultBuffer.append(" },\n");
        resultBuffer.append("  \"writer\": {\n");
        if ("1".equals(parameMap.get("dbType")))
        {
            resultBuffer.append("\"name\": \"mysqlwriter\",\n");
        }
        else if ("2".equals(parameMap.get("dbType")))
        {
            resultBuffer.append("\"name\": \"oraclewriter\",\n");
        }
        resultBuffer.append("\"parameter\": {\n");
        resultBuffer.append("\"writeMode\": \"insert\",\n");
        resultBuffer.append("\"username\": \""+ parameMap.get("username") +"\",\n");
        resultBuffer.append("\"password\": \"" + parameMap.get("password") + "\",\n");
        resultBuffer.append("\"column\": ['*'],\n");

        resultBuffer.append("\"connection\": [\n");
        resultBuffer.append(" {\n");
        resultBuffer.append(" \"jdbcUrl\":");
        resultBuffer.append("\"");
        resultBuffer.append(parameMap.get("jdbcURL"));
        resultBuffer.append("\"");
        resultBuffer.append(",\n");

        resultBuffer.append(" \"table\":");
        resultBuffer.append("['");
        resultBuffer.append(parameMap.get("tablename"));
        resultBuffer.append("']");
        resultBuffer.append("\n");
        resultBuffer.append("}\n");
        resultBuffer.append("]\n");

        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("   }\n");
        resultBuffer.append("  ]\n");
        resultBuffer.append("  }\n");
        resultBuffer.append("  }\n");
        return resultBuffer.toString();
    }

    public static void main(String[] args) {
        List<String> fieldTypeList = new ArrayList<String>();
        fieldTypeList.add("VARCHAR");
        fieldTypeList.add("VARCHAR");
        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.add("accesstime");
        fieldNameList.add("ip");
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("protocol", "sftp");
        hashMap.put("host", "localhost");
        hashMap.put("port", "22");
        hashMap.put("username", "root");
        hashMap.put("password", "111111");
        hashMap.put("path", "\"/home/ftp1/luke");
        hashMap.put("fieldDelimiter", "|");
        hashMap.put("compress", "gz");
        hashMap.put("tablename", "aaaaa");
        hashMap.put("dbType", "1");
        hashMap.put("jdbcURL", "jdbc:mysql://10.10.10.21:3309/test?user=root&password=root123");
        List<Map<String, String>> columnMapList = new ArrayList<Map<String,String>>();
        for (int i=0; i < 5; i++) {
            Map<String, String> columnMap = new HashMap<String, String>();
            columnMap.put("COLUMN_NAME", "NAME" + i);
            columnMap.put("TYPE_NAME", "VARCHAR");
            columnMapList.add(columnMap);
        }
        System.out.println(generateHdfsToDbJson(hashMap, columnMapList));
    }
}

