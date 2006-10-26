package contrib.wicket.cms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author Joe Toth (WeaZeLb0y)
 * 
 */

@Entity
@Table(name = "contents")
public class Content implements Serializable, Updatable {

	public final static Integer ROOT = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
	@Length(min = 1, max = 128)
	String name;

	// @NotNull
	// TODO: Only top most parent can be null, but the concept of "folders"
	// should really be replaced with "labels" so each piece of content can have
	// more than one parent (symlinking)
	@ManyToOne
	@JoinColumn(name = "folderId")
	Content folder;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	byte[] data;

	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "contentTypeId")
	ContentType contentType;

	// @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder")
	// @OrderBy("updatedDate")
	// private Set<Content> children = new HashSet<Content>();

	private Date updatedDate;

	public Content() {
	}

	public Content(Integer id) {
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

	// public Set<Content> getChildren() {
	// return children;
	// }
	//
	// public void setChildren(Set<Content> children) {
	// this.children = children;
	// }

	public Content getFolder() {
		return folder;
	}

	public void setFolder(Content folder) {
		this.folder = folder;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDataAsString(String data) {
		setData(data.getBytes());
	}

	public String getDataAsString() {
		return data == null ? "" : new String(data);
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}