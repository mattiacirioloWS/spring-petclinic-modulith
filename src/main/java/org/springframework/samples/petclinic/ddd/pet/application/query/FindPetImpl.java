package org.springframework.samples.petclinic.ddd.pet.application.query;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetRepository;

import java.util.Optional;

@Query
public class FindPetImpl implements FindPet {

	private final PetRepository petRepository;

	private final PetDtoMapper petDtoMapper;

	public FindPetImpl(PetRepository petRepository, PetDtoMapper petDtoMapper) {
		this.petRepository = petRepository;
		this.petDtoMapper = petDtoMapper;
	}

	@Override
	public Optional<PetDto> byIdAndOwnerId(@NotNull PetId petId, @NotNull OwnerId ownerId) {
		return petRepository.findByIdAndOwnerId(petId, ownerId).map(petDtoMapper::fromAggregate);
	}

}
