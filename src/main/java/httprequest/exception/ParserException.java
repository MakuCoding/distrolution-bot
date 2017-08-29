package httprequest.exception;

public class ParserException extends SimpleHTTPRequestException {
    private static final long serialVersionUID = -7624141178847624365L;

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message) {
        super(message);
    }
}