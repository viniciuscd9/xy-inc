package com.github.viniciuscd.xyinc.session;

import java.util.List;

public interface BuscaCepSession {
    /**
     * Conecta ao site dos correios e busca o logradouro associado ao CEP informado.
     *
     * @param cep O CEP passado para busca.
     * @return O logradouro retornado ou <code>null</code> caso n&atilde;o exista nenhum.
     */
    String buscaLogradouro(String cep);

    /**
     * Conecta ao site dos correios e busca o CEP associado ao logradouro informado.
     *
     * @param logradouro O Logradouro passado para busca.
     * @return O CEP retornado ou <code>null</code> caso n&atilde;o exista nenhum.
     */
    List<String> buscaCep(String logradouro);
}
