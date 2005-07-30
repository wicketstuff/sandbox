package wicket.examples.cdapp2.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Track implements Comparable<Track> {
	private Integer id;
	private Album album;
	private Integer number;
	private String title;
	private Integer length;
	
	// ********************** Constructors ********************** //
	
	public Track() {}
	
	public Track(Album album, Integer number, String title, Integer length) {
		this.album = album;
		this.number = number;
		this.title = title;
		this.length = length;
	}

	// ********************** Common Methods ********************** //
	
	public int compareTo(Track rhs) {
		return new CompareToBuilder()
			.append(getNumber(), rhs.getNumber())
			.append(getTitle(), rhs.getTitle())
			.append(getLength(), rhs.getLength())
			.toComparison();
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof Track) {
			return compareTo((Track) rhs) == 0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getNumber())
			.append(getTitle())
			.append(getLength())
			.toHashCode();
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
	// ********************** Accessor Methods ********************** //
	
	@Id(generate = GeneratorType.AUTO)
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	@ManyToOne
	public Album getAlbum() { return album; }
	public void setAlbum(Album album) { this.album = album; }

	public Integer getLength() { return length; }
	public void setLength(Integer length) { this.length = length; }

	public Integer getNumber() { return number; }
	public void setNumber(Integer number) { this.number = number; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
}
