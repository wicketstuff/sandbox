package contrib.wicket.cms.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import contrib.wicket.cms.model.Creatable;
import contrib.wicket.cms.model.Updatable;

@Entity
@Table(name = "members", uniqueConstraints = { @UniqueConstraint(columnNames = { "emailAddress" }) })
public class Member implements Updatable, Creatable, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@NotNull
	@Length(min = 6, max = 30)
	@Email
	private String emailAddress;

	@NotNull
	@Length(min = 6, max = 30)
	@Column(length = 32)
	private String password;

	// @ManyToOne(cascade = { CascadeType.ALL })
	// @JoinColumn(name = "addressId")
	// private Address address;

	private Date createdDate;

	private Date updatedDate;

	public Member() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
