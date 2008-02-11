package wicket.contrib.mootools.plugins;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXJavascriptUtils;

public class MFXTextileLivePreview extends Panel {
	private static final long serialVersionUID = 1L;
	private MFXNawEditor editor;
	private WebMarkupContainer preview;

	public MFXTextileLivePreview(final String id, final MFXNawEditor editor) {
		super(id);
		this.editor = editor;

		add(preview = new WebMarkupContainer("preview"));
		preview.setOutputMarkupId(true);
		add(new IncludeMooToolsStateless());
		add(HeaderContributor.forJavaScript(MFXJavascriptUtils.getMooAddonsJS()));

	}

	@Override
	protected void onRender(final MarkupStream markupStream) {
		super.onRender(markupStream);
		String output = String.format("<script>new MFXTextile().attach('%s','%s');</script>", editor.getMarkupId(),
				preview.getMarkupId());
		getResponse().write(output);
	}

}
