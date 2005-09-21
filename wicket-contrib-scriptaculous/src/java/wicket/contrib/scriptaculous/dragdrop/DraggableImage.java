package wicket.contrib.scriptaculous.dragdrop;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.image.Image;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

public class DraggableImage extends Image {

    public DraggableImage(String id, String img) {
        super(id, img);
        add(new JavascriptBindingEventHandler());
    }

    protected void onRender() {
        super.onRender();

        getResponse().write("<script type=\"text/javascript\">new Draggable('" + getId() + "', {revert:true})</script>");
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", getId());
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
