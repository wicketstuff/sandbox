package org.wicketstuf.iolite.persistence;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;
import org.wicketstuff.iolite.persistence.provider.MessageRepository;

/**
 * Base class for simplifying tests.
 * 
 * Inherits from AbstractJpaTests, which means each test is run in a transaction
 * that is rolled back after each test. No need to clean up after yourself.
 * 
 */
@ContextConfiguration( locations = "AllInOneRepositoryContext.xml" )
public abstract class TestSupport extends AbstractJpaTests {

	protected MessageRepository messageRepository;

	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
	}

	@Required
	public void setDbProvider(MessageRepository dbProvider) {
		this.messageRepository = dbProvider;
	}


	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:AllInOneRepositoryContext.xml" };
	}

}
