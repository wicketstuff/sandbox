package wicket.examples.cdapp2.model;

import javax.persistence.*;

@Entity
public class Category implements Comparable<Category> {
	private Integer id;
	private String name;
	
	// ********************** Constructors ********************** //
	
	public Category() {}
	
	public Category(String name) {
		this.name = name;
	}
	
	// ********************** Common Methods ********************** //
	
	public int compareTo(Category rhs) {
		return getName().compareTo(rhs.getName());
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof Category) {
			return compareTo((Category) rhs) == 0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}

	// ********************** Accessor Methods ********************** //
	
	@Id(generate = GeneratorType.AUTO)
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
