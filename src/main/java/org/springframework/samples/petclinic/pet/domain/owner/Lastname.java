package org.springframework.samples.petclinic.pet.domain.owner;

public record Lastname(String name) {
	public Lastname {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Lastname cannot be null or empty");
		}
	}

	public String toString() {
		return name;
	}
}
