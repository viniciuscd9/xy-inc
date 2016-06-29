package com.github.viniciuscd.xyinc.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.viniciuscd.xyinc.exception.ServiceException;
import com.github.viniciuscd.xyinc.model.Endereco;
import com.github.viniciuscd.xyinc.presenter.IBuscaCepPresenter;

@RunWith(MockitoJUnitRunner.class)
public class BuscaCepEndpointTest {
    private static final Endereco RETURN_ENDERECO_TEST = new Endereco();

    private BuscaCepEndpoint buscaCepEndpoint;
    @Mock
    private IBuscaCepPresenter buscaCepPresenter;

    @Before
    public void setUp() throws ServiceException {
        this.buscaCepEndpoint = new BuscaCepEndpoint(this.buscaCepPresenter);

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(RETURN_ENDERECO_TEST);
        Mockito.when(this.buscaCepPresenter.buscaCeps(Matchers.anyString())).thenReturn(enderecos);
        Mockito.when(this.buscaCepPresenter.buscaEndereco(Matchers.anyString())).thenReturn(RETURN_ENDERECO_TEST);
    }

    @Test
    public void buscaCepSuccess() throws ServiceException {
        String logradouro = "Endereco Teste";
        List<Endereco> result = this.buscaCepEndpoint.getCeps(logradouro);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());

        Mockito.verify(this.buscaCepPresenter).buscaCeps(logradouro);
    }

    @Test
    public void buscaEnderecosSuccess() throws ServiceException {
        Endereco result = this.buscaCepEndpoint.getEndereco("12400-800");

        Assert.assertEquals(RETURN_ENDERECO_TEST, result);

        Mockito.verify(this.buscaCepPresenter).buscaEndereco("12400800");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buscaEnderecoCepInvalido() throws ServiceException {
        this.buscaCepEndpoint.getEndereco("Rua teste cep invalido");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buscaCepLogradouroInvalido() throws ServiceException {
        this.buscaCepEndpoint.getCeps("   ");
    }

}
