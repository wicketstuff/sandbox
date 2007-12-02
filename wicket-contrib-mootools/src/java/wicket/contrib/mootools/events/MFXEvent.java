package wicket.contrib.mootools.events;

import org.apache.wicket.Component;
import org.apache.wicket.Response;

import wicket.contrib.mootools.MFXJavascriptUtils;

public class MFXEvent extends MFXWindowLoad {
	private static final long serialVersionUID = 1L;
	private String action;

	public enum EVENT {
		MOUSEOVER, MOUSEOUT, CLICK, FOCUS, KEYDOWN, KEYPRESS, KEYUP, MOUSEUP, MOUSEDOWN, MOUSEMOVE, BLUR
	};

	/**
	 * Constructor
	 * 
	 * @param action
	 *            action to look for
	 */
	public MFXEvent(final EVENT event) {
		this.action = event.toString().toLowerCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.mootools.events.MFXWindowLoad#onRendered(org.apache.wicket.Component)
	 */
	@Override
	public void onRendered(final Component arg0) {
		super.onRendered(arg0);
		Response resp = arg0.getResponse();
		resp.write(MFXJavascriptUtils.DOM_READY_OPEN());
		resp.write(mooFunction());
		resp.write(MFXJavascriptUtils.DOM_READY_CLOSE());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.mootools.events.MFXWindowLoad#mooFunction()
	 */
	@Override
	public String mooFunction() {
		StringBuffer buf = new StringBuffer();
		buf.append("$('" + getTarget() + "').addEvent(");
		buf.append("'" + action + "', function(e) {");
		buf.append(writeActions());
		buf.append(writeStyles());
		buf.append("});");
		return buf.toString();
	}

}
