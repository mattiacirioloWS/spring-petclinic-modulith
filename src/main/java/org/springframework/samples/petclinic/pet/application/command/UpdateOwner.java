package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.pet.application.OwnerDto;

public interface UpdateOwner {

	OwnerDto execute(OwnerDto owner);

}
