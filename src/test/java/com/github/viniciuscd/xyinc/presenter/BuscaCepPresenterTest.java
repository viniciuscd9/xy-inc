package com.github.viniciuscd.xyinc.presenter;

import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.viniciuscd.xyinc.exception.NotFoundServiceException;
import com.github.viniciuscd.xyinc.exception.ServiceException;
import com.github.viniciuscd.xyinc.model.Endereco;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class BuscaCepPresenterTest {
    private static final String FULL_ARGUMENT = "Adress with result";
    private static final String EMPTY_ARGUMENT = "Adress with no result";

    private IBuscaCepPresenter buscaCepPresenter = BuscaCepPresenterImpl.getInstance();

    @Mock
    private Connection anyConnection;
    @Mock
    private Connection emptyConection;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Jsoup.class);
        Mockito.when(Jsoup.connect(Matchers.any())).thenReturn(this.anyConnection);

        Mockito.when(this.anyConnection.data(Matchers.anyString(), Matchers.anyString())).thenReturn(this.anyConnection);
        Mockito.when(this.anyConnection.data("relaxation", FULL_ARGUMENT)).thenReturn(this.anyConnection);

        Mockito.when(this.anyConnection.data("relaxation", EMPTY_ARGUMENT)).thenReturn(this.emptyConection);
        Mockito.when(this.emptyConection.data(Matchers.anyString(), Matchers.anyString())).thenReturn(this.emptyConection);

        StringBuilder stringBuilder = new StringBuilder("<table class=\"tmptabela\">")
                .append("<tr>")
                .append("<td>Header0</td>")
                .append("<td>Header1</td>")
                .append("<td>Header2</td>")
                .append("<td>Header3</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td>Endereco0&nbsp;</td>")
                .append("<td>Endereco1</td>")
                .append("<td>Endereco2</td>")
                .append("<td>Endereco3</td>")
                .append("</tr></table>");

        Document document = new Document("");
        document.append(stringBuilder.toString());

        Mockito.when(this.anyConnection.post()).thenReturn(document);
        Mockito.when(this.emptyConection.post()).thenReturn(new Document(""));
    }

    @Test
    public void buscaCepsSuccess() throws ServiceException {
        List<Endereco> result = this.buscaCepPresenter.buscaCeps(FULL_ARGUMENT);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }

    @Test(expected = NotFoundServiceException.class)
    public void buscaCepsEmpty() throws ServiceException {
        this.buscaCepPresenter.buscaCeps(EMPTY_ARGUMENT);
    }

    @Test
    public void buscaEnderecoSuccess() throws ServiceException {
        Endereco result = this.buscaCepPresenter.buscaEndereco(FULL_ARGUMENT);

        Assert.assertNotNull(result);
        Assert.assertEquals("Endereco0", result.getLogradouro());
        Assert.assertEquals("Endereco1", result.getBairro());
        Assert.assertEquals("Endereco2", result.getLocalidade());
        Assert.assertEquals("Endereco3", result.getCep());
    }

    @Test(expected = NotFoundServiceException.class)
    public void buscaEnderecoEmpty() throws ServiceException {
        this.buscaCepPresenter.buscaEndereco(EMPTY_ARGUMENT);
    }

}
