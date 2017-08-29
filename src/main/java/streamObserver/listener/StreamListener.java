package streamObserver.listener;

import streamObserver.event.StreamStatusEvent;
import streamObserver.event.StreamUpdateEvent;

/**
 * StreamListener
 */
public abstract class StreamListener {

	/**
	 * stream is online event
	 * 
	 * @param event
	 */
	public void streamIsOnline(StreamStatusEvent event) {

	}

	/**
	 * stream is offline event
	 * 
	 * @param event
	 */
	public void streamIsOffline(StreamStatusEvent event) {

	}

	/**
	 * stream update event
	 * 
	 * @param event
	 */
	public void streamUpdate(StreamUpdateEvent event) {

	}
}