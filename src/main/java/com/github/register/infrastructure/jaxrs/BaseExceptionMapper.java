package com.github.register.infrastructure.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * A global handler used for under the hood,
 * which will handle bringing errors to the front-end if all other Mappers are inappropriate
 *
 * @author sniper
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
