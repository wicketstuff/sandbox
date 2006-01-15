package wicket.contrib.gmap;

import wicket.RequestCycle;
import wicket.Response;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.request.target.ComponentRequestTarget;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;


/**
 * @author Iulian-Corneliu Costan
 */
class GMapAjaxHandler extends DojoAjaxHandler {

    protected void renderHeadInitContribution(HtmlHeaderContainer container) {
        //todo coding issue, developers always forget to call super method to add dojo javascript import  
        super.renderHeadInitContribution(container);

        StringBuffer s = new StringBuffer("\t<script language=\"JavaScript\" type=\"text/javascript\">\n");
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

        container.getResponse().write(s.toString());
    }

    /**
     * When an AJAX request, gmarker component is hidden and user's info component is made visible.
     */
    protected void respond() {
        // visibility trick
        ((GMarkerContainer) getComponent()).toggleVisibility();

        RequestCycle requestCycle = RequestCycle.get();
        requestCycle.setRequestTarget(new ComponentRequestTarget(getComponent()));
    }

    protected IResourceStream getResponse() {
        Response response = RequestCycle.get().getResponse();
        StringBufferResourceStream resourceStream = new StringBufferResourceStream("text/html");
        resourceStream.append(response.toString());
        return resourceStream;
    }
}
