package wicket.contrib.mootools.plugins;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXJavascriptUtils;

public class MFXNawEditor extends TextArea {
	private static final long serialVersionUID = 1L;

	public MFXNawEditor(final String id) {
		super(id);
		bootStrap();
	}

	public MFXNawEditor(final String id, final IModel model) {
		super(id, model);
		bootStrap();
	}

	protected void bootStrap() {
		setOutputMarkupId(true);
		add(new IncludeMooToolsStateless());
		add(HeaderContributor.forCss(new ResourceReference(MFXNawEditor.class, "mfxaddons.css")));
		add(HeaderContributor.forJavaScript(MFXJavascriptUtils.getMooAddonsJS()));
	}

	@Override
	protected void onRender(final MarkupStream markupStream) {
		getResponse().write("<ul class='mfxtoolbar' id='" + getMFXNawToolbarId() + "'></ul>");
		super.onRender(markupStream);
		getResponse().write(MFXJavascriptUtils.DOM_READY_OPEN());
		getResponse().write(getJavascriptFunction());
		getResponse().write(MFXJavascriptUtils.DOM_READY_CLOSE());
	}

	protected String getMFXNawId() {
		return "mfxnaw" + getMarkupId();
	}

	protected String getMFXNawToolbarId() {
		return "mfxtoolbar" + getMarkupId();
	}

	protected String getJavascriptFunction() {
		StringBuffer str = new StringBuffer();

		str.append(String.format("%s = new nawte('%s','%s');", getMFXNawId(), getMarkupId(), getMFXNawToolbarId()));
		str.append(String.format("%s.addFunction('Bold', textile_bold , {'id':'bold_button'});", getMFXNawId()));
		str.append(String.format("%s.addFunction('Italic', textile_italic , {'id':'italic_button'});", getMFXNawId()));
		str.append(String.format("%s.addFunction('Link', textile_link , {'id':'link_button'});", getMFXNawId()));
		str.append(String.format("%s.addFunction('Image', textile_image , {'id':'image_button'});", getMFXNawId()));
		str.append(String.format("%s.addFunction('Trademark', textile_trademark , {'id':'trademark_button'});",
				getMFXNawId()));
		str.append(String.format("%s.addFunction('Registered', textile_registered , {'id':'registered_button'});",
				getMFXNawId()));
		str.append(String.format("%s.addFunction('Copyright', textile_copyright , {'id':'copyright_button'});",
				getMFXNawId()));

		return str.toString();
	}
	/*
	 * window.addEvent('domready', function() { nawte = new nawte('thetext',
	 * 'toolbar');
	 * 
	 * nawte.addFunction('Bold', function() { selection = this.getSelection();
	 * this.replaceSelection('[b]' + selection + '[/b]'); }, {'id':
	 * 'bold_button'});
	 * 
	 * nawte.addFunction('Italic', function() { selection = this.getSelection();
	 * this.replaceSelection('[i]' + selection + '[/i]'); }, {'id':
	 * 'italic_button'});
	 * 
	 * nawte.addFunction('Insert Link', function() { var selection =
	 * this.getSelection(); var response = prompt('Enter Link URL','');
	 * if(response == null) return; this.replaceSelection('[url=' + (response == '' ?
	 * 'http://link_url/' : response) + ']' + (selection == '' ? 'Link Text' :
	 * selection) + '[/url]'); }, {'id': 'link_button'}); });
	 */

}
