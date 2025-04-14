package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.samples.petclinic.common.application.annotation.Query;
import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.pet.PetId;
import org.springframework.samples.petclinic.pet.domain.pet.PetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Query
class QueryPetsImpl implements QueryPets {

	private final PetRepository petRepository;

	QueryPetsImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public List<PetDto> findAllByOwner(UUID ownerId) {
		return petRepository.findPetsByOwnerId(new OwnerId(ownerId)).stream().map(PetDto::from).toList();
	}

	@Override
	public Optional<PetDto> findByIdAndOwnerId(UUID petId, UUID ownerId) {
		return petRepository.findByIdAndOwnerId(new PetId(petId), new OwnerId(ownerId)).map(PetDto::from);
	}

}
