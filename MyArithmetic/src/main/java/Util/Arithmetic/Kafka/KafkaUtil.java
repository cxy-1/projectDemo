package Util.Arithmetic.Kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * Kafka操作类
 *
 * @author lgc
 *
 */

public class KafkaUtil {

    private static Producer<String, String> producer;
    private static ProducerConfig config;
    private static String zookeeperList = ResourceLoader
            .getTableFieldMapping("zookeeper.connect");
    private static String brokerList = ResourceLoader
            .getTableFieldMapping("broker.list");

    List<KeyedMessage<String, byte[]>> kms = new ArrayList<KeyedMessage<String, byte[]>>();

    static {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", zookeeperList);
        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("metadata.broker.list", brokerList);
        config = new ProducerConfig(properties);
        producer = new Producer(config);
    }

    public static void main(String[] args) {
        try {
            KeyedMessage message = new KeyedMessage(
                    "streamtest",
                    "xp.apple.com|mzuserxp.itunes-apple.com.akadns.net|17.173.66.133|117.136.45.241|1|1|2015-07-01 06:00:22|");
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送消息
    public static boolean SendMessage(String topic, String messageStr) {
        try {
            KeyedMessage message = new KeyedMessage(topic, messageStr);
            producer.send(message);
            message = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 批量发送消息
    public static boolean bachSendMessage(String topic, String messageStr, int j) {
        try {
            List<KeyedMessage<String, String>> kms = new ArrayList<KeyedMessage<String, String>>();
            KeyedMessage<String, String> km = new KeyedMessage<String, String>(
                    topic, String.valueOf(j), messageStr);
            kms.add(km);
            producer.send(kms);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
