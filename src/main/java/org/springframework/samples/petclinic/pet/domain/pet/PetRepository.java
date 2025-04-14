package org.springframework.samples.petclinic.pet.domain.pet;

import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

	Optional<Pet> findById(PetId petId);

	List<Pet> findPetsByOwnerId(OwnerId ownerId);

	Optional<Pet> findByIdAndOwnerId(PetId petId, OwnerId ownerId);

	Optional<Pet> findByNameAndOwnerId(Name name, OwnerId ownerId);

	Pet save(Pet pet);

	List<PetType> findPetTypes();

	Boolean existsByIdAndOwnerId(PetId id, OwnerId ownerId);

}
