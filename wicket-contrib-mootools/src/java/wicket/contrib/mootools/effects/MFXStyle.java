package wicket.contrib.mootools.effects;


/**
 * Class to write Mootool styles
 * @author victori
 *
 */
public class MFXStyle extends MFXBase {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public MFXStyle() {
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 */
	public MFXStyle(String style) {
		setStyle(style);
	}
	
	/**
	 * Constructor 
	 * @param style element type to modify
	 * @param transition transition to use {@link MFXTransition}
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,String transition,String start,String end) {
		setStyle(style);
		setTransition(transition);
		setStartValue(start);
		setEndValue(end);
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 * @param transition transition to use {@link MFXTransition}
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,String transition,int start,int end) {
		setStyle(style);
		setTransition(transition);
		setStartValue(String.valueOf(start));
		setEndValue(String.valueOf(end));
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,String start,String end) {
		setStyle(style);
		setStartValue(start);
		setEndValue(end);
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,int start,int end) {
		setStyle(style);
		setStartValue(String.valueOf(start));
		setEndValue(String.valueOf(end));
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBase#mooFunction()
	 */
	public String mooFunction() {
		StringBuffer buf = new StringBuffer();
		buf.append("new Fx.Style('"+getTarget()+"','" +
				getStyle()+"',{");
		buf.append(writeOptions());
		buf.append("});");
		return buf.toString();
	}
	


}
