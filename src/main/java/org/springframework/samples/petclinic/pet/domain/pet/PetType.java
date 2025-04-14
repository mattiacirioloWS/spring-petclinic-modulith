package org.springframework.samples.petclinic.pet.domain.pet;

import java.util.UUID;

public record PetType(PetTypeId id, Name name) {

	public PetType {
		if (id == null) {
			throw new IllegalArgumentException("Pet Type ID cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Pet Type name cannot be null");
		}
	}

	public static PetType reconstitute(UUID id, String name) {
		return new PetType(new PetTypeId(id), new Name(name));
	}
}
