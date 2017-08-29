package httprequest.exception;

public class StatusCodeException extends SimpleHTTPRequestException {
    private static final long serialVersionUID = -3090769510762425491L;

    public StatusCodeException(String message) {
        super(message);
    }
}