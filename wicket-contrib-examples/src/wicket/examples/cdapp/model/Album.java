package wicket.examples.cdapp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.apache.wicket.WicketRuntimeException;

@Entity
public class Album implements Comparable<Album> {
	private Integer id;
	private String artist;
	private String title;
	private Date date;
	private Category category;

	// ********************** Constructors ********************** //
	
	public Album() {}

	public Album(String artist, String title, String date, Category category) {
		this.title = title;
		this.artist = artist;
		
		try {
			this.date = new SimpleDateFormat("m/d/yyyy").parse(date);
		} catch (ParseException e) {
			throw new WicketRuntimeException(e);
		}
		
		this.category = category;
	}
	
	// ********************** Common Methods ********************** //
	
	public int compareTo(Album rhs) {
		return new CompareToBuilder()
			.append(getArtist(), rhs.getArtist())
			.append(getTitle(), rhs.getTitle())
			.toComparison();
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof Album) {
			return compareTo((Album) rhs) == 0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getArtist())
			.append(getTitle())
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
	
	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }
	
	@ManyToOne
	public Category getCategory() {	return category; }
	public void setCategory(Category category) { this.category = category; }
}
