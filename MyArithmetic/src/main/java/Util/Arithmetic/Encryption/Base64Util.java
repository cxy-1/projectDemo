package Util.Arithmetic.Encryption;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64���������
 * @author wuyl
 *
 */
public final class Base64Util {

    private Base64Util(){
    }

    /**
     * ����
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * ����
     * @param content
     * @return
     * @throws Exception
     */
    public static String encode(String content) throws Exception {
        return encode(content.getBytes());
    }

    /**
     * ����
     * @param content
     * @param charset
     * @return
     * @throws Exception
     */
    public static String encode(String content,String charset) throws Exception {
        return encode(content.getBytes(charset));
    }

    /**
     * ����
     * @param content
     * @param charset
     * @return
     * @throws Exception
     */
    public static String decode(String content,String charset) throws Exception {
        return new String(Base64.decodeBase64(content), charset);
    }

    /**
     * ����
     * @param bytes
     * @param charset
     * @return
     * @throws Exception
     */
    public static String decode(byte[] bytes,String charset) throws Exception {
        return new String(Base64.decodeBase64(bytes), charset);
    }














    /**
     * Base64 ����
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String str) throws UnsupportedEncodingException {

        final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        byte[] textByte=str.getBytes("gbk");
        return encoder.encodeToString(textByte);

    }


}
