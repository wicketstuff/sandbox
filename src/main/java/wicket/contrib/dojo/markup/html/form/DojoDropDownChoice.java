package wicket.contrib.dojo.markup.html.form;

import java.util.List;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.IChoiceRenderer;
import wicket.model.IModel;

public class DojoDropDownChoice extends DropDownChoice {
	
	private boolean handleSelectionChange = false;

	public DojoDropDownChoice(String id, IModel choices, IChoiceRenderer renderer) {
		super(id, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices, IChoiceRenderer renderer) {
		super(id, model, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices) {
		super(id, model, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List data, IChoiceRenderer renderer) {
		super(id, model, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel choices) {
		super(id, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List data, IChoiceRenderer renderer) {
		super(id, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List choices) {
		super(id, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id) {
		super(id);
		add(new DojoDropDownChoiceHandler());
	}
	
	
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "select");
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_COMBOBOX);
	}

	public boolean isHandleSelectionChange() {
		return handleSelectionChange;
	}

	public void setHandleSelectionChange(boolean handleSelectionChange) {
		this.handleSelectionChange = handleSelectionChange;
	}

	protected final boolean wantOnSelectionChangedNotifications() {
		return isHandleSelectionChange();
	}

	protected void onAttach() {
		super.onAttach();
		this.setOutputMarkupId(true);
	}

}
