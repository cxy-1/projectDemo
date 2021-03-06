package Util.Arithmetic.a0.Hdfs;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作抽象类
 *
 * @author cc
 *
 */
public abstract class HDFSHandle implements HDFSSupport {
    protected final static Logger LOG = LoggerFactory
            .getLogger(HDFSHandle.class);
    protected static FileSystem fs;
    protected static Configuration conf;

    /**
     * 初始化实例化HDFS文件系统
     *
     * @throws IOException
     */
    static {
        conf = UTILS.getConfiguration();
        try {
            fs = UTILS.getFileSystem(conf);
        } catch (IOException e) {
            LOG.error("初始化HDFSHandle失败,加载Configuration获取FileSystem抛出IO异常!", e);
        }
    }

    /**
     * 获取文件系统对象
     *
     * @return
     */
    public static FileSystem getFileSytem() {
        return fs;
    }

    /**
     * 判读某个用户下，dirPath路径下的文件是否存在
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static boolean isFileExist(String dirPath, String username)
            throws IOException {
        Path path = new Path(dirPath, username);
        try {
            return fs.exists(path);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 判读某个用户下，dirPath路径是否是文件夹
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static boolean isDirectory(String dirPath, String username)
            throws IOException {
        Path path = getPathWithUser(dirPath, username);
        try {
            return fs.isDirectory(path);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 返回拼接用户之后的路径
     *
     * @param basePath
     * @param userName
     * @return
     */
    public static Path getPathWithUser(String relativePath, String userName) {
        Path userPath = HDFSUtils.getUserPath(userName);
        if (StringUtils.isNotBlank(relativePath)) {
            userPath = new Path(userPath, relativePath);
        }
        return userPath;
    }

    /**
     * 返回当前用户根路径
     *
     * @param basePath
     * @param userName
     * @return
     */
    public static Path getUserPath(String userName) {
        Path userPath = HDFSUtils.getUserPath(userName);
        return userPath;
    }

    /**
     * 重命名文件夹-如果重名则在后面添加数字
     *
     * @param dirPath
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static Path folderRename(Path dirPath)
            throws IllegalArgumentException, IOException {
        Integer i = 1;
        do {
            String temp = dirPath.toString();
            if (fs.exists(dirPath) && i.equals(1)) {
                dirPath = new Path(temp + "(" + i + ")");
            } else if (fs.exists(dirPath)) {
                int j = temp.lastIndexOf("(" + (i - 1) + ")");
                if (j != -1) {
                    String str = temp.substring(0, j);
                    dirPath = new Path(str + "(" + i + ")");
                }
            } else {
                break;
            }
            i++;
        } while (i < Integer.MAX_VALUE);
        return dirPath;
    }
}
