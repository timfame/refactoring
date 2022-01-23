package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;

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
    private final GetProductsServlet servlet = new GetProductsServlet();
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

        String expected = "<html><body>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testSingle() throws IOException, SQLException {
        Database.insert("first", 111);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "first\t111</br>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testMultiple() throws IOException, SQLException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 111);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "first\t111</br>" + Utils.ENDL +
                "second\t222</br>" + Utils.ENDL +
                "third\t111</br>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    private String getResponse() {
        return stringWriter.getBuffer().toString();
    }
}
