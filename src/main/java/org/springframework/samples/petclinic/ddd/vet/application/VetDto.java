package org.springframework.samples.petclinic.ddd.vet.application;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class VetDto implements Serializable {

	private UUID id;

	private String firstName;

	private String lastName;

	private List<SpecialtyDto> specialties;

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

	public List<SpecialtyDto> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<SpecialtyDto> specialties) {
		this.specialties = specialties;
	}

	public Integer getNrOfSpecialties() {
		return specialties == null ? 0 : specialties.size();
	}

}
