package org.apache.wicket.persistence.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * The version number for optimistic locking.
	 */
	@Version
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer version;

	public Long getId() {
		return id;
	}

	/**
	 * This method has private accessibility because Hibernate mandates that it
	 * exists but it really should not. This is best effort Public because
	 * interface needs it
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		if (id == null)
			return false;

		final BaseEntity other = (BaseEntity) o;
		if (!id.equals(other.id))
			return false;

		return true;
	}

}
