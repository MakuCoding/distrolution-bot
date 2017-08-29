package httprequest;

public class SimpleResult extends Result {
    private StringBuilder response;

    public SimpleResult() {

    }

    public SimpleResult(StringBuilder response) {
        this.response = response;
    }

    public SimpleResult(String response) {
        this.response = new StringBuilder(response);
    }

    public StringBuilder getResponse() {
        return response;
    }

    public void setResponse(StringBuilder response) {
        this.response = response;
    }
}