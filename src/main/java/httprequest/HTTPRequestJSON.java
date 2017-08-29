package httprequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httprequest.exception.ParserException;

import java.io.IOException;

public class HTTPRequestJSON extends AbstractHTTPRequest {

	/**
	 * Constructor with no baseUrl
	 */
	public HTTPRequestJSON() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param baseUrl
	 */
	public HTTPRequestJSON(String baseUrl) {
		super(baseUrl);
	}

	/* (non-Javadoc)
	 * @see de.tobj.http.simplerequest.AbstractHTTPRequest#parseResponse(java.lang.StringBuilder, de.tobj.http.simplerequest.Result)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <R extends Result> R parseResponse(StringBuilder response, R result) throws ParserException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (R) mapper.readValue(response.toString(), result.getClass());
		} catch (JsonParseException | JsonMappingException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ParserException(e);
		}
	}
}