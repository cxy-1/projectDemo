package Util.Arithmetic.SecurityCode;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.rmi.ServerException;

public class Sendsms {

    // 产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    // 产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIdgXZpUnPw9XL";
    static final String accessKeySecret = "kr8xMjTF5RKrLhkwbO9wHbqAi3hDBD";

    private final static String SIGN_NAME = "植保管家"; // 签名名称从控制台获取，必须是审核通过的

    private final static String TEMPLATE_CODE = "SMS_86490035"; // 模板CODE从控制台获取，必须是审核通过的

    public static SendSmsResponse sendSms(String phone, String sendcode) throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(phone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGN_NAME);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TEMPLATE_CODE);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        String content = "{\"code\":\"" + sendcode + "\"}";
        request.setTemplateParam(content);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public static void main(String[] args) throws ServerException, ClientException {
        SendSmsResponse sendSmsResponse = sendSms("15295766390", "123456");
        // if (sendSmsResponse.getCode() != null &&
        // sendSmsResponse.getCode().equals("OK")) {
        // // 请求成功
        // System.out.println("发送成功！");
        // } else if
        // (sendSmsResponse.getCode().equals("isv.BUSINESS_LIMIT_CONTROL")) {
        // System.out.println("此号码频繁发送验证码，暂时不能获取！");
        // }
        System.out.println(sendSmsResponse.getCode());
    }

    /**
     * 随机生成6位随机验证码
     */
    public static String createRandomVcode() {
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }

}
