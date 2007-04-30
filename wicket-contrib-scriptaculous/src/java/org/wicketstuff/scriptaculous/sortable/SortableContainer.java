package org.wicketstuff.scriptaculous.sortable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;


/**
 * Extension to {@link ListView} that allows for drag/drop reordering of items.
 * 
 * @see http://wiki.script.aculo.us/scriptaculous/show/Sortable.create
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class SortableContainer extends WebMarkupContainer
{
	private AbstractAjaxBehavior onUpdateBehavior = new SortableContainerBehavior();
	private Map options = new HashMap();
	private final List items;

	public SortableContainer(String id, String itemId, final List items)
	{
		super(id);
		this.items = items;

		setOutputMarkupId(true);

		add(onUpdateBehavior);

		add(new ListView(itemId, items)
		{
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item)
			{
				item.add(new AttributeModifier("id", true, new Model(getMarkupId() + "_"
						+ item.getIndex())));

				populateItemInternal(item);
			}
		});
	}

	public void setConstraintVertical()
	{
		options.put("constraint", "vertical");
	}
	
	public void setConstraintHorizontal() 
	{
		options.put("constraint", "horizontal");
	}

	protected abstract void populateItemInternal(ListItem item);

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);


		options.put("onUpdate", new JavascriptBuilder.JavascriptFunction(
				"function(element) { wicketAjaxGet('" + onUpdateBehavior.getCallbackUrl()
						+ "&' + Sortable.serialize(element)); }"));

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("Sortable.create('" + getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		getResponse().write(builder.buildScriptTagString());
	}
	
	private class SortableContainerBehavior extends ScriptaculousAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		protected IResourceStream getResponse() {
			AjaxRequestTarget target = new AjaxRequestTarget();
			String[] parameters = getRequestCycle().getRequest().getParameters(
					getMarkupId() + "[]");

			if (parameters != null)
			{
				List originalItems = new ArrayList(items);
				for (int index = 0; index < items.size(); index++)
				{
					int newIndex = Integer.parseInt(parameters[index]);
					items.set(index, originalItems.get(newIndex));
				}
			}

			getRequestCycle().setRequestTarget(target);

			target.addComponent(getComponent());
			return null;
		}
	}
}
