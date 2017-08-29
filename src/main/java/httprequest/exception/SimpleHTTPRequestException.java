package httprequest.exception;

public class SimpleHTTPRequestException extends Exception {
    private static final long serialVersionUID = -205853790222733993L;

    public SimpleHTTPRequestException(Throwable cause) {
        super(cause);
    }

    public SimpleHTTPRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleHTTPRequestException(String message) {
        super(message);
    }
}