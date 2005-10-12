/*
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public static class PluginButton extends Button {

        public static final PluginButton save = new PluginButton("save", Plugin.save);
        public static final PluginButton zoom = new PluginButton("zoom", Plugin.zoom);

        private Plugin plugin;

        private PluginButton(String name, Plugin plugin) {
            super(name);
            this.plugin = plugin;
        }

        public Plugin getPlugin() {
            return plugin;
        }
    }
}
