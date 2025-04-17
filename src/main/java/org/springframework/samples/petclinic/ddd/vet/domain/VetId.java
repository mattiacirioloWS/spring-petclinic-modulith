package org.springframework.samples.petclinic.ddd.vet.domain;

import java.io.Serializable;
import java.util.UUID;

public record VetId(UUID uuid) implements Serializable {
	public VetId {
		if (uuid == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
	}

	public static VetId create() {
		return new VetId(UUID.randomUUID());
	}

}
