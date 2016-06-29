package com.github.viniciuscd.xyinc.presenter;

import java.util.List;

import com.github.viniciuscd.xyinc.exception.ServiceException;
import com.github.viniciuscd.xyinc.model.Endereco;

public interface IBuscaCepPresenter {
    /**
     * Realiza a busca do logradouro associado ao CEP informado.
     *
     * @param cep O CEP passado para busca.
     * @return O logradouro retornado ou <code>null</code> caso n&atilde;o exista nenhum.
     * @throws ServiceException Caso o resultado esperado seja incorreto.
     */
    Endereco buscaEndereco(String cep) throws ServiceException;

    /**
     * Realiza a busca do CEP associado ao logradouro informado.
     *
     * @param logradouro O Logradouro passado para busca.
     * @return O CEP retornado ou <code>null</code> caso n&atilde;o exista nenhum.
     * @throws ServiceException Caso o resultado esperado seja incorreto.
     */
    List<Endereco> buscaCeps(String logradouro) throws ServiceException;
}
