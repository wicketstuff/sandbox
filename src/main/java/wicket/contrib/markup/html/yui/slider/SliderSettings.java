package wicket.contrib.markup.html.yui.slider;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.InlineStyle;
import wicket.markup.html.PackageResource;
import wicket.markup.html.PackageResourceReference;

public class SliderSettings {

    private static final long serialVersionUID = 1L;
    
    /* Resources needed to generate the slider bar */
    private ResourceReference rightCornerResource;
    private ResourceReference leftCornerResource;
    private ResourceReference rightTickResource;
    private ResourceReference leftTickResource;
    private ResourceReference backgroundResource;
    private ResourceReference thumbResource;
    
    private InlineStyle background = new InlineStyle();
    private InlineStyle handle = new InlineStyle();
    private InlineStyle thumb = new InlineStyle();
    
    private String leftUp;
    private String rightDown;
    private String tick;
    private String tickSize;
    
    private boolean showTicks = false;
    
    /**
     * Contructor 
     * TODO : allow for dynamic creation of other styles...
     */
    
    public SliderSettings() {
    }
    
    /**
     * Default Style constructor. If no Style specified, creates a default style slider
     * 
     * @param leftUp
     * @param rightDown
     * @param tick
     */
    
    public SliderSettings(Integer leftUp, Integer rightDown, Integer tick) {

        int thumbWidth = 18;
        int left = leftUp.intValue() - thumbWidth;
        int height = 25;
        int bgLength = leftUp.intValue() + rightDown.intValue();
        
        String width = Integer.toString(bgLength - thumbWidth);
        
        setLeftUp(leftUp.toString());
        setRightDown(rightDown.toString());
        setTick(tick.toString());
        
        /* background */
        setBackgroundResource(new PackageResourceReference(Slider.class, "style/bg.gif"));
        getBackground().add("background", "url(" + RequestCycle.get().urlFor(getBackgroundResource()) + ") repeat-x");
        getBackground().add("height",     "25px");
        getBackground().add("width",      width + "px");
        
        /* left */
        setLeftCornerResource(new PackageResourceReference(Slider.class, "style/left.gif"));
        setLeftTickResource(new PackageResourceReference( Slider.class, "style/left_tick.gif"));
        
        /* right */
        setRightCornerResource(new PackageResourceReference( Slider.class, "style/right.gif"));
        setRightTickResource(new PackageResourceReference( Slider.class, "style/right_tick.gif"));
        
        /* handle */
        getHandle().add("width",    thumbWidth + "px");
        getHandle().add("height",   height + "px"); 
        getHandle().add("top",      "0px");
        getHandle().add("left",     left + "px");
        
        /* thumb */
        setThumbResource(new PackageResourceReference(Slider.class, "style/thumb.png"));
        getThumb().add("background",        "url(" + RequestCycle.get().urlFor(getThumbResource()) + ") no-repeat");
        getThumb().add("background-position", "center");
        getThumb().add("height",            "100%");
        getThumb().add("width",             thumbWidth + "px");
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

    public ResourceReference getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(ResourceReference backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

    public ResourceReference getLeftCornerResource() {
        return leftCornerResource;
    }

    public void setLeftCornerResource(ResourceReference leftCornerResource) {
        this.leftCornerResource = leftCornerResource;
    }

    public ResourceReference getRightCornerResource() {
        return rightCornerResource;
    }

    public void setRightCornerResource(ResourceReference rightCornerResource) {
        this.rightCornerResource = rightCornerResource;
    }

    public ResourceReference getThumbResource() {
        return thumbResource;
    }

    public void setThumbResource(ResourceReference thumbResource) {
        this.thumbResource = thumbResource;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public ResourceReference getLeftTickResource() {
        return leftTickResource;
    }

    public void setLeftTickResource(ResourceReference leftTickResource) {
        this.leftTickResource = leftTickResource;
    }

    public ResourceReference getRightTickResource() {
        return rightTickResource;
    }

    public void setRightTickResource(ResourceReference rightTickResource) {
        this.rightTickResource = rightTickResource;
    }

	public boolean isShowTicks() {
		return showTicks;
	}

	public void setShowTicks(boolean showTicks) {
		this.showTicks = showTicks;
	}

}
