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

public class GetProductsServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    private final ConnectionProvider connectionProvider = new ConnectionProviderImpl(Database.DATABASE_URL);
    private final ProductDao productDao = new ProductDaoImpl(connectionProvider);
    private final Service service = new ServiceImpl(productDao);
    private final ResponseBuilderGetter responseBuilderGetter = new ResponseBuilderGetter(Utils.END_LINE);
    private final GetProductsServlet servlet = new GetProductsServlet(service, responseBuilderGetter);

    private StringWriter stringWriter;

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
    public void testEmpty() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.END_LINE +
                "</body></html>" + Utils.END_LINE;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testSingle() throws IOException, SQLException, ServletException {
        Database.insert("first", 111);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.END_LINE +
                "first\t111</br>" + Utils.END_LINE +
                "</body></html>" + Utils.END_LINE;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testMultiple() throws IOException, SQLException, ServletException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 111);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.END_LINE +
                "first\t111</br>" + Utils.END_LINE +
                "second\t222</br>" + Utils.END_LINE +
                "third\t111</br>" + Utils.END_LINE +
                "</body></html>" + Utils.END_LINE;

        Assert.assertEquals(expected, getResponse());
    }

    private String getResponse() {
        return stringWriter.getBuffer().toString();
    }
}
