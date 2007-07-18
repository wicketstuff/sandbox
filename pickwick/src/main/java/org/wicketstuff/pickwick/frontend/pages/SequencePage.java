package org.wicketstuff.pickwick.frontend.pages;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.SequenceGridPanel;

import com.google.inject.Inject;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class SequencePage extends FrontendBasePage {
	private static final Logger log = LoggerFactory.getLogger(SequencePage.class);
	@Inject
	ImageUtils imageUtils;
	public SequencePage(PageParameters parameters) {
		String uri = parameters.getString("uri");
		if (uri == null) {
			uri = "";
		}
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Ignore
		}
		DojoLayoutContainer layout;
		addOnClient(layout = new DojoLayoutContainer("mainArea"));
		DojoSimpleContainer s1 = new DojoSimpleContainer("thumbnails");

		// Read sequence information
		File imageDir = imageUtils.toFile(uri);
		Sequence sequence = imageUtils.readSequence(imageDir);
		setModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));

		s1.add(new Label("date", new AbstractReadOnlyModel() {
			@Override
			public Object getObject() {
				DateFormat f = DateFormat.getDateInstance(DateFormat.FULL, getSession().getLocale());
				Date date = ((DisplaySequence)getModelObject()).getDate();
				log.debug("date: " + date);
				return f.format(date);
			}
		}));
		s1.add(new Label("title"));
		s1.add(new SequenceGridPanel("thumbnails", uri));
		layout.add(s1, Position.Client);
		DojoSimpleContainer s2 = new DojoSimpleContainer("treePanel");
		s2.add(new FolderTreePanel("treePanel"));
		s2.setWidth("20%");
		layout.add(s2, Position.Left);
	}
}
