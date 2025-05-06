package org.springframework.samples.petclinic.ddd.pet.domain;

import java.io.Serializable;
import java.util.UUID;

public record VisitId(UUID uuid) implements Serializable {

	public VisitId {
		if (uuid == null) {
			throw new IllegalArgumentException("PetId cannot be null");
		}
	}

	static VisitId create() {
		return new VisitId(UUID.randomUUID());
	}
}
