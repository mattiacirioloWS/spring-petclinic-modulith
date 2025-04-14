package org.springframework.samples.petclinic.pet.application.query;

import java.util.UUID;

public interface PetNameUniqueness {

	Boolean verify(UUID ownerId, String name);

}
