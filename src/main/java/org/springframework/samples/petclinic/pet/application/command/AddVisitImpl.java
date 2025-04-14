package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.common.application.annotation.Command;
import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.application.PetVisitDto;
import org.springframework.samples.petclinic.pet.domain.pet.Pet;
import org.springframework.samples.petclinic.pet.domain.pet.PetId;
import org.springframework.samples.petclinic.pet.domain.pet.PetRepository;

import java.util.UUID;

@Command
class AddVisitImpl implements AddVisit {

	private final PetRepository petRepository;

	public AddVisitImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public PetDto execute(UUID petId, PetVisitDto visit) {
		Pet pet = petRepository.findById(new PetId(petId))
			.orElseThrow(() -> new IllegalArgumentException("Pet not found"));
		pet.addVisit(visit.getDate(), visit.getDescription());
		Pet savedPet = petRepository.save(pet);
		return PetDto.from(savedPet);
	}

}
