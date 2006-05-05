package wicket.contrib.markup.html.yui.slider;

import java.io.Serializable;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.markup.html.PackageResourceReference;

public class SliderSettings implements Serializable 
{

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
     * Contructor for custom creation of slider styles, you can use getDefault() ti
     * get a default SliderSettings.
     */
    
    public SliderSettings() {
    }
    
    /**
     * returns a default SliderSettings. This returns all the deafult images for the 
     * slider, but does not include the sizes. at the time this is created, it only
     * knows the 6 images that make up this deafult looking slider.
     *  
     * @param leftUp
     * 				the left or up number of pixels
     * @param rightDown
     * 				the right or down number of pixels
     * @param tick
     * 				the number of pixels for each tick 
     * @return
     * 				a default look and feel slider bar
     */
    
    public static SliderSettings getDefault(int leftUp, int rightDown, int tick) 
    {
    	ResourceReference background 	= new PackageResourceReference(Slider.class, "style/bg.gif");
    	ResourceReference thumb 	 	= new PackageResourceReference(Slider.class, "style/thumb.png");
    	ResourceReference leftCorner 	= new PackageResourceReference(Slider.class, "style/left.gif");
    	ResourceReference leftTick 		= new PackageResourceReference(Slider.class, "style/left_tick.gif");
    	ResourceReference rightCorner 	= new PackageResourceReference(Slider.class, "style/right.gif");
    	ResourceReference rightTick 	= new PackageResourceReference(Slider.class, "style/right_tick.gif");
    	
    	SliderSettings defSettings = new SliderSettings();    	
    	defSettings.setResources(leftUp, rightDown, tick, background, thumb, leftCorner, leftTick, rightCorner, rightTick, false);
    	return defSettings;
    }
    
    /**
     * Aqua version. 
     * 
     * @param leftUp
     * 				the left or up number of pixels
     * @param rightDown
     * 				the right or down number of pixels
     * @param tick
     * 				the number of pixels for each tick 
     * @return
     * 				a default look and feel slider bar
     */
    public static SliderSettings getAqua(int leftUp, int rightDown, int tick) 
    {
    	ResourceReference background 	= new PackageResourceReference(Slider.class, "style/aqua/aqua_bg.png");
    	ResourceReference thumb 	 	= new PackageResourceReference(Slider.class, "style/aqua/aqua_thumb.gif");
    	ResourceReference leftCorner 	= new PackageResourceReference(Slider.class, "style/aqua/aqua_left.png");
    	ResourceReference leftTick 		= new PackageResourceReference(Slider.class, "style/aqua/aqua_left_tick.png");
    	ResourceReference rightCorner 	= new PackageResourceReference(Slider.class, "style/aqua/aqua_right.png");
    	ResourceReference rightTick 	= new PackageResourceReference(Slider.class, "style/aqua/aqua_right_tick.png");
    	
    	SliderSettings defSettings = new SliderSettings();    	
    	defSettings.setResources(leftUp, rightDown, tick, background, thumb, leftCorner, leftTick, rightCorner, rightTick, true);
    	return defSettings;
    }
    
    
    /**
     * sets the resources
     * 
     * @param background
     * @param thumb
     * @param leftCorner
     * @param leftTick
     * @param rightCorner
     * @param rightTick
     */
    
    public void setResources(int leftUp, int rightDown, int tick,
    						 ResourceReference background, ResourceReference thumb,
    						 ResourceReference leftCorner, ResourceReference leftTick,
    						 ResourceReference rightCorner, ResourceReference rightTick,
    						 boolean showTicks) 
    {
    	setShowTicks(showTicks);
    	/*
    	 * sets all the resources
    	 */
    	
    	setBackgroundResource(background);
    	setLeftCornerResource(leftCorner);
    	setRightCornerResource(rightCorner);
    	setLeftTickResource(leftTick);
    	setRightTickResource(rightTick);
    	setThumbResource(thumb);

    	/* 
    	 * calculate the width and height 
    	 */
    	
    	ImageResourceInfo bgInfo = new ImageResourceInfo(background);
    	ImageResourceInfo thumbInfo = new ImageResourceInfo(thumb);
    	
    	int height 		= bgInfo.getHeight();
        int thumbWidth 	= thumbInfo.getWidth();
        int left 		= leftUp - thumbWidth;
        int bgLength 	= leftUp + rightDown;
        String width 	= Integer.toString(bgLength - thumbWidth);
        
        setLeftUp(Integer.toString(leftUp));
        setRightDown(Integer.toString(rightDown));
        setTick(Integer.toString(tick));
        
    	/* background */
    	getBackground().add("background", "url(" + RequestCycle.get().urlFor(getBackgroundResource()) + ") repeat-x");
    	getBackground().add("height",     height + "px");
    	getBackground().add("width",      width + "px");
    	
    	/* handle */
    	getHandle().add("width",    thumbWidth + "px");
    	getHandle().add("height",   height + "px"); 
    	getHandle().add("top",      "0px");
    	getHandle().add("left",     left + "px");
    	
    	/* thumb */
    	getThumb().add("background",        	"url(" + RequestCycle.get().urlFor(getThumbResource()) + ") no-repeat");
    	getThumb().add("background-position", 	"center");
    	getThumb().add("height",            	"100%");
    	getThumb().add("width",             	thumbWidth + "px");
    }
    
    /*
     * Accessors  
     *
     */
    
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
