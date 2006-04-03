package contrib.wicket.cms.example.session;

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

public class SecuritySession extends WebSession {

	private Integer memberId;

	public SecuritySession(final WebApplication application) {
		super(application);
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public static SecuritySession get() {
		return (SecuritySession) WebSession.get();
	}

}
