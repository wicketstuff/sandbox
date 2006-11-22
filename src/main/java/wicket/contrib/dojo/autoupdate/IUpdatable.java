package wicket.contrib.dojo.autoupdate;

/**
 * Interface for components which need to be compatible with  DojoAutoUpdatehandler. 
 * @author Ruud Booltink
 * @author Marco van de Haar
 * @deprecated will be remove in 2.0
 */
public interface IUpdatable
{

/**
 * This method is called on any component implementing Updatable before it is rerendered.
 * @return true if the update succeded
 */
boolean update();




/**
 * @return string representing CSS/HTML id
 */
String getHTMLID();

	
}
