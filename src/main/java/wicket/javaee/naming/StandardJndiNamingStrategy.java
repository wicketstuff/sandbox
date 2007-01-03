package wicket.javaee.naming;

public class StandardJndiNamingStrategy implements IJndiNamingStrategy {

	public String calculateName(String ejbName, Class ejbType) {
		return "java:comp/env/" + (ejbName == null ? ejbType.getName() : ejbName);
	}

}
