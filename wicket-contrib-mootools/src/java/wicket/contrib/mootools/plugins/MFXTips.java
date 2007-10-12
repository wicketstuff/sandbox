package wicket.contrib.mootools.plugins;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;
import wicket.contrib.mootools.MFXJavascriptUtils;
import wicket.contrib.mootools.MFXOptions;

/**
 * Class for adding Display tooltips
 * <p>
 * This class is stateless
 * </p>
 * 
 * @author victori
 * 
 */
public class MFXTips extends AbstractRequireMooStatelessBehavior {
	private static final long serialVersionUID = 1L;
	private static final ResourceReference MOOBUBBLE = new ResourceReference(MFXTips.class, "bubble.png");
	private String tip;
	private MFXOptions mfxOptions = new MFXOptions();

	/**
	 * Constructor
	 * 
	 * @param tip
	 *            Tip to display
	 */
	public MFXTips(final String tip) {
		this.tip = tip;
		mfxOptions.setWait("false");
	}

	private String renderMooCSS() {
		String str = "<style>";
		str += ".tool-tip { color: #fff; width: 139px; z-index: 13000; }";
		str += ".tool-title { font-weight: both; font-size: 11px; margin: 0; color: #9FD4FF; padding: 8px 8px 4px;";
		str += "background: url(" + getResource(MOOBUBBLE) + ") top left; }";
		str += ".tool-text { font-size: 11px; padding: 4px 8px 8px; background: url(" + getResource(MOOBUBBLE)
				+ ") bottom right; }";
		str += "</style>";
		return str;
	}

	private String getResource(final ResourceReference ref) {
		return (String) RequestCycle.get().urlFor(ref);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		response.renderString(renderMooCSS());

		response.renderString(MFXJavascriptUtils.DOM_READY_OPEN());

		response.renderString("var Tips1 = new Tips($$('.mfxtip'), {" + "initialize:function(){ "
				+ "this.fx = new Fx.Style(this.toolTip, 'opacity', " + mfxOptions.writeOptions() + ");"
				+ "},onShow: function(toolTip) { this.fx.start(1); },onHide: function(toolTip) {"
				+ "this.fx.start(0); }});");

		response.renderString(MFXJavascriptUtils.DOM_READY_CLOSE());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#onComponentTag(org.apache.wicket.Component,
	 *      org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	public void onComponentTag(final Component arg0, final ComponentTag arg1) {
		super.onComponentTag(arg0, arg1);

		String currentClass = "";
		if (arg1.getString("class") != null) {
			currentClass = arg1.getString("class").toString();
		}
		arg1.put("class", currentClass + " mfxtip");
		arg1.put("title", tip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#mooFunction()
	 */
	@Override
	public String mooFunction() {
		return null;
	}

	/**
	 * Append a MFXOptions class to set any Mootool option
	 * 
	 * @see MFXOptions
	 * @param mfxOptions
	 * @return
	 */
	public MFXTips setMfxOptions(final MFXOptions mfxOptions) {
		this.mfxOptions = mfxOptions;
		return this;
	}

	/**
	 * Get {@link MFXOption} reference
	 * 
	 * @return
	 */
	public MFXOptions getMfxOptions() {
		return mfxOptions;
	}

}
