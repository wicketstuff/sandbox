/*
 * $Id$ $Revision$ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.examples.cdapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Resource;
import wicket.contrib.data.model.PersistentObjectModel;
import wicket.contrib.data.model.hibernate.HibernateObjectModel;
import wicket.examples.cdapp.model.CD;
import wicket.examples.cdapp.util.HibernateSessionDelegate;
import wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import wicket.markup.html.PackageResource;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.upload.FileUpload;
import wicket.markup.html.form.upload.FileUploadField;
import wicket.markup.html.form.validation.IntegerValidator;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.image.Image;
import wicket.markup.html.image.resource.DynamicImageResource;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.IModel;
import wicket.model.PropertyModel;
import wicket.util.lang.Bytes;


/**
 * Page for editing CD's.
 * 
 * @author Eelco Hillenius
 */
public final class EditPage extends CdAppBasePage
{
	/** Logger. */
	private static Log log = LogFactory.getLog(SearchPage.class);

	/**
	 * static resource from this package; references image 'questionmark.gif'.
	 */
	private static Resource IMG_UNKNOWN;

	/** model for one cd. */
	private final PersistentObjectModel cdModel;

	/** search page to navigate back to. */
	private final SearchPage searchCDPage;

	/**
	 * form for detail editing.
	 */
	private final class DetailForm extends Form
	{
		/**
		 * Construct.
		 * 
		 * @param name
		 *            component name
		 * @param cdModel
		 *            the model
		 */
		public DetailForm(String name, PersistentObjectModel cdModel)
		{
			super(name, cdModel);
			RequiredTextField titleField = new RequiredTextField("title", new PropertyModel(
					cdModel, "title"));
			titleField.add(LengthValidator.max(50));
			add(titleField);
			RequiredTextField performersField = new RequiredTextField("performers",
					new PropertyModel(cdModel, "performers"));
			performersField.add(LengthValidator.max(50));
			add(performersField);
			TextField labelField = new TextField("label", new PropertyModel(cdModel, "label"));
			labelField.add(LengthValidator.max(50));
			add(labelField);
			RequiredTextField yearField = new RequiredTextField("year", new PropertyModel(cdModel,
					"year"));
			yearField.add(IntegerValidator.POSITIVE_INT);
			add(yearField);
			add(new Link("cancelButton")
			{
				public void onClick()
				{
					setResponsePage(searchCDPage);
				}
			});
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		public void onSubmit()
		{
			CD cd = (CD)getModelObject();
			boolean isNew = (cd.getId() == null);
			// note that, as we used the Ognl property model, the fields are
			// allready updated
			getCdDao().save(cd);

			if (isNew)
			{
				// if it is a new cd, start with a fresh page
				SearchPage searchPage = new SearchPage();
				searchPage.setInfoMessageForNextRendering("cd " + cd.getTitle() + " created");
				setResponsePage(searchPage);
			}
			else
			{
				// set message for search page to display on next rendering
				searchCDPage.setInfoMessageForNextRendering("cd " + cd.getTitle() + " saved");
				setResponsePage(searchCDPage); // navigate back to search page
			}
		}
	}

	/**
	 * Form for uploading an image and attaching that image to the cd.
	 */
	private final class ImageUploadForm extends Form
	{
		private FileUploadField uploadField;

		/**
		 * Construct.
		 * 
		 * @param name
		 * @param cdModel
		 */
		public ImageUploadForm(String name, PersistentObjectModel cdModel)
		{
			super(name, cdModel);

			// set this form to multipart mode
			setMultiPart(true);

			// add the actual upload field
			add(uploadField = new FileUploadField("file"));
		}

		protected void onSubmit()
		{
			// get the uploaded file
			FileUpload upload = uploadField.getFileUpload();
			if (upload != null)
			{
				CD cd = (CD)getModelObject();
				cd.setImageBytes(upload.getBytes());
				thumbnailImage.setImageResource(getThumbnail());
				getCdDao().save(cd);
			}
		}
	}

	/**
	 * Deletes the cd image.
	 */
	private final class DeleteImageLink extends Link
	{
		/**
		 * Construct.
		 * 
		 * @param name
		 * @param cdModel
		 */
		public DeleteImageLink(String name, IModel cdModel)
		{
			super(name, cdModel);
		}

		/**
		 * @see wicket.markup.html.link.Link#onClick()
		 */
		public void onClick()
		{
			CD cd = (CD)getModelObject();
			cd.setImage(null);
			thumbnailImage.setImageResource(getThumbnail());
			getCdDao().save(cd);
		}

		/**
		 * @see wicket.Component#isVisible()
		 */
		public boolean isVisible()
		{
			// only set visible when there is an image set
			return ((CD)getModelObject()).getImage() != null;
		}
	}

	private Image thumbnailImage;

	/**
	 * Constructor.
	 * 
	 * @param searchCDPage
	 *            the search page to navigate back to
	 * @param id
	 *            the id of the cd to edit
	 */
	public EditPage(final SearchPage searchCDPage, Long id)
	{
		super();
		cdModel = new HibernateObjectModel(id, CD.class, new HibernateSessionDelegate());
		this.searchCDPage = searchCDPage;
		add(new Label("cdTitle", new TitleModel(cdModel)));
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		add(new DetailForm("detailForm", cdModel));
		ImageUploadForm imageUploadForm = new ImageUploadForm("imageUpload", cdModel);
		imageUploadForm.setMaxSize(Bytes.kilobytes(200));
		add(imageUploadForm);

		getThumbnail();

		// create a link that displays the full image in a popup page
		ImagePopupLink popupImageLink = new ImagePopupLink("popupImageLink", cdModel);

		// create an image using the image resource
		popupImageLink.add(thumbnailImage = new Image("cdimage", getThumbnail()));

		// add the link to the original image
		add(popupImageLink);

		// add link for deleting the image
		add(new DeleteImageLink("deleteImageLink", cdModel));
	}

	/**
	 * Gets either the cd's thumbnail image, or a special question mark image.
	 */
	private Resource getThumbnail()
	{
		// create an image resource that displays a question mark when no image
		// is set on the cd, or displays a thumbnail of the cd's image when
		// there is one
		final CD cd = (CD)cdModel.getObject(null);
		if (cd.getImage() == null)
		{
			if(IMG_UNKNOWN == null)
			{
				 IMG_UNKNOWN = PackageResource.get(EditPage.class, "questionmark.gif").setCacheable(false);
			}
			return IMG_UNKNOWN;
		}
		else
		{
			DynamicImageResource img = new DynamicImageResource()
			{
				protected byte[] getImageData()
				{
					return cd.getImageBytes();
				}
			};
			return new ThumbnailImageResource(img, 100).setCacheable(false);
		}
	}

	/**
	 * @see wicket.Component#initModel()
	 */
	protected IModel initModel()
	{
		return cdModel;
	}
}