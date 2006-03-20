package wicket.contrib.jasperreports;

import wicket.markup.html.WebComponent;
import wicket.markup.ComponentTag;
import wicket.IResourceListener;

/**
 * Created Mar 19, 2006
 *
 * @author <a href="mailto:evanchooly@gmail.com">Justin Lee</a>
 */
public class JRObject extends WebComponent implements IResourceListener {
    private final JRResource _resource;

    /**
     * Construcxt.
     *
     * @param componentID component componentID
     * @param resource the resource
     */
    public JRObject(String componentID, JRResource resource) {
        super(componentID);
        _resource = resource;
    }

    protected void onComponentTag(ComponentTag tag) {
        if(!"object".equalsIgnoreCase(tag.getName())) {
            findMarkupStream().throwMarkupException("Component " + getId()
                + " must be applied to a tag of type 'object' not " + tag.toUserDebugString());
        }
        tag.put("data", getResponse().encodeURL(urlFor(IResourceListener.INTERFACE)));
        tag.put("type", _resource.getContentType());
        super.onComponentTag(tag);
    }

    public void onResourceRequested() {
        _resource.onResourceRequested();
    }
}