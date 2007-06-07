package org.wicketstuff.pickwick;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.application.WildcardMatcherHelper;

public class ImageList implements Serializable{
    private List imageList;

    public Object get(int index) {
        return imageList.get(index);
    }

    public Iterator iterator() {
        return imageList.iterator();
    }

    public int size() {
        return imageList.size();
    }
    
    public ImageList(String pattern, File directory) {
        imageList = new ArrayList();
        File[] files = directory.listFiles();
        if (files == null) {
            throw new RuntimeException("Not a directory: " + directory);
        }
        for(int i=0; i<files.length; i++) {
            File file = files[i];
            if (WildcardMatcherHelper.match(pattern, file.getName()) != null) {
                try {
                    imageList.add(ImageUtils.getImageProperties(file));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
