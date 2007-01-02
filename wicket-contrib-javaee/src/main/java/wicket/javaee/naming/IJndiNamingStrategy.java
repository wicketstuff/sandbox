package wicket.javaee.naming;

public interface IJndiNamingStrategy {

	String calculateName(String ejbName, Class ejbType);

}
