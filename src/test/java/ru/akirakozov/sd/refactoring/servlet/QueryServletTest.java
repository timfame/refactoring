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

public class QueryServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final QueryServlet servlet = new QueryServlet();
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
    public void testMaxEmpty() throws Exception {
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "<h1>Product with max price: </h1>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testMax() throws IOException, SQLException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 55);

        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "<h1>Product with max price: </h1>" + Utils.ENDL +
                "second\t222</br>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testMinEmpty() throws Exception {
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "<h1>Product with min price: </h1>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testMin() throws IOException, SQLException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 55);

        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "<h1>Product with min price: </h1>" + Utils.ENDL +
                "third\t55</br>" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testSumEmpty() throws Exception {
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "Summary price: " + Utils.ENDL +
                "0" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testSum() throws IOException, SQLException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 55);

        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "Summary price: " + Utils.ENDL +
                "388" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testCountEmpty() throws Exception {
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "Number of products: " + Utils.ENDL +
                "0" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    @Test
    public void testCount() throws IOException, SQLException {
        Database.insert("first", 111);
        Database.insert("second", 222);
        Database.insert("third", 55);

        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>" + Utils.ENDL +
                "Number of products: " + Utils.ENDL +
                "3" + Utils.ENDL +
                "</body></html>" + Utils.ENDL;

        Assert.assertEquals(expected, getResponse());
    }

    private String getResponse() {
        return stringWriter.getBuffer().toString();
    }
}
