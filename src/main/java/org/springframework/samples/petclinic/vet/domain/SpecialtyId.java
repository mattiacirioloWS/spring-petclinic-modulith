package org.springframework.samples.petclinic.vet.domain;

import java.util.UUID;

/**
 * Dedicated identifier for a {@link Specialty}.
 *
 * @param value The underlying UUID value.
 */
public record SpecialtyId(UUID value) {

	/**
	 * Creates a new random SpecialtyId.
	 * @return A new SpecialtyId instance.
	 */
	public static SpecialtyId random() {
		return new SpecialtyId(UUID.randomUUID());
	}
}
