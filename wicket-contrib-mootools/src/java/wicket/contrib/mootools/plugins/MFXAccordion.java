package wicket.contrib.mootools.plugins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.events.MFXWindowLoad;

public class MFXAccordion extends Panel implements Serializable {
	private List tabs = new ArrayList();
	private String PANEL_ID = "content";
	private CompressedResourceReference AC_CSS = new CompressedResourceReference(
			MFXAccordion.class, "MFXAccordion.css");

	public MFXAccordion(final String id, final List tabs) {
		super(id);
		add(HeaderContributor.forCss(AC_CSS));
		add(new IncludeMooToolsStateless());
		RepeatingView rp = new RepeatingView("repeater");

		for (int i = 0; i < tabs.size(); i++) {
			ITab tab = (ITab) tabs.get(i);
			rp.add(new MFXTab(String.valueOf(i), tab));
		}
		add(rp);

		add(new MFXWindowLoad().addAction(getJavaScript()));
	}

	// var accordion = new Accordion('h3.atStart', 'div.atStart', {
	// opacity: false,
	// onActive: function(toggler, element){
	// toggler.setStyle('color', '#ff3300');
	// },
	//	 
	// onBackground: function(toggler, element){
	// toggler.setStyle('color', '#222');
	// }
	// }, $('accordion'));

	protected String getJavaScript() {
		StringBuffer str = new StringBuffer();

		str
				.append("var accordion = new Accordion('h3.atStart', 'div.atStart', {");
		str.append("opacity: false");
		str.append("}, $('" + getMarkupId() + "'));");

		return str.toString();
	}

	protected class MFXTab extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;

		public MFXTab(final String id, final ITab tab) {
			super(id);
			Label title;
			add(title = new Label("title", tab.getTitle()));
			title.add(new SimpleAttributeModifier("class", "toggler atStart"));
			Panel panel;
			add(panel = tab.getPanel(PANEL_ID));
			panel.add(new SimpleAttributeModifier("class", "element atStart"));
		}
	}

	private static final long serialVersionUID = 1L;
}
