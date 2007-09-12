package wicket.contrib.gmap;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;

import wicket.contrib.gmap.api.GLatLng;

/**
 */
public abstract class GeoCodingBehavior extends AjaxEventBehavior {
	private static final long serialVersionUID = 1L;

	// the TextField providing the requested address.
	private TextField requestField;

	private GMapHeaderContributor headerContrib;

	/**
	 * Construct.
	 * 
	 * @param event
	 */
	public GeoCodingBehavior(String event, TextField requestField, String key) {
		super(event);

		this.requestField = requestField;
		this.requestField.setOutputMarkupId(true);

		this.headerContrib = new GMapHeaderContributor(key);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		headerContrib.renderHead(response);
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();
		onGeoCode(request.getParameter("address"), GLatLng.parse(request
				.getParameter("point")));
	}

	public abstract void onGeoCode(String address, GLatLng point);

	@Override
	protected CharSequence generateCallbackScript(CharSequence partialCall) {
		return "Wicket.geocoder.getLatLng('" + getCallbackUrl() + "', '"
				+ requestField.getMarkupId() + "');" + "return false;";
	}
}
