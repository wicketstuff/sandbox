package wicket.contrib.mootools.components;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.string.JavascriptUtils;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXMooBindable;
import wicket.contrib.mootools.MFXOptions;
import wicket.contrib.mootools.events.MFXEvent;

public class MFXAjaxStatelessLink extends WebMarkupContainer implements MFXMooBindable {
	private static final long serialVersionUID = 1L;
	private String domId;
	private String url;
	private MFXOptions options;

	public MFXAjaxStatelessLink(final String id, final MarkupContainer component, final Class page) {
		this(id, component, page, new PageParameters());
	}

	public MFXAjaxStatelessLink(final String id, final MarkupContainer component, final Class page,
			final PageParameters pp) {
		super(id);
		component.setOutputMarkupId(true);
		this.domId = component.getMarkupId();
		this.url = RequestCycle.get().urlFor(page, pp).toString();

		setOutputMarkupId(true);
		add(new IncludeMooToolsStateless());
		options = new MFXOptions();
		options.setUpdate(domId);
		options.setMethod("get");
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("href", "#");
	}

	@Override
	protected boolean getStatelessHint() {
		return true;
	}

	@Override
	protected void onAfterRender() {
		super.onAfterRender();
		MFXEvent e = new MFXEvent(MFXEvent.EVENT.CLICK);
		e.addAction(mooFunction());
		e.setTarget(getMarkupId());
		getResponse().write(JavascriptUtils.SCRIPT_OPEN_TAG);
		getResponse().write(e.mooFunction());
		getResponse().write(JavascriptUtils.SCRIPT_CLOSE_TAG);
	}

	private String getResourceUrl(final Page page) {
		return RequestCycle.get().urlFor(page).toString();
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(final MFXOptions options) {
		this.options = options;
	}

	/**
	 * @return the options
	 */
	public MFXOptions getOptions() {
		return options;
	}

	public String mooFunction() {
		return "e = new Event(e).stop(); new Ajax('" + url + "'," + options.writeOptions() + ").request();";
	}
}
