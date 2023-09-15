package com.github.register.infrastructure.jaxrs;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 带编码的实体容器
 * <p>
 * 一般来说REST服务应采用HTTP Status Code带回错误信息编码
 * 但很多前端开发都习惯以JSON-RPC的风格处理异常，所以仍然保留这个编码容器
 * 用于返回给客户端以形式为“{code,message,data}”的对象格式
 *
 * @author
 * @date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodedMessage {
    /**
     * 约定的成功标志
     */
    public static final Integer CODE_SUCCESS = 0;
    /**
     * 默认的失败标志，其他失败含义可以自定义
     */
    public static final Integer CODE_DEFAULT_FAILURE = 1;

    private Integer code;
    private String message;
    private Object data;

    public CodedMessage(Integer code, String message) {
        setCode(code);
        setMessage(message);
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
}
