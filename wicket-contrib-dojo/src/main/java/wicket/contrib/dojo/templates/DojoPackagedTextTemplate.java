package wicket.contrib.dojo.templates;

import wicket.Component;
import wicket.util.resource.PackagedTextTemplate;

public class DojoPackagedTextTemplate extends PackagedTextTemplate
{
	private String fileName;
	private Component component;
	/**
	 * Constructor.
	 * 
	 * @param component
	 *            The component to be used for retrieving the classloader for
	 *            loading the packaged template.
	 * @param fileName
	 *            the name of the file, relative to the clazz position
	 * @param contentType
	 *            The mime type of this resource, such as "image/jpeg" or
	 *            "text/html"
	 * @param encoding
	 *            The file's encoding, e.g. 'UTF-8'
	 */
	public DojoPackagedTextTemplate(Component component, String fileName, String contentType, String encoding)
	{
		super(component.getClass(), fileName, contentType, encoding);
		this.component = component;
		this.fileName = fileName;
	}

	/**
	 * Constructor.
	 * 
	 * @param component
	 *            The component to be used for retrieving the classloader for
	 *            loading the packaged template.
	 * @param fileName
	 *            the name of the file, relative to the clazz position
	 * @param contentType
	 *            The mime type of this resource, such as "image/jpeg" or
	 *            "text/html"
	 */
	public DojoPackagedTextTemplate(Component component, String fileName, String contentType)
	{
		this(component, fileName, contentType, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param component
	 *            The component to be used for retrieving the classloader for
	 *            loading the packaged template.
	 * @param fileName
	 *            The name of the file, relative to the clazz position
	 */
	public DojoPackagedTextTemplate(Component component, String fileName)
	{
		this(component, fileName, "text");
	}
	
	
	/**
	 * Return a key to use in renderJavascript.
	 * A Static Key is a Key <b>Unique for all widget of the same class</b>.
	 * So if there are n widget on the same page, a resource rendered with a
	 * StaticKey will be displayed once.
	 * @return a key to use in renderJavascript
	 */
	public String getStaticKey(){
		return this.component.getClass().getName() + this.fileName;
	}
	
	/**
	 * Return a key to use in renderJavascript.
	 * A WidgetUnique Key is a Key <b>Unique for <u>one</u> widget of the class</b>.
	 * So if there are n widget on the same page, a resource rendered with a
	 * WidgetUniqueKey will be displayed n times. But if a same widget is render m times, this resource wil render once
	 * @return a key to use in renderJavascript
	 */
	public String getWidgetUniqueKey(){
		//use memory adress to generate key
		return this.component.toString() + this.fileName;
	}

}
