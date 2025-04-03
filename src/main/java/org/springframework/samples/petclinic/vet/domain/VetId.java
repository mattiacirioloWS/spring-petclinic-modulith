package org.springframework.samples.petclinic.vet.domain;

import java.util.UUID;

/**
 * Dedicated identifier for a {@link Vet}.
 *
 * @param value The underlying UUID value.
 */
public record VetId(UUID value) {

	/**
	 * Creates a new random VetId.
	 * @return A new VetId instance.
	 */
	public static VetId random() {
		return new VetId(UUID.randomUUID());
	}
}
