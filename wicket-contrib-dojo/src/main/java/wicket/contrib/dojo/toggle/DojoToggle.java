package wicket.contrib.dojo.toggle;

/**
 * Define a Dojo toggle
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public abstract class DojoToggle
{
	private int duration;

	public DojoToggle(){
		this.duration = 200;
	}
	
	/**
	 * Construct a toggler
	 * @param duration effect duration in ms
	 */
	public DojoToggle(int duration){
		this.duration = duration;
	}
	
	/**
	 * return the effet
	 * @return the effet
	 */
	public abstract String getToggle();
	
	/**
	 * return the duration of the effet
	 * @return the effet duration
	 */
	public int getDuration(){
		return this.duration;
	}
	
}
