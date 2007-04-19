package wicket.contrib.markup.html.yui.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.StringHeaderContributor;
import wicket.contrib.YuiImage;
import org.apache.wicket.extensions.util.resource.PackagedTextTemplate;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class DragDropGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private DragableSlotList dragableSlotList;

	private TargetSlotList targetSlotList;

	private String javaScriptId;

	public DragDropGroup(String id, final DragDropSettings settings,
			final FormComponent dragableElement,
			final FormComponent targetElement) {
		super(id);
		this.dragableSlotList = settings.getDragableSlotList();
		this.targetSlotList = settings.getTargetSlotList();

		if (dragableElement != null) {
			dragableElement.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject() {
							return dragableElement.getId() + "_" + javaScriptId;
						}
					}));
		}
		add(dragableElement);

		if (targetElement != null) {
			targetElement.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject() {
							return targetElement.getId() + "_" + javaScriptId;
						}
					}));
		}
		add(targetElement);

		// Add the target slots
		ListView targetSlotView = new ListView(targetSlotList.getId(),
				targetSlotList.getTargetList()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				TargetSlot targetSlot = (TargetSlot) item.getModelObject();
				YuiImage slot = targetSlot.getSlot();
				item.add(new DragDropTargetSlot(targetSlotList.getSlotId(),
						item.getIndex(), slot, settings));
			};
		};
		add(targetSlotView);

		// Add the dragable slots and dragable images
		ListView dragableSlotView = new ListView(dragableSlotList.getId(),
				dragableSlotList.getDragableList()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				DragableSlot dragableSlot = (DragableSlot) item
						.getModelObject();
				YuiImage slot = dragableSlot.getSlot();
				YuiImage img = dragableSlot.getImage();
				item.add(new DragDropDragableSlot(dragableSlotList.getSlotId(),
						item.getIndex(), slot, settings));
				item.add(new DragDropPlayer(dragableSlotList.getImgId(), item
						.getIndex(), img, settings));
			};
		};
		add(dragableSlotView);
	}

	protected String getJavaScriptComponentInitializationScript() {
		// targetSlot [empty, empty, empty, empty];
		String targetSlot = "";
		for (int i = 0; i < targetSlotList.getSize(); i++) {
			if (targetSlot.equals("") || targetSlot == "") {
				targetSlot = "'empty'";
			} else {
				targetSlot = targetSlot + ", 'empty'";
			}
		}

		// targetSlotId ["t1","t2","t3","t4"];
		// the ids are to be provided by the user
		String targetSlotId = "";
		for (int i = 0; i < targetSlotList.getSize(); i++) {
			TargetSlot target = (TargetSlot) targetSlotList.getTargetSlot(i);
			if (targetSlotId.equals("") || targetSlotId == "") {
				targetSlotId = "'" + target.getSlot().getDesc() + "'";
			} else {
				targetSlotId = targetSlotId + ", '"
						+ target.getSlot().getDesc() + "'";
			}
		}

		// dragSlot ["p1", "p2", "p3", "p4"];
		// the ids are to be provided by the user
		String dragSlot = "";
		for (int i = 0; i < dragableSlotList.getSize(); i++) {
			DragableSlot dragable = (DragableSlot) dragableSlotList
					.getDragableSlot(i);
			if (dragSlot.equals("") || dragSlot == "") {
				dragSlot = "'" + dragable.getImage().getDesc() + "'";
			} else {
				dragSlot = dragSlot + ", '" + dragable.getImage().getDesc()
						+ "'";
			}
		}

		// dragSlotId ["b1", "b2", "b3", "b4"];
		String dragSlotId = "";
		for (int i = 0; i < dragableSlotList.getSize(); i++) {
			DragableSlot dragable = (DragableSlot) dragableSlotList
					.getDragableSlot(i);
			if (dragSlotId.equals("") || dragSlotId == "") {
				dragSlotId = "'" + dragable.getSlot().getDesc() + "'";
			} else {
				dragSlotId = dragSlotId + ", '" + dragable.getSlot().getDesc()
						+ "'";
			}
		}

		PackagedTextTemplate template = new PackagedTextTemplate(
				DragDropGroup.class, "init.js");
		Map<String, String> variables = new HashMap<String, String>(5);
		variables.put("javaScriptId", javaScriptId);
		variables.put("targetSlot", targetSlot);
		variables.put("targetSlotId", targetSlotId);
		variables.put("dragSlot", dragSlot);
		variables.put("dragSlotId", dragSlotId);
		template.interpolate(variables);
		return template.getString();
	}

	protected void onAttach() {
		super.onAttach();
		javaScriptId = getMarkupId();

		String js = "\n<script type=\"text/javascript\">"
				+ getJavaScriptComponentInitializationScript()
				+ "\n</script>\n";
		add(new StringHeaderContributor(js));
	}
}
