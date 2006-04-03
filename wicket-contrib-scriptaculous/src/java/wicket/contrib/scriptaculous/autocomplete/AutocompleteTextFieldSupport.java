package wicket.contrib.scriptaculous.autocomplete;

import wicket.ResourceReference;
import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.form.TextField;
import wicket.markup.html.internal.HtmlHeaderContainer;

/**
 * support class for all autocomplete text fields. handles binding of needed css
 * and javascript.
 * 
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class AutocompleteTextFieldSupport extends TextField {

        private static final long serialVersionUID = 1L;

        /**
         * Construct.
         * 
         * @param id
         */
        public AutocompleteTextFieldSupport(String id) {
                super(id);
                add(ScriptaculousAjaxHandler.newJavascriptBindingHandler());
        }

        /**
         * @see wicket.Component#onComponentTag(wicket.markup.ComponentTag)
         */
        protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("id", getId());
                tag.put("autocomplete", "off");
        }

        /**
         * @see wicket.Component#renderHead(wicket.markup.html.internal.HtmlHeaderContainer)
         */
        public void renderHead(HtmlHeaderContainer container) {
                super.renderHead(container);

                addCssReference(container, getCss());
        }

        private void addCssReference(HtmlHeaderContainer container,
                        ResourceReference ref) {
        	CharSequence url = container.getPage().urlFor(ref);
            write(container, "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
            write(container, url);
            write(container, "\"/>\n");
        }

        /**
         * Writes the given string to the header container.
         * 
         * @param container
         *                the header container
         * @param s
         *                the string to write
         */
        private void write(HtmlHeaderContainer container, CharSequence s) {
                container.getResponse().write(s);
        }

        protected ResourceReference getCss() {
                return new PackageResourceReference(
                                AutocompleteTextFieldSupport.class, "style.css");
        }

        /**
         * adds a placeholder div where auto completion results will be
         * populated.
         */
        protected void onRender(MarkupStream markupStream) {
                super.onRender(markupStream);

                getResponse().write(
                                "<div class=\"auto_complete\" id=\""
                                                + getAutocompleteId()
                                                + "\"></div>");
        }

        protected final String getAutocompleteId() {
                return getId() + "_autocomplete";
        }
}
