package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;
import ru.akirakozov.sd.refactoring.connection.ConnectionProvider;
import ru.akirakozov.sd.refactoring.connection.ConnectionProviderImpl;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductDaoImpl;
import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.service.ServiceImpl;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    private final ConnectionProvider connectionProvider = new ConnectionProviderImpl(Database.DATABASE_URL);
    private final ProductDao productDao = new ProductDaoImpl(connectionProvider);
    private final Service service = new ServiceImpl(productDao);
    private final ResponseBuilderGetter responseBuilderGetter = new ResponseBuilderGetter(Utils.END_LINE);
    private final AddProductServlet servlet = new AddProductServlet(service, responseBuilderGetter);

    private StringWriter stringWriter;

    private final static String expectedOK = "OK" + Utils.END_LINE;

    @BeforeClass
    public static void init() throws SQLException {
        Database.setupForTest();
    }

    @Before
    public void beforeTest() throws SQLException {
        stringWriter = new StringWriter();
        Database.deleteAllProducts();
    }

    @Test
    public void testNameAndPrice() throws Exception {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals(expectedOK, getResponse());
    }

    @Test
    public void testEmptyNameAndPrice() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals(expectedOK, getResponse());
    }

    @Test(expected = NumberFormatException.class)
    public void testNameAndEmptyPrice() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }

    private String getResponse() {
        return stringWriter.getBuffer().toString();
    }
}
