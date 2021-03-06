package Util.Arithmetic.a0.Hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.slf4j.*;

/**
 * ����HDFS�ļ�
 * Created by lgc on 2017/2/9 0009.
 */
public class HdfsUtil {

    protected static final org.slf4j.Logger LOG = LoggerFactory
            .getLogger(HdfsUtil.class);

    public static int getHdfsFileCount(String filePath) {
        int result = 0;
        FileSystem fs = null;
        try {
            Configuration configuration = new Configuration();
            String hdfsUrl = ResourceLoader.getExtraProperties("hdfs.http.url");
            configuration.set("fs.defaultFS",hdfsUrl);
            fs = FileSystem.get(configuration);
            filePath ="/apps/hive/warehouse/" + filePath;
            Path path = new Path(filePath);
            FileStatus[] status = fs.listStatus(path);
            result = status.length;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(" hdfs configuration  error", e);
        }finally {
            if(fs != null)
            {
                try {
                    fs.close();
                }catch (Exception  e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void deleteHdfsFile(String filePath) {
        int result = 0;
        FileSystem fs = null;
        try {
            Configuration configuration = new Configuration();
            String hdfsUrl = ResourceLoader.getExtraProperties("hdfs.http.url");
            configuration.set("fs.defaultFS",hdfsUrl);
            fs = FileSystem.get(configuration);
            filePath ="/apps/hive/warehouse/" + filePath;
            Path path = new Path(filePath);
            fs.delete(path,true);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(" delete  hdfs  error", e);
        }finally {
            if(fs != null)
            {
                try {
                    fs.close();
                }catch (Exception  e1)
                {
                    e1.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args)
    {
        int a =3;
        double b =11;
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        System.out.println(df.format(a/b*100));
    }

}
