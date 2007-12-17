package org.wicketstuff.push.timer;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelPublisher;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IPushTarget;

public class TimerChannelService implements IChannelService, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Duration duration;
	private IChannelPublisher publisher = new TimerChannelPublisher();

	public TimerChannelService(Duration duration) {
		this.duration = duration;
	}

	public void addChannelListener(Component component, final String listenerChannel, final IChannelListener listener) {
		final TimerChannelBehavior timerChannelBehavior = new TimerChannelBehavior(duration);
		final IPushTarget pushTarget = timerChannelBehavior.newPushTarget();
		component.add(timerChannelBehavior);
		EventStore.get().addEventStoreListener(new EventStoreListener(){
			public void EventTriggered(String eventChannel, Map data)
			{
				if (listenerChannel.equals(eventChannel)){
					listener.onEvent(listenerChannel, data, pushTarget);
					pushTarget.trigger();
				}
			}
			
		});
	}

	public void publish(ChannelEvent event) {
		publisher.publish(event);
	}

}
