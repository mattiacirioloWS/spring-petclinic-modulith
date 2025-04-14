package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.samples.petclinic.common.application.annotation.Query;
import org.springframework.samples.petclinic.pet.application.PetTypeDto;
import org.springframework.samples.petclinic.pet.domain.pet.PetRepository;

import java.util.List;

@Query
class QueryPetTypesImpl implements QueryPetTypes {

	private final PetRepository petRepository;

	QueryPetTypesImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public List<PetTypeDto> findAll() {
		return petRepository.findPetTypes().stream().map(PetTypeDto::from).toList();
	}

}
