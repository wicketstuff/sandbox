package wicket.contrib.panel.layout;

import org.apache.wicket.Component;

public class CellAlignModifier extends CellModifier {
	private final int align;
	public CellAlignModifier(Component component, int align) {
		super(component);
		this.align = align;
	}

	public void modify(CellLayout cell) {
		cell.align = align;
	}
}
