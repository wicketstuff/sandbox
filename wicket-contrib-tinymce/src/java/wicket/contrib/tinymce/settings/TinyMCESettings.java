/*
 * Copyright (C) 2005 Iulian-Corneliu Costan
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package wicket.contrib.tinymce.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.set.ListOrderedSet;

/**
 * Settings class for TinyMCE editor. User can add/remove buttons,
 * enable/disable resizing, change positions, orientation, alignment and much
 * more.
 * 
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille Jensen (fbille@avaleo.net)
 * @see Plugin
 * @see Button
 */
public class TinyMCESettings implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Mode mode;
	private Theme theme;
	private Location toolbarLocation;
	private Location statusbarLocation;
	private Align toolbarAlign;
	private boolean verticalResizing;
	private boolean horizontalResizing;

	private Set<Plugin> plugins;
	private List<Control> controls;

	private Set<Button> disabledButtons;

	/**
	 * Construct default tinymce settings.
	 */
	public TinyMCESettings()
	{
		this(Mode.textareas, Theme.simple);
	}

	/**
	 * Construct tinymce settings with specific mode.
	 * 
	 * @param mode
	 *            the tinycemce's mode
	 */
	public TinyMCESettings(Mode mode)
	{
		this(mode, Theme.simple);
	}

	/**
	 * Construct tinymce settings based on specific theme.
	 * 
	 * @param theme
	 *            the tinymce theme
	 */
	public TinyMCESettings(Theme theme)
	{
		this(Mode.textareas, theme);
	}

	/**
	 * Construct tinymce settings by specifing mode and theme.
	 * 
	 * @param mode
	 *            the tinymce mode
	 * @param theme
	 *            the tinymce theme
	 */
	public TinyMCESettings(Mode mode, Theme theme)
	{
		this.mode = mode;
		this.theme = theme;
		this.controls = new LinkedList<Control>();
		this.plugins = new ListOrderedSet();
		this.disabledButtons = new ListOrderedSet();
	}

	/**
	 * Specifies the location of the toolbar.
	 * 
	 * @param toolbarLocation
	 */
	public void setToolbarLocation(Location toolbarLocation)
	{
		this.toolbarLocation = toolbarLocation;
	}

	/**
	 * Specifies the location of the status bar.
	 * 
	 * @param statusbarLocation
	 */
	public void setStatusbarLocation(Location statusbarLocation)
	{
		this.statusbarLocation = statusbarLocation;
	}

	/**
	 * Specifies the alignment of toolbar.
	 * 
	 * @param toolbarAlign
	 */
	public void setToolbarAlign(Align toolbarAlign)
	{
		this.toolbarAlign = toolbarAlign;
	}

	/**
	 * Enable/Disable vertical resizing.
	 * 
	 * @param resizing
	 */
	public void setVerticalResizing(boolean resizing)
	{
		this.verticalResizing = resizing;
	}

	/**
	 * Enable/Disable horizontal resizing.
	 * 
	 * @param horizontalResizing
	 */
	public void setHorizontalResizing(boolean horizontalResizing)
	{
		this.horizontalResizing = horizontalResizing;
	}

	/**
	 * Add a default button to tinymce editor. These plugins are defined by
	 * tinymce editor and are ready to use.
	 * 
	 * @param button -
	 *            button to be added
	 * @param toolbar -
	 *            the toolbar where to add this button to
	 * @param position -
	 *            position of this button
	 */
	public void add(Button button, Toolbar toolbar, Position position)
	{
		controls.add(new Control(button, toolbar, position));
	}

	/**
	 * Allow users to add plugin button. Plugin buttons are defined by tinymce
	 * plugins and these plugins have to be registered. Fortunately, when new
	 * plugin button is added, the plugin that defines the button is
	 * automatically registered within tinymce editor.
	 * 
	 * @param button -
	 *            button to be added
	 * @param toolbar -
	 *            the toolbar where to add this button to
	 * @param position -
	 *            position of this button
	 */
	public void add(PluginButton button, Toolbar toolbar, Position position)
	{
		register(button.getPlugin());
		controls.add(new Control(button, toolbar, position));
	}

	/**
	 * Disable specific button in advanced theme mode.
	 * 
	 * @param button
	 *            button to be disabled
	 */
	public void disableButton(Button button)
	{
		disabledButtons.add(button);
	}

	/**
	 * Register a tinymce plugin. In order to reuse a existing plugin it has to
	 * be registered before. Usually plugins are registered automatically when a
	 * plugin button is added, but there are some plugins that contains no
	 * buttons. This method is used to register those plugins. (eg AutoSave)
	 * 
	 * @param plugin
	 */
	public void register(Plugin plugin)
	{
		plugins.add(plugin);
	}

	// used in testing
	Set getPlugins()
	{
		return plugins;
	}

	/**
	 * todo change access
	 * @return JavaScript string
	 */
	public String toJavaScript()
	{
		StringBuffer buffer = new StringBuffer();

		// mode
		buffer.append("\n\tmode : ").append("\"").append(mode.name()).append("\"");
		// theme
		buffer.append(",\n\t").append("theme : ").append("\"").append(theme.name()).append("\"");

		if (Theme.advanced.equals(theme))
		{
			appendAdvancedSettings(buffer);
		}

		appendPluginSettings(buffer);

		return buffer.toString();
	}

	private void appendPluginSettings(StringBuffer buffer)
	{
		if (plugins.size() > 0)
		{
			Iterator iterator = plugins.iterator();
			while (iterator.hasNext())
			{
				Plugin plugin = (Plugin)iterator.next();
				plugin.definePluginSettings(buffer);
			}
		}
	}

	/**
	 * @return JavaScript string to load custom tinymce plugin 
	 */
	public String getLoadPluginJavaScript()
	{
		StringBuffer loadPluginJavaScript = new StringBuffer();

		if (plugins.size() > 0)
		{
			Iterator iterator = plugins.iterator();
			while (iterator.hasNext())
			{
				Plugin plugin = (Plugin)iterator.next();
				String pluginPath = plugin.getPluginPath();

				if (pluginPath != null && pluginPath.equals("") == false)
				{
					loadPluginJavaScript.append("tinyMCE.loadPlugin('");
					loadPluginJavaScript.append(plugin.getName());
					loadPluginJavaScript.append("','");
					loadPluginJavaScript.append(pluginPath);
					loadPluginJavaScript.append("');\n");
				}
			}
		}

		return loadPluginJavaScript.toString();
	}

	/**
	 * Get additional javascript from the plugins.
	 * 
	 * @return The different plugins additional javascript.
	 */
	public String getAdditionalPluginJavaScript()
	{
		StringBuffer buffer = new StringBuffer();

		if (plugins.size() > 0)
		{
			Iterator iterator = plugins.iterator();
			while (iterator.hasNext())
			{
				Plugin plugin = (Plugin)iterator.next();
				plugin.definePluginExtensions(buffer);
			}
		}

		return buffer.toString();
	}

	private void appendAdvancedSettings(StringBuffer buffer)
	{
		// disable buttons
		addDisabledButtons(buffer);

		// plugins
		addPlugins(buffer);

		// add additional controls
		addButtons1_Before(buffer);
		addButtons1_After(buffer);
		addButtons2_Before(buffer);
		addButtons2_After(buffer);
		addButtons3_Before(buffer);
		addButtons3_After(buffer);
		addButtons4(buffer);

		// toolbar, statusbar
		addToolbarLocation(buffer);
		addStatusbarLocation(buffer);
		addToolbarAlign(buffer);

		// resizing
		addVerticalResizing(buffer);
		addHorizontalResizing(buffer);
	}

	void addPlugins(StringBuffer buffer)
	{
		if (plugins.size() > 0)
		{
			String value = namedObjectAsString(plugins);
			buffer.append(",\n\t").append("plugins : ").append("\"").append(value).append("\"");
		}
	}

	private void addButtons1_Before(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.first, Position.before);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons1_add_before : ").append("\"")
					.append(controlsAsString(result)).append("\"");
		}
	}

	private void addButtons1_After(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.first, Position.after);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons1_add : ").append("\"").append(
					controlsAsString(result)).append("\"");
		}
	}

	private void addButtons2_Before(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.second, Position.before);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons2_add_before: ").append("\"")
					.append(controlsAsString(result)).append("\"");
		}
	}

	private void addButtons2_After(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.second, Position.after);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons2_add : ").append("\"").append(
					controlsAsString(result)).append("\"");
		}
	}

	private void addButtons3_Before(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.third, Position.before);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons3_add_before : ").append("\"")
					.append(controlsAsString(result)).append("\"");
		}
	}

	private void addButtons3_After(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.third, Position.after);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons3_add : ").append("\"").append(
					controlsAsString(result)).append("\"");
		}
	}

	private void addButtons4(StringBuffer buffer)
	{
		ControlPredicate predicate = new ControlPredicate(Toolbar.fourth, Position.before);
		Collection<Control> result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons4 : ").append("\"").append(
					controlsAsString(result)).append("\"");
		}

		predicate = new ControlPredicate(Toolbar.fourth, Position.after);
		result = CollectionUtils.select(controls, predicate);
		if (result.size() > 0)
		{
			buffer.append(",\n\t").append("theme_advanced_buttons4 : ").append("\"").append(
					controlsAsString(result)).append("\"");
		}
	}

	void addDisabledButtons(StringBuffer buffer)
	{
		if (disabledButtons.size() > 0)
		{
			String value = namedObjectAsString(disabledButtons);
			buffer.append(",\n\t").append("theme_advanced_disable : ").append("\"").append(value)
					.append("\"");
		}
	}

	private String controlsAsString(Collection<Control> controls)
	{
		List<Button> buttons = new ArrayList<Button>();
		Iterator iterator = controls.iterator();
		while (iterator.hasNext())
		{
			Control control = (Control)iterator.next();
			buttons.add(control.getButton());
		}
		return namedObjectAsString(buttons);
	}

	private String namedObjectAsString(Collection<? extends NamedObject> collection)
	{
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = collection.iterator();
		while (iterator.hasNext())
		{
			NamedObject namedObject = (NamedObject)iterator.next();
			if (buffer.length() > 0)
			{
				buffer.append(", ");
			}
			buffer.append(namedObject.getName());
		}
		return buffer.toString();
	}

	void addHorizontalResizing(StringBuffer buffer)
	{
		String value = horizontalResizing ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
		buffer.append(",\n\t").append("theme_advanced_resize_horizontal : ").append(value);
	}

	void addVerticalResizing(StringBuffer buffer)
	{
		String value = verticalResizing ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
		buffer.append(",\n\t").append("theme_advanced_resizing : ").append(value);
	}

	void addToolbarAlign(StringBuffer buffer)
	{
		if (toolbarAlign != null)
		{
			buffer.append(",\n\t").append("theme_advanced_toolbar_align : ").append("\"").append(
					toolbarAlign.name()).append("\"");
		}
	}

	void addToolbarLocation(StringBuffer buffer)
	{
		if (toolbarLocation != null)
		{
			buffer.append(",\n\t").append("theme_advanced_toolbar_location : ").append("\"")
					.append(toolbarLocation.name()).append("\"");
		}
	}

	void addStatusbarLocation(StringBuffer buffer)
	{
		if (statusbarLocation != null)
		{
			buffer.append(",\n\t").append("theme_advanced_statusbar_location : ").append("\"")
					.append(statusbarLocation.name()).append("\"");
		}
	}

	private class ControlPredicate implements Predicate
	{

		private Toolbar toolbar;
		private Position position;

		ControlPredicate(Toolbar toolbar, Position position)
		{
			this.toolbar = toolbar;
			this.position = position;
		}

		/**
		 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object object)
		{
			Control control = (Control)object;
			return toolbar.equals(control.getToolbar()) && position.equals(control.getPosition());
		}
	}

	/**
	 * This enum specifies how elements is to be converted into TinyMCE WYSIWYG
	 * editor instances. This option can be set to any of the values below:
	 * <ul>
	 * <li>textareas - converts all textarea elements to editors when the page
	 * loads.</li>
	 * <li>exact - exact - Converts only explicit elements, these are listed in
	 * the elements option.</li>
	 * <li>specific_textares - Converts all textarea elements with the a
	 * textarea_trigger attribute set to "true".</li>
	 * </ul>
	 * At this moment, only <b>textareas</b> mode is supported.
	 */
	public enum Mode
	{
		textareas;
	}

	/**
	 * This class enables you to specify what theme to use when rendering the
	 * TinyMCE WYSIWYG editor instances. Two themes are supported:
	 * <ul>
	 * <li>simple - This is the most simple theme for TinyMCE it contains only
	 * the basic functions.</li>
	 * <li>advanced - This theme enables users to add/remove buttons and panels .</li>
	 * </ul>
	 */
	public enum Theme
	{
		simple,
		advanced;
	}

	/**
	 * This enum enables you to specify where the toolbar should be located.
	 * This value can be top or bottom.
	 */
	public enum Location
	{
		top,
		bottom;
	}

	/**
	 * This enum enables you to specify the alignment of the controls. This
	 * value can be left, right or center the default value is center.
	 */
	public enum Align
	{
		left,
		center,
		right;
	}

	/**
	 * This enum specifies the position of new added control. It can be before
	 * or after existing elements.
	 */
	public enum Position
	{
		before,
		after;
	}

	/**
	 * This class specifices the toolbar to add specific control to. TinyMCE
	 * editor defines three toolbars named: first, second, third.
	 */
	public enum Toolbar
	{
		first,
		second,
		third,
		fourth;
	}

	// default tinymce buttons
	public static final Button bold = new Button("bold");
	public static final Button italic = new Button("italic");
	public static final Button underline = new Button("underline");
	public static final Button strikethrough = new Button("strikethrough");
	public static final Button justifyleft = new Button("justifyleft");
	public static final Button justifycenter = new Button("justifycenter");
	public static final Button justifyright = new Button("justifyright");
	public static final Button justifyfull = new Button("justifyfull");
	public static final Button styleselect = new Button("styleselect");
	public static final Button formatselect = new Button("formatselect");
	public static final Button bullist = new Button("bullist");
	public static final Button numlist = new Button("numlist");
	public static final Button outdent = new Button("outdent");
	public static final Button indent = new Button("indent");
	public static final Button undo = new Button("undo");
	public static final Button redo = new Button("redo");
	public static final Button link = new Button("link");
	public static final Button unlink = new Button("unlink");
	public static final Button anchor = new Button("anchor");
	public static final Button image = new Button("image");
	public static final Button cleanup = new Button("cleanup");
	public static final Button help = new Button("help");
	public static final Button code = new Button("code");
	public static final Button hr = new Button("hr");
	public static final Button removeformat = new Button("removeformat");
	public static final Button visualaid = new Button("visualaid");
	public static final Button sub = new Button("sub");
	public static final Button sup = new Button("sup");
	public static final Button charmap = new Button("charmap");
	public static final Button separator = new Button("separator");

	// others buttons added by plugins
	public static final Button newdocument = new Button("newdocument");
	public static final Button cut = new Button("cut");
	public static final Button copy = new Button("copy");
	public static final Button fontselect = new Button("fontselect");
	public static final Button fontsizeselect = new Button("fontsizeselect");
	public static final Button forecolor = new Button("forecolor");
	public static final Button backcolor = new Button("backcolor");

}
