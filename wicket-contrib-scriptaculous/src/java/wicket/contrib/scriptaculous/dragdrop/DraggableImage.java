package wicket.contrib.scriptaculous.dragdrop;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.image.Image;

public class DraggableImage extends Image {

    private final String style;
    private final String id;

    public DraggableImage(String wicketId, String id, String img, String style) {
        super(wicketId, img);
        this.id = id;
        this.style = style;

		add(ScriptaculousAjaxHandler.JAVASCRIPT_BINDING_HANDLER);
    }

    protected void onRender() {
        super.onRender();

        getResponse().write("<script type=\"text/javascript\">new Draggable('" + id + "', {revert:true})</script>");
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", id);
        tag.put("class", style);
    }
}
