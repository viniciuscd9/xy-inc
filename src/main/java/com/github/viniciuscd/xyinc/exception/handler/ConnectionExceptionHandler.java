package com.github.viniciuscd.xyinc.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.github.viniciuscd.xyinc.exception.ConnectionException;

/**
 * Intercepta erros de conex&atilde;o e retorna mensagem de erro e status corretos.
 *
 * @author Vinicius C. de Deus
 *
 */
@Provider
public class ConnectionExceptionHandler implements ExceptionMapper<ConnectionException> {

    /*
     * (non-Javadoc)
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(ConnectionException exception) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }

}
