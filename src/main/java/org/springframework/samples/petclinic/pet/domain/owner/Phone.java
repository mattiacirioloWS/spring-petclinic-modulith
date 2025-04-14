package org.springframework.samples.petclinic.pet.domain.owner;

public record Phone(String phone) {
	public Phone {
		// test this pattern match too "\\d{10}"
		if (phone == null || phone.trim().isEmpty() || !phone.matches("\\d{10}")) {
			throw new IllegalArgumentException("Phone must be a 10-digit number");
		}
	}

	public String toString() {
		return phone;
	}
}
