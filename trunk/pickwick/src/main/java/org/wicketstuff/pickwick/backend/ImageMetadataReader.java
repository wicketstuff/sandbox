package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * A metadata reader reading metadata from image file
 * @author Vincent Demay
 *
 */
public class ImageMetadataReader {
	private String filePath;
	private Map<String, String> metadata;

	public ImageMetadataReader(String filePath) {
		super();
		this.filePath = filePath;
		this.metadata = new HashMap<String, String>();
	}
	
	public Map<String, String> getMetadata(){
		readAndFillMetadata();
		return metadata;
	}
	
	private void readAndFillMetadata() {
        try {

            File file = new File(this.filePath);
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    fillMetadata(metadata.getAsTree(names[i]), "", true);
                }
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void fillMetadata(Node node, String root, boolean ignorePrefix) {
        // print open tag of element
    	if (!"".equals(root)){
    		root = root + "." + node.getNodeName();
    	}else if (!ignorePrefix){
    		root = node.getNodeName();
    	}
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                String property = attr.getNodeName();
                String attribute = attr.getNodeValue();
                metadata.put(root + "." + property, attribute);
            }
        }

        Node child = node.getFirstChild();
        while (child != null) {
            // get children recursively
            fillMetadata(child, root, false);
            child = child.getNextSibling();
        }
    }
}
