package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.application.PetVisitDto;

import java.util.UUID;

public interface AddVisit {

	PetDto execute(UUID petId, PetVisitDto visit);

}
