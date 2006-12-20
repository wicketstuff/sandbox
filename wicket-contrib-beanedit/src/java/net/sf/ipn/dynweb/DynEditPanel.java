/*
 * Created on Dec 28, 2004
 */
package net.sf.ipn.dynweb;

import java.util.LinkedList;
import java.util.List;

import net.sf.ipn.dynweb.visitor.FormBuilderVisitor;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson Provides a rapid development bean editing Panel. No messing
 *         with HTML is required to build a panel.
 *         <p>
 *         Referring class (not the subclass) must call #completePanel() after the
 *         constructor call. This call cannot unfortunately be made inside the constructor
 *         or it would be done automatically.
 *         </p>
 *         Subclasses implement the setup() method like this: <code>
 * protected void setup()
 * {
 *   setModel(new Model(myBean)); // or any other IModel implementer
 *   addTextField("subject").setTitle("Subject").setMaxLength(100).setSize(20).setRequired(true);
 *   addTextArea("body").setTitle("Body").setColumns(50).setRows(5);
 *   List groups = getIpnSession().getUser().getGroupList();      
 *   addDropDown("group", groups).setTitle("Group").setRequired(true);
 * }
 * </code>
 */
public abstract class DynEditPanel extends Panel
{
	private FeedbackPanel feedbackPanel = null;
	private Form form = null;
	private List formComponents = new LinkedList();

	public DynEditPanel(String id)
	{
		super(id);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);
		this.form = new Form("dynEditForm", this.feedbackPanel)
		{
			public void onSubmit()
			{
				formSubmitted();
			}
		};
		add(this.form);
	}

	/**
	 * Calls setup() in the subclass and then creates the form components. This must be
	 * called by the referencing class after class construction (not the subclass). This
	 * unfortunately can't be called from the Constructor due to a limitation or
	 * characteristic of Java.
	 */
	public final DynEditPanel complete()
	{
		// setup();
		final FormBuilderVisitor formBuilder = new FormBuilderVisitor(getModel());
		ListView wicketListView = new ListView("formFields", this.formComponents)
		{
			public void populateItem(final ListItem row)
			{
				final DynFormComponentPanel panel = (DynFormComponentPanel)row.getModelObject();
				if (panel.getTitle() == null)
					row.add(new Label("dynamicLabel", ""));
				else
					row.add(new Label("dynamicLabel", panel.getTitle()));
				row.add(panel);
			}
		};
		this.form.add(wicketListView);
		return this;
	}

	/**
	 * Subclasses must override to set the model and add attributes.
	 */
	protected abstract void setup();

	protected DynTextFieldPanel addTextField(String ognlExpression)
	{
		DynTextFieldPanel panel = new DynTextFieldPanel("dynamicField", new PropertyModel(
				getModel(), ognlExpression));
		this.formComponents.add(panel);
		return panel;
	}

	protected DynTextAreaPanel addTextArea(String ognlExpr)
	{
		DynTextAreaPanel panel = new DynTextAreaPanel("dynamicField", new PropertyModel(getModel(),
				ognlExpr));
		this.formComponents.add(panel);
		return panel;
	}

	protected DynDropDownPanel addDropDown(String ognlExpr, List choices)
	{
		DynDropDownPanel panel = new DynDropDownPanel("dynamicField", new PropertyModel(getModel(),
				ognlExpr), choices);
		this.formComponents.add(panel);
		return panel;
	}

	protected Form getForm()
	{
		return this.form;
	}

	protected FeedbackPanel getFeedbackPanel()
	{
		return this.feedbackPanel;
	}

	/**
	 * Provides a way for subclasses to specify the action taken when the edit form is
	 * submitted.
	 */
	protected abstract void formSubmitted();

}