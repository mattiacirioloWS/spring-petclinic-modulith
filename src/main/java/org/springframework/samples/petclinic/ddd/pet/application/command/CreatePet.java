package org.springframework.samples.petclinic.ddd.pet.application.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.domain.BirthDate;
import org.springframework.samples.petclinic.ddd.pet.domain.Name;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetTypeId;

public interface CreatePet {

	PetDto execute(@NotNull Name name, @NotNull BirthDate birthDate, @NotNull PetTypeId petTypeId,
			@NotNull OwnerId ownerId);

}
