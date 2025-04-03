package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA Entity representing a Vet.
 */
@Entity
@Table(name = "vets")
public class VetEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	@Column(name = "last_name")
	@NotEmpty
	private String lastName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	@OrderBy("name ASC")
	private Set<SpecialtyEntity> specialties;

	// Default constructor for JPA
	protected VetEntity() {
	}

	public VetEntity(UUID id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialties = new HashSet<>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<SpecialtyEntity> getSpecialties() {
		if (this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	public void setSpecialties(Set<SpecialtyEntity> specialties) {
		this.specialties = specialties;
	}

	public int getNrOfSpecialties() {
		return getSpecialties().size();
	}

	public void addSpecialty(SpecialtyEntity specialty) {
		getSpecialties().add(specialty);
	}

}
