package wicket.contrib.panel.layout;

import org.apache.wicket.Component;

import wicket.contrib.panel.LayoutModifier;

public abstract class CellModifier implements LayoutModifier {
	protected final Component component ;
	public CellModifier(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return component;
	}

	public boolean forComponent(Component component) {
		if (this.component == null) return true;
		return component == this.component;
	}
}
