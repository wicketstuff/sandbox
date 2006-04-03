package contrib.wicket.cms.example.page;

import java.io.Serializable;

import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.EmailAddressPatternValidator;
import wicket.markup.html.form.validation.StringValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.example.model.Member;
import contrib.wicket.cms.example.service.MembersService;
import contrib.wicket.cms.example.session.SecuritySession;
import contrib.wicket.cms.util.WicketUtil;
import contrib.wicket.cms.validator.EqualsValidator;

public class Registration extends Template {

	@SpringBean
	private MembersService memberService;

	public Registration() {
		RegistrationForm form = new RegistrationForm("form");

		add(form);
	}

	private class RegistrationForm extends Form {

		public RegistrationForm(final String id) {
			super(id, new CompoundPropertyModel(new MemberForm()));

			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
			add(feedbackPanel);

			TextField emailAddress = new TextField("emailAddress");
			emailAddress.setRequired(true);
			emailAddress.add(EmailAddressPatternValidator.getInstance());
			WicketUtil.addErrorableLabel(this, "emailAddressLabel",
					emailAddress);

			TextField confirmEmailAddress = new TextField("confirmEmailAddress");
			confirmEmailAddress.setRequired(true);
			confirmEmailAddress.add(EmailAddressPatternValidator.getInstance());
			confirmEmailAddress.add(new EqualsValidator(emailAddress));
			WicketUtil.addErrorableLabel(this, "confirmEmailAddressLabel",
					confirmEmailAddress);

			PasswordTextField password = new PasswordTextField("password");
			password.setRequired(true);
			password.add(StringValidator.lengthBetween(6, 32));
			WicketUtil.addErrorableLabel(this, "passwordLabel", password);

			PasswordTextField confirmPassword = new PasswordTextField(
					"confirmPassword");
			confirmPassword.setRequired(true);
			confirmPassword.add(StringValidator.lengthBetween(6, 32));
			confirmPassword.add(new EqualsValidator(password));
			WicketUtil.addErrorableLabel(this, "confirmPasswordLabel",
					confirmPassword);
		}

		public final void onSubmit() {
			MemberForm form = (MemberForm) getModelObject();

			boolean isError = false;

			if (memberService.findByEmailAddress(form.getEmailAddress()) != null) {
				get("emailAddress").error(
						getString("registration.user.exists", null));
				isError = true;
			}

			// Create database objects and copy data to them
			if (!isError) {
				Member member = new Member();

				WicketUtil.copyProperties(form, member);

				// Save to database
				memberService.register(member);

				// Add security objects to the session
				SecuritySession.get().setMemberId(member.getId());

				if (!continueToOriginalDestination()) {
					setResponsePage(Home.class);
				}

			}

		}
	}

	public class MemberForm implements Serializable {

		String emailAddress;

		String confirmEmailAddress;

		String password;

		String confirmPassword;

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getConfirmEmailAddress() {
			return confirmEmailAddress;
		}

		public void setConfirmEmailAddress(String confirmEmailAddress) {
			this.confirmEmailAddress = confirmEmailAddress;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

	}

}
