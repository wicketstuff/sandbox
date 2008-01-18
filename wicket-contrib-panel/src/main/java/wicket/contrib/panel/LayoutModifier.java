package wicket.contrib.panel;

import java.io.Serializable;

import org.apache.wicket.Component;

import wicket.contrib.panel.layout.CellLayout;

public interface LayoutModifier extends Serializable {

	boolean forComponent(Component component);

	void modify(CellLayout cell);

}
