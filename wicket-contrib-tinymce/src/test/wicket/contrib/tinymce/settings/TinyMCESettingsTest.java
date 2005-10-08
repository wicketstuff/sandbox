package wicket.contrib.tinymce.settings;

import junit.framework.TestCase;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCESettingsTest extends TestCase {

    private TinyMCESettings settings;
    private StringBuffer buffer;

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
        settings.addHorizontalLocation(buffer);

        assertTrue(buffer.capacity() > 0);
        assertEquals(",\n\ttheme_advanced_resize_horizontal : true", buffer.toString());
    }

    //todo more tests

    protected void setUp() throws Exception {
        settings = new TinyMCESettings();
        buffer = new StringBuffer();
    }

    protected void tearDown() throws Exception {
        settings = null;
        buffer = null;
    }
}
