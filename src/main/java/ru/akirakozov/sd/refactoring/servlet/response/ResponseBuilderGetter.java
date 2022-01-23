package ru.akirakozov.sd.refactoring.servlet.response;

public class ResponseBuilderGetter {
    private final String endLine;

    public ResponseBuilderGetter(String endLine) {
        this.endLine = endLine;
    }

    public ResponseBuilder getResponseBuilder() {
        return new ResponseBuilder(endLine);
    }
}
