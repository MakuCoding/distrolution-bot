package httprequest.exception;

public class ConnectorException extends SimpleHTTPRequestException {
    private static final long serialVersionUID = -6796783854667533829L;

    public ConnectorException(Throwable cause) {
        super(cause);
    }

    public ConnectorException(String message) {
        super(message);
    }
}