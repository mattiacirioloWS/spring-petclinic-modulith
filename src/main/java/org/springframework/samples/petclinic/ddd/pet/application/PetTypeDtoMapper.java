package org.springframework.samples.petclinic.ddd.pet.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.pet.domain.PetType;

@Mapper(componentModel = "spring")
public interface PetTypeDtoMapper {

	@Mapping(target = "id", expression = "java(petType.getId().uuid())")
	@Mapping(source = "name.name", target = "name")
	PetTypeDto fromAggregate(PetType petType);

}
