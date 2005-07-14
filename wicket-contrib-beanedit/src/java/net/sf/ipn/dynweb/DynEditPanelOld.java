/*
 * Created on Dec 28, 2004
 */
package net.sf.ipn.dynweb;

import java.io.Serializable;

import net.sf.ipn.app.SavableLoadableModel;
import net.sf.ipn.dynweb.metadata.Attribute;
import net.sf.ipn.dynweb.metadata.panel.EditPanelData;
import net.sf.ipn.dynweb.visitor.FormBuilderVisitor;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * @author Jonathan Carlson Builds an HTML form component using the given EditPanelData
 *         instance
 */
public class DynEditPanelOld extends Panel
{
	private FeedbackPanel feedback = null;

	public DynEditPanelOld(String id)
	{
		super(id);
	}

	public DynEditPanelOld(String id, final EditPanelData editPanelData, final IModel model)
	{
		super(id);
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		add(new EditForm("dynamicEditForm", model, this.feedback, editPanelData));

		if (editPanelData.getSubPanelDatas().isEmpty())
		{
			add(new Label("relationships", "").setVisible(false)); // a null
		}
		else
		{
			// This panel will display a tab for each subPanel in editPanelData
			add(new DynRelationshipsPanel("relationships", editPanelData.getSubPanelDatas(), model));
		}
	}

	/**
	 * A search form that uses itself as the model (form field attributes)
	 */
	public class EditForm extends Form implements Serializable
	{
		public EditForm(String name, final IModel model, FeedbackPanel feedback,
				final EditPanelData panelData)
		{
			super(name, model, feedback);

			// WebMarkupContainer does nothing except allowing me to hide stuff in it.
			WebMarkupContainer titleContainer = new WebMarkupContainer("headerTitle");
			titleContainer.setVisible(panelData.isHeaderShown());
			add(titleContainer);

			add(new WebMarkupContainer("headerSeparator").setVisible(panelData.isHeaderShown()));

			titleContainer.add(new Label("formTitle", panelData.getName()));


			final FormBuilderVisitor formBuilder = new FormBuilderVisitor(model);
			ListView wicketListView = new ListView("formFields", panelData.getAttributes())
			{
				public void populateItem(final ListItem row)
				{
					final Attribute attr = (Attribute)row.getModelObject();
					// TODO: attr.getName() below probably won't cut it long-term
					row.add(new Label("dynamicLabel", attr.getName()));
					formBuilder.setListItem(row);
					attr.visit(formBuilder);
				}
			};
			add(wicketListView);
		}

		public final void onSubmit()
		{
			// TODO: Do save logic here.
			System.out.print("$$$$ Form Submitted");
			System.out.println("  " + this.getModelObject());

			if (getModel() instanceof SavableLoadableModel)
			{
				SavableLoadableModel m = (SavableLoadableModel)getModel();
				m.save();
			}
		}
	}

}