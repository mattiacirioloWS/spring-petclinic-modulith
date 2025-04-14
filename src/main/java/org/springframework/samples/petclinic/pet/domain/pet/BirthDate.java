package org.springframework.samples.petclinic.pet.domain.pet;

import java.time.LocalDate;

public record BirthDate(LocalDate localDate) {
	public BirthDate {
		if (localDate == null || localDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Birth date cannot be null or in the future");
		}
	}
}
