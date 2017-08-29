package streamObserver;

import streamObserver.event.StreamStatusEvent;
import streamObserver.listener.StreamListener;

import java.util.List;

/**
 * notify all listeners about a status update
 */
public class NotifyStatusUpdateRunner extends Thread {
	private List<StreamListener> streamListeners;
	private StreamStatusEvent event;

	/**
	 * Constructor
	 * 
	 * @param streamListeners
	 * @param event
	 */
	public NotifyStatusUpdateRunner(List<StreamListener> streamListeners, StreamStatusEvent event) {
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
						if (event.isOnline())
							listener.streamIsOnline(event);
						else
							listener.streamIsOffline(event);
					}
				}).run();
			} catch (Exception e) {
			}
		}

	}
}