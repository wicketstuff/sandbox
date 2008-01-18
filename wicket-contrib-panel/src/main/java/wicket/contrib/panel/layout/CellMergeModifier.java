package wicket.contrib.panel.layout;

import org.apache.wicket.Component;

public class CellMergeModifier extends CellModifier {
	public CellMergeModifier(Component component) {
		super(component);
	}

	public void modify(CellLayout cell) {
		cell.mergeWithFollowing = true;
	}

}
