package com.github.viniciuscd.xyinc.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.github.viniciuscd.xyinc.exception.ServiceException;
import com.github.viniciuscd.xyinc.model.Endereco;
import com.github.viniciuscd.xyinc.presenter.BuscaCepPresenterImpl;
import com.github.viniciuscd.xyinc.presenter.IBuscaCepPresenter;

/**
 * Endpoint RESTful para busca de endere&ccedil;os e CEPs.
 *
 * @author Vinicius C. de Deus
 *
 */
@Path("/buscar")
public class BuscaCepEndpoint {
    private static final Logger LOGGER = Logger.getLogger(BuscaCepEndpoint.class);

    private IBuscaCepPresenter buscaCepSession;

    public BuscaCepEndpoint() {
        // Muito simples para utilizar framework de injecao de dependencia
        this.buscaCepSession = BuscaCepPresenterImpl.getInstance();
    }

    public BuscaCepEndpoint(IBuscaCepPresenter buscaCepPresenter) {
        this.buscaCepSession = buscaCepPresenter;
    }

    /**
     * Busca um endere&ccedil;o por CEP
     *
     * @param cep O CEP cujo o endere&ccedil;o buscado pertence.
     * @return Um endere&ccedil;o com o mesmo CEP passado como par&acirc;metro.
     * @throws ServiceException Caso haja erro relacionado &agrave; busca.
     * @throws IllegalArgumentException Caso o par&acirc;metro passado seja inv&aacute;lido.
     */
    @GET
    @Path("endereco/{cep}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Endereco getEndereco(@PathParam("cep") String cep) throws ServiceException {
        String cepFormatado;

        if (cep !=  null) {
            cepFormatado = cep.replaceAll("[^0-9]", "");	//remove caracteres nao numericos

            if (cepFormatado.length() != 8) {
                LOGGER.warn("Cep Incorreto: " + cep);
                throw new IllegalArgumentException("O CEP deve conter 8 caracteres numericos.");
            }

        } else {
            LOGGER.warn("Cep nulo");
            throw new IllegalArgumentException("O CEP nao foi informado.");
        }

        return this.buscaCepSession.buscaEndereco(cepFormatado);
    }

    /**
     * Busca CEP por logradouro.
     *
     * @param endereco O endere&ccedil; passado como par&acirc;metro de busca.
     * @return Uma lista com os endere&ccedil;os contendo o CEP dos logradouros retornados.
     * @throws ServiceException Caso haja erro relacionado &acirc; busca.
     * @throws IllegalArgumentException Caso o par&acirc;metro passado seja inv&aacute;lido.
     */
    @GET
    @Path("cep/{endereco}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public List<Endereco> getCeps(@PathParam("endereco") String endereco) throws ServiceException {

        if (endereco == null || endereco.trim().isEmpty()) {
            LOGGER.warn("Endereco vazio: " + endereco);
            throw new IllegalArgumentException("O logradouro nao foi informado");
        }

        return this.buscaCepSession.buscaCeps(endereco);
    }

}
