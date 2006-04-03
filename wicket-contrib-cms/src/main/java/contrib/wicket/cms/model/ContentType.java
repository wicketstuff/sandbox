package contrib.wicket.cms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contentTypes")
public class ContentType implements Serializable {

	public final static Integer FOLDER = 1;
	
	public final static Integer UNKNOWN = 2;
	
	public final static Integer TEXT = 3;

	public final static Integer HTML = 4;

	public final static Integer GIF = 5;

	public final static Integer JPG = 6;

	public final static Integer PNG = 7;

	public final static Integer FLASH = 8;

	public final static Integer PDF = 9;

	@Id
	private Integer id;

	private String name;

	public ContentType() {
	}

	public ContentType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
