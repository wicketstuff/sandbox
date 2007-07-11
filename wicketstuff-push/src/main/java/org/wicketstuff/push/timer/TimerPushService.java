package org.wicketstuff.push.timer;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.IPushTarget;

/**
 * An implementation of IPushService based on a polling mechanism.
 * 
 * This class is thread safe, and can be safely reused.
 * 
 * @author Xavier Hanin
 */
public class TimerPushService implements IPushService {
	private final Duration duration;

	/**
	 * Constructs a TimerPushService with the given polling interval.
	 * 
	 * @param duration the polling interval, must not be null
	 */
	public TimerPushService(Duration duration) {
		if (duration == null) {
			throw new IllegalArgumentException("duration must not be null");
		}
		this.duration = duration;
	}

	public IPushTarget installPush(Component component) {
		TimerChannelBehavior timerChannelBehavior = new TimerChannelBehavior(duration);
		component.add(timerChannelBehavior);
		return timerChannelBehavior.newPushTarget();
	}

	/**
	 * Returns the polling interval
	 * @return the polling interval
	 */
	public Duration getDuration() {
		return duration;
	}

}
