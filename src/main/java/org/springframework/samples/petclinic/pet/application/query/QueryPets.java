package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.samples.petclinic.pet.application.PetDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryPets {

	List<PetDto> findAllByOwner(UUID ownerId);

	Optional<PetDto> findByIdAndOwnerId(UUID petId, UUID ownerId);

}
