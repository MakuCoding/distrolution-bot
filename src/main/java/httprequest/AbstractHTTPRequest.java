package httprequest;

import httprequest.exception.ConnectorException;
import httprequest.exception.ParserException;
import httprequest.exception.StatusCodeException;
import httprequest.request.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHTTPRequest {
    private static final int TIMEOUT = 10 * 1000; // 10 seconds
    private String baseUrl;

    private List<httprequest.StatusCodeListener> listenerStatusCode = new ArrayList<httprequest.StatusCodeListener>();

    /**
     * Constructor with no baseUrl
     */
    public AbstractHTTPRequest() {
        this.baseUrl = null;
    }

    /**
     * Constructor
     *
     * @param baseUrl
     */
    public AbstractHTTPRequest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * do request
     *
     * @param request
     * @param resultClass
     * @return object extends de.tobj.http.simplerequest.Result
     * @throws ConnectorException
     * @throws ParserException
     * @throws StatusCodeException
     */
    public <R extends httprequest.Result> R request(httprequest.Request request, R resultClass) throws ConnectorException, ParserException,
            StatusCodeException {
        return sendRequest(request, resultClass);
    }

    /**
     * add listener for status-code
     *
     * @param listener
     */
    public void addListenerStatusCode(httprequest.StatusCodeListener listener) {
        this.listenerStatusCode.add(listener);
    }

    /**
     * generate and make request
     *
     * @param request
     * @param resultClass
     * @return object extends de.tobj.http.simplerequest.Result
     * @throws ConnectorException
     * @throws ParserException
     * @throws StatusCodeException
     */
    protected <R extends httprequest.Result> R sendRequest(httprequest.Request request, R resultClass) throws ConnectorException,
            ParserException, StatusCodeException {
        String requestUrl = (baseUrl == null || StringUtils.isBlank(baseUrl)) ? request.getEndpointUrl() : baseUrl
                + request.getEndpointUrl();

        HttpUriRequest httpRequest = null;
        CloseableHttpResponse response;
        try {
            httpRequest = generateHttpRequest(requestUrl, request);
            response = generateHttpClient().execute(httpRequest);
            return readResponse(response, resultClass);
        } catch (IOException e) {
            throw new ConnectorException(e);
        } finally {
            if (httpRequest instanceof HttpPost)
                ((HttpPost) httpRequest).releaseConnection();
            else if (httpRequest instanceof HttpGet)
                ((HttpGet) httpRequest).releaseConnection();
        }
    }

    /**
     * generate the GET/POST-request
     *
     * @param requestUrl
     * @param request
     * @return
     */
    private HttpUriRequest generateHttpRequest(String requestUrl, Request request) {
        HttpUriRequest httpRequest = null;
        if (request.getHttpMethod() != null && request.getHttpMethod() == Method.POST) {
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(request.getHttpParams(), Consts.UTF_8));
            httpRequest = httpPost;
        } else {
            if (request.getHttpParams() != null && request.getHttpParams().size() > 0) {
                String paramString = URLEncodedUtils.format(request.getHttpParams(), Consts.UTF_8);
                if (StringUtils.isNotEmpty(paramString))
                    requestUrl += "?" + paramString;
            }
            httpRequest = new HttpGet(requestUrl);
        }

        for (Header header : request.getHttpHeader())
            httpRequest.addHeader(header);

        return httpRequest;
    }

    /**
     * generate the http client with configuration
     *
     * @return
     */
    private CloseableHttpClient generateHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * read response from http request
     *
     * @param response
     * @param result
     * @return
     * @throws IOException
     * @throws ParserException
     * @throws StatusCodeException
     */
    private <R extends httprequest.Result> R readResponse(HttpResponse response, R result) throws IOException, ParserException,
            StatusCodeException {
        StringBuilder buffer = new StringBuilder();

        try {
            buffer.append(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
        } finally {
            EntityUtils.consume(response.getEntity());
        }

        notifyListenerForStatusCode(response.getStatusLine().getStatusCode(), buffer);
        return parseResponse(buffer, result);
    }

    /**
     * notify listener for status code
     *
     * @param statusCode
     * @param response
     * @throws StatusCodeException
     */
    private void notifyListenerForStatusCode(int statusCode, StringBuilder response) throws StatusCodeException {
        for (StatusCodeListener listener : this.listenerStatusCode) {
            listener.checkStatusCode(statusCode, response);
        }
    }

    /**
     * parse response
     *
     * @param response
     * @param result
     * @return
     * @throws ParserException
     */
    public abstract <R extends Result> R parseResponse(StringBuilder response, R result) throws ParserException;
}