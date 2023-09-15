package com.github.register.infrastructure.jaxrs;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintViolation;



import java.util.stream.Collectors;

/**
 * This class is used to unify the handling of error messages
 * that brought back to the client in Resource due to validator validation failures.
 *
 * @author
 * @date
 **/
@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = LoggerFactory.getLogger(ViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.warn("The data passed in from clients has illegal checksum results", exception);
        String msg = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("；"));
        return CommonResponse.send(Response.Status.BAD_REQUEST, msg);
    }
}
