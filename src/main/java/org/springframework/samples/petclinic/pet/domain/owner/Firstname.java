package org.springframework.samples.petclinic.pet.domain.owner;

public record Firstname(String name) {
	public Firstname {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Firstname cannot be null or empty");
		}
	}

	public String toString() {
		return name;
	}
}
