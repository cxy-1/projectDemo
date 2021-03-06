package Util.Arithmetic.a0.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sugon.strz.scheduler.common.MysqlProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * JDBC连接Mysql数据库
 * @author wuyl
 *
 */
@Slf4j
@Component
public class MysqlJdbcUtil {

    @Resource
    private MysqlProperties mysqlProperties;

    private Connection con = null;

    /**
     * 获得连接
     * @return
     */
    public Connection getConnection() {
        if(null == con) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = mysqlProperties.getUrl();
                String username = mysqlProperties.getUsername();
                String password = mysqlProperties.getPassword();
                con = DriverManager.getConnection(url,username,password);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
        return con;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (null != con) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

}
