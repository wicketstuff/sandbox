package wicket.contrib.dojo.toggle;

/**
 * Return a Fade toogler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoWipeToggle extends DojoToggle
{
	public DojoWipeToggle(int duration){
		super(duration);
	}
	
	public DojoWipeToggle(){
		super();
	}
	
	public String getToggle(){
		return "wipe";
	}

}
