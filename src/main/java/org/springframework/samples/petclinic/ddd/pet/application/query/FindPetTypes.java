package org.springframework.samples.petclinic.ddd.pet.application.query;

import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDto;

import java.util.List;

public interface FindPetTypes {

	List<PetTypeDto> findAll();

}
