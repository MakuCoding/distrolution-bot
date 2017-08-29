package httprequest;

import httprequest.exception.StatusCodeException;

public abstract class StatusCodeListener {
    public abstract void checkStatusCode(int statusCode, StringBuilder response) throws StatusCodeException;
}