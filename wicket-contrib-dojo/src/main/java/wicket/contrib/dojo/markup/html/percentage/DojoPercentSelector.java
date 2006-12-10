package wicket.contrib.dojo.markup.html.percentage;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_PERCENTSELECTOR;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.markup.html.percentage.model.PercentageRanges;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;

public class DojoPercentSelector extends WebMarkupContainer {

	public DojoPercentSelector(MarkupContainer parent, String id, IModel<PercentageRanges> model)
	{
		super(parent, id, model);
		this.add(new DojoPercentSelectorHandler());
	}
	

	public Component setModel(IModel model)
	{
		if (!(model.getObject() instanceof PercentageRanges)){
			throw new WicketRuntimeException("Model for a DojoPercentSelector should be a PercentageRanges instance");
		}
		return super.setModel(model);
	}

	public DojoPercentSelector(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoPercentSelectorHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_PERCENTSELECTOR);
		tag.put("widgetId", getMarkupId());

		tag.put("templatePath", urlFor(new ResourceReference(DojoPercentSelector.class, "PercentSelector.htm")));
		tag.put("templateCssPath", urlFor(new ResourceReference(DojoPercentSelector.class, "PercentSelector.css")));
	}

}
