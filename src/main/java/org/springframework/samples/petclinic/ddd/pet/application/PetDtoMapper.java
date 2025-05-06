package org.springframework.samples.petclinic.ddd.pet.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.pet.domain.Pet;

@Mapper(componentModel = "spring", uses = { PetTypeDtoMapper.class, VisitDtoMapper.class })
public interface PetDtoMapper {

	@Mapping(target = "id", expression = "java(pet.getId().uuid())")
	@Mapping(source = "name.name", target = "name")
	@Mapping(source = "birthDate.localDate", target = "birthDate")
	@Mapping(target = "ownerId", expression = "java(pet.getOwnerId().uuid())")
	@Mapping(source = "ownerName.firstName", target = "ownerFirstName")
	@Mapping(source = "ownerName.lastName", target = "ownerLastName")
	PetDto fromAggregate(Pet pet);

}
