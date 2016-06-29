package com.github.viniciuscd.xyinc.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.viniciuscd.xyinc.exception.NotFoundServiceException;
import com.github.viniciuscd.xyinc.exception.ServiceException;
import com.github.viniciuscd.xyinc.model.Endereco;

/**
 * Realiza busca de endere&ccedil;os e CEPs retornados pelo site dos correios.
 *
 * @author Vinicius C de Deus
 *
 */
public class BuscaCepPresenterImpl implements IBuscaCepPresenter {
    private static final BuscaCepPresenterImpl INSTANCE = new BuscaCepPresenterImpl();

    private static final String BUSCA_CEP_ENDERECO_URL = "http://www.buscacep.correios.com.br/sistemas/buscacep/resultadoBuscaCepEndereco.cfm";

    /**
     * Factory Method para cria&ccedil;&atilde;o de objeto como singleton.
     * @return Uma inst&agrave;ncia &uacute;nica do objeto BuscaCepSession.
     */
    public static IBuscaCepPresenter getInstance() {
        return INSTANCE;
    }

    /**
     * Impede que a classe seja criada diretamente.
     */
    private BuscaCepPresenterImpl() {
        //se construtor foi chamado via reflection
        if (INSTANCE != null) {
            throw new IllegalStateException("Nao e possiviel instanciar classe singleton via reflection.");
        }

    }

    /*
     * (non-Javadoc)
     * @see com.github.viniciuscd.xyinc.session.BuscaCepSession#buscaLogradouro(java.lang.String)
     */
    @Override
    public List<Endereco> buscaCeps(String cep) throws ServiceException {
        List<Endereco> valores = this.parseEnderecos(cep);

        if (valores.isEmpty()) {
            throw new NotFoundServiceException("CEP nao encontrado.");
        }

        return valores;
    }

    /*
     * (non-Javadoc)
     * @see com.github.viniciuscd.xyinc.session.BuscaCepSession#buscaCep(java.lang.String)
     */
    @Override
    public Endereco buscaEndereco(String logradouro) throws ServiceException {
        List<Endereco> valores = this.parseEnderecos(logradouro);

        if (valores.isEmpty()) {
            throw new NotFoundServiceException("Logradouro nao encontrado.");
        }

        return valores.get(0);
    }

    /**
     * Conecta ao site dos correios e obt&eacute;m os valores desejados.
     *
     * @param valor String contendo o valor a ser buscado.
     * @param elemento N&uacute;mero da coluna (iniciando com zero) que cont&eacute;m os valores desejados.
     * @return Lista com os valores desejados retornados pelo site dos correios.
     * @throws ServiceException Se ocorreu erro ao fazer conex&atilde;o com banco de dados.
     */
    private List<Endereco> parseEnderecos(String valor) throws ServiceException {
        List<Endereco> resultado = new ArrayList<>();

        try {
            //abre uma conexao post e obtem o resultado da pesquisa]
            Document doc = Jsoup.connect(BUSCA_CEP_ENDERECO_URL)
                    .data("relaxation", valor)
                    .data("tipoCEP", "ALL")
                    .data("semelhante", "N")
                    .post();

            //busca pelos elementos da tabela contendo os ceps e logradouros
            Elements table = doc.select("table.tmptabela");

            //se retornou valores
            if (table != null && !table.isEmpty()) {
                this.parseTableRows(resultado, table);
            }


        } catch (IOException e) {
            throw new ServiceException("Erro ao relizar conexao com site [" + BUSCA_CEP_ENDERECO_URL + "]", e);
        }

        return resultado;
    }

    private void parseTableRows(List<Endereco> resultado, Elements table) {
        //busca todas linhas da tablela
        Elements tableRows = table.select("tr");

        boolean firstElement = true;

        //para todas as linhas da tabela de cep
        for (Element e : tableRows) {

            //se nao e o primeiro elemento
            if (!firstElement) {
                if (e.children().size() == 4) {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(e.child(0).html());
                    endereco.setBairro(e.child(1).html());
                    endereco.setLocalidade(e.child(2).html());
                    endereco.setCep(e.child(3).html());

                    resultado.add(endereco);
                }
            } else {
                //despresa o cabecalho
                firstElement = false;
            }

        }
    }

}
