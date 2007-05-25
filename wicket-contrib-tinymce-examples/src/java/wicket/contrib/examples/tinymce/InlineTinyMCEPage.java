package wicket.contrib.examples.tinymce;

public class InlineTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	public InlineTinyMCEPage() {

		EditableComponent component = new EditableComponent(
				"editable",
				"<p><b>click me</b> and <i>edit me</i> with <font color=\"red\">tinymce</font> well ajax saving not ready but it is coming soon !!!</p>");
		component.setEscapeModelStrings(false);
		add(component);
	}

}
