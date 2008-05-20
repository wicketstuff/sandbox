package org.wicketstuff.scriptaculous.dragdrop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * Extension to {@link ListView} that allows for drag/drop reordering of items.
 * <p>
 * The underlying List model will be updated whenever the user drag/drop reorders 
 * the list.  The application can listen for the reordering using 
 * a ??? model change listener ???
 * </p>
 * <p>
 * it *might* be possible to add/remove list items by drag/dropping list items 
 * from one sortable list to another or to a draggable target.  I haven't tested this yet...
 * </p>
 * 
 * @see http://wiki.script.aculo.us/scriptaculous/show/Sortable.create
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class SortableListView extends WebMarkupContainer {
	private static final Logger LOG = LoggerFactory.getLogger(SortableListView.class);

	private AbstractAjaxBehavior onUpdateBehavior = new SortableContainerBehavior();
	private Map<String, Serializable> options = new HashMap<String, Serializable>();
	private List<SortableListView> containmentSortables = new ArrayList<SortableListView>();
	
	public SortableListView(String id, final String itemId, final List<?> items) {
		this(id, itemId, new Model((Serializable) items));
	}

	public SortableListView(String id, final String itemId, IModel model) {
		super(id, model);

		setOutputMarkupId(true);

		add(onUpdateBehavior);
		add(new ListView(itemId, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected ListItem newItem(int index) {
				return new SortableListItem(itemId, index, getListItemModel(getModel(), index));
			}

			@Override
			protected void populateItem(ListItem item) {
				if (null != getDraggableClassName()) {
					item.add(new AttributeAppender("class", new Model(getDraggableClassName()), " "));
				}
				populateItemInternal(item);
			}
		});
	}

	public void setConstraintVertical() {
		options.put("constraint", "vertical");
	}

	public void setConstraintHorizontal() {
		options.put("constraint", "horizontal");
	}
	
	public void setGhosting(boolean value) {
		options.put("ghosting", value);
	}
	
	public void setScrollSensitivity(int value) {
		options.put("scrollSensitivity", value);
	}
	
	public void setScrollSpeed(int value) {
		options.put("scrollSpeed", value);
	}
	
	public void setDropOnEmpty(boolean value) {
		options.put("dropOnEmpty", value);
	}
	
	public void setHoverClass(String className) {
		options.put("hoverclass", className);
	}

	/**
	 * add other sortable containers that items can be drag/dropped to.
	 * @param otherView
	 */
	public void addContainment(SortableListView otherView) {
		containmentSortables.add(otherView);
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
	
	protected void onRender(MarkupStream markupStream) {
		super.onRender(markupStream);

		if (!containmentSortables.isEmpty()) {
			String ids = "['" + getMarkupId() + "'";
			for (SortableListView container : containmentSortables) {
				ids += ", '" + container.getMarkupId() + "'";
			}
			ids += "]";
			options.put("containment", ids);
		}
		options.put("onUpdate", new JavascriptBuilder.JavascriptFunction(
				"function(element) { wicketAjaxGet('" + onUpdateBehavior.getCallbackUrl()
						+ "&' + Sortable.serialize(element)); }"));

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("Sortable.create('" + getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		//TODO: investigate renderOnDomReadyJavascript within renderHead??
		getResponse().write(builder.buildScriptTagString());
	}

	private static class SortableContainerBehavior extends ScriptaculousAjaxBehavior {
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target) {
			SortableListView listView = (SortableListView) getComponent();
			String[] parameters = listView.getRequest().getParameters(listView.getMarkupId() + "[]");

			if (parameters == null) {
				LOG.warn("Invalid parameters passed in Ajax request.");
				return;
			}

            List<Object> items = (List<Object>) listView.getModelObject();
            List<Object> originalItems = new ArrayList<Object>(items);
            for (int index = 0; index < items.size(); index++) {
            	int newIndex = Integer.parseInt(parameters[index]);
            	if (!items.get(index).equals(items.get(newIndex))) {
            		LOG.info("Moving sortable object from location " + newIndex + " to " + index);
            	}
            	items.set(index, originalItems.get(newIndex));
            }
            listView.modelChanged();
			target.addComponent(getComponent());
		}
	}
	
	private static class SortableListItem extends ListItem {
		private static final long serialVersionUID = 1L;
		private final String itemId;

		public SortableListItem(String itemId, int index, IModel listItemModel) {
			super(index, listItemModel);
			this.itemId = itemId;
			
			setOutputMarkupId(true);
		}

		@Override
		public String getMarkupId() {
			return itemId + "_" + getIndex();
		}
	}
}
