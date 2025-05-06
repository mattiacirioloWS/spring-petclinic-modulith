package org.springframework.samples.petclinic.ddd.pet.domain;

import java.io.Serializable;
import java.util.UUID;

public record PetId(UUID uuid) implements Serializable {

	public PetId {
		if (uuid == null) {
			throw new IllegalArgumentException("PetId cannot be null");
		}
	}

	public static PetId create() {
		return new PetId(UUID.randomUUID());
	}
}
