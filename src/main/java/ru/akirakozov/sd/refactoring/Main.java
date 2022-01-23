package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.connection.ConnectionProvider;
import ru.akirakozov.sd.refactoring.connection.ConnectionProviderImpl;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductDaoImpl;
import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.service.ServiceImpl;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.http.HttpServlet;

public class Main {

    private static final String DATABASE_URL = "jdbc:sqlite:test.db";
    private static final String END_LINE = "\r\n";

    public static void main(String[] args) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProviderImpl(DATABASE_URL);

        ProductDao productDao = new ProductDaoImpl(connectionProvider);
        productDao.createTable();

        Service service = new ServiceImpl(productDao);
        ResponseBuilderGetter responseBuilderGetter = new ResponseBuilderGetter(END_LINE);

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        addServlet(context, new AddProductServlet(service, responseBuilderGetter), "/add-product");
        addServlet(context, new GetProductsServlet(service, responseBuilderGetter), "/get-products");
        addServlet(context, new QueryServlet(service, responseBuilderGetter), "/query");

        server.start();
        server.join();
    }

    private static void addServlet(ServletContextHandler context, HttpServlet servlet, String path) {
        context.addServlet(new ServletHolder(servlet), path);
    }
}
