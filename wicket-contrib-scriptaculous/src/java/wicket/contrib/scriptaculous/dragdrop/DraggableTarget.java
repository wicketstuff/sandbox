package wicket.contrib.scriptaculous.dragdrop;

import wicket.PageParameters;
import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;

public class DraggableTarget extends WebMarkupContainer {

    private final Class pageContribution;
    private final String draggableClass;

    public DraggableTarget(String id, String draggableClass, Class pageContribution) {
        super(id);

        this.draggableClass = draggableClass;
        this.pageContribution = pageContribution;


		add(ScriptaculousAjaxHandler.JAVASCRIPT_BINDING_HANDLER);
    }

    protected void onRender() {
        super.onRender();

        String url = this.getPage().urlFor(null, pageContribution, new PageParameters());
        getResponse().write("<script type=\"text/javascript\">Droppables.add('"+ getId() + "', " +
                " {accept:'" + draggableClass + "', " +
                " onDrop:function(element){ " +
                    " new Ajax.Updater('" + getId() + "', '"+ url + "', " +
                        " { " +
//                        " onLoading:function(request){ Element.show('indicator')}, " +
//                        " onComplete:function(request){Element.hide('indicator')}, " +
                        " parameters:'id=' + encodeURIComponent(element.id), " +
                        " evalScripts:true, " +
                        " asynchronous:true" +
                        " }" +
                ")}, " +
                " hoverclass:'" + getId() +"-active'})</script>");
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", getId());
    }
}
