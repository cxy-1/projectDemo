package Util.Arithmetic.http;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhuwz
 */
public class TokenUtil_Jsb {

    public static String getTokenUserId() {
        // 从 Util.http 请求头中取出 token
        String token = getRequest().getHeader("token");
        if(token != null){
            System.out.println("-----------------"+token);
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        }else {
            System.out.println("-----------------"+token);
            return null;
        }
    }

    public static String getToken() {
        // 从 Util.http 请求头中取出 token
        String token = getRequest().getHeader("token");
        String userId = JWT.decode(token).getToken();
        return userId;
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
