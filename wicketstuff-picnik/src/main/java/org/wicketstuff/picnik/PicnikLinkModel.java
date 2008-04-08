/**
 * 
 */
package org.wicketstuff.picnik;

import java.util.Map;

import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.RequestUtils;
import org.wicketstuff.picnik.api.PicnikCall;

/**
 * Model usable for a Link to make a call to Picnik.
 * <p>Created 30.03.2008 19:04:44</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PicnikLinkModel extends Model {

	public PicnikLinkModel(PicnikCall picnikCall) {
		super(picnikCall);
	}

	/**
	 * @see org.apache.wicket.model.Model#getObject()
	 */
	@Override
	public Object getObject() {
		PicnikCall picnikCall = (PicnikCall) super.getObject();
		StringBuffer buf = new StringBuffer();
		buf.append(PicnikCall.PICNIK_API_URL);
		buf.append('?');
		for (Map.Entry<String, String> entry : picnikCall.getParams().entrySet()) {
			buf.append(RequestUtils.encode(entry.getKey()));
			buf.append('=');
			buf.append(RequestUtils.encode(entry.getValue()));
			buf.append('&');
		}
		return buf;
	}
	
}
