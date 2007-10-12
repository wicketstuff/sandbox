package wicket.contrib.mootools;

import org.apache.wicket.util.string.JavascriptUtils;

/**
 * Convenience class for writing Moocentric Javascript tags.
 * 
 * @author victori
 * 
 */
public class MFXJavascriptUtils {
	/**
	 * Write domReady function open tag
	 * 
	 * @return
	 */
	public final static String DOM_READY_OPEN() {
		String str;
		str = JavascriptUtils.SCRIPT_OPEN_TAG;
		str += "window.addEvent(\"load\", function() {";
		return str;
	}

	/**
	 * Write domReady function close tag
	 * 
	 * @return
	 */
	public final static String DOM_READY_CLOSE() {
		return "});" + JavascriptUtils.SCRIPT_CLOSE_TAG;
	}
}
