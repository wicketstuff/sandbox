package wicket.contrib.scriptaculous.dragdrop;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.image.Image;
import wicket.util.resource.IResourceStream;

public class DraggableImage extends Image {

    private final String draggableStyle;
    private final String id;

    public DraggableImage(String wicketId, String id, String img, String draggableStyle) {
        super(wicketId, img);
        this.id = id;
        this.draggableStyle = draggableStyle;

		add(new JavascriptBindingHandler());
    }

    protected void onRender() {
        super.onRender();

        getResponse().write("<script type=\"text/javascript\">new Draggable('" + id + "', {revert:true})</script>");
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", id);
        tag.put("class", draggableStyle);
    }

    public String getDraggableStyle() {
    	return draggableStyle;
    }

	private class JavascriptBindingHandler extends ScriptaculousAjaxHandler {

		protected IResourceStream getResponse() {
			return null;
		}

	}
}
