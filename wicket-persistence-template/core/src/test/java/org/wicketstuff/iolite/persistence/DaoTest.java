package org.wicketstuff.iolite.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.wicketstuff.iolite.persistence.domain.Message;

public class DaoTest extends TestSupport {

	private Logger log = Logger.getLogger(DaoTest.class);

	public void testIsSomethingWorking() {
		Message message = new Message("Hello world");
		messageRepository.add(message);
		
		assertEquals(1, messageRepository.getAll().size());
	}
	public void testIsSomethingWorkingGetAllAsList() {
		Message message = new Message("Hello world");
		messageRepository.add(message);

		assertEquals(1, messageRepository.getAllAsList().size());
	}
	public void testGenericMethodGetList(){
		Message message=new Message();
		message.setMessage("hello");
		generalRepository.add(message);
		
		List<Message> messageList=generalRepository.getAllAsList(Message.class);
		assertEquals(1, messageList.size());
	}

}