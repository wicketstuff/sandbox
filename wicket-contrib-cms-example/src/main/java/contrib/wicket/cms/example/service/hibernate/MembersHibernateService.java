package contrib.wicket.cms.example.service.hibernate;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import contrib.wicket.cms.example.model.Member;
import contrib.wicket.cms.example.service.MembersService;
import contrib.wicket.cms.service.hibernate.GenericHibernateService;

@Transactional
public class MembersHibernateService extends GenericHibernateService implements
		MembersService {

	public Member getMember(Integer memberId) {
		return (Member) session().load(Member.class, memberId);
	}

	public Member findByEmailAddress(String emailAddress) {
		return (Member) session().createCriteria(Member.class).add(
				Restrictions.eq("emailAddress", emailAddress)).uniqueResult();
	}

	public Member findByEmailAddressAndPassword(String emailAddress,
			String password) {
		return (Member) session().createCriteria(Member.class).add(
				Restrictions.eq("emailAddress", emailAddress)).add(
				Restrictions.eq("password", password)).uniqueResult();
	}

	public Member getAdministrator() {
		return (Member) session().load(Member.class, 1);
	}

	public void register(Member member) {
		save(member);
	}

	public void changePassword(Member member) {
		save(member);
	}

}
