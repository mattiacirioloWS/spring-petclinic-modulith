package org.springframework.samples.petclinic.pet.domain.pet;

public record Name(String name) {
	public Name {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
	}

	public String toString() {
		return name;
	}
}
