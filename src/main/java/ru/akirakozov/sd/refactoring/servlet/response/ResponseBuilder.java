package ru.akirakozov.sd.refactoring.servlet.response;

public class ResponseBuilder {
    private final String endLine;
    private String responseBody = "";

    public ResponseBuilder(String endLine) {
        this.endLine = endLine;
    }

    public void addLine(String line) {
        responseBody += line + endLine;
    }

    public String build() {
        String result = "<html><body>" + endLine + responseBody;
        result += "</body></html>";
        return result;
    }
}
