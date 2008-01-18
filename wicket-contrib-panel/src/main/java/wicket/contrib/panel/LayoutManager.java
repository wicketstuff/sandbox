package wicket.contrib.panel;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Response;

public interface LayoutManager extends Serializable {

	void startLayout(Response response);

	void startLayout(Response response, Component component);

	void endLayout(Response response, Component component);

	void endLayout(Response response);

	void addModifier(LayoutModifier modifier);

}
