package org.springframework.samples.petclinic.ddd.vet.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record PersonName(String firstName, String lastName) {
	public PersonName {
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("First name cannot be null or blank");
		}
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("Last name cannot be null or blank");
		}
	}

}
