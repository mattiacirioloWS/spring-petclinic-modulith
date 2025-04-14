package org.springframework.samples.petclinic.pet.infrastructure.api;

import org.springframework.samples.petclinic.pet.application.OwnerDto;

import java.util.UUID;

public final class OwnerEntryRepresentation {

	private final OwnerDto ownerDto;

	private final String petNames;

	public OwnerEntryRepresentation(OwnerDto ownerDto, String petNames) {
		this.ownerDto = ownerDto;
		this.petNames = petNames;
	}

	public UUID getId() {
		return ownerDto.getId();
	}

	public String getFirstName() {
		return ownerDto.getFirstName();
	}

	public String getLastName() {
		return ownerDto.getLastName();
	}

	public String getAddress() {
		return ownerDto.getAddress();
	}

	public String getCity() {
		return ownerDto.getCity();
	}

	public String getTelephone() {
		return ownerDto.getTelephone();
	}

	public String getPetNames() {
		return petNames;
	}

}
