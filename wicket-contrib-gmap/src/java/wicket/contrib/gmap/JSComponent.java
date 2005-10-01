package wicket.contrib.gmap;

import java.io.Serializable;

/**
 * @author Iulian-Corneliu COSTAN
 */
public abstract class JSComponent implements Serializable {

    private JSContainer container;

    JSContainer getContainer() {
        return container;
    }

    void setContainer(JSContainer container) {
        this.container = container;
    }

    protected abstract String toJavaScript();
}
