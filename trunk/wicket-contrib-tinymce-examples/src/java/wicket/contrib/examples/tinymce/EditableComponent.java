package wicket.contrib.examples.tinymce;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.SavePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Position;
import wicket.contrib.tinymce.settings.TinyMCESettings.Toolbar;

public class EditableComponent extends Label {
	private static final long serialVersionUID = 1L;

	public EditableComponent(String id, String text) {
		super(id, text);

		// initialize tinymce
		SavePlugin savePlugin = new SavePlugin();
		TinyMCESettings settings = new TinyMCESettings(
				TinyMCESettings.Theme.advanced);
		settings.add(savePlugin.getSaveButton(), Toolbar.first, Position.after);

		final TinyMceBehavior tinyMceBehavior = new TinyMceBehavior(settings,
				false);
		add(tinyMceBehavior);
		add(new AjaxEventBehavior("onclick") {

			protected void onEvent(AjaxRequestTarget target) {
				String loadScript = tinyMceBehavior.getLoadEditorScript()
						.toString();
				target.appendJavascript(loadScript);
			}
		});
	}
}
