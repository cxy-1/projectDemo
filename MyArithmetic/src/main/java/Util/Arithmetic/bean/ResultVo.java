package Util.Arithmetic.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * ClassName:   ResultVo
 * Description: 前端请求返回的最外层对象
 * @author Dawn.Ji
 * @date Created in 14:36 2019/3/20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo {

    /**
     * 错误码
     */
    private Boolean success;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private Object data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
