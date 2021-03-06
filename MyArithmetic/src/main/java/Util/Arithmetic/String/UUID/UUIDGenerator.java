package Util.Arithmetic.String.UUID;

import java.util.UUID;

/**
 * @ClassName UUIDGenerator
 * @Author liu
 * @Date 2019/3/21 15:03
 * @Version 1.0
 **/
public class UUIDGenerator {
    public UUIDGenerator() {
    }

    /**
     * ���һ��UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }
    public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    public static String getShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        int size = 6;
        for (int i = 0; i < size; i++) {
            String str = uuid.substring(i, i + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x24]);
        }
        return shortBuffer.toString();
    }

}
