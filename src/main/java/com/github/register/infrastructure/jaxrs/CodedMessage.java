package com.github.register.infrastructure.jaxrs;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Entity containers with codes
 * <p>
 * Generally speaking, REST services should use HTTP Status Code to bring back the error message code.
 * But many front-end developers are accustomed to handling exceptions in JSON-RPC style.
 * So this code container is still retained, and used to return to the client in the form of "{code,message,data}" object format.
 *
 * @author
 * @date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodedMessage {
    /**
     * Signs of Success
     */
    public static final Integer CODE_SUCCESS = 0;
    /**
     * Default failure flag, other failure meanings can be customized
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
