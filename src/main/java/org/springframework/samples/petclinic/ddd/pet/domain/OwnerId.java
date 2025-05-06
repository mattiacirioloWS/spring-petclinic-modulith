package org.springframework.samples.petclinic.ddd.pet.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record OwnerId(UUID uuid) {
	public OwnerId {
		if (uuid == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
	}
}
