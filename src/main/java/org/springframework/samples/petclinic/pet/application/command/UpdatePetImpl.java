package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.common.application.annotation.Command;
import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.pet.*;

import java.util.stream.Collectors;

@Command
class UpdatePetImpl implements UpdatePet {

	private final PetRepository petRepository;

	public UpdatePetImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public PetDto execute(PetDto pet) {
		Pet savedPet = petRepository.save(Pet.update(new PetId(pet.getId()), new Name(pet.getName()),
				new BirthDate(pet.getBirthDate()), PetType.reconstitute(pet.getType().getId(), pet.getType().getName()),
				pet.getVisits()
					.stream()
					.map(visit -> Visit.reconstitute(visit.getId(), pet.getId(), visit.getDate(),
							visit.getDescription()))
					.collect(Collectors.toSet()),
				new OwnerId(pet.getOwnerId()), petRepository));
		return PetDto.from(savedPet);
	}

}
