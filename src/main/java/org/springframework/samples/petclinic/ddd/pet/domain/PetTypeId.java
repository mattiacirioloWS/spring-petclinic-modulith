package org.springframework.samples.petclinic.ddd.pet.domain;

import java.io.Serializable;
import java.util.UUID;

public record PetTypeId(UUID uuid) implements Serializable {

	public PetTypeId {
		if (uuid == null) {
			throw new IllegalArgumentException("PetTypeId cannot be null");
		}
	}

	static UUID create() {
		return UUID.randomUUID();
	}
}
