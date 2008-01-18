package wicket.contrib.panel.layout;

import org.apache.wicket.Component;

public class CellWidthModifier extends CellModifier {
	private final int width;
	public CellWidthModifier(Component component, int width) {
		super(component);
		this.width = width;
	}

	public void modify(CellLayout cell) {
		cell.width = width;
	}
}
