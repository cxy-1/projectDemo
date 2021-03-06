package Util.Arithmetic.a0.Hive;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by XYL
 */
public class HiveJdbcKUtil {

    private final Logger logger = LoggerFactory.getLogger(HiveJdbcKUtil.class);

    private static HiveJdbcKUtil util;

    private final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private String urlTemplate = "";
    private Connection conn = null;

    private HiveJdbcKUtil() throws ClassNotFoundException {
        Class.forName(driverName);
    }

    public static HiveJdbcKUtil getInstance() throws ClassNotFoundException {
        if(util == null){
            synchronized (HiveJdbcKUtil.class){
                util = new HiveJdbcKUtil();
            }
        }
        return util;
    }

    public Connection getConnection() {
        try {
            urlTemplate = ResourceLoader.getEnvProperties("hive.kerberos.url");
            String kbr5 = ResourceLoader.getEnvProperties("hive.kerberos.kbr5");
            //String keytab = ResourceLoader.getEnvProperties("hive.kerberos.keytab");
            //String name = ResourceLoader.getEnvProperties("hive.thrift.username");
            //String ktname = ResourceLoader.getEnvProperties("hive.thrift.ktusername");
            //String password = ResourceLoader.getEnvProperties("hive.thrift.password");
            //String qop = ResourceLoader.getEnvProperties("hive.server2.thrift.sasl.qop");
            //String authentication = ResourceLoader.getEnvProperties("hadoop.security.authentication");
            //String rpcProtection = ResourceLoader.getEnvProperties("hadoop.rpc.protection");

            System.setProperty("java.security.krb5.debug", "true");
            System.setProperty("zookeeper.server.principal", "zookeeper/hadoop.hadoop.com");


            System.setProperty("java.security.krb5.conf", kbr5);
	        
        	/*Configuration conf = new Configuration();
	        conf.set("hadoop.security.authentication", authentication);
	        conf.set("hadoop.rpc.protection", rpcProtection);
	        UserGroupInformation.setConfiguration(conf);
	        UserGroupInformation.loginUserFromKeytab(ktname,keytab);
	        */
            //conn  = DriverManager.getConnection(urlTemplate,name,password);
	        
	        /*Properties info = new Properties();
	        info.setProperty("user", name);
	        info.setProperty("password", password);
	        info.setProperty("hiveconf:hive.server2.thrift.sasl.qop", qop);
	        conn = DriverManager.getConnection(urlTemplate, info);*/
            System.out.println("urlTemplate="+urlTemplate);
            conn  = DriverManager.getConnection(urlTemplate, "", "");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            logger.error("�ر� hive jdbc����");
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> querySqlForKeyValue(String sql) throws Exception{
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
            logger.error("��ѯsqlʧ�ܣ�"+sql,e);
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
            logger.error("��ѯsqlʧ��"+sql,e);
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
            logger.error("ִ��sqlʧ��:" + sql, e);
            e.printStackTrace();
            throw new SQLException("ִ��sqlʧ��:" + sql);
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
     * ִ�ж���sql���
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public boolean excuteMultSql(String[] sqls) throws Exception {
        Connection con = getConnection();
        boolean flag = false;
        Statement stmt = null;
        String sqlTemp = null;
        try {
            stmt = con.createStatement();
            if (sqls != null) {
                for (String sql : sqls) {
                    sqlTemp = sql;
                    stmt.execute(sqlTemp);
                }
                flag=true;
            }
        } catch (SQLException e) {
            logger.error("ִ��sqlʧ��:" + sqlTemp, e);
            throw new SQLException("ִ��sqlʧ��:" + sqlTemp);
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

}
c