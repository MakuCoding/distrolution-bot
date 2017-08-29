package streamObserver;

import streamObserver.event.StreamUpdateEvent;
import streamObserver.listener.StreamListener;

import java.util.List;

/**
 * Notify all listeners about a stream update
 */
public class NotifyStreamUpdateRunner extends Thread {
	private List<StreamListener> streamListeners;
	private StreamUpdateEvent event;

	/**
	 * Constructor
	 * 
	 * @param streamListeners
	 * @param event
	 */
	public NotifyStreamUpdateRunner(List<StreamListener> streamListeners, StreamUpdateEvent event) {
		this.streamListeners = streamListeners;
		this.event = event;
	}

	/**
	 * start notify all listeners
	 */
	@Override
	public void run() {
		for (final StreamListener listener : streamListeners) {
			try {
				(new Runnable() {
					@Override
					public void run() {
						listener.streamUpdate(event);
					}
				}).run();
			} catch (Exception e) {
			}
		}
	}
}