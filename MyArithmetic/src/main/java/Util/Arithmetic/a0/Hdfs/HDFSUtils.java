package Util.Arithmetic.a0.Hdfs;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.executor.core.util.common.ResourceLoader;

/**
 * HDFS工具类-单例模式
 *
 * @author ccc
 *
 */
public class HDFSUtils extends HDFSConstants {

    private final static Logger LOG = LoggerFactory.getLogger(HDFSUtils.class);

    private static volatile HDFSUtils hdfsUtils = null;

    private HDFSUtils() {
    }

    private static Configuration CONF;

    public static final String SLASH = "/";

    public static final String HADOOP_ADDRESS_IP;

    /**
     * 数据中心根目录
     */
    public static final Path DATA_CENTER;

    /**
     * 数据中心根目录字符串
     */
    public static final String DATA_CENTERSTR;

    public static final String ERROR_1 = "创建用户失败";

    public static final String ERROR_2 = "加载配置文件失败";

    // 从配置文件中初始化HDFS信息
    static {
        String dataCenter = ResourceLoader.getExecutorProperties(HDFS_DATACENTER);
        if (StringUtils.isBlank(dataCenter)) {
            throw new RuntimeException("数据中心根目录在配置文件中为空或不存在!");
        }
        DATA_CENTER = new Path(dataCenter);
        DATA_CENTERSTR = DATA_CENTER.toString();

        // 初始化配置
        CONF = new Configuration();
        String conAddress = "";
        Long timeout = 1000L;
        Integer retries = 2;
        Integer retryOntimeout = 10;
        Long retryInterval = 500L;
        try {
            conAddress = ResourceLoader.getExecutorProperties(HADOOP_ADDRESS);
            timeout = Long.parseLong(ResourceLoader.getExecutorProperties(IPC_CONNECT_TIMEOUT));
            retries = Integer.parseInt(ResourceLoader.getExecutorProperties(IPC_CONNECT_RETRIES));
            retryInterval = Long.parseLong(ResourceLoader.getExecutorProperties(IPC_CONNECT_RETRY_INTERVAL));
            retryOntimeout = Integer.parseInt(ResourceLoader.getExecutorProperties(IPC_CONNECT_RETRY_ON_TIMEOUTS));
        } catch (NullPointerException e) {
            LOG.error("连接ip地址为空", e);
            throw e;
        }
        HADOOP_ADDRESS_IP = conAddress;
        CONF.set("fs.default.name", conAddress);
        CONF.setLong(IPC_CONNECT_TIMEOUT, timeout);
        CONF.setInt(IPC_CONNECT_RETRIES, retries);
        CONF.setLong(IPC_CONNECT_RETRY_INTERVAL, retryInterval);
        CONF.setInt(IPC_CONNECT_RETRY_ON_TIMEOUTS, retryOntimeout);
    }

    /**
     * 单例模式-实例化HDFS工具类
     *
     * @return
     */
    public static HDFSUtils getInstance() {
        if (hdfsUtils == null) {
            synchronized (HDFSUtils.class) {
                if (hdfsUtils == null) {
                    hdfsUtils = new HDFSUtils();
                }
            }
        }
        return hdfsUtils;
    }

    /**
     * 获取用户hdfs路径
     *
     * @param user
     * @return
     */
    public static Path getUserPath(String user) {
        Path userPath = null;
        if (StringUtils.isNotBlank(user)) {
            userPath = new Path(DATA_CENTER, user);
        }
        return userPath;
    }

    /**
     * 获取用户hdfs路径
     *
     * @param group
     * @param user
     * @return
     */
//	public static String getUserPathStr(String user) {
//		Path userPath = null;
//		if (StringUtils.isNotBlank(user)) {
//			userPath = new Path(DATA_CENTER, user);
//		}
//		return userPath.toString();
//	}

    /**
     * 获取路径
     *
     * @return
     */
    public static Path getPath(String dirPath) {
        Path path = null;
        if (StringUtils.isNotBlank(dirPath)) {
            path = new Path(dirPath);
        }
        return path;
    }

    /**
     * 获取用户下指定路径-获取PATH类型
     *
     * @param dirPath
     * @param user
     * @return
     */
    public static Path getPathInUser(String dirPath, String user) {
        Path temp = null;
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(dirPath)) {
            Path userPath = new Path(DATA_CENTER, user);
            temp = new Path(userPath, dirPath);
        }
        return temp;
    }

    /**
     * 获取用户下指定路径-获取字符串类型
     *
     * @param dirPath
     * @param user
     * @return
     */
    public static String getPathInUserStr(String dirPath, String user) {
        String pathStr = null;
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(dirPath)) {
            Path userPath = new Path(DATA_CENTER, user);
            Path temp = new Path(userPath, dirPath);
            if (temp != null) {
                pathStr = temp.toString();
            }
        }
        return pathStr;
    }

    /**
     * 获取相对datacenter之后的相对路径
     *
     * @param path
     * @return
     */
    public static String getRelativePath(String path) {
        String userPath = "";
        if (StringUtils.isNotBlank(path)) {
            String[] paths = path.split(DATA_CENTERSTR);
            if (paths != null && paths.length > 1) {
                userPath = paths[1];
            }
        }
        return userPath;
    }

    /**
     * 拼接路径
     *
     * @return
     */
    public static String rePathStr(String parent, String filename) {
        Path path = new Path(parent, filename);
        return path.toString();
    }

    /**
     * 获取配置项
     *
     * @return
     * @throws IOException
     */
    public Configuration getConfiguration() {
        return CONF;
    }

    /**
     * 开启当前工具类管理的文件系统
     *
     * @return
     * @throws IOException
     */
    public FileSystem getFileSystem() throws IOException {
        FileSystem fs = FileSystem.get(CONF);
        return fs;
    }

    /**
     * 开启指定的文件系统
     *
     * @param conf
     * @return
     * @throws IOException
     */
    public FileSystem getFileSystem(Configuration conf) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        return fs;
    }

    /**
     * 关闭指定的文件系统
     *
     * @param fs
     * @throws IOException
     */
    public void closeFileSystem(FileSystem fs) throws IOException {
        if (fs != null) {
            fs.close();
        }
    }

    /**
     * 关闭当前工具类管理的文件系统
     *
     * @throws IOException
     */
    public void closeFileSystem() throws IOException {
        FileSystem fs = FileSystem.get(CONF);
        if (fs != null) {
            fs.close();
        }
    }
}
