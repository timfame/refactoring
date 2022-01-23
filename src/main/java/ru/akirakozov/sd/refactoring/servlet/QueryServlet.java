package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.Command;
import ru.akirakozov.sd.refactoring.service.Service;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.response.ResponseBuilderGetter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akirakozov
 */
public class QueryServlet extends ProductsServlet {

    public QueryServlet(Service service, ResponseBuilderGetter responseBuilderGetter) {
        super(service, responseBuilderGetter);
    }

    @Override
    protected String getResponseBody(HttpServletRequest request) {
        String commandParameter = request.getParameter("command");
        Command command;
        try {
            command = Command.valueOf(commandParameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Unknown command: " + commandParameter;
        }

        ResponseBuilder responseBuilder = responseBuilderGetter.getResponseBuilder();

        switch (command) {
            case MAX:
            case MIN:
                responseBuilder.addLine("<h1>Product with " + commandParameter + " price: </h1>");
                Product product = service.QueryProduct(command);
                if (product != null) {
                    responseBuilder.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
                }
                break;
            case SUM:
                processQueryLong(command, responseBuilder, "Summary price");
                break;
            case COUNT:
                processQueryLong(command, responseBuilder, "Number of products");
                break;
        }

        return responseBuilder.build();
    }

    private void processQueryLong(Command command, ResponseBuilder responseBuilder, String description) {
        responseBuilder.addLine(description + ": ");
        long result = service.QueryLong(command);
        responseBuilder.addLine(Long.toString(result));
    }

}
