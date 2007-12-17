package org.apache.wicket.cluster.tribes;

import org.apache.catalina.tribes.Member;

public class TribesMember implements org.apache.wicket.cluster.Member {
	
	private final Member delegate;
	
	TribesMember(Member delegate) {
		
		if (delegate == null)
		{
			throw new IllegalArgumentException("Tribes member may not be null");
		}
		
		this.delegate = delegate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		if (obj instanceof TribesMember == false)
		{
			return false;
		}
		
		TribesMember rhs = (TribesMember) obj;
		return delegate.equals(rhs.delegate);
	}
	
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
	
	@Override
	public String toString() {
		return delegate.toString();
	}
	
	public Member getTribesMember() {
		return delegate;
	}
}
