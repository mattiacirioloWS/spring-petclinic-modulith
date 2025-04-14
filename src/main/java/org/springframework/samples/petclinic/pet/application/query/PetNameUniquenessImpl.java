package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.samples.petclinic.common.application.annotation.Query;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.pet.Name;
import org.springframework.samples.petclinic.pet.domain.pet.PetRepository;

import java.util.UUID;

@Query
class PetNameUniquenessImpl implements PetNameUniqueness {

	private final PetRepository petRepository;

	PetNameUniquenessImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Boolean verify(UUID ownerId, String name) {
		return petRepository.findByNameAndOwnerId(new Name(name), new OwnerId(ownerId)).isEmpty();
	}

}
