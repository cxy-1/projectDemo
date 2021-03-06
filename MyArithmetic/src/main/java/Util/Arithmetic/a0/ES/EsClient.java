package Util.Arithmetic.a0.ES;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;


/**
 * ESClient
 *
 * @author cjz
 *
 */
public class EsClient {

    private static String clusterName = "es-fulltext";
    private static String inetAddress = null;
    private static Integer esHttpPort = 9200;// 默认9200
    private static Integer esClientPort = 9300;// 默认9300
    private static Client client = null;
    private static String clusterNameStr;

    private static String inetAddressStr;

    private static String esHttpPortStr;

    private static String esClientPortStr;

    private EsClient() {
    }

    static {
        clusterNameStr=ResourceLoader.getExtraProperties("es.cluster.name");
        inetAddressStr=ResourceLoader.getExtraProperties("es.address");
        esHttpPortStr=ResourceLoader.getExtraProperties("es.http.port");
        esClientPortStr=ResourceLoader.getExtraProperties("es.client.port");
        System.out.println("````````````"+clusterNameStr+"11"+inetAddressStr+"22"+esHttpPortStr+"33"+esClientPortStr);
    }

    /**
     * 初始化
     *
     * @throws NumberFormatException
     * @throws UnknownHostException
     */
    private static void initClient() throws NumberFormatException,
            UnknownHostException {
        if (StringUtils.isNotBlank(clusterNameStr)) {
            clusterName = clusterNameStr;
        }
        if (StringUtils.isNotBlank(inetAddressStr)) {
            inetAddress = inetAddressStr;
        }
        if (StringUtils.isNotBlank(esHttpPortStr)) {
            esHttpPort = Integer.parseInt(esHttpPortStr);
        }
        if (StringUtils.isNotBlank(esClientPortStr)) {
            esClientPort = Integer.parseInt(esClientPortStr);
        }
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();
        TransportClient tClient = TransportClient.builder().settings(settings)
                .build();
        List<String> addressList = Lists.newArrayList();
        // 设置默认IP和端口
        if (StringUtils.isBlank(inetAddress)) {
            addressList.add("localhost:9300");
        } else {
            inetAddress = inetAddress.replaceAll(" ", "").trim();
            addressList = Arrays.asList(inetAddress.split(","));
        }
        for (int i = 0; i < addressList.size(); i++) {
            String address = addressList.get(i);
            String[] arr = address.split(";");
            Integer clientPort = esClientPort;
            if (arr.length > 1) {
                clientPort = Integer.parseInt(arr[1]);
            }
            tClient.addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName(arr[0].trim()), clientPort));
        }
        client = tClient;
    }

    public void init() throws NumberFormatException, UnknownHostException {
        client = EsClient.getClient();
    }

    /**
     * 获取Client
     *
     * @return
     * @throws UnknownHostException
     * @throws NumberFormatException
     */
    public static Client getClient() throws NumberFormatException,
            UnknownHostException {
        if (client == null) {
            synchronized (EsClient.class) {
                if (client == null)
                    initClient();
            }
        }
        return client;
    }

    /**
     * 关闭
     */
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        EsClient.clusterName = clusterName;
    }

    public String getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(String inetAddress) {
        EsClient.inetAddress = inetAddress;
    }

    public Integer getEsHttpPort() {
        return esHttpPort;
    }

    public void setEsHttpPort(Integer esHttpPort) {
        EsClient.esHttpPort = esHttpPort;
    }

    public Integer getEsClientPort() {
        return esClientPort;
    }

    public void setEsClientPort(Integer esClientPort) {
        EsClient.esClientPort = esClientPort;
    }
}
