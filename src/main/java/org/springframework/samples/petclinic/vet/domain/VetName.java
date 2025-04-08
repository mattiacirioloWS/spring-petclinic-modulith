package org.springframework.samples.petclinic.vet.domain;

public record VetName(String firstName, String lastName) {

	public VetName {
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("First name cannot be null or blank");
		}
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("Last name cannot be null or blank");
		}
		firstName = firstName.trim();
		lastName = lastName.trim();
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}
