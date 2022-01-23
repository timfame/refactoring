package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akirakozov
 */
public class AddProductServlet extends ProductsServlet {

    public AddProductServlet(Service service, ResponseBuilderGetter responseBuilderGetter) {
        super(service, responseBuilderGetter);
    }

    @Override
    protected String getResponseBody(HttpServletRequest request) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        service.AddProduct(name, price);

        return "OK";
    }
}
