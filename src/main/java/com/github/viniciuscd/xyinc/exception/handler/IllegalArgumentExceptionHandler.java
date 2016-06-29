package com.github.viniciuscd.xyinc.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Intercepta erros de argumento(s) inv&aacute;lido e envia mensagem de erro e codigo 400 (Bad Resquest).
 *
 * @author Vinicius C. de Deus
 *
 */
@Provider
public class IllegalArgumentExceptionHandler implements ExceptionMapper<IllegalArgumentException> {

    /*
     * (non-Javadoc)
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}
