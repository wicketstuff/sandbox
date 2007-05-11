package org.wicketstuff.push.timer;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelPublisher;
import org.wicketstuff.push.IChannelService;

public class TimerChannelService implements IChannelService, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Duration duration;
	private IChannelPublisher publisher = new TimerChannelPublisher();

	public TimerChannelService(Duration duration) {
		this.duration = duration;
	}

	public void addChannelListener(Component component, String channel, IChannelListener listener) {
		component.add(new TimerChannelBehavior(duration, channel, listener));
	}

	public void publish(ChannelEvent event) {
		publisher.publish(event);
	}

}
