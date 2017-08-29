package httprequest;

import httprequest.exception.ConnectorException;
import httprequest.exception.ParserException;
import httprequest.exception.StatusCodeException;

public class SimpleHTTPRequest extends AbstractHTTPRequest {

    /**
     * Constructor with no baseUrl
     */
    public SimpleHTTPRequest() {
        super();
    }

    /**
     * Constructor
     *
     * @param url
     */
    public SimpleHTTPRequest(String url) {
        super(url);
    }

    /**
     * send request
     *
     * @param request
     * @return SimpleResult
     * @throws ConnectorException
     * @throws ParserException
     * @throws StatusCodeException
     */
    public httprequest.SimpleResult request(Request request) throws ConnectorException, ParserException, StatusCodeException {
        return sendRequest(request, new httprequest.SimpleResult());
    }

    /**
     * parse response
     *
     * @param response
     * @param result
     * @return
     * @throws ParserException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R extends Result> R parseResponse(StringBuilder response, R result) throws ParserException {
        return (R) new SimpleResult(response);
    }
}