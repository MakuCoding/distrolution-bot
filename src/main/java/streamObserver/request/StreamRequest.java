package streamObserver.request;

import httprequest.Request;
import httprequest.request.Method;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import streamObserver.Channel;

import java.util.LinkedList;
import java.util.List;

/**
 * StreamRequest object for HTTPRequestJSON
 */
public class StreamRequest extends Request {

	private Channel channel;
	private String clientId;

	/**
	 * Construtor with channel
	 * 
	 * @param channel
	 * @param clientId
	 */
	public StreamRequest(Channel channel, String clientId) {
		this.channel = channel;
		this.clientId = clientId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tobj.http.simplerequest.Request#getEndpointUrl()
	 */
	@Override
	public String getEndpointUrl() {
		return "streams/" + channel.getChannelName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tobj.http.simplerequest.Request#getHttpMethod()
	 */
	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}

	@Override
	public List<Header> getHttpHeader() {
		List<Header> list = new LinkedList<Header>();
		list.add(new BasicHeader("Accept", "application/vnd.twitchtv.v3+json"));
		if (StringUtils.isNotBlank(clientId))
			list.add(new BasicHeader("Client-ID", clientId));
		return list;
	}
}