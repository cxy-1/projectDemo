package Util.Arithmetic.file.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.kswa.entity.FtpEntity;

@Component
public class FtpUtil2 {

    private static final Logger log = LoggerFactory.getLogger(FtpUtil.class);

    @Autowired
    private FtpEntity ftpProperties;

    /**
     * 当前命令执行完成命令完成
     *
     * @throws IOException
     */
    public void complete(FTPClient ftpClient) throws IOException {
        ftpClient.completePendingCommand();
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @Version1.0
     *
     * @param remoteFile 上传到FTP服务器上的文件名
     * @param input      本地文件流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(FTPClient ftpClient,String remoteFile, InputStream input) {
        boolean result = false;
        try {
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.storeFile(remoteFile, input);
            input.close();
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @Version1.0
     *
     * @param remoteFile 上传到FTP服务器上的文件名
     * @param localFile  本地文件
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(FTPClient ftpClient,String remoteFile, String localFile) {
        FileInputStream input = null;
        try {
            String path = StringUtil.nullToEmpty(ftpProperties.getBaseUrl());
            boolean a = ftpClient.changeWorkingDirectory(path);
            if(!a) {
                ftpClient.makeDirectory(path);
                boolean b = ftpClient.changeWorkingDirectory(path);
            }
            input = new FileInputStream(new File(localFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFile(ftpClient,remoteFile, input);
    }

    /**
     * 拷贝文件
     *
     * @param fromFile
     * @param toFile
     * @return
     * @throws Exception
     */
    public boolean copyFile(FTPClient ftpClient,String fromFile, String toFile) throws Exception {
        InputStream in = getFileInputStream(ftpClient,fromFile);
        boolean flag = ftpClient.storeFile(toFile, in);
        in.close();
        return flag;
    }

    /**
     * 获取文件输入流
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream getFileInputStream(FTPClient ftpClient,String fileName) throws Exception {
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ftpClient.retrieveFile(fileName, fos);
        ByteArrayInputStream in = new ByteArrayInputStream(fos.toByteArray());
        fos.close();
        return in;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @Version1.0
     *
     * @return
     */
    public boolean downFile(FTPClient ftpClient,String remoteFile, String localFile) {
        boolean result = false;
        try {
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(remoteFile, os);
            ftpClient.logout();
            ftpClient.disconnect();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 从ftp中获取文件流
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public InputStream getInputStream(FTPClient ftpClient,String filePath) throws Exception {
        InputStream inputStream = ftpClient.retrieveFileStream(filePath);
        return inputStream;
    }

    /**
     * ftp中文件重命名
     *
     * @param fromFile
     * @param toFile
     * @return
     * @throws Exception
     */
    public boolean rename(FTPClient ftpClient,String fromFile, String toFile) throws Exception {
        boolean result = ftpClient.rename(fromFile, toFile);
        return result;
    }

    /**
     * 获取ftp目录下的所有文件
     *
     * @param dir
     * @return
     */
    public FTPFile[] getFiles(FTPClient ftpClient,String dir) throws Exception {
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles(dir);
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return files;
    }

    /**
     * 获取ftp目录下的某种类型的文件
     *
     * @param dir
     * @param filter
     * @return
     */
    public FTPFile[] getFiles(FTPClient ftpClient,String dir, FTPFileFilter filter) throws Exception {
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles(dir, filter);
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return files;
    }

    /**
     * 创建文件夹
     *
     * @param remoteDir
     * @return 如果已经有这个文件夹返回false
     */
    public boolean makeDirectory(FTPClient ftpClient,String remoteDir) throws Exception {
        boolean result = false;
        try {
            result = ftpClient.makeDirectory(remoteDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean mkdirs(FTPClient ftpClient,String dir) throws Exception {
        boolean result = false;
        if (null == dir) {
            return result;
        }
        ftpClient.changeWorkingDirectory("/");
        StringTokenizer dirs = new StringTokenizer(dir, "/");
        String temp = null;
        while (dirs.hasMoreElements()) {
            temp = dirs.nextElement().toString(); // 创建目录
            ftpClient.makeDirectory(temp); // 进入目录
            ftpClient.changeWorkingDirectory(temp);
            result = true;
        }
        ftpClient.changeWorkingDirectory("/");
        return result;
    }

}
