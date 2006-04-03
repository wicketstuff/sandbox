package contrib.wicket.cms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author Joe Toth (WeaZeLb0y)
 * 
 */

@Entity
@Table(name = "contentHistories")
public class ContentHistory implements Serializable, Creatable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@NotNull
	@Length(min = 1, max = 128)
	String name;

	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "contentId")
	Content content;

	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "folderId")
	Content folder;

	@NotNull
	boolean isFolder;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	byte[] data;

	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "contentTypeId")
	ContentType contentType;

	private Date createdDate;

	public ContentHistory() {
	}

	public ContentHistory(Integer id) {
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

	public boolean getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}