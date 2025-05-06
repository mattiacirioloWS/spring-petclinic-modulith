package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.ddd.pet.domain.Visit;

@Mapper(componentModel = "spring")
interface VisitEntityMapper {

	VisitEntity fromAggregate(Visit aggregate);

	default Visit toAggregate(VisitEntity visitEntity) {
		return Visit.Builder.reconstitute(visitEntity.getId(), visitEntity.getDate(), visitEntity.getDescription());
	}

}
