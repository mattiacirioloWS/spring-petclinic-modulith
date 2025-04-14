package org.springframework.samples.petclinic.pet.domain.owner;

public record Address(String address) {
	public Address {
		if (address == null || address.trim().isEmpty()) {
			throw new IllegalArgumentException("Address cannot be null or empty");
		}
	}

	public String toString() {
		return address;
	}
}
