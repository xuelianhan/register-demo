package com.github.register.infrastructure.jaxrs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.function.Consumer;

/**
 * HTTP Response object wrapper classes and toolset designed to simplify coding
 * <p>
 * JavaBean field object wrapper classes with service status encoding (with Code field)
 * The Code field is typically used by the service consumer to determine whether the business processing of the request was successful.
 * <p>
 * Unified Conventions:
 * - When the service call is completed normally, the return Code is always expressed as 0.
 * - When the service call produces an exception , you can customize the Code value is not 0 , the Message field as a return to the client's details .
 *
 * @authour zhouzhiming
 * @author sniper
 * @see <a href="https://www.baeldung.com/jax-rs-spec-and-implementations"></a>
 * @date
 **/
public abstract class CommonResponse {

    private static final Logger log = LoggerFactory.getLogger(CommonResponse.class);

    /**
     * Send customized action messages to clients
     */
    public static Response send(Response.Status status, String message) {
        Integer code = status.getFamily() == Response.Status.Family.SUCCESSFUL ? CodedMessage.CODE_SUCCESS : CodedMessage.CODE_DEFAULT_FAILURE;
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(new CodedMessage(code, message)).build();
    }

    /**
     * Send operation failure message to clients
     */
    public static Response failure(String message) {
        return send(Response.Status.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * Send a successful operation message to clients
     */
    public static Response success(String message) {
        return send(Response.Status.OK, message);
    }

    /**
     * Send a successful operation message to the client.
     */
    public static Response success() {
        return send(Response.Status.OK, "操作已成功");
    }

    /**
     * Execute the operation and return the corresponding information to the client according to whether the operation is successful or not.
     * Encapsulates the common operations that are commonly performed in server-side interfaces,
     * and returns a success flag for success and a failure flag for failure,
     * which is used to simplify coding.
     */
    public static Response op(Runnable executor) {
        return op(executor, e -> log.error(e.getMessage(), e));
    }

    /**
     * Execute the operation (with customized failure handling),
     * and return the corresponding information to the client according to whether the operation is successful or not.
     * Encapsulates the common operations that are commonly performed in server-side interfaces,
     * returning success flags for success and failure flags for failure,
     * which is used to simplify coding.
     */
    public static Response op(Runnable executor, Consumer<Exception> exceptionConsumer) {
        try {
            executor.run();
            return CommonResponse.success();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
            return CommonResponse.failure(e.getMessage());
        }
    }

}
