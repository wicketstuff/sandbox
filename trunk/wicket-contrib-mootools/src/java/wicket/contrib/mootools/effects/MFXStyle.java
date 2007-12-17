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
		this(style,transition,String.valueOf(start),String.valueOf(end));
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,String start,String end) {
		this(style,null,start,end);
	}
	
	/**
	 * Constructor
	 * @param style element type to modify
	 * @param start start value
	 * @param end end value
	 */
	public MFXStyle(String style,int start,int end) {
		this(style,String.valueOf(start),String.valueOf(end));
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBase#mooFunction()
	 */
	public String mooFunction() {
		StringBuffer buf = new StringBuffer();
		buf.append("new Fx.Style('"+getTarget()+"','" +
				getStyle()+"',"+writeOptions()+");");
		return buf.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return mooFunction();
	}
}
