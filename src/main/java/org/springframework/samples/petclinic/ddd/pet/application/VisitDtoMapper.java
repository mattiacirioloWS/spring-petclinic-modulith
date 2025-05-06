package org.springframework.samples.petclinic.ddd.pet.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.pet.domain.Visit;

@Mapper(componentModel = "spring")
public interface VisitDtoMapper {

	@Mapping(target = "id", expression = "java(visit.getId().uuid())")
	VisitDto fromAggregate(Visit visit);

}
