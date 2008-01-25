package wicket.contrib.activewidgets.system;

/**
 * Represents a HTML size unit.
 *
 * @author Oleg Taranenko
 */
public enum SizeUnit {
	/**
	 * CSS EM unit
	 */
	em,
	/**
	 * CSS PX unit
	 */
	px,
	
	/**
	 * CSS EX unit
	 */
	ex,
	
	/**
	 * CSS '%' unit
	 */
	procent, 
	
	/**
	 * blank unit
	 */
	blank;

	@Override
	public final String toString() {
		if (this == procent) {
			return "%";
		} else if (this == blank) {
			return "";
		} else {
			return super.toString();
		}
	}
		
	
	
};
