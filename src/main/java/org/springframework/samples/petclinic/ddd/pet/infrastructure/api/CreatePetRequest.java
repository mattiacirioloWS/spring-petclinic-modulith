package org.springframework.samples.petclinic.ddd.pet.infrastructure.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDto;
import org.springframework.samples.petclinic.ddd.pet.application.VisitDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreatePetRequest {

	@NotBlank
	private String name;

	@NotNull
	private UUID petTypeId;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@NotNull
	private UUID ownerId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getPetTypeId() {
		return petTypeId;
	}

	public void setPetTypeId(UUID petTypeId) {
		this.petTypeId = petTypeId;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

}
