package wicket.contrib.panel.layout;

import java.io.Serializable;

public class CellLayout implements Serializable {
	public static final int WIDTH_UNSPECIFIED = -1;
	public static final int WIDTH_REST = -2;
	public static final int COLSPAN_UNSPECIFIED = 0;
	public static final int COLSPAN_REST = -1;

	public static final int ALIGN_UNSPECIFIED = -1;
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_RIGHT = 1;
	public static final int ALIGN_CENTER = 2;

	public int width = WIDTH_UNSPECIFIED;
	public int colspan = 0;
	public int align = ALIGN_LEFT;
	public boolean mergeWithFollowing = false;

	public static String getAlign(int value) {
		if (value == ALIGN_LEFT) return "left";
		if (value == ALIGN_RIGHT) return "right";
		if (value == ALIGN_CENTER) return "center";
		return null;
	}

}
