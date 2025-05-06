package org.springframework.samples.petclinic.ddd.pet.application;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class VisitDto {

	private UUID id;

	private LocalDate date;

	@NotBlank
	private String description;

	public UUID getId() {
		return id;
	}

	public Boolean isNew() {
		return this.id == null;
	}

	public void setId(UUID id) {
		this.id = id;
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
