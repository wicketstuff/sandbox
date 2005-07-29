package wicket.examples.cdapp.page;

import java.util.Date;
import java.util.List;

import wicket.IFeedback;
import wicket.examples.cdapp.model.Album;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.validation.RequiredValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;

public class NewAlbum extends CdPage {
	public NewAlbum() {
		super("New Album");
		
		IModel formModel = new CompoundPropertyModel(new Album());
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		
		add(new NewAlbumForm(feedback, formModel));
		add(feedback);
	}
	
	private class NewAlbumForm extends Form {
		public NewAlbumForm(IFeedback feedback, IModel model) {
			super("form", model, feedback);
			add(new RequiredTextField("artist"));
			add(new RequiredTextField("title"));
			add(new RequiredTextField("date", Date.class));
			
			// It's a good idea to let the list know that we're not going to 
			// be paging it so that it can optimize better.
			List allCats = getDao().findAllCategories().setUsePaging(false);
			add(new DropDownChoice("category", allCats)
				.add(RequiredValidator.getInstance()));
			
			add(new Button("cancel") {
				@Override
				public void onSubmit() {
					setResponsePage(new BrowseAlbums());
				}
			}.setImmediate(true));
		}
		
		@Override
		public void onSubmit() {
			getDao().merge(getModelObject());
			setResponsePage(new BrowseAlbums("The album has been added."));
		}
	}
}
