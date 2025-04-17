package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ddd.vet.domain.Specialty;

@Mapper(componentModel = "spring")
interface SpecialtyEntityMapper {

	SpecialtyEntity fromAggregate(Specialty aggregate);

	default Specialty toAggregate(SpecialtyEntity entity) {
		return Specialty.Builder.reconstitute(entity.getId(), entity.getName());
	}

}
