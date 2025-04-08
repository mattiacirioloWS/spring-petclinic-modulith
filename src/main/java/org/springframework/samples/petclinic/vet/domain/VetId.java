package org.springframework.samples.petclinic.vet.domain;

import java.util.UUID;

public record VetId(UUID value) {

	public VetId {
		if (value == null) {
			throw new IllegalArgumentException("Vet ID cannot be null");
		}
	}

	public static VetId create() {
		return new VetId(UUID.randomUUID());
	}

	public UUID toUUID() {
		return value;
	}
}