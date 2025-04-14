package org.springframework.samples.petclinic.pet.domain.owner;

import java.util.UUID;

public record OwnerId(UUID id) {

	public OwnerId {
		if (id == null) {
			throw new IllegalArgumentException("Owner ID cannot be null");
		}
	}

	static OwnerId create() {
		return new OwnerId(UUID.randomUUID());
	}

	// this is a reference from the Owner Aggregate Root, it can't be a new one here

	public UUID toUUID() {
		return id;
	}
}
