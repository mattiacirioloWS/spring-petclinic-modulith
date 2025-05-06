package org.springframework.samples.petclinic.ddd.pet.domain;

import java.time.LocalDate;

public record BirthDate(LocalDate localDate) {
	public BirthDate {
		if (localDate == null || localDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Birth date must be defined and not in the future");
		}
	}
}
