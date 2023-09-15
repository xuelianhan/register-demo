package com.github.register.infrastructure.jaxrs;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.ConstraintViolation;



import java.util.stream.Collectors;

/**
 * 用于统一处理在Resource中由于验证器验证失败而带回客户端的错误信息
 *
 * @author
 * @date
 **/
@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = LoggerFactory.getLogger(ViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.warn("客户端传入了校验结果为非法的数据", exception);
        String msg = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("；"));
        return CommonResponse.send(Response.Status.BAD_REQUEST, msg);
    }
}
