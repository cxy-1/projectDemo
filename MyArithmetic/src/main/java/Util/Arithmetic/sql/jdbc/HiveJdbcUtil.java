package Util.Arithmetic.sql.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sugon.executor.core.util.common.ResourceLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xyl
 *
 * hive jdbc 工具类
 */
public class HiveJdbcUtil {

    private final Logger LOG = LoggerFactory.getLogger(HiveJdbcUtil.class);

    private final String driverName = "org.apache.hive.jdbc.HiveDriver";

    private final String hiveThriftUrl = "jdbc:hive2://ip:port/";

    private static HiveJdbcUtil util;

    private static String username ;

    private  static String password ;

    private static String port;

    private static String ip;

    private String url;

    private List<String> urls;

    static {
        port= ResourceLoader.getExecutorProperties("hive.thrift.port");
        ip=ResourceLoader.getExecutorProperties("hive.thrift.ip");
        password=ResourceLoader.getExecutorProperties("hive.thrift.password");
        username=ResourceLoader.getExecutorProperties("hive.thrift.username");
    }
    private HiveJdbcUtil() throws ClassNotFoundException {


        if (StringUtils.isBlank(port) || StringUtils.isBlank(ip)) {
            LOG.error("获取Hive Thrift Ip 或 Port为空!");
            throw new RuntimeException("Hive Thrift Ip or Port is Empty!");
        }
        urls = Lists.newArrayList();
        List<String> ips = Arrays.asList(ip.replaceAll(" ", "").trim()
                .split(","));
        for (String ipLine : ips) {
            String hiveUrl = hiveThriftUrl;
            if (ipLine.contains(";")) {
                hiveUrl = hiveUrl.replace("ip:port", url);
            } else {
                hiveUrl = hiveUrl.replace("ip", ipLine).replace("port", port);
            }
            urls.add(hiveUrl);
        }
        if (ips.size() != 0) {
            url = urls.get(0);
        }

        Class.forName(driverName);
    }

    /**
     * 获取Hive Thrift Jdbc实例
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static HiveJdbcUtil getInstance() throws ClassNotFoundException,
            SQLException {
        if (util == null) {
            synchronized (HiveJdbcUtil.class) {
                util = new HiveJdbcUtil();
            }
        }
        return util;
    }

    /**
     * 获取Hive Thrift连接,如果当前URL不可用,轮询所有URL找到可用连接
     *
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Connection con = null;
        String usingUrl = url;
        for (String urlTemp : urls) {
            try {
                con = DriverManager.getConnection(usingUrl, username, password);
                // 如果现在默认使用的URL和可用的URL不相同,修改当前可用URL
                if (url == null || !url.equals(usingUrl)) {
                    synchronized (HiveJdbcUtil.class) {
                        url = usingUrl;
                    }
                }
                LOG.info("当前使用Hive Thrift地址为" + url);
                return con;
            } catch (Exception e) {
                LOG.error("Hive Thrift连接失败,IP地址为：" + url);
                LOG.error("开始使用" + urlTemp + "重试!");
                usingUrl = urlTemp;
            }
        }
        LOG.error("所有Hive Thrift连接都不可用,请检查配置文件!");
        return con;
    }

    private void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (!conn.isClosed()) {
                // 关闭数据库连接
                conn.close();
            }
        } catch (SQLException e) {
            LOG.error("#ERROR-HiveJdbc# :关闭数据库连接发生异常，请检查！", e);
        }
    }

    /**
     * 查询sql语句,返回键值类型
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySqlForKeyValue(String sql)
            throws Exception {
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error("查询sql失败:" + sql, ex);
            throw new SQLException("查询sql失败:" + sql);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                closeConnection(con);
            }
        }
        return list;
    }

    /**
     * 执行sql语句
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public boolean excuteSql(String sql) throws Exception {
        Connection con = getConnection();
        boolean flag = false;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            flag = stmt.execute(sql);
        } catch (SQLException e) {
            LOG.error("执行sql失败:" + sql, e);
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
     * 查询sql语句,返回List<String[]>
     *
     * String[]为一列数据
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<String[]> queryRows(String sql) throws Exception {
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<String[]>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = String.valueOf(rs.getObject(i + 1));
                }
                list.add(row);
            }
            LOG.info("表列信息:" + list.toString());
        } catch (SQLException ex) {
            LOG.error("查询sql失败:" + sql, ex);
            throw new SQLException("查询sql失败:" + sql);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                closeConnection(con);
            }
        }
        return list;
    }

    /**
     * 执行多条sql语句
     *
     * @param
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
                    LOG.info("===hive==execute==sql===" + sqlTemp);
                    stmt.execute(sqlTemp);
                }
                flag=true;
            }
        } catch (SQLException e) {
            LOG.error("执行sql失败:" + sqlTemp, e);
            throw new SQLException("执行sql失败:" + sqlTemp);
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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        HiveJdbcUtil.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        HiveJdbcUtil.password = password;
    }
}

