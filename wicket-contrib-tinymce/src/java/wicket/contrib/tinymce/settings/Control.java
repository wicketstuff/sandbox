package wicket.contrib.tinymce.settings;

import java.io.Serializable;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class Control implements Serializable {

    private Button button;
    private Toolbar toolbar;
    private Position position;

    public Control(Button button, Toolbar toolbar, Position position) {
        this.button = button;
        this.toolbar = toolbar;
        this.position = position;
    }

    public Button getButton() {
        return button;
    }

    public Control.Toolbar getToolbar() {
        return toolbar;
    }

    public Control.Position getPosition() {
        return position;
    }

    public enum DefaultButton implements Button {

        bold,
        italic,
        underline,
        strikethrough,
        justifyleft,
        justifycenter,
        justifyright,
        justifyfull,
        styleselect,
        formatselect,
        bullist,
        numlist,
        outdent,
        indent,
        undo,
        redo,
        link,
        unlink,
        anchor,
        image,
        cleanup,
        help,
        code,
        hr,
        removeformat,
        visualaid,
        sub,
        sup,
        charmap,
        separator;

        public String getName() {
            return name();
        }
    }

    public enum Position {

        before,
        after;
    }

    public enum Toolbar {

        first,
        second,
        third;
    }
}
