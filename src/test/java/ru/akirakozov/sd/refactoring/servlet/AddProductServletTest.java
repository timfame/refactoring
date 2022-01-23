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

public class AddProductServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AddProductServlet servlet = new AddProductServlet();
    private StringWriter stringWriter;

    private final static String expectedOK = "OK" + Utils.ENDL;

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
    public void testEmptyNameAndPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals(expectedOK, getResponse());
    }

    @Test(expected = NumberFormatException.class)
    public void testNameAndEmptyPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }

    private String getResponse() {
        return stringWriter.getBuffer().toString();
    }
}
