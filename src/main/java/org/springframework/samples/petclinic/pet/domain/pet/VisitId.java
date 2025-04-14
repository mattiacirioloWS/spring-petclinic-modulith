package org.springframework.samples.petclinic.pet.domain.pet;

import java.util.UUID;

public record VisitId(UUID id) {

	public VisitId {
		if (id == null) {
			throw new IllegalArgumentException("Visit ID cannot be null");
		}
	}

	static VisitId create() {
		return new VisitId(UUID.randomUUID());
	}

	public UUID toUUID() {
		return id;
	}
}
