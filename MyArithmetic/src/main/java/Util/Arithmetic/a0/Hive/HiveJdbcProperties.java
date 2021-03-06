package Util.Arithmetic.a0.Hive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  lz
 * @date 2018-06-11 19:12
 */
@Data
@Component
@ConfigurationProperties(prefix="hive.thrift")
public class HiveJdbcProperties {

    private String username;

    private String password;

    private String driver = "org.apache.hive.jdbc.HiveDriver";

    private List<String> urls = new ArrayList<>();

}
