package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.pet.application.PetDto;

public interface UpdatePet {

	PetDto execute(PetDto pet);

}
