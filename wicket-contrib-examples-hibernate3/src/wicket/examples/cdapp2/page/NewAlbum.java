package wicket.examples.cdapp2.page;

import java.util.Date;
import java.util.List;

import wicket.examples.cdapp2.model.Album;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.validation.RequiredValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;

/**
 * Our page to handle creating a new album. This is not set up to handle
 * editing a form since that is done from the list itself.
 * 
 * @author Phil Kulak
 */
public class NewAlbum extends CdPage {
	public NewAlbum() {
		super("New Album");
		
		IModel formModel = new CompoundPropertyModel(new Album());
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		
		add(new NewAlbumForm(formModel));
		add(feedback);
	}
	
	private class NewAlbumForm extends Form {
		public NewAlbumForm(IModel model) {
			super("form", model);
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
			}.setDefaultFormProcessing(false)); // so the form isn't validated
		}
		
		@Override
		public void onSubmit() {
			getDao().merge(getModelObject());
			setResponsePage(new BrowseAlbums("The album has been added."));
		}
	}
}
