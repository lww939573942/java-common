package cn.company.common.bean;

import org.slf4j.MDC;

import java.io.Serializable;

/**
 * @author donaldhan
 * API返回
 */
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = 0;

    private String message = "成功";

    private Object data;

    private String requestId = MDC.get("requestId");

    public ApiResponse() {

    }

    public ApiResponse(Object data) {
        super();
        this.data = data;
    }

    public ApiResponse(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiResponse(Integer code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
