package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public interface Property extends Serializable
{
	IModel getLabel();

	Component getViewer(String id, IModel object);

	Component getEditor(String id, IModel object);

	Component getFilter(String id, IModel object);

}
