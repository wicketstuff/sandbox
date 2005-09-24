package wicket.contrib.scriptaculous.dragdrop;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.image.Image;

public class DraggableImage extends Image {

    private final String draggableStyle;
    private final String id;

    public DraggableImage(String wicketId, String id, String img, String draggableStyle) {
        super(wicketId, img);
        this.id = id;
        this.draggableStyle = draggableStyle;

		add(ScriptaculousAjaxHandler.newJavascriptBindingHandler());
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
}
