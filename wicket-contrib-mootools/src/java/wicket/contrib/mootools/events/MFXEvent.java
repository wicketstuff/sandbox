package wicket.contrib.mootools.events;

import org.apache.wicket.Component;
import org.apache.wicket.Response;

import wicket.contrib.mootools.MFXJavascriptUtils;


public class MFXEvent extends MFXWindowLoad  {
	private static final long serialVersionUID = 1L;
	private String action;
	
	public final static String MOUSEOVER = "mouseover";
	public final static String MOUSEOUT = "mouseout";
	public final static String CLICK = "click";
	public final static String FOCUS = "focus";
	public final static String KEYDOWN = "keydown";
	public final static String KEYPRESS = "keypress";
	public final static String KEYUP = "keyup";
	public final static String MOUSEUP = "mouseup";
	public final static String MOUSEDOWN = "mousedown";
	public final static String MOUSEMOVE = "mousemove";
	public final static String BLUR = "blur";
	
	
	/**
	 * Constructor
	 * @param action action to look for
	 */
	public MFXEvent(String action) {
		this.action = action;
	}
	
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXWindowLoad#onRendered(org.apache.wicket.Component)
	 */
	public void onRendered(Component arg0) {
		super.onRendered(arg0);
		Response resp = arg0.getResponse();
		resp.write(MFXJavascriptUtils.DOM_READY_OPEN());
		resp.write(mooFunction());
		resp.write(MFXJavascriptUtils.DOM_READY_CLOSE());
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXWindowLoad#mooFunction()
	 */
	public String mooFunction() {
		StringBuffer buf = new StringBuffer();
		buf.append("$('"+getTarget()+"').addEvent(");
		buf.append("'"+action+"', function(e) {");
		buf.append(writeActions());
		buf.append(writeStyles());
		buf.append("});");
		return buf.toString();
	}
	
}
