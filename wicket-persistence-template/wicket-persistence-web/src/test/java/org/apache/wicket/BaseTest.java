package org.apache.wicket;

import org.apache.wicket.persistence.provider.MessageRepository;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.jpa.AbstractJpaTests;

public abstract class BaseTest extends AbstractJpaTests {

	protected WicketTester wicketTester;

	protected MessageRepository messageRepository;

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:AllInOneRepositoryContext.xml", };
	}

	protected BaseTest() {
		super();

		setDependencyCheck(false);

		// To make inherited properties (like
		// DataSource) not required.
	}

	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		readyStuff();

	}

	@Override
	protected boolean shouldUseShadowLoader() {
		// TODO Auto-generated method stub
		return false;
	}

	private void readyStuff() {
		ApplicationContext appcxt = new ClassPathXmlApplicationContext(
				"AllInOneRepositoryContext.xml");
		messageRepository = (MessageRepository) appcxt.getBean("messageRepository");
		// 2. setup mock injection environment
		AnnotApplicationContextMock appctx = new AnnotApplicationContextMock();
		appctx.putBean("messageRepository", messageRepository);

		WicketApplication wicketPersistanceApplication = new WicketApplication();
		wicketPersistanceApplication
				.setSpringComponentInjector(new SpringComponentInjector(
						wicketPersistanceApplication, appctx));
		wicketTester = new WicketTester(wicketPersistanceApplication);

	}

	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

}
