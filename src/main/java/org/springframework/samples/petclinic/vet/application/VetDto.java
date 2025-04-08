package org.springframework.samples.petclinic.vet.application;

import org.springframework.samples.petclinic.vet.domain.Vet;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record VetDto(UUID id, String firstName, String lastName,
		List<SpecialtyDto> specialties) implements Serializable {
	public static VetDto from(Vet vet) {
		return new VetDto(vet.getId().toUUID(), vet.getName().firstName(), vet.getName().lastName(),
				vet.getSpecialties().stream().map(SpecialtyDto::from).toList());
	}

	public int getNrOfSpecialties() {
		return specialties.size();
	}
}