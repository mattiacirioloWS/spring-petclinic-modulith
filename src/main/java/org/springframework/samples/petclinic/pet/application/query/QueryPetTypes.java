package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.samples.petclinic.pet.application.PetTypeDto;

import java.util.List;

public interface QueryPetTypes {

	List<PetTypeDto> findAll();

}
