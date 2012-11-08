package com.leman.core.data.dictionar.jpa.domain.dumpIds;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.emailvision.data.jpa.domain.IEntity;

@Entity(name = "DumpIds")
@Table(name = "dumpIds")
@Access(AccessType.FIELD)
public class DumpIds implements IEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2090450220419309231L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="LAST_NAME", columnDefinition="NVARCHAR(32)", nullable=false)
	private String lastName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}