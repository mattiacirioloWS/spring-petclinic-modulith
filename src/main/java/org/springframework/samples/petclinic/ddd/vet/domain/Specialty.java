package org.springframework.samples.petclinic.ddd.vet.domain;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Factory;
import org.jmolecules.ddd.annotation.Identity;

@Entity
public final class Specialty {

	@Identity
	private final SpecialtyId id;

	private final Name name;

	private Specialty(SpecialtyId id, Name name) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}

		this.id = id;
		this.name = name;
	}

	public SpecialtyId getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	@Factory
	public static final class Builder {

		public static Specialty reconstitute(SpecialtyId id, Name name) {
			return new Specialty(id, name);
		}

	}

}
