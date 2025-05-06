package org.springframework.samples.petclinic.ddd.owner.domain;

import java.io.Serializable;
import java.util.UUID;

public record OwnerId(UUID uuid) implements Serializable {
	public OwnerId {
		if (uuid == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
	}

	public static OwnerId create() {
		return new OwnerId(UUID.randomUUID());
	}

}
