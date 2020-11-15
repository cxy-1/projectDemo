package com.sugon.analysis.enums.result;

import com.sugon.analysis.enums.ResponsesStatus;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @author hxb
 */
@Data
public class ResultModel
{
    private Object data;
    private int code;
    private String message;

    public ResultModel()
    {
        this.data = null;
        this.code = ResponsesStatus.ERROR.getCode();
        this.message = ResponsesStatus.ERROR.getMessage();
    }

    public void setResult(ResponsesStatus responsesStatus, Object data)
    {
        this.data = data;
        this.code = responsesStatus.getCode();
        this.message = responsesStatus.getMessage();
    }

    public void setResult(ResponsesStatus responsesStatus)
    {
        this.data = null;
        this.code = responsesStatus.getCode();
        this.message = responsesStatus.getMessage();
    }

    public void setResult(Object data)
    {
        if (data == null) {
            this.data = null;
            this.code = ResponsesStatus.NODATA.getCode();
            this.message = ResponsesStatus.NODATA.getMessage();
        } else {
            this.data = data;
            this.code = ResponsesStatus.SUCCESS.getCode();
            this.message = ResponsesStatus.SUCCESS.getMessage();
        }
    }

    public static ResultModel success(){
        return success("²Ù×÷³É¹¦");
    }

    public static ResultModel success(Object data)
    {
        ResultModel resultModel = new ResultModel();
        if (data == null) {
            resultModel.setCode(ResponsesStatus.NODATA.getCode());
            resultModel.setMessage(ResponsesStatus.NODATA.getMessage());
        } else {
            resultModel.setData(data);
            resultModel.setCode(ResponsesStatus.SUCCESS.getCode());
            resultModel.setMessage(ResponsesStatus.SUCCESS.getMessage());
        }
        return resultModel;
    }

    public static ResultModel failure(String message)
    {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(ResponsesStatus.FAILURE.getCode());
        if(StringUtils.isNotBlank(message)){
            resultModel.setMessage(message);
        }else{
            resultModel.setMessage(ResponsesStatus.FAILURE.getMessage());
        }
        return resultModel;
    }
}