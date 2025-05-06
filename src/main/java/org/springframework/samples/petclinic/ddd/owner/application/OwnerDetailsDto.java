package org.springframework.samples.petclinic.ddd.owner.application;

import java.util.UUID;

public class OwnerDetailsDto {

	private final UUID id;

	private final String firstName;

	private final String lastName;

	public OwnerDetailsDto(UUID id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
