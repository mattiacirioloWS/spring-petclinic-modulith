package org.springframework.samples.petclinic.ddd.owner.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record FullAddress(String street, String city) {
	public FullAddress {
		if (street == null || street.isBlank()) {
			throw new IllegalArgumentException("Street must be defined and not blank");
		}
		if (city == null || city.isBlank()) {
			throw new IllegalArgumentException("City must be defined and not blank");
		}
	}
}
