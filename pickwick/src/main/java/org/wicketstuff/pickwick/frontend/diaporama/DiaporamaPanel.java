package org.wicketstuff.pickwick.frontend.diaporama;

import java.io.File;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Image;
import org.wicketstuff.yui.YuiHeaderContributor;

import com.google.inject.Inject;

public class DiaporamaPanel extends Panel {

	@Inject
	private Settings settings;

	@Inject
	ImageUtils imageUtils;
	
	List<Image> imageList;
	
	public DiaporamaPanel(String id, IModel model) {
		super(id, model);
		
		imageList = imageUtils.getImageList(new File(settings.getImageDirectoryRoot(), getDefaultModelObjectAsString()));
	
		add(new DiaporamaBehavior());

		add(YuiHeaderContributor.forModule("container"));
		add(YuiHeaderContributor.forModule("utilities"));
		add(YuiHeaderContributor.forModule("dragdrop"));
		
		add(HeaderContributor.forJavaScript(new ResourceReference(DiaporamaPanel.class, "js/carousel.js")));
		add(HeaderContributor.forJavaScript(new ResourceReference(DiaporamaPanel.class, "js/carouselHelper.js")));
		
		add(HeaderContributor.forCss(new ResourceReference(DiaporamaPanel.class, "css/carousel.css")));
		add(HeaderContributor.forCss(new ResourceReference(DiaporamaPanel.class, "css/carouselHelper.css")));
		
		add(new WebMarkupContainer("previous"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("src", urlFor(new ResourceReference(DiaporamaPanel.class,"images/previous.png")));
			}
		});
		add(new WebMarkupContainer("next"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("src", urlFor(new ResourceReference(DiaporamaPanel.class,"images/next.png")));
			}
		});
		add(new WebMarkupContainer("play"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("src", urlFor(new ResourceReference(DiaporamaPanel.class,"images/play.png")));
			}
		});
		add(new WebMarkupContainer("pause"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("src", urlFor(new ResourceReference(DiaporamaPanel.class,"images/pause.png")));
			}
		});
	}
	
	public class DiaporamaBehavior extends AbstractBehavior implements IHeaderContributor{
		@Override
		public void renderHead(IHeaderResponse response) {
			super.renderHead(response);
			
			StringBuffer buffer = new StringBuffer("var imageList = ['',\n");
			for(Image image : imageList){
				String imagePath = imageUtils.getRelativePath(image.getFile());
				buffer.append("\"" + getRequest()
						.getRelativePathPrefixToContextRoot()
						+ PickwickApplication.THUMBNAIL_IMAGE_PATH + "/" +  imagePath + "\",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("];\n");
			
			buffer.append("var urlList = ['',\n");
			for(Image image : imageList){
				String imagePath = imageUtils.getRelativePath(image.getFile());
				buffer.append("\"" + getRequest()
						.getRelativePathPrefixToContextRoot()
						+ PickwickApplication.SCALED_IMAGE_PATH + "/" + imagePath + "\",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("];\n");
			
			buffer.append("var diapoSize = " + imageList.size() + ";");
			
			response.renderJavascript(buffer.toString(), "images");
		}
	}
	
	

}
