package contrib.wicket.cms.example.service;

import org.springframework.transaction.annotation.Transactional;

import contrib.wicket.cms.example.model.Member;
import contrib.wicket.cms.service.GenericService;

@Transactional
public interface MembersService extends GenericService {
	
	public Member getMember(Integer memberId);
	
	public Member findByEmailAddress(String emailAddress);

	public Member findByEmailAddressAndPassword(String emailAddress,
			String password);
		
	public void register(Member member);
	
	public void changePassword(Member member);
}