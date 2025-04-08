package org.springframework.samples.petclinic.vet.domain;

public record SpecialtyName(String name) {

	public SpecialtyName {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Specialty name cannot be null or empty");
		}
		if (name.length() > 80) {
			throw new IllegalArgumentException("Specialty name cannot be longer than 80 characters");
		}
		name = name.trim();
	}

	@Override
	public String toString() {
		return "SpecialtyName{" + "name='" + name + '\'' + '}';
	}

}