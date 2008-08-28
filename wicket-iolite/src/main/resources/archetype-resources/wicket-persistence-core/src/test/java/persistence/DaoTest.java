package ${package}.persistence;

import org.apache.log4j.Logger;
import ${package}.persistence.domain.Message;

public class DaoTest extends TestSupport {

	private Logger log = Logger.getLogger(DaoTest.class);

	public void testIsSomethingWorking() {
		Message message = new Message("Hello world");
		messageRepository.add(message);

		assertEquals(1, messageRepository.getAll().size());
	}

}
