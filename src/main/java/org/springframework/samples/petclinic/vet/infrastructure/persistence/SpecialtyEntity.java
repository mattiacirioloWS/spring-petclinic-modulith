package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * JPA Entity representing a Specialty.
 */
@Entity
@Table(name = "specialties")
public class SpecialtyEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	// Default constructor for JPA
	protected SpecialtyEntity() {
	}

	public SpecialtyEntity(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
