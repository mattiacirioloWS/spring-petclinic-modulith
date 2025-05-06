package org.springframework.samples.petclinic.ddd.owner.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Telephone(String phoneNumber) {
	public Telephone {
		if (phoneNumber == null || phoneNumber.isBlank()) {
			throw new IllegalArgumentException("Phone number must be defined and not blank");
		}
		if (!phoneNumber.matches("\\d{10}")) {
			throw new IllegalArgumentException("Telephone must be a 10-digit number");
		}
	}
}
