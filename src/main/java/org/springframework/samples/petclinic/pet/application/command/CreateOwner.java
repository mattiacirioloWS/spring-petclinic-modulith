package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.pet.application.OwnerDto;

public interface CreateOwner {

	OwnerDto execute(OwnerDto owner);

}
