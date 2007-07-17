package org.apache.wicket.cluster.initializer;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.PageStoreReplicator;
import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.cluster.SessionProvider;
import org.apache.wicket.cluster.initializer.message.AddSessionCountMessage;
import org.apache.wicket.cluster.initializer.message.ReplicateSessionMessage;
import org.apache.wicket.cluster.initializer.message.ReplicationDoneMessage;
import org.apache.wicket.cluster.initializer.message.ReplicationRequestMessage;
import org.apache.wicket.cluster.initializer.message.StopProvidingReplicationDataMessage;
import org.apache.wicket.cluster.session.SessionAttributeHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for initializing newly started nodes. Initializing means
 * replicating state of a node already running in cluster.
 * <p>
 * Initialization works rougly like this:
 * <ul>
 * <li>Existing members detect a new member and each one of them sends an
 * {@link AddSessionCountMessage} to the new member. The message contains number
 * of sessions on existing client.
 * <li>The new member picks existing member with highest number of sessions and
 * sends {@link ReplicationRequestMessage} to that member.
 * <li>The existing member then sends periodically
 * {@link ReplicateSessionMessage}s for each session on the member. After all
 * sessions are processed, a {@link ReplicationDoneMessage} is sent.
 * <li>When the new member detects an interval between
 * {@link ReplicateSessionMessage}s that is larger than
 * {@link #getReplicationTimeout()}, it stops the replication by sending the
 * {@link StopProvidingReplicationDataMessage} to the existing member. After
 * that the new member picks another existing member (if there is any) and
 * intiates replication (going to step 2).
 * </ul>
 * 
 * @author Matej Knopp
 * 
 */
public class NodeInitializer {

	/**
	 * Simple class that maps member to session count
	 * 
	 * @author Matej Knopp
	 */
	private static class MemberSessionCount implements Comparable<MemberSessionCount> {
		Member member;

		int sessionCount;

		public MemberSessionCount(Member member, int sessionCount) {
			this.member = member;
			this.sessionCount = sessionCount;
		}

		public int compareTo(MemberSessionCount that) {
			return this.sessionCount < that.sessionCount ? -1 : (this.sessionCount == that.sessionCount ? 0 : 1);
		}
	}

	/**
	 * Sorted set of member session counts (highest session count is the last)
	 */
	private SortedSet<MemberSessionCount> memberSessionCounts = new TreeSet<MemberSessionCount>();

	private final MessageSender messageSender;

	private final SessionProvider sessionProvider;
	
	private final PageStoreReplicator clusteredPageStore;

	public NodeInitializer(MessageSender messageSender, SessionProvider sessionProvider, PageStoreReplicator clusteredPageStore) {
		this.messageSender = messageSender;
		this.sessionProvider = sessionProvider;
		this.clusteredPageStore = clusteredPageStore;
	}

	/**
	 * Whether we have alrady started the main thread.
	 */
	private boolean threadStarted = false;

	/**
	 * Adds the given member->sessionCount pair, and starts the main thread if
	 * that is not already started
	 * 
	 * @param sessionCount
	 * @param member
	 */
	public void addSessionCount(int sessionCount, Member member) {
		// only add the session count if this node hasn't already more sessions
		// (this message is sent from new members to existing members as well,
		// so
		// we need to filter that out)
		if (sessionProvider.getActiveSessionsCount() < sessionCount) {
			boolean startThread = false;
			synchronized (this) {
				memberSessionCounts.add(new MemberSessionCount(member, sessionCount));
				if (threadStarted == false) {
					startThread = threadStarted = true;
				}
			}
			if (startThread) {
				startThread();
			}
		}
	}

	/**
	 * Sets how much to wait since the first of existing members reports it's
	 * session count.
	 * 
	 * @param waitTime
	 *            time to wait (in milliseconds), default 1000
	 */
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	private long waitTime = 1000;

	/**
	 * Starts the main thread (invoked from first
	 * {@link #addSessionCount(int, Object)} call)
	 */
	private void startThread() {
		Thread thread;

		thread = new Thread() {
			public void run() {
				// wait for the other members to report sessions count
				try {
					Thread.sleep(getWaitTime());
				} catch (InterruptedException ignore) {
				}

				requestReplicationCycle();
			}
		};

		thread.setDaemon(true);
		thread.start();
	}

	private synchronized boolean atLeastOneMemberLeft() {
		return memberSessionCounts.size() > 0;
	}

	/**
	 * Main replication cycle. It asks all members to replicate sessions, until
	 * at least one replication is successfull (ended with sending
	 * {@link ReplicationDoneMessage} from existing member to the new member.
	 */
	private void requestReplicationCycle() {
		while (atLeastOneMemberLeft()) {
			try {
				Object member = requestReplication();

				replicationTimestamp = System.currentTimeMillis();

				// if replicationTimestamp is -1, it means that we are done.
				// if the delay between replicationTimestamp and current time
				// is too high, it means that the existing node stopped
				// providing
				// replication information
				while (replicationTimestamp != -1
						&& (System.currentTimeMillis() - replicationTimestamp < getReplicationTimeout())) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignore) {
					}
				}

				// if we are not yet done
				if (replicationTimestamp != -1) {
					log.info("Member ceased to provide replication information " + member);
					// continue the cycle
				} else {
					// we are done, replication successful
					break;
				}
			} catch (RuntimeException e) {
				log.error("Error sending replication initiation message", e);
				// continue to other members
			}
		}
	}

	/**
	 * Request session replication from client with highest number of session.
	 * Also returns the member and removes the member from
	 * {@link #memberSessionCounts}.
	 */
	private Object requestReplication() {
		MemberSessionCount highest;
		synchronized (this) {
			highest = memberSessionCounts.last();
			memberSessionCounts.remove(highest);
		}

		log.info("Sending replication request to " + highest.member);
		messageSender.sendMessage(new ReplicationRequestMessage(), highest.member);
		return highest.member;
	}

	/**
	 * Creates a new replication thread to replicate all sessions of current
	 * member.
	 * 
	 * @param member
	 *            target member (new)
	 */
	public void replicateSessions(final Member member) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				replicateSessionsInternal(member);
			}
		};
		thread.setDaemon(true);
		thread.run();
	}

	// members that asked this member to stop providing replication data
	private Map<Object, Boolean> stopProvidingReplicationDataTargets = new ConcurrentHashMap<Object, Boolean>();

	/**
	 * Message from member to stop providing replication data
	 * 
	 * @param targetMember
	 */
	public void stopProvidingReplicationData(Object targetMember) {
		stopProvidingReplicationDataTargets.put(targetMember, true);
	}

	/**
	 * Replicates the sessions from this member to target member
	 * 
	 * @param member
	 *            target member
	 */
	private void replicateSessionsInternal(Member member) {
		Set<String> active = sessionProvider.getActiveSessions();
		log.info("Replicating " + active.size() + " sessions - destination " + member);

		try {
			for (String id : active) {
				// should we stop replicating?
				if (stopProvidingReplicationDataTargets.containsKey(member)) {
					stopProvidingReplicationDataTargets.remove(member);
					break;
				}

				HttpSession session = sessionProvider.getSession(id, false);
				if (session != null) {
					ReplicateSessionMessage action = new ReplicateSessionMessage(session);
					messageSender.sendMessage(action, member);
				}
				
				clusteredPageStore.replicatePageStore(id, member);
			}

			messageSender.sendMessage(new ReplicationDoneMessage(), member);

			log.info("Replicating sessions done - OK");
		} catch (RuntimeException e) {
			log.error("Replicating sessions error", e);
		}
	}

	private long replicationTimeout = 20000;

	/**
	 * Sets the maximal timespan between replication message. When no new
	 * replication message arrives within the replicationTimeout, the node asked
	 * for providing replication is considered dead and another node (if there
	 * is any) is asked to provide replication data.
	 * 
	 * @param replicationTimeout
	 */
	public void setReplicationTimeout(long replicationTimeout) {
		this.replicationTimeout = replicationTimeout;
	}

	public long getReplicationTimeout() {
		return replicationTimeout;
	}

	/**
	 * Timestamp updated with each replication message that the target node
	 * gets. After replication is done, the timestamp is set again to -1. The
	 * timestamp is used to detect cases when the node asked for providing
	 * session iformation won't replicate all sessions.
	 */
	private volatile long replicationTimestamp = -1;

	/**
	 * Message invoked on new node to store session provided by existing node.
	 * @param id
	 * @param maxInactiveInterval
	 * @param attributes
	 */
	public void replicateSession(String id, int maxInactiveInterval, Map<String, SessionAttributeHolder> attributes) {
		log.debug("Replicating session " + id);
		HttpSession session = sessionProvider.getSession(id, true);
		session.setMaxInactiveInterval(maxInactiveInterval);
		for (Entry<String, SessionAttributeHolder> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		if (replicationTimestamp != -1) {
			replicationTimestamp = System.currentTimeMillis();
		}
	}

	/**
	 * Invoked on new node when replication is done. This is to let the main thread know
	 * that th replication finished ok and there is no need to request replication from
	 * another member.
	 */
	public void replicationDone() {
		replicationTimestamp = -1;
	}

	/**
	 * Called when new member is added to cluster.
	 * @param member
	 */
	public void memberAdded(Member member) {
		int count = sessionProvider.getActiveSessionsCount();
		try {
			if (count > 0) {
				// if we have any sessions inform the added member about our session count
				messageSender.sendMessage(new AddSessionCountMessage(count), member);
			}
		} catch (RuntimeException e) {
			log.error("Error informing new member about sessions count", e);
		}
	}

	private static final Logger log = LoggerFactory.getLogger(NodeInitializer.class);
}
