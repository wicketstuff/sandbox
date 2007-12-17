package org.wicketstuff.scriptaculous.dragdrop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * Extension to {@link ListView} that allows for drag/drop reordering of items.
 *
 * @see http://wiki.script.aculo.us/scriptaculous/show/Sortable.create
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class SortableListView extends WebMarkupContainer
{
	private AbstractAjaxBehavior onUpdateBehavior = new SortableContainerBehavior();
	private Map options = new HashMap();
	private final List items;

	public SortableListView(String id, String itemId, final List items)
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

				if (null != getDraggableClassName()) {
					item.add(new AttributeAppender("class", new Model(getDraggableClassName()), " "));
				}
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

	/**
	 * callback extension point for populating each list item.
	 * @param item
	 */
	protected abstract void populateItemInternal(ListItem item);

	/**
	 * extension point for integrating with {@link DraggableTarget}
	 * @see DraggableTarget#acceptAll(SortableContainer)
	 * @return
	 */
	public String getDraggableClassName() {
		return null;
	}

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

		@Override
		protected void respond(AjaxRequestTarget target) {
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

			target.addComponent(getComponent());
		}
	}
}
