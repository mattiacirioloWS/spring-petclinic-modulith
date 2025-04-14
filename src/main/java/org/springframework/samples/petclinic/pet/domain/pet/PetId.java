package org.springframework.samples.petclinic.pet.domain.pet;

import java.util.UUID;

public record PetId(UUID id) {

	public PetId {
		if (id == null) {
			throw new IllegalArgumentException("Pet ID cannot be null");
		}
	}

	static PetId create() {
		return new PetId(UUID.randomUUID());
	}

	public UUID toUUID() {
		return id;
	}
}
