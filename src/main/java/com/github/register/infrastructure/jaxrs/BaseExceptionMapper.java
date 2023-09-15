package com.github.register.infrastructure.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 用于兜底的全局处理器，如果其他所有的Mapper都不合适，将由此处理把错误带到前端
 *
 * @author
 * @date
 **/
@Provider
public class BaseExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.failure(exception.getMessage());
    }
}
