package wicket.contrib.scriptaculous;


public class JavascriptBuilder {
	private StringBuffer buffer = new StringBuffer();

	public void addLine(String line) {
		buffer.append(line).append("\n");
	}
	public String buildScriptTagString() {
		return "\n<script type=\"text/javascript\">\n" +
		buffer.toString() +
		"</script>\n";
	}
}
