package org.springframework.samples.petclinic.ddd.pet.infrastructure.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public class UpdatePetRequest {

	@NotBlank
	private String name;

	@NotNull
	private UUID petTypeId;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

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

}
