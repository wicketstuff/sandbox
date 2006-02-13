package wicket.examples.flickr.ajax;

import java.util.List;
import java.util.Vector;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.examples.flickr.FlickrDao;
import wicket.examples.flickr.HelpInfo;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.IFormSubmitListener;
import wicket.markup.html.form.TextField;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.request.target.component.ComponentRequestTarget;

public class Index extends WebPage {
	/**
	 * Generates the onsubmit JavaScript event for the form.
	 */
	private final class AjaxSubmitJavaScriptEvent extends Model {
		/** The form that recieves the event handler. */
		private final Form form;

		/** for serialization. */
		private static final long serialVersionUID = 1L;

		private AjaxSubmitJavaScriptEvent(Form form) {
			super();
			this.form = form;
		}

		@Override
		public Object getObject(Component component) {
			StringBuilder sb = new StringBuilder();

			sb.append("new Ajax.Updater('photos',");
			sb.append("'" + form.urlFor(IFormSubmitListener.INTERFACE) + "',");

			sb.append("{method:'get',");
			sb.append(" parameters:Form.serialize('form'),");
			sb.append(" onCreate:Element.show('spinner'),");
			sb
					.append(" onComplete:function(){Effect.BlindDown('photos');Element.hide('spinner');}");
			sb.append("}");
			sb.append(");");
			// always return false, otherwise the submit event gets sent to the
			// server. We
			// are already processing the ajax event.
			sb.append("return false;");

			return sb.toString();
		}
	}

	/** Used for serialization. */
	private static final long serialVersionUID = 1L;

	/** Holds the tags from the input field. */
	public String tags;

	/** Holds the urls to the image thumbnails. */
	public List<String> images = new Vector<String>();

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public Index(final PageParameters parameters) {
		add(new HelpInfo("helpinfo"));
		final Form form = new Form("form") {
			/** used for serialization. */
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				// clear the list of images
				images.clear();

				// get the new image URL's from flickr
				FlickrDao flickr = new FlickrDao();
				if (tags == null)
					tags = "";
				images.addAll(flickr.getSmallSquareImages(tags.split(" ")));

				// prepare the listview for the new images.
				ListView photos = (ListView) get("photos");
				photos.removeAll();
				// photos.internalBeginRequest();

				// render only the listview as the response
				ComponentRequestTarget target = new ComponentRequestTarget(
						photos);
				RequestCycle cycle = RequestCycle.get();
				cycle.setRequestTarget(target);
			}
		};
		add(form);
		form.add(new AttributeModifier("onsubmit", true,
				new AjaxSubmitJavaScriptEvent(form)));
		TextField tags = new TextField("tags", new PropertyModel(this, "tags"));
		ListView photos = new ListView("photos", new PropertyModel(this,
				"images")) {
			/** Used for serialization. */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVersioned() {
				return false;
			}

			@Override
			protected void populateItem(ListItem item) {
				item.add(new AttributeModifier("src", item.getModel()));
			}
		};
		form.add(tags);
		form.add(photos);
	}
}
