package org.apache.wicket.cluster;

public interface MemberListener {
	
	public void onMemberAdded(Member member);

	public void onMemberRemoved(Member member);
}
