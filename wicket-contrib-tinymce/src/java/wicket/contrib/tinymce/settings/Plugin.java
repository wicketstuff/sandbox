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

import java.io.Serializable;

/**
 * @author Iulian-Corneliu COSTAN
 */
class Plugin extends Enum {

    public static final Plugin advhr = new Plugin("advhr");
    public static final Plugin advimage = new Plugin("advimage");
    public static final Plugin advlink = new Plugin("advlink");
    public static final Plugin contextmenu = new Plugin("contextmenu");
    public static final Plugin emotions = new Plugin("emotions");
    public static final Plugin flash = new Plugin("flash");
    public static final Plugin autosave = new Plugin("autosave");
    public static final Plugin iespell = new Plugin("iespell");
    public static final Plugin insertdatetime = new Plugin("insertdatetime");
    public static final Plugin paste = new Plugin("paste");
    public static final Plugin preview = new Plugin("preview");
    public static final Plugin print = new Plugin("print");
    public static final Plugin save = new Plugin("save");
    public static final Plugin noneditable = new Plugin("noneditable");
    public static final Plugin searchreplace = new Plugin("searchreplace");
    public static final Plugin table = new Plugin("table");
    public static final Plugin zoom = new Plugin("zoom");
    public static final Plugin directinality = new Plugin("directinality");
    public static final Plugin fullscreen = new Plugin("fullscreen");
    public static final Plugin inlinepopups = new Plugin("inlinepopups");

    private Plugin(String name) {
        super(name);
    }
}
