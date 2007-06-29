package wicket.contrib.mootools.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;

public class MFXDragPaneContainer extends Panel {
	private static final long serialVersionUID = 1L;
	private int columnWidth;
	private RepeatingView rp;
	
	public MFXDragPaneContainer(String id, int columns) {
		super(id);

		setOutputMarkupId(true);
		
		setColumnWidth(350);
		rp = new RepeatingView("columns");
		for (int i = 0; i < columns; i++) {
			rp.add(new ColumnPanel(String.valueOf(rp.size())));
		}
		add(rp);
	}

	public class ColumnPanel extends WebMarkupContainer {
		private String id;
		public ColumnPanel(String id) {
			super(id);
			this.id=id;
			setOutputMarkupId(true);
			add(new AttributeModifier("width", true,
					new PropertyModel(MFXDragPaneContainer.this, "columnWidth")));
			add(new RepeatingView("repeater"));
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			super.onComponentTag(tag);
			tag.put("id",id);
		}
		
		private static final long serialVersionUID = 1L;
		
	}
	
	protected RepeatingView getInternalRepeater(int column) {
		final List<RepeatingView> rps = new ArrayList<RepeatingView>();
		rp.visitChildren(new IVisitor() {
			public Object component(Component arg0) {
				if(arg0 instanceof RepeatingView) {
					rps.add((RepeatingView)arg0);
				}
				return IVisitor.CONTINUE_TRAVERSAL;
			}
		});
		return rps.get(column);
	}
	
	public Component getColumnComponent(int column) {
		return getInternalRepeater(column).getParent();
	}
	
	public MFXDragPaneContainer add(final MFXDragPane pane,int column) {
		getInternalRepeater(column).add(pane);
		return this;
	}
	
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

}
