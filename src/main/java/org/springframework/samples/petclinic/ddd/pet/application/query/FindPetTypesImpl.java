package org.springframework.samples.petclinic.ddd.pet.application.query;

import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.PetRepository;

import java.util.List;

@Query
public class FindPetTypesImpl implements FindPetTypes {

	private final PetRepository petRepository;

	private final PetTypeDtoMapper petTypeDtoMapper;

	public FindPetTypesImpl(PetRepository petRepository, PetTypeDtoMapper petTypeDtoMapper) {
		this.petRepository = petRepository;
		this.petTypeDtoMapper = petTypeDtoMapper;
	}

	@Override
	public List<PetTypeDto> findAll() {
		return petRepository.findAllPetTypes().stream().map(petTypeDtoMapper::fromAggregate).toList();
	}

}
