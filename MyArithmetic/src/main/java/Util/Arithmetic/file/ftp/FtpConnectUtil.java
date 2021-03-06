package Util.Arithmetic.file.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class FtpConnectUtil {

    //ftp对象
    private static FTPClient ftp;
    //需要连接到的ftp端的ip
    private static String ip;
    //连接端口，默认21
    private static int port=21;
    //要连接到的ftp端的名字
    private static String name;
    //要连接到的ftp端的对应得密码
    private static String pwd;

    static {
        ip = ResourceLoader.getValueProperties("ftp.connection.ip");
        name = ResourceLoader.getValueProperties("ftp.connection.name");
        pwd = ResourceLoader.getValueProperties("ftp.connection.pwd");
    }

    public static FTPClient getconnection() {
        ftp = new FTPClient();
        try {
            ftp.connect(ip, port);
            ftp.login(name, pwd);
            int reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("ftp连接失败");
            }else{
                System.out.println("ftp连接成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "ftp连接失败", e);
        }
        return ftp;
    }
}
