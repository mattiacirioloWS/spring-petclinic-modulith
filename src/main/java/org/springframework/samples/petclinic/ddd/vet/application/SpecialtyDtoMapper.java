package org.springframework.samples.petclinic.ddd.vet.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.vet.domain.Specialty;

@Mapper(componentModel = "spring")
public interface SpecialtyDtoMapper {

	@Mapping(target = "id", expression = "java(aggregate.getId().uuid())")
	@Mapping(target = "name", expression = "java(aggregate.getName().get())")
	SpecialtyDto fromAggregate(Specialty aggregate);

}
