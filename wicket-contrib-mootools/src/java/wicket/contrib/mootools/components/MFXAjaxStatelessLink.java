package wicket.contrib.mootools.components;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXMooBindable;
import wicket.contrib.mootools.MFXOptions;
import wicket.contrib.mootools.events.MFXEvent;

public class MFXAjaxStatelessLink extends WebMarkupContainer implements MFXMooBindable {
	private static final long serialVersionUID = 1L;
	private String domId;
	private String url;
	private MFXOptions options;
	
	
	public MFXAjaxStatelessLink(String id, String component , Page page) {
		super(id);
		this.url = getResourceUrl(page);
		this.domId = component;
		setupLink();
	}
	
	public MFXAjaxStatelessLink(String id, Component component, Page page) {
		super(id);
		this.url = getResourceUrl(page);
		this.domId = component.getMarkupId();
		setupLink();
	}
	
	public MFXAjaxStatelessLink(String id, String component, String url) {
		super(id);
		this.domId = component;
		this.url = url;
		setupLink();
	}
	
	private void setupLink() {
		setOutputMarkupId(true);
		add(new IncludeMooToolsStateless());
		options = new MFXOptions();
		options.setUpdate(domId);
		options.setMethod("get");
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("href", "#");
	}

	@Override
	protected void onAfterRender() {
		super.onAfterRender();
		MFXEvent e = new MFXEvent(MFXEvent.CLICK);
		e.addAction(mooFunction());
		e.setTarget(getMarkupId());
		getResponse().write("<script>");
		getResponse().write(e.mooFunction());
		getResponse().write("</script>");
	}
	
	private String getResourceUrl(Page page) {
		return RequestCycle.get().urlFor(page).toString();
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(MFXOptions options) {
		this.options = options;
	}

	/**
	 * @return the options
	 */
	public MFXOptions getOptions() {
		return options;
	}

	public String mooFunction() {
		return "new Ajax('"+url+"',"+options.writeOptions()+").request();";
	}
}
