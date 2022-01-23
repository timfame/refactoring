package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends ProductsServlet {

    public GetProductsServlet(Service service, ResponseBuilderGetter responseBuilderGetter) {
        super(service, responseBuilderGetter);
    }

    @Override
    protected String getResponseBody(HttpServletRequest request) {
        List<Product> products = service.GetProducts();

        ResponseBuilder responseBuilder = responseBuilderGetter.getResponseBuilder();

        for (Product product : products) {
            responseBuilder.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
        }

        return responseBuilder.build();
    }
}
