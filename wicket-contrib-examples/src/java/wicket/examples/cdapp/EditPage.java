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

import java.sql.Blob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.MarkupContainer;
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
import wicket.markup.html.form.validation.NumberValidator;
import wicket.markup.html.form.validation.StringValidator;
import wicket.markup.html.image.Image;
import wicket.markup.html.image.resource.BlobImageResource;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.util.lang.Bytes;


/**
 * Page for editing CD's.
 * 
 * @author Eelco Hillenius
 */
public final class EditPage extends CdAppBasePage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Logger. */
	private static Log log = LogFactory.getLog(SearchPage.class);

	/**
	 * static resource from this package; references image 'questionmark.gif'.
	 */
	private static Resource IMG_UNKNOWN;

	/** model for one cd. */
	private final PersistentObjectModel<CD, Long> cdModel;

	/** search page to navigate back to. */
	private final SearchPage searchCDPage;

	/**
	 * form for detail editing.
	 */
	private final class DetailForm extends Form<CD>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * 
		 * @param name
		 *            component name
		 * @param cdModel
		 *            the model
		 */
		public DetailForm(MarkupContainer parent, String name, IModel<CD> cdModel)
		{
			super(parent, name, new CompoundPropertyModel<CD>(cdModel));
			RequiredTextField titleField = new RequiredTextField(this, "title");
			titleField.add(StringValidator.maximumLength(50));
			RequiredTextField performersField = new RequiredTextField(this, "performers");
			performersField.add(StringValidator.maximumLength(50));
			TextField labelField = new TextField(this, "label");
			labelField.add(StringValidator.maximumLength(50));
			RequiredTextField yearField = new RequiredTextField(this, "year", Integer.class);
			yearField.add(NumberValidator.POSITIVE);
			new Link(this, "cancelButton")
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick()
				{
					setResponsePage(searchCDPage);
				}
			};
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit()
		{
			CD cd = getModelObject();
			boolean isNew = (cd.getId() == null);
			// note that, as we used the Ognl property model, the fields are
			// allready updated
			getCdDao().save(cd);

			if (isNew)
			{
				// if it is a new cd, start with a fresh page
				SearchPage searchPage = new SearchPage();
				searchPage.info("cd " + cd.getTitle() + " created");
				setResponsePage(searchPage);
			}
			else
			{
				// set message for search page to display on next rendering
				searchCDPage.info("cd " + cd.getTitle() + " saved");
				setResponsePage(searchCDPage); // navigate back to search page
			}
		}
	}

	/**
	 * Form for uploading an image and attaching that image to the cd.
	 */
	private final class ImageUploadForm extends Form<CD>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private FileUploadField uploadField;

		/**
		 * Construct.
		 * 
		 * @param parent
		 * @param name
		 * @param cdModel
		 */
		public ImageUploadForm(MarkupContainer parent, String name,
				PersistentObjectModel<CD, Long> cdModel)
		{
			super(parent, name, cdModel);

			// set this form to multipart mode
			setMultiPart(true);

			// add the actual upload field
			uploadField = new FileUploadField(this, "file");
		}

		@Override
		protected void onSubmit()
		{
			// get the uploaded file
			FileUpload upload = uploadField.getFileUpload();
			if (upload != null)
			{
				CD cd = getModelObject();
				cd.setImageBytes(upload.getBytes());
				thumbnailImage.setImageResource(getThumbnail());
				getCdDao().save(cd);
			}
		}
	}

	/**
	 * Deletes the cd image.
	 */
	private final class DeleteImageLink extends Link<CD>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 * @param name
		 * @param cdModel
		 */
		public DeleteImageLink(MarkupContainer parent, String name, IModel<CD> cdModel)
		{
			super(parent, name, cdModel);
		}

		/**
		 * @see wicket.markup.html.link.Link#onClick()
		 */
		@Override
		public void onClick()
		{
			CD cd = getModelObject();
			cd.setImage(null);
			thumbnailImage.setImageResource(getThumbnail());
			getCdDao().save(cd);
		}

		/**
		 * @see wicket.Component#isVisible()
		 */
		@Override
		public boolean isVisible()
		{
			// only set visible when there is an image set
			return getModelObject().getImage() != null;
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
		cdModel = new HibernateObjectModel<CD, Long>(id, CD.class, new HibernateSessionDelegate());
		this.searchCDPage = searchCDPage;
		new Label(this, "cdTitle", new TitleModel(cdModel));
		FeedbackPanel feedback = new FeedbackPanel(this, "feedback");
		new DetailForm(this, "detailForm", cdModel);
		ImageUploadForm imageUploadForm = new ImageUploadForm(this, "imageUpload", cdModel);
		imageUploadForm.setMaxSize(Bytes.kilobytes(200));

		getThumbnail();

		// create a link that displays the full image in a popup page
		ImagePopupLink popupImageLink = new ImagePopupLink(this, "popupImageLink", cdModel);

		// create an image using the image resource
		thumbnailImage = new Image(popupImageLink, "cdimage", getThumbnail());

		// add link for deleting the image
		new DeleteImageLink(this, "deleteImageLink", cdModel);
	}

	/**
	 * Gets either the cd's thumbnail image, or a special question mark image.
	 */
	private Resource getThumbnail()
	{
		// create an image resource that displays a question mark when no image
		// is set on the cd, or displays a thumbnail of the cd's image when
		// there is one
		final CD cd = cdModel.getObject();
		if (cd.getImage() == null)
		{
			if (IMG_UNKNOWN == null)
			{
				IMG_UNKNOWN = PackageResource.get(EditPage.class, "questionmark.gif").setCacheable(
						false);
			}
			return IMG_UNKNOWN;
		}
		else
		{
			BlobImageResource img = new BlobImageResource()
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected Blob getBlob()
				{
					return cd.getImage();
				}
			};
			return new ThumbnailImageResource(img, 100).setCacheable(false);
		}
	}

	/**
	 * @see wicket.Component#initModel()
	 */
	@Override
	protected IModel initModel()
	{
		return cdModel;
	}
}