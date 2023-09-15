package com.github.register.infrastructure.jaxrs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;


/**
 * 用于统一处理在Resource中由于Spring Security授权访问产生的异常信息
 *
 * @autho
 * @date 
 **/
@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(AccessDeniedException exception) {
        log.warn("越权访问被禁止 {}: {}", request.getMethod(), request.getPathInfo());
        return CommonResponse.send(Response.Status.FORBIDDEN, exception.getMessage());
    }
}