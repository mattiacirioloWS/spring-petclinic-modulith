package org.springframework.samples.petclinic.ddd.pet.application.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.domain.*;

public interface UpdatePet {

	PetDto execute(@NotNull PetId petId, @NotNull Name name, @NotNull BirthDate birthDate, @NotNull PetTypeId petTypeId,
			@NotNull OwnerId ownerId);

}
