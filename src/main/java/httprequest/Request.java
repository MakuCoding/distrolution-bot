package httprequest;

import httprequest.request.Method;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.Collections;
import java.util.List;

public abstract class Request {

    /**
     * @return endpoint
     */
    public abstract String getEndpointUrl();

    /**
     * HTTP-Method used to request
     *
     * @return httpMethod
     */
    public abstract Method getHttpMethod();

    /**
     * HTTP-Parameter used for the request
     *
     * @return list of key-value-pairs
     */
    public List<NameValuePair> getHttpParams() {
        return Collections.emptyList();
    }

    /**
     * HTTP-Header used for the request
     *
     * @return
     */
    public List<Header> getHttpHeader() {
        return Collections.emptyList();
    }
}