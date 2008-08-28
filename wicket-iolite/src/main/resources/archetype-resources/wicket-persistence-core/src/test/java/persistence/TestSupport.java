package ${package}.persistence;

import ${package}.persistence.provider.MessageRepository;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * Base class for simplifying tests.
 * 
 * Inherits from AbstractJpaTests, which means each test is run in a transaction
 * that is rolled back after each test. No need to clean up after yourself.
 * 
 */
//@ContextConfiguration( locations = "AllInOneRepositoryContext.xml" )
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

	protected TestSupport() {
		super();
		setDependencyCheck(false); // To make inherited properties (like
									// DataSource) not required.
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:AllInOneRepositoryContext.xml", };
	}

}
