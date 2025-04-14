package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.common.application.annotation.Command;
import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.pet.*;

@Command
class CreatePetImpl implements CreatePet {

	private final PetRepository petRepository;

	public CreatePetImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public PetDto execute(PetDto pet) {
		Pet savedPet = petRepository.save(Pet.create(new Name(pet.getName()), new BirthDate(pet.getBirthDate()),
				PetType.reconstitute(pet.getType().getId(), pet.getType().getName()), new OwnerId(pet.getOwnerId()),
				petRepository));
		return PetDto.from(savedPet);
	}

}
