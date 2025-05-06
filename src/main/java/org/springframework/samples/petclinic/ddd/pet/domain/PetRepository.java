package org.springframework.samples.petclinic.ddd.pet.domain;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

	List<PetType> findAllPetTypes();

	List<Pet> findPetsByOwnerId(OwnerId ownerId);

	Optional<Pet> findByIdAndOwnerId(PetId petId, OwnerId ownerId);

	Optional<PetType> findTypeById(PetTypeId typeId);

	Pet save(Pet pet);

	Optional<Pet> findById(PetId petId);

	Boolean hasOwnerAnotherPetWIthSameName(OwnerId ownerId, PetId id, Name name);

}
