package wicket.contrib.dojo.autoupdate;

/**
 * Interface for components which need to be compatible with  DojoAutoUpdatehandler. 
 * @author Ruud Booltink
 * @author Marco van de Haar
 */
public interface IUpdatable
{

/**
 * This method is called on any component implementing Updatable before it is rerendered.
 */
void update();




/**
 * @return string representing CSS/HTML id
 */
String getHTMLID();

	
}
