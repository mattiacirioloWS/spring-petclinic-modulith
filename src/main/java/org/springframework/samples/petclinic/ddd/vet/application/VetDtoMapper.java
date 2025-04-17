package org.springframework.samples.petclinic.ddd.vet.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;

@Mapper(componentModel = "spring", uses = SpecialtyDtoMapper.class)
public interface VetDtoMapper {

	@Mapping(target = "id", expression = "java(aggregate.getId().uuid())")
	@Mapping(source = "name.firstName", target = "firstName")
	@Mapping(source = "name.lastName", target = "lastName")
	VetDto fromAggregate(Vet aggregate);

}
