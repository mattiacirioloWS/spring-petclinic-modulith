package org.springframework.samples.petclinic.ddd.vet.domain;

import java.io.Serializable;
import java.util.UUID;

public record SpecialtyId(UUID uuid) implements Serializable {
	public SpecialtyId {
		if (uuid == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
	}

	public static SpecialtyId create() {
		return new SpecialtyId(UUID.randomUUID());
	}

}
