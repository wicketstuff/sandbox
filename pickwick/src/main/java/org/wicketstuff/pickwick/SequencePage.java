package org.wicketstuff.pickwick;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.model.Model;

public class SequencePage extends WebPage {
	public SequencePage() {
		final Settings settings = TestApplication.get().getSettings();
		final ImageProvider imageProvider = new ImageProvider(settings);
		// FIXME handle includes and excludes
		imageProvider.setPattern("*.JPG");
		// FIXME get request URI
		imageProvider.setImagePath("");
		GridView grid;
		add(grid = new GridView("rows", imageProvider) {
			@Override
			protected void populateEmptyItem(Item item) {
				WebMarkupContainer link;
				item.add(link = new WebMarkupContainer("link"));
				link.setVisible(false);
			}

			@Override
			protected void populateItem(Item item) {
				try {
					ImageProperties imageProperties = (ImageProperties) item.getModelObject();
					if (!imageProperties.file.getCanonicalPath().startsWith(settings.getImageDirectoryRoot().getCanonicalPath()))
						throw new RuntimeException("Requested image directory not within the root image directory");
					WebMarkupContainer link;
					String imagePath = imageProvider.getImagePath(imageProperties.file);
					PageParameters params = new PageParameters();
					params.add("uri", imagePath);
					item.add(link = new WebMarkupContainer("link"));
					// FIXME compute relative path instead of using absolute
					// path
					String contextPath = getApplication().getApplicationSettings().getContextPath();
					link.add(new AttributeModifier("href", true, new Model(contextPath + "/"
							+ TestApplication.IMAGE_PAGE_PATH + "/" + imagePath)));
					WebComponent image;
					link.add(image = new WebComponent("thumbnail"));
					image.add(new AttributeModifier("src", true, new Model(contextPath + "/"
							+ TestApplication.THUMBNAIL_IMAGE_PATH + "/" + imagePath)));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		grid.setColumns(5);
	}
}
