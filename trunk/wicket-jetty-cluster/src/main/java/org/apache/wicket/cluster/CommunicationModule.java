package org.apache.wicket.cluster;


public interface CommunicationModule extends MessageSender {

	public void addMessageListener(MessageListener listener);

	public void removeMessageListener(MessageListener listener);

	public void addMemberListener(MemberListener listener);

	public void removeMemberListener(MemberListener listener);

	public void run();
	
	public void beginMessagesBatch();
	
	public void endMessagesBatch();
}
