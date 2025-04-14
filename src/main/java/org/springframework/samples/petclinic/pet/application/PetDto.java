package org.springframework.samples.petclinic.pet.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.pet.domain.pet.Pet;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PetDto {

	private UUID id;

	@NotBlank
	private String name;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@NotNull
	private PetTypeDto type;

	private UUID ownerId;

	private Set<PetVisitDto> visits = new HashSet<>();

	public PetDto() {
	}

	private PetDto(UUID id, String name, LocalDate birthDate, PetTypeDto type, UUID ownerId, Set<PetVisitDto> visits) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.type = type;
		this.ownerId = ownerId;
		this.visits = visits;
	}

	public static PetDto from(Pet pet) {
		return new PetDto(pet.getId().toUUID(), pet.getName(), pet.getBirthDate().localDate(),
				PetTypeDto.from(pet.getPetType()), pet.getOwnerId().toUUID(),
				pet.getVisits().stream().map(PetVisitDto::from).collect(Collectors.toSet()));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public PetTypeDto getType() {
		return type;
	}

	public void setType(PetTypeDto type) {
		this.type = type;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public Set<PetVisitDto> getVisits() {
		return visits;
	}

	public void setVisits(Set<PetVisitDto> visits) {
		this.visits = visits;
	}

}
