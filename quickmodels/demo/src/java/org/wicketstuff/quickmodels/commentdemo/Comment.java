package org.wicketstuff.quickmodels.commentdemo;

import java.io.Serializable;
import java.util.Date;

/**
 * Sample pojo to store in the database.
 *
 * @author Tim Boudreau
 */
public class Comment implements Serializable {
    private final Date date;
    private String name;
    private String comment;
    
    public Comment() {
        date = new Date();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDate() {
        return date;
    }
}
