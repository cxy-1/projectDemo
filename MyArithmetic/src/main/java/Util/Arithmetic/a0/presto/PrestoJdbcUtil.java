package Util.Arithmetic.a0.presto;

/**
 * Created by XYL
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @author xyl
 *
 * presto jdbc util
 */
public class PrestoJdbcUtil {

	private final Logger logger = LoggerFactory.getLogger(PrestoJdbcUtil.class);
	public final static int MAP_SIZE = 16;
	private static PrestoJdbcUtil util;

	private final String driverName = "com.facebook.presto.jdbc.PrestoDriver";
	private String urlTemplate = "jdbc:presto://ip:port/catalogName/schemaName";
	private static String username = "presto";
	private static String password = "presto";
	private static String port = "9000";

	private List<String> urls;
	private String currentUrl;

	private PrestoJdbcUtil() throws ClassNotFoundException {
		//从配置文件中获取
		String userName = ResourceLoader.getExecutorProperties("presto.username");
		String password = ResourceLoader.getExecutorProperties("presto.password");
		String port = ResourceLoader.getExecutorProperties("presto.port");
		String ips = ResourceLoader.getExecutorProperties("presto.ip");
		String catalogName = ResourceLoader.getExecutorProperties("presto.catalog.name");
		String schemaName = ResourceLoader.getExecutorProperties("presto.schema.name");

		if (StringUtils.isNotBlank(userName)) {
			PrestoJdbcUtil.username = userName;
		}
		if (StringUtils.isNotBlank(password)) {
			PrestoJdbcUtil.password = password;
		}
		if (StringUtils.isBlank(port)) {
			port = PrestoJdbcUtil.port;
		}

		urls = new ArrayList<String>();
		List<String> ipList = Arrays.asList(ips.replaceAll(" ", "").trim().split(","));
		for (String ipLine : ipList) {
			String urltmp = urlTemplate;
			urltmp = urltmp.replace("ip", ipLine).replace("port", port).replace("catalogName", catalogName)
					.replace("schemaName", schemaName);
			urls.add(urltmp);
		}

		if (ipList.size() != 0) {
			currentUrl = urls.get(0);
		}
		Class.forName(driverName);
	}

	public static PrestoJdbcUtil getInstance() throws ClassNotFoundException {
		if (util == null) {
			synchronized (PrestoJdbcUtil.class) {
				util = new PrestoJdbcUtil();
			}
		}
		return util;
	}

	public Connection getConnection() {
		Connection conn = null;
		String usingUrl = currentUrl;
		for (String urlTmp : urls) {
			try {
				conn = DriverManager.getConnection(usingUrl, username, password);
				if (currentUrl == null || !currentUrl.equals(usingUrl)) {
					synchronized (PrestoJdbcUtil.class) {
						currentUrl = usingUrl;
					}
				}
				logger.info("当前presto的url地址为:" + currentUrl);
				return conn;
			} catch (SQLException e) {
				logger.error("presto 连接地址连接失败" + currentUrl);
				logger.error("开始使用:" + urlTmp);
				usingUrl = urlTmp;
			}
		}
		logger.error("当前presto url连接不可用");
		return conn;
	}

	private void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("关闭presto jdbc连接");
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> querySqlForKeyValue(String sql) {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			logger.info("query sql content===:" + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>(PrestoJdbcUtil.MAP_SIZE);
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error("查询sql失败：" + sql, e);
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public Map<String, Object> querySqlForOneKeyValue(String sql) {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>(PrestoJdbcUtil.MAP_SIZE);
		try {
			logger.info("query sql content===:" + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnLabel(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			logger.error("查询sql失败：" + sql, e);
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	public List<String[]> queryRows(String sql) {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				String[] row = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					row[i] = (String) rs.getObject(i + 1);
				}
				list.add(row);
			}
		} catch (SQLException e) {
			logger.error("查询sql失败" + sql, e);
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				closeConnection(conn);
			}
		}
		return list;
	}

	public String executeQueryForJson(String sql) {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String json = "";
		try {
			logger.info("query sql content====:" + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			JSONArray array = new JSONArray();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = rs.getString(columnName);
					jsonObject.put(columnName, value);
				}
				array.add(jsonObject);
			}
			json = array.toJSONString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
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
	 * 最大值的类型判断
	 * @param colName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public String queryForMaxCondition(String colName, String tableName) throws Exception {
		Connection con = getConnection();
		boolean flag = true;
		Statement stmt = null;
		String sql = "select max(" + colName + ") as curmaxvalue from " + tableName;
		Map<String, Object> map = null;
		String conditionValue = "";
		try {
			map = querySqlForOneKeyValue(sql);
			conditionValue =String.valueOf(map.get("curmaxvalue"));
		} catch (Exception e) {
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
		return conditionValue;
	}
}
