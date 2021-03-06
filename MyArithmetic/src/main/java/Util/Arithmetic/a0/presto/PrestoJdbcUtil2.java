package Util.Arithmetic.a0.presto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sugon.strz.utils.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * Presto jdbc连接工具类
 * @author MaJing
 *
 */
@Slf4j
@Component
@Conditional(StarterPrestoCondition.class)
public class PrestoJdbcUtil2 {

    @Autowired
    private PrestoJdbcProperties prestoJdbcProperties;

    private Random random = new Random();
    public Connection getConnection() {

        Connection conn = null;
        for (int i = 0; i < prestoJdbcProperties.getUrls().size(); i++) {
            try {
                conn = DriverManager.getConnection(prestoJdbcProperties.getUrls().get(random.nextInt
                                (prestoJdbcProperties.getUrls().size())),
                        prestoJdbcProperties.getUsername(), prestoJdbcProperties.getPassword());
                return conn;
            } catch (Exception e) {
                log.error("获取Prosto Jdbc连接失败", e);
                if (i + 1 == prestoJdbcProperties.getUrls().size()) {
                    log.error("获取全部Prosto Jdbc连接失败", e);
                }
            }
        }
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
            log.error("关闭presto jdbc连接失败");
            log.error(e.getMessage(),e);
        }
    }

    public List<Map<String, Object>> querySqlForKeyValue(String sql) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            log.info("query sql content===:" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            log.error("查询sql失败：" + sql, e);
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

    public List<Object[]> queryRows(String sql) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            log.info("query sql content===:" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            Object[] row = null;
            while (rs.next()) {
                row = new Object[columnCount];
                ;
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                list.add(row);
            }
        } catch (SQLException e) {
            log.error("查询sql失败" + sql, e);
            e.printStackTrace();
            throw new RuntimeException("Presto查询异常");
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
            log.info("query sql content====:" + sql);
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

    public boolean excuteSql(String sql){
        Connection con = getConnection();
        boolean flag = true;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            log.error("执行sql失败:" + sql, e);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                }
            }
            if (con != null) {
                closeConnection(con);
            }
        }
        return flag;
    }

    public long countQuery(String sql){
        Connection conn = getConnection();
        Statement stmt = null;
        long count = 0L;
        try {
            stmt = conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("执行sql失败:" + sql, e);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                }
            }
            if (null != conn) {
                closeConnection(conn);
            }
        }
        return count;
    }

    /**
     * 分页查询，实体映射版本 要求实体的名称=查询出来的字段名称
     * 如果是第一页，则直接limit，不去计算count及分页查询
     * 如果不是第一页，才会去count及分页
     * @param sql
     * @return
     */
    public <T> Page<T> pageQuery(String sql, Pageable pageable, Class<T> clazz) throws
            SQLException, IllegalAccessException, InstantiationException {
        Page<Map<String, Object>> mapPage = pageQuery(sql, pageable);
        List<T> result = BeanMapUtils.mapsToObjects(mapPage.getContent(), clazz);
        return new PageImpl(result, pageable,
                mapPage.getTotalElements());
    }

    public <T> Page<T> pageQueryCamel(String sql, Pageable pageable, Class<T> clazz) throws
            SQLException, IllegalAccessException, InstantiationException {
        Page<Map<String, Object>> mapPage = pageQuery(sql, pageable);
        List<T> result = BeanMapUtils.mapsToObjectsCamel(mapPage.getContent(), clazz);
        return new PageImpl(result, pageable,mapPage.getTotalElements());
    }

    public Page<Map<String, Object>> pageQuery(String sql, Pageable pageable) {
        List<Map<String, Object>> result = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        long count = 0L;
        try {
            /*判断页码，如果是第一页，则不先统计count*/
            //if(pageable.getPageNumber() != 0) {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getCountSql(sql));
            while (rs.next()) {
                count = rs.getInt(1);
            }
            //}

            // 求数据
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getPageSql(sql, pageable));
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnLabel(i), rs.getObject(i));
                }
                result.add(map);
            }


        } catch (SQLException e) {
            log.error("执行sql失败:" + sql, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                }
            }
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                }
            }
            if (conn != null) {
                closeConnection(conn);
            }
        }
        return new PageImpl(result, pageable,count);
    }

    private String getCountSql(String sql) {
        String result = "select count(1) from (" + sql + ") temp_count_tab";
        log.info(result);
        return result;
    }

    private String getPageSql(String sql, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        if (pageable.getPageNumber() == 0) {
            sb.append(sql).append(" ").append(getSortStr(pageable)).append(" LIMIT ").append(pageable.getPageSize());
        } else {
            sb.append("select * from (select row_number()over( ").append(getSortStr(pageable)).append(" ) AS rn,b.* " +
                    "from (").append(sql).append(")b) pageTable " +
                    "where rn >  ").append((pageable.getPageNumber()*pageable.getPageSize())).append(" and rn <= ").append(pageable.getPageNumber()*pageable.getPageSize()+pageable.getPageSize());
        }
        log.info(sb.toString());
        return sb.toString();
    }

    /**
     * 获取排序字符串
     * @return
     */
    private String getSortStr(Pageable pageable) {
        Iterator<Sort.Order> it = pageable.getSort().iterator();
        Sort.Direction direction = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" ORDER BY ");
        while (it.hasNext()) {
            Sort.Order order = it.next();
            if (direction != null && direction != order.getDirection()) {
                throw new RuntimeException("Presto 查询只支持一种排序规则");
            } else {
                direction = order.getDirection();
                sb.append(order.getProperty());
                if (it.hasNext()) {
                    sb.append(",");
                }
            }
        }
        sb.append(" ").append(direction);
        return sb.toString();
    }
}

class StarterPrestoCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(
                context.getEnvironment(), "strz.switch.");

        String env = resolver.getProperty("presto");
        if (env == null) {
            return false;
        }
        return "true".equalsIgnoreCase(env.toLowerCase());
    }

}
