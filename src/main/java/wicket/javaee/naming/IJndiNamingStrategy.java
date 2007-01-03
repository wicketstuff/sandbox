package wicket.javaee.naming;

import java.io.Serializable;

public interface IJndiNamingStrategy extends Serializable{

	String calculateName(String ejbName, Class ejbType);

}
