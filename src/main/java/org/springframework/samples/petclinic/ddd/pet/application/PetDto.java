package org.springframework.samples.petclinic.ddd.pet.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PetDto {

	private UUID id;

	@NotBlank
	private String name;

	@NotNull
	private PetTypeDto type;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	private List<VisitDto> visits;

	@NotNull
	private UUID ownerId;

	private String ownerFirstName;

	private String ownerLastName;

	public UUID getId() {
		return id;
	}

	public Boolean isNew() {
		return this.id == null;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PetTypeDto getType() {
		return type;
	}

	public void setType(PetTypeDto type) {
		this.type = type;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<VisitDto> getVisits() {
		return visits;
	}

	public void setVisits(List<VisitDto> visits) {
		this.visits = visits;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

}
