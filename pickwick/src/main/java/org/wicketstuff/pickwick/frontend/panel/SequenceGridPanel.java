package org.wicketstuff.pickwick.frontend.panel;

import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.ImageProperties;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.provider.ImageProvider;

/**
 * A panel displaying a table of all thumbnail given by the uri
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 * @author Vincent Demay
 */
public class SequenceGridPanel extends Panel {

	public SequenceGridPanel(String id, String uri) {
		super(id);

		final Settings settings = PickWickApplication.get().getSettings();
		final ImageProvider imageProvider = new ImageProvider(settings);

		imageProvider.setImagePath("/" + uri);
		add(newGridView("rows", imageProvider));
	}

	protected GridView newGridView(String id, ImageProvider imageProvider) {
		return new SequenceGridView(id, imageProvider);
	}

	public class SequenceGridView extends GridView {
		public SequenceGridView(String id, ImageProvider dataProvider) {
			super(id, dataProvider);
		}

		@Override
		protected void populateEmptyItem(Item item) {
			WebMarkupContainer link;
			item.add(link = new WebMarkupContainer("link"));
			link.setVisible(false);
		}

		@Override
		protected void populateItem(Item item) {
			try {
				final Settings settings = PickWickApplication.get().getSettings();
				ImageProperties imageProperties = (ImageProperties) item.getModelObject();
				if (!imageProperties.getFile().getCanonicalPath().startsWith(
						settings.getImageDirectoryRoot().getCanonicalPath()))
					throw new RuntimeException("Requested image directory not within the root image directory");
				WebMarkupContainer link;
				String imagePath = ((ImageProvider)getDataProvider()).getImageRelativePath(imageProperties.getFile());
				PageParameters params = new PageParameters();
				params.add("uri", imagePath);
				item.add(link = new WebMarkupContainer("link"));
				link.add(new AttributeModifier("href", true, new Model(getRequest()
						.getRelativePathPrefixToContextRoot()
						+ PickWickApplication.IMAGE_PAGE_PATH + "/" + imagePath)));
				WebComponent image;
				link.add(image = new WebComponent("thumbnail"));
				image.add(new AttributeModifier("src", true, new Model(getRequest()
						.getRelativePathPrefixToContextRoot()
						+ PickWickApplication.THUMBNAIL_IMAGE_PATH + "/" + imagePath)));
				link.add(new Label("thumbnailLabel", imageProperties.getTitle()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public int getColumns() {
			return 5;
		}
	}
}
