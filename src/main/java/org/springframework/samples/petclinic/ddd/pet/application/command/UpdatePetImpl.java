package org.springframework.samples.petclinic.ddd.pet.application.command;

import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.*;

@Command
public class UpdatePetImpl implements UpdatePet {

	private final PetRepository petRepository;

	private final PetDtoMapper petDtoMapper;

	public UpdatePetImpl(PetRepository petRepository, PetDtoMapper petDtoMapper) {
		this.petRepository = petRepository;
		this.petDtoMapper = petDtoMapper;
	}

	@Override
	public PetDto execute(PetId petId, Name name, BirthDate birthDate, PetTypeId petTypeId, OwnerId ownerId) {
		Pet pet = petRepository.findByIdAndOwnerId(petId, ownerId)
			.orElseThrow(() -> new IllegalArgumentException("Pet not found"));
		pet.changeName(name);
		pet.changeBirthDate(birthDate);
		pet.changeType(petTypeId);
		pet = petRepository.save(pet);
		return petDtoMapper.fromAggregate(pet);
	}

}
