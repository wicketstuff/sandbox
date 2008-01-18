package wicket.contrib.panel.layout;

import org.apache.wicket.Component;

public class CellColspanModifier extends CellModifier {
	private final int colspan;
	public CellColspanModifier(Component component, int colspan) {
		super(component);
		this.colspan = colspan;
	}

	public void modify(CellLayout cell) {
		cell.colspan = colspan;
		if (colspan == CellLayout.COLSPAN_REST) {
			cell.width = CellLayout.WIDTH_UNSPECIFIED;
		}
	}
}
