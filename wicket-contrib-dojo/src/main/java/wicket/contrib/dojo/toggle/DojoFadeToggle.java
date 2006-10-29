package wicket.contrib.dojo.toggle;

/**
 * Return a Fade toogler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoFadeToggle extends DojoToggle
{
	public DojoFadeToggle(int duration){
		super(duration);
	}
	
	public DojoFadeToggle(){
		super();
	}

	public String getToggle(){
		return "fade";
	}

}
