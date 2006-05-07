package wicket.contrib.tinymce.settings;

import junit.framework.TestCase;
import wicket.contrib.tinymce.settings.SavePlugin;
import wicket.contrib.tinymce.settings.DateTimePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCESettingsTest extends TestCase {

    private TinyMCESettings settings;
    private StringBuffer buffer;

    protected void setUp() throws Exception {
        settings = new TinyMCESettings();
        buffer = new StringBuffer();
    }

    protected void tearDown() throws Exception {
        settings = null;
        buffer = null;
    }

    public void testAddStatusbarLocation() throws Exception {
        settings.setStatusbarLocation(TinyMCESettings.Location.top);
        settings.addStatusbarLocation(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_statusbar_location : \"top\"", buffer.toString());
    }

    public void testAddToolbarLocation() throws Exception {
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.addToolbarLocation(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_toolbar_location : \"top\"", buffer.toString());
    }

    public void testAddToolbarAlign() throws Exception {
        settings.setToolbarAlign(TinyMCESettings.Align.left);
        settings.addToolbarAlign(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_toolbar_align : \"left\"", buffer.toString());
    }

    public void testAddVerticalResizing() throws Exception {
        settings.setVerticalResizing(true);
        settings.addVerticalResizing(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_resizing : true", buffer.toString());
    }

    public void testAddHorizontalResizing() throws Exception {
        settings.setHorizontalResizing(true);
        settings.addHorizontalResizing(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_resize_horizontal : true", buffer.toString());
    }

    public void testAddPlugins() throws Exception {
        SavePlugin savePlugin = new SavePlugin();
        DateTimePlugin dateTimePlugin = new DateTimePlugin();

        settings.register(savePlugin);
        settings.register(dateTimePlugin);

        settings.addPlugins(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\tplugins : \"save, insertdatetime\"", buffer.toString());
    }

    public void testAdd() throws Exception {
        SavePlugin savePlugin = new SavePlugin();
        settings.register(savePlugin);
        assertNotNull(settings.getPlugins());
        assertEquals(1, settings.getPlugins().size());
        settings.add(savePlugin.getSaveButton(), TinyMCESettings.Toolbar.first, TinyMCESettings.Position.before);
        assertEquals(1, settings.getPlugins().size());
    }
    
    public void testDisableButton1() {
    	settings.disableButton(TinyMCESettings.bold);
    	
    	settings.addDisabledButtons(buffer);
    	
    	assertTrue(buffer.capacity() > 0);
    	assertEquals(",\n\ttheme_advanced_disable : \"bold\"", buffer.toString());
    }
    
    public void testDisableButton2() {
    	settings.disableButton(TinyMCESettings.bold);
    	settings.disableButton(TinyMCESettings.italic);
    	
    	settings.addDisabledButtons(buffer);
    	
    	assertTrue(buffer.capacity() > 0);
    	assertEquals(",\n\ttheme_advanced_disable : \"bold, italic\"", buffer.toString());
    }
}
