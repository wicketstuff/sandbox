package wicket.contrib.dojo.markup.html;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.ClientEvent;
import wicket.contrib.dojo.DojoEventBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.link.AbstractLink;
import wicket.model.IModel;

/**
 * @author vdemay
 *
 * @param <T>
 */
public abstract class DojoLink<T> extends AbstractLink<T> implements IDojoLink
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct.
	 * 
	 * @param parent
	 *            The parent of this component
	 * 
	 * @param id
	 */
	public DojoLink(MarkupContainer parent, final String id)
	{
		this(parent, id, null);
	}
	
	/**
	 * Construct.
	 * 
	 * @param parent
	 *            The parent of this component
	 * 
	 * @param id
	 * @param model
	 */
	public DojoLink(MarkupContainer parent, final String id, final IModel<T> model)
	{
		super(parent, id, model);

		add(new DojoEventBehavior(ClientEvent.CLICK)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				onClick(target);
			}
			
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				// add the onclick handler only if link is enabled 
				if (isLinkEnabled())
				{
					super.onComponentTag(tag);
				}
			}

		});
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		
		if (isLinkEnabled())
		{
			// disable any href attr in markup
			if (tag.getName().equalsIgnoreCase("a") || tag.getName().equalsIgnoreCase("link")
					|| tag.getName().equalsIgnoreCase("area"))
			{
				tag.put("href", "#");
			}
		}
		else
		{
			disableLink(tag);
		}
	}

	/**
	 * Listener method invoked on the ajax request generated when the user
	 * clicks the link
	 * 
	 * @param target
	 */
	public abstract void onClick(final AjaxRequestTarget target);
}
