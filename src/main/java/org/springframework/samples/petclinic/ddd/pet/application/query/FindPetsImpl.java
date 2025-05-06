package org.springframework.samples.petclinic.ddd.pet.application.query;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.samples.petclinic.ddd.pet.FindPets;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetRepository;

import java.util.List;
import java.util.UUID;

@Query
public class FindPetsImpl implements FindPets {

	private final PetRepository petRepository;

	private final PetDtoMapper petDtoMapper;

	public FindPetsImpl(PetRepository petRepository, PetDtoMapper petDtoMapper) {
		this.petRepository = petRepository;
		this.petDtoMapper = petDtoMapper;
	}

	@Override
	public List<PetDto> byOwnerId(@NotNull UUID ownerId) {
		return petRepository.findPetsByOwnerId(new OwnerId(ownerId)).stream().map(petDtoMapper::fromAggregate).toList();
	}

}
