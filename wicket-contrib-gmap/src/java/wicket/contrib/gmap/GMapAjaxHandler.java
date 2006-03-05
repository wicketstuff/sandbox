package wicket.contrib.gmap;

import wicket.RequestCycle;
import wicket.Response;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.request.target.component.ComponentRequestTarget;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapAjaxHandler extends DojoAjaxHandler
{

    /**
     * @see wicket.contrib.dojo.DojoAjaxHandler#onRenderHeadInitContribution(wicket.Response)
     */
    protected void onRenderHeadInitContribution(Response r)
    {
        super.onRenderHeadInitContribution(r);

        StringBuffer s = new StringBuffer(
                "\t<script language=\"JavaScript\" type=\"text/javascript\">\n");
        s.append("\tfunction inject(component, data) {\n");
        s.append("\t\tvar gmarker = document.getElementById(component);\n");
        s.append("\t\tgmarker.innerHTML = data;\n");
        s.append("\t}\n");
        s.append("\tfunction dojoCall(componentUrl, componentId) { \n");
        s.append("\t\tdojo.io.bind({\n");
        s.append("\t\t\turl: componentUrl,\n");
        s.append("\t\t\tmimetype: \"text/html\",\n");
        s.append("\t\t\tload: function(type, data, evt) {  inject(componentId, data); }");
        s.append("\n\t\t});").append("\n\t}\n");
        s.append("\t</script>\n");

        r.write(s.toString());
    }

    /**
     * On AJAX request, gmarker component is hidden and user's info
     * component is made visible.
     */
    protected void respond()
    {
        // visibility trick
        ((GMarkerContainer) getComponent()).toggleVisibility();

        RequestCycle requestCycle = RequestCycle.get();
        requestCycle.setRequestTarget(new ComponentRequestTarget(getComponent()));
    }

    protected IResourceStream getResponse()
    {
        Response response = RequestCycle.get().getResponse();
        StringBufferResourceStream rs = new StringBufferResourceStream("text/html");
        rs.append(response.toString());
        return rs;
    }

    public void onException()
    {
        // todo
    }
}
