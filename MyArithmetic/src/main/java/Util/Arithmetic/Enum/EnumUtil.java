package Util.Arithmetic.Enum;

import com.sugon.strz.enums.CodeEnum;

/**
 * @author wuyl
 *
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
