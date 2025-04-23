package org.springframework.samples.petclinic.ddd.vet.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Name(String name) {

	public Name {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}
	}

	public String get() {
		return name;
	}

}
