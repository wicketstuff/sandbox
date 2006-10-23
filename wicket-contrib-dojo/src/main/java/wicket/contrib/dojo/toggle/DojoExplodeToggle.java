package wicket.contrib.dojo.toggle;

/**
 * Return a Fade toogler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoExplodeToggle extends DojoToggle
{
	public DojoExplodeToggle(int duration){
		super(duration);
	}
	
	public String getToggle(){
		return "explode";
	}

}
