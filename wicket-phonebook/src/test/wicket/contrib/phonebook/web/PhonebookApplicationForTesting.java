package wicket.contrib.phonebook.web;

import org.springframework.context.ApplicationContext;

/**
 * @author Kare Nuorteva
 */
public class PhonebookApplicationForTesting extends BasePhonebookApplication {
    public final MockContext context = new MockContext();

    @Override
    public ApplicationContext context() {
        return context;
    }
}
