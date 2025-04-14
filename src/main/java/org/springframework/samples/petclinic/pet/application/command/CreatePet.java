package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.pet.application.PetDto;

public interface CreatePet {

	PetDto execute(PetDto pet);

}
