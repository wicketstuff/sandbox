/**
 *
 */
package wicket.contrib.panel;

import java.util.Iterator;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Response;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.model.IModel;

import wicket.contrib.panel.layout.TableLayoutManager;

public class Panel extends WebMarkupContainer implements WithoutMarkup
{

	protected LayoutManager layoutManager;
	/**
	 * Construct.
	 *
	 * @param id
	 *            See Component
	 * @see wicket.Component#Component(String)
	 */
	public Panel(final String id)
	{
		this(id, null);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 *            See Component
	 * @param model
	 *            Must contain a Integer model object
	 * @see wicket.Component#Component(String, IModel)
	 */
	public Panel(final String id, final IModel model)
	{
		this(id, model, new TableLayoutManager());
	}

	/**
	 * Construct.
	 *
	 * @param id
	 *            See Component
	 * @param model
	 *            Must contain a Integer model object
	 * @see wicket.Component#Component(String, IModel)
	 */
	public Panel(final String id, final IModel model, LayoutManager layoutManager)
	{
		super(id, model);
		this.layoutManager = layoutManager;
		this.setRenderBodyOnly(true);
	}

	/**
	 * Renders this Loop container.
	 */
	protected void onRender(MarkupStream markupStream)
	{
		Application.get().getDebugSettings().setComponentUseCheck(false);
		Response response = getResponse();
		boolean writeTag = !getRenderBodyOnly();
		if (writeTag) {
			response.write(markupStream.get().toCharSequence());
		}

		if (layoutManager!=null) layoutManager.startLayout(response);
		int currentIndex = markupStream.getCurrentIndex();
		int iteration = 0;
		// Iterate through children on this container
		for (Iterator<?> iterator = iterator(); iterator.hasNext();)
		{
			iteration ++;
			// Get next child component
			final Component component = (Component)iterator.next();

			if (component == null)
			{
				throw new WicketRuntimeException(
						"Loop item is null.  Probably the number of loop iterations were changed between onBeginRequest and render time.");
			}
			if (layoutManager!=null) layoutManager.startLayout(response, component);

			// Rewind to start of markup for kids
			markupStream.setCurrentIndex(currentIndex);
			if (component.isVisible()) {
				if (component instanceof WithoutMarkup) {//Panel) {
					component.render(markupStream);
				} else if (component instanceof WebMarkupContainerWithAssociatedMarkup) {
					WebMarkupContainerWithAssociatedMarkup markupContainer = (WebMarkupContainerWithAssociatedMarkup) component;
					markupContainer.renderAssociatedMarkup("panel", "exceptionMessage");//(" saki exceptionMessage");
				} else if (component instanceof MarkupContainer) {
					MarkupContainer markupContainer = (MarkupContainer) component;
					MarkupStream markupStream2 = getApplication().getMarkupSettings().getMarkupCache().getMarkupStream(markupContainer, false, true);
					component.render(markupStream2);
				} else if (component instanceof HasMarkup) {
					component.render();
				} else {
					component.render();
				}
			}
			if (layoutManager!=null) layoutManager.endLayout(response, component);
		}
		if (layoutManager!=null) layoutManager.endLayout(response);

		markupStream.setCurrentIndex(currentIndex);
		while (!markupStream.atCloseTag())
			markupStream.next();
		if (writeTag) {
			response.write(markupStream.get().toCharSequence());
		}
	}

	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

}