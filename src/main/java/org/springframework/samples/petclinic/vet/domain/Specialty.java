package org.springframework.samples.petclinic.vet.domain;

import java.util.Objects;

public record Specialty(SpecialtyId id, SpecialtyName name) {

	public Specialty {
		if (id == null) {
			throw new IllegalArgumentException("Specialty ID cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Specialty name cannot be null");
		}
	}

	public static Specialty create(String name) {
		return new Specialty(SpecialtyId.create(), new SpecialtyName(name));
	}

	public String getName() {
		return name.name();
	}
}