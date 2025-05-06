package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ddd.pet.domain.Pet;
import org.springframework.samples.petclinic.ddd.pet.domain.PetRepository;

@Mapper(componentModel = "spring", uses = { PetTypeEntityMapper.class, VisitEntityMapper.class })
interface PetEntityMapper {

	PetEntity fromAggregate(Pet aggregate);

}
