package org.springframework.samples.petclinic.ddd.pet.domain;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Factory;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Objects;

@Entity
public final class PetType {

	@Identity
	private final PetTypeId id;

	private final Name name;

	private PetType(PetTypeId id, Name name) {
		if (id == null) {
			throw new IllegalArgumentException("Id is required");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name is required");
		}
		this.id = id;
		this.name = name;
	}

	public PetTypeId getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	@Factory
	public static final class Builder {

		public static PetType reconstitute(PetTypeId id, Name name) {
			return new PetType(id, name);
		}

	}

}
