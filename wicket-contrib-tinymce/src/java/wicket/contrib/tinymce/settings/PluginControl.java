package wicket.contrib.tinymce.settings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class PluginControl extends Control {

    private Plugin plugin;

    public PluginControl(PluginButton button, Toolbar toolbar, Position position) {
        super(button, toolbar, position);
        this.plugin = button.getPlugin();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public enum PluginButton implements Button {

        save(Plugin.save),
        zoom(Plugin.zoom);

        private Plugin plugin;

        PluginButton(Plugin plugin) {
            this.plugin = plugin;
        }

        public Plugin getPlugin() {
            return plugin;
        }

        public String getName() {
            return name();
        }
    }
}
