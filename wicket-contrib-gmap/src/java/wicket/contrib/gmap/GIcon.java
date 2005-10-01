package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GIcon {

    private String image;
    private GPoint anchor;

    public GIcon(String image, GPoint anchor) {
        this.image = image;
        this.anchor = anchor;
    }

    public String getImage() {
        return image;
    }

    public GPoint getAnchor() {
        return anchor;
    }
}
