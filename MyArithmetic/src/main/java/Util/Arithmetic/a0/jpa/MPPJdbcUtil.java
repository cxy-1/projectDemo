package Util.Arithmetic.a0.jpa;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MPPJdbcUtil(PostgreSql华为版)
 *
 */
public class MPPJdbcUtil {

    private final Logger logger = LoggerFactory.getLogger(MPPJdbcUtil.class);

    private static MPPJdbcUtil util;

    private final String driverName = "org.postgresql.Driver";
    private String urlTemplate = "jdbc:postgresql://ip:port/catalogName";
    private static String username = "sugon";
    private static String password = "123456";
    private static String port ="25308";

    private List<String> urls ;
    private String currentUrl;

    private MPPJdbcUtil() throws ClassNotFoundException {
        //从配置文件中获取
        String _username = ResourceLoader.getEnvProperties("mpp.username");
        String _password = ResourceLoader.getEnvProperties("mpp.password");
        String _port = ResourceLoader.getEnvProperties("mpp.port");
        String _ips = ResourceLoader.getEnvProperties("mpp.ip");
        String _catalogName = ResourceLoader.getEnvProperties("mpp.catalog.name");

        if(StringUtils.isNotBlank(_username)){
            MPPJdbcUtil.username = _username;
        }
        if(StringUtils.isNotBlank(_password)){
            MPPJdbcUtil.password = _password;
        }
        if(StringUtils.isNotBlank(_port)){
            MPPJdbcUtil.port = _port;
        }

        urls = Lists.newArrayList();
        List<String> ipList = Arrays.asList(_ips.replaceAll(" ","").trim().split(","));
        for(String ipLine : ipList){
            String urltmp = urlTemplate;
            urltmp = urltmp.replace("ip", ipLine).replace("port", port).replace("catalogName",_catalogName);
            urls.add(urltmp);
        }

        if(ipList.size()!=0){
            currentUrl = urls.get(0);
        }
    }

    private MPPJdbcUtil(String url) throws ClassNotFoundException {
        //从参数中获取
        urls.add(url);
        currentUrl = url;
    }

    /**
     * 使用jsdk指定的mpp地址
     * @return
     * @throws ClassNotFoundException
     */
    public static MPPJdbcUtil getInstance() throws ClassNotFoundException {
        if(util == null){
            synchronized (MPPJdbcUtil.class){
                util = new MPPJdbcUtil();
            }
        }
        return util;
    }

    /**
     * 使用url指定的mpp地址
     * @param url
     * @return
     * @throws ClassNotFoundException
     */
    public static MPPJdbcUtil getInstance(String url) throws ClassNotFoundException {
        if(util == null){
            synchronized (MPPJdbcUtil.class){
                util = new MPPJdbcUtil(url);
            }
        }
        return util;
    }

    public Connection getConnection() {
        Connection conn = null;
        String usingUrl = currentUrl;
        try {
            Class.forName(driverName).newInstance();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        for(String urlTmp : urls){
            try {
                conn = DriverManager.getConnection(usingUrl,username,password);
                if(currentUrl==null || !currentUrl.equals(usingUrl)){
                    synchronized (MPPJdbcUtil.class){
                        currentUrl = usingUrl;
                    }
                }
                logger.info("当前mpp的url地址为:"+currentUrl);
                return conn;
            } catch (SQLException e) {
                logger.error("mpp 连接地址连接失败" + currentUrl);
                logger.error("开始使用:"+urlTmp);
                usingUrl = urlTmp;
                e.printStackTrace();
            }
        }
        logger.error("当前mpp url连接不可用");
        return conn;
    }

    private void closeConnection(Connection conn){
        if(conn == null){
            return ;
        }
        try {
            if(!conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("关闭mpp jdbc连接");
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> querySqlForKeyValue(String sql){
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            logger.info("query sql content===:"+sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while(rs.next()){
                Map<String,Object> map = Maps.newHashMap();
                for(int i=1;i<=columnCount;i++){
                    map.put(md.getColumnLabel(i),rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            logger.error("查询sql失败："+sql,e);
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                closeConnection(conn);
            }
        }
        return list;
    }

    public List<String[]> queryRows(String sql){
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<String[]>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()){
                String[] row = new String[columnCount];
                for(int i=0;i<columnCount;i++){
                    row[i] = (String)rs.getObject(i+1);
                }
                list.add(row);
            }
        } catch (SQLException e) {
            logger.error("查询sql失败"+sql,e);
            e.printStackTrace();
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt !=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                closeConnection(conn);
            }
        }
        return list;
    }

    public String executeQueryForJson(String sql){
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String json = "";
        try {
            logger.info("query sql content====:"+sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            JSONArray array = new JSONArray();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while(rs.next()){
                JSONObject jsonObject = new JSONObject();
                for(int i=1;i<=columnCount;i++){
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObject.put(columnName,value);
                }
                array.add(jsonObject);
            }
            json = array.toJSONString();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt !=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                closeConnection(conn);
            }
        }
        return json;
    }

    public boolean excuteSql(String sql) throws Exception {
        Connection con = getConnection();
        boolean flag = true;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("执行sql失败:" + sql, e);
            throw new SQLException("执行sql失败:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                closeConnection(con);
            }
        }
        return flag;
    }

    /**
     * 从文件导入mpp
     * @param fPath 文件路径
     * @param tableName
     * @param delimiter
     */
    public boolean copyData(String fPath, String tableName, String delimiter){
        FileInputStream fs =null;
        Connection conn = null;
        if(delimiter == null || "".equals(delimiter)){
            delimiter = ",";
        }
        try {
            conn = getConnection();
            CopyManager cm = new CopyManager((BaseConnection)conn);
            fs = new FileInputStream(fPath);
            String sql = "COPY "+ tableName +" FROM STDIN DELIMITER AS '"+ delimiter +"' ";
            cm.copyIn(sql,fs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                fs.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            if(fs!=null){
                closeConnection(conn);
            }
        }
        return false;
    }


}
