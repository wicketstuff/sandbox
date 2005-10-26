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

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.Button;

import java.io.Serializable;

/**
 * @author Iulian-Corneliu COSTAN
 */
class Control implements Serializable {

    private Button button;
    private TinyMCESettings.Toolbar toolbar;
    private TinyMCESettings.Position position;

    Control(Button button, TinyMCESettings.Toolbar toolbar, TinyMCESettings.Position position) {
        this.button = button;
        this.toolbar = toolbar;
        this.position = position;
    }

    public Button getButton() {
        return button;
    }

    public TinyMCESettings.Toolbar getToolbar() {
        return toolbar;
    }

    public TinyMCESettings.Position getPosition() {
        return position;
    }
}
