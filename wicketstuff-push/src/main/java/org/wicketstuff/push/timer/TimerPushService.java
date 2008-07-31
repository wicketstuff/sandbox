package org.wicketstuff.push.timer;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.IPushInstaller;
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
	private static final class PushInstallerBehavior extends AbstractBehavior {
    private static final long serialVersionUID = 1L;

    private final Duration duration;
		private boolean installed = false;

		public PushInstallerBehavior(final Duration duration) {
			this.duration = duration;
		}

		@Override
		public void beforeRender(final Component component) {
			if (!installed || !hasPush(component, duration)) {
				final IPushInstaller installer = getPushInstaller(component);
				final TimerChannelBehavior timerChannelBehavior = getTimerChannelBehavior(component);
				if (timerChannelBehavior != null && installer != null) {
					final IPushTarget pushTarget = timerChannelBehavior.newPushTarget();
					installer.install(component, pushTarget);
					installed = true;
				}
			}
		}
	}

	private static final MetaDataKey PUSH_INSTALLER = new MetaDataKey(IPushInstaller.class) {
		private static final long serialVersionUID = 1L;
	};

	private static boolean hasPush(final Component component, final Duration duration) {
		final TimerChannelBehavior timerChannelBehavior = getTimerChannelBehavior(component);
		if (timerChannelBehavior != null) {
			return TimerChannelBehavior.isConnected(
					component.getApplication(),
					timerChannelBehavior.getId(),
					duration.add(TimerChannelBehavior.TIMEOUT_MARGIN));
		} else {
			return false;
		}
	}

	private static IPushInstaller getPushInstaller(final Component component) {
		return (IPushInstaller) component.getMetaData(PUSH_INSTALLER);
	}

	private static void putPushInstaller(final Component component, final IPushInstaller pushInstaller) {
		component.setMetaData(PUSH_INSTALLER, pushInstaller);
	}

	private static void removePushInstaller(final Component component) {
		putPushInstaller(component, null);
	}

	@SuppressWarnings("unchecked")
  private static TimerChannelBehavior getTimerChannelBehavior(final Component component) {
		for (final Iterator it = component.getBehaviors().iterator(); it.hasNext();) {
			final IBehavior behavior = (IBehavior) it.next();
			if (behavior instanceof TimerChannelBehavior) {
				return (TimerChannelBehavior) behavior;
			}
		}
		return null;
	}


	private final Duration duration;

	/**
	 * Constructs a TimerPushService with the given polling interval.
	 *
	 * @param duration the polling interval, must not be null
	 */
	public TimerPushService(final Duration duration) {
		if (duration == null) {
			throw new IllegalArgumentException("duration must not be null");
		}
		this.duration = duration;
	}

	public void installPush(final Component component, final IPushInstaller installer) {
		putPushInstaller(component, installer);
		component.add(new PushInstallerBehavior(duration));
		component.add(new TimerChannelBehavior(duration));
	}

	public void uninstallPush(final Component component) {
		removePushInstaller(component);
		for (final Object behavior : component.getBehaviors()) {
			if (behavior instanceof PushInstallerBehavior) {
				component.remove((IBehavior) behavior);
			}
			if (behavior instanceof TimerChannelBehavior) {
				component.remove((IBehavior) behavior);
			}
		}
	}

	/**
	 * Returns the polling interval
	 * @return the polling interval
	 */
	public Duration getDuration() {
		return duration;
	}

}
