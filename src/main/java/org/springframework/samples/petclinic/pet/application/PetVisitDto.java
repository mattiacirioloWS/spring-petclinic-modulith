package org.springframework.samples.petclinic.pet.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.pet.domain.pet.Visit;

import java.time.LocalDate;
import java.util.UUID;

public class PetVisitDto {

	private UUID id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate date;

	@NotBlank
	private String description;

	public PetVisitDto() {
	}

	private PetVisitDto(UUID id, LocalDate date, String description) {
		this.id = id;
		this.date = date;
		this.description = description;
	}

	public static PetVisitDto from(Visit visit) {
		return new PetVisitDto(visit.getId().toUUID(), visit.getDate(), visit.getDescription());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Boolean isNew() {
		return id == null;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
