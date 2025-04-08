package org.springframework.samples.petclinic.vet.domain;

import java.util.UUID;

public record SpecialtyId(UUID id) {

	public SpecialtyId {
		if (id == null) {
			throw new IllegalArgumentException("Specialty ID cannot be null");
		}
	}

	public static SpecialtyId create() {
		return new SpecialtyId(UUID.randomUUID());
	}

	public UUID toUUID() {
		return id;
	}
}
