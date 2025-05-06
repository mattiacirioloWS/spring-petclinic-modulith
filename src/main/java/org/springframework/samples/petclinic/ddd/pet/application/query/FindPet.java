package org.springframework.samples.petclinic.ddd.pet.application.query;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;

import java.util.Optional;

public interface FindPet {

	Optional<PetDto> byIdAndOwnerId(@NotNull PetId petId, @NotNull OwnerId ownerId);

}
