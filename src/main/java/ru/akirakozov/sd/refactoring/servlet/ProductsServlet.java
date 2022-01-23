package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class ProductsServlet extends HttpServlet {

    protected final Service service;
    protected final ResponseBuilderGetter responseBuilderGetter;

    protected ProductsServlet(Service service, ResponseBuilderGetter responseBuilderGetter) {
        this.service = service;
        this.responseBuilderGetter = responseBuilderGetter;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = getResponseBody(req);
        resp.getWriter().println(body);
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    abstract String getResponseBody(HttpServletRequest req);
}
