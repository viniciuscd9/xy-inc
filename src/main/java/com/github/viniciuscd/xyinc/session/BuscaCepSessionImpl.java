package com.github.viniciuscd.xyinc.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BuscaCepSessionImpl implements BuscaCepSession {
    private static final BuscaCepSessionImpl INSTANCE = new BuscaCepSessionImpl();

    private static final String BUSCA_CEP_ENDERECO_URL = "http://www.buscacep.correios.com.br/";
    private static final String BUSCA_CEP_ENDERECO_ACTION = "sistemas/buscacep/resultadoBuscaCepEndereco.cfm";

    /**
     * Factory Method para cria&ccedil;&atilde;o de objeto como singleton.
     * @return Uma inst&agrave;ncia &uacute;nica do objeto BuscaCepSession.
     */
    public static BuscaCepSession getInstance() {
        return INSTANCE;
    }

    /**
     * Impede que a classe seja criada diretamente.
     */
    private BuscaCepSessionImpl() {
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
    public String buscaLogradouro(String cep) {
        List<String> valores = this.buscaValoresSite(cep, 0);

        if (valores.isEmpty()) {
            return null;
        }

        return valores.get(0);
    }

    /*
     * (non-Javadoc)
     * @see com.github.viniciuscd.xyinc.session.BuscaCepSession#buscaCep(java.lang.String)
     */
    @Override
    public List<String> buscaCep(String logradouro) {
        return this.buscaValoresSite(logradouro, 3);
    }

    /**
     * Conecta ao site dos correios e obt&eacute;m os valores desejados.
     *
     * @param valor String contendo o valor a ser buscado.
     * @param elemento N&uacute;mero da coluna (iniciando com zero) que cont&eacute;m os valores desejados.
     * @return Lista com os valores desejados retornados pelo site dos correios.
     */
    private List<String> buscaValoresSite(String valor, int elemento) {
        List<String> resultado = new ArrayList<>();

        try {
            //abre uma conexao post e obtem o resultado da pesquisa
            Document doc = Jsoup.connect(BUSCA_CEP_ENDERECO_URL + BUSCA_CEP_ENDERECO_ACTION)
                    .data("relaxation", valor)
                    .data("tipoCEP", "ALL")
                    .data("semelhante", "N")
                    .post();

            //busca pelos elementos da tabela contendo os ceps e logradouros
            Elements table = doc.select("table.tmptabela");

            //se retornou valores
            if (table != null && !table.isEmpty()) {
                //busca todas linhas da tablela
                Elements tableRows = table.select("tr");

                boolean firstElement = true;

                for (Element e : tableRows) {

                    //despresa o cabecalho
                    if (!firstElement) {
                        if (e.children().size() > elemento) {
                            //insere o elemento na lista
                            resultado.add(e.child(elemento).html());
                        }
                    } else {
                        firstElement = false;
                    }

                }

            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resultado;
    }

}
