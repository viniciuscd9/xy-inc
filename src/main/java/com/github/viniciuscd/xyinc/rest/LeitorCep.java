package com.github.viniciuscd.xyinc.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.viniciuscd.xyinc.session.BuscaCepSession;
import com.github.viniciuscd.xyinc.session.BuscaCepSessionImpl;

@Path("/buscar")
public class LeitorCep {
    private BuscaCepSession buscaCepSession = BuscaCepSessionImpl.getInstance();

    @GET
    @Path("endereco/{cep}")
    @Produces(MediaType.APPLICATION_JSON + "charset=UTF-8")
    public String getEndereco(@PathParam("cep") String cep) {
        return this.buscaCepSession.buscaLogradouro(cep);
    }

    @GET
    @Path("cep/{endereco}")
    @Produces(MediaType.APPLICATION_JSON + "charset=UTF-8")
    public List<String> getCep(@PathParam("logradouro") String logradouro) {
        return this.buscaCepSession.buscaCep(logradouro);
    }
}
