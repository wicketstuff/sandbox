package wicket.contrib.mootools.plugins;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXJavascriptUtils;

public abstract class MFXDialogBase extends Panel {
	private static final long serialVersionUID = 1L;
	private PageCreator pageCreator;
	private int width;
	private String title;
	private int offsetTop;
	private String body;
	private String domId;

	private ResourceReference PLAINCSS = new CompressedResourceReference(MFXDialog.class, "mfxaddons.css");

	public MFXDialogBase(final String id) {
		super(id);

		this.width = 300;
		this.offsetTop = 300;
		this.title = "Modal Window";

		add(new IncludeMooToolsStateless());
		add(HeaderContributor.forJavaScript(MFXJavascriptUtils.getMooAddons()));
		add(HeaderContributor.forCss(PLAINCSS));
	}

	public interface PageCreator extends IClusterable {
		public Page createPage();
	}

	protected Boolean getBlackScreen() {
		return false;
	}

	protected Boolean getBounceDialog() {
		return true;
	}

	protected Page createPage() {

		if (pageCreator == null) {
			return null;
		}

		try {
			Page page = pageCreator.createPage();
			return page;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void renderHead(final HtmlHeaderContainer container) {
		container.getResponse().write("<script type='text/javascript'>var dialog" + getDomId() + " = null;</script>");
		super.renderHead(container);
	}

	private String getDialogDomId() {
		return "dialog" + getDomId();
	}

	protected String genericCloseWindowJavaScript(final String dialogId) {
		String dialog = "dialog" + dialogId;
		return dialog + ".closeDialog();";
	}

	protected String genericOpenJavaScript() {
		StringBuffer str = new StringBuffer();

		Page page = createPage();

		str.append(getDialogDomId() + " = new MFXDialog($('" + getDomId() + "'));");

		if (getBlackScreen()) {
			str.append(getDialogDomId() + ".makeScreenDark();");
		}
		str.append(getDialogDomId() + ".showDialog();");

		return str.toString();
	}

	public PageCreator getPageCreator() {
		return pageCreator;
	}

	public void setPageCreator(final PageCreator pageCreator) {
		this.pageCreator = pageCreator;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public int getOffsetTop() {
		return offsetTop;
	}

	public void setOffsetTop(final int offsetTop) {
		this.offsetTop = offsetTop;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	protected void setDomId(final String domId) {
		this.domId = domId;
	}

	protected String getDomId() {
		return domId;
	}

}
