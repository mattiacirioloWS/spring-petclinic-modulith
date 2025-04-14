package org.springframework.samples.petclinic.pet.application;

import org.springframework.samples.petclinic.pet.domain.pet.PetType;

import java.util.UUID;

public class PetTypeDto {

	private UUID id;

	private String name;

	public PetTypeDto() {
	}

	private PetTypeDto(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public static PetTypeDto from(PetType petType) {
		return new PetTypeDto(petType.id().toUUID(), petType.name().toString());
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

	@Override
	public String toString() {
		return name;
	}

}
