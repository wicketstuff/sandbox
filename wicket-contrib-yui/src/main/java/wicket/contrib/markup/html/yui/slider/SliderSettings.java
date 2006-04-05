package wicket.contrib.markup.html.yui.slider;

import wicket.contrib.InlineStyle;
import wicket.markup.html.PackageResource;

public class SliderSettings {

    private static final long serialVersionUID = 1L;
    
    private PackageResource rightCornerResource;
    private PackageResource leftCornerResource;
    
    private InlineStyle background = new InlineStyle();
    private InlineStyle handle = new InlineStyle();
    private InlineStyle thumb = new InlineStyle();
    
    private String leftUp;
    private String rightDown;
    private String tick;

    /**
     * Contructor 
     * TODO : allow for dynamic creation of other styles...
     */
    
    public SliderSettings() {
    }
    
    /**
     * Default Style constructor
     * 
     * @param leftUp
     * @param rightDown
     * @param tick
     */
    
    public SliderSettings(Integer leftUp, Integer rightDown, Integer tick) {

        setLeftUp(leftUp.toString());
        setRightDown(rightDown.toString());
        setTick(tick.toString());
        
        String width = Integer.toString(leftUp.intValue() + rightDown.intValue());
        
        /* background */
        getBackground().add("background", "url(" + PackageResource.get(Slider.class, "style/bg.gif").getResourceStream() + ")");
        getBackground().add("background-repeat", "repeat-x");
        getBackground().add("height", "15px");
        getBackground().add("width", width + "px");
        
        /* left */
        setLeftCornerResource(PackageResource.get(Slider.class, "style/left.gif"));
        
        /* right */
        setRightCornerResource(PackageResource.get( Slider.class, "style/right.gif"));
        
        /* handle */
        getHandle().add("width", "18px");
        getHandle().add("height", "15px"); 
        getHandle().add("top", "0px");
        getHandle().add("left", "-15px"); // TODO : calculate ??
        
        /* thumb */
        getThumb().add("background", "url(" + PackageResource.get(Slider.class, "style/thumb.png").getResourceStream() + ")");
        getThumb().add("background-repeat", "no-repeat");
        getThumb().add("height", "15px");
        getThumb().add("width", "18px");
    }

    public InlineStyle getBackground() {
        return background;
    }

    public void setBackground(InlineStyle background) {
        this.background = background;
    }

    public InlineStyle getHandle() {
        return handle;
    }

    public void setHandle(InlineStyle handle) {
        this.handle = handle;
    }

    public InlineStyle getThumb() {
        return thumb;
    }

    public void setThumb(InlineStyle thumb) {
        this.thumb = thumb;
    }

    public PackageResource getLeftCornerResource() {
        return leftCornerResource;
    }

    public void setLeftCornerResource(PackageResource leftCornerResource) {
        this.leftCornerResource = leftCornerResource;
    }

    public PackageResource getRightCornerResource() {
        return rightCornerResource;
    }

    public void setRightCornerResource(PackageResource rightCornerResource) {
        this.rightCornerResource = rightCornerResource;
    }

    public String getLeftUp() {
        return leftUp;
    }

    public void setLeftUp(String leftUp) {
        this.leftUp = leftUp;
    }

    public String getRightDown() {
        return rightDown;
    }

    public void setRightDown(String rightDown) {
        this.rightDown = rightDown;
    }

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }
}
