package com.github.viniciuscd.xyinc.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.github.viniciuscd.xyinc.exception.NotFoundServiceException;

/**
 * Intersepta erro para valores n&atilde;o encontrados e retorna mensagem de erro e status 404 (Not Found).
 *
 * @author Vinicius C. de Deus
 *
 */
@Provider
public class NotFoundExceptionHandler  implements ExceptionMapper<NotFoundServiceException> {

    /*
     * (non-Javadoc)
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(NotFoundServiceException exception) {
        return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}
