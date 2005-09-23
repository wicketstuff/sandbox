package wicket.contrib.scriptaculous.dragdrop;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.image.Image;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

public class DraggableImage extends Image {

    private final String style;
    private final String id;

    public DraggableImage(String wicketId, String id, String img, String style) {
        super(wicketId, img);
        this.id = id;
        this.style = style;

        add(new JavascriptBindingEventHandler());
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

    /**
     * event handler to make sure that scriptaculous javascript files get included.
     */
    private class JavascriptBindingEventHandler extends ScriptaculousAjaxHandler {
        protected IResourceStream getResponse() {
            StringBufferResourceStream s = new StringBufferResourceStream();
            return s;
        }
    }
}
