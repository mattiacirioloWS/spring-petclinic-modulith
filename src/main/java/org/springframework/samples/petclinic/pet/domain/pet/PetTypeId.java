package org.springframework.samples.petclinic.pet.domain.pet;

import java.util.UUID;

public record PetTypeId(UUID id) {

	public PetTypeId {
		if (id == null) {
			throw new IllegalArgumentException("Pet Type ID cannot be null");
		}
	}

	static PetTypeId create() {
		return new PetTypeId(UUID.randomUUID());
	}

	public UUID toUUID() {
		return id;
	}
}
