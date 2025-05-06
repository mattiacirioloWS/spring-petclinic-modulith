package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.ddd.pet.domain.PetType;

@Mapper(componentModel = "spring")
interface PetTypeEntityMapper {

	PetTypeEntity fromAggregate(PetType aggregate);

	default PetType toAggregate(PetTypeEntity type) {
		return PetType.Builder.reconstitute(type.getId(), type.getName());
	}

}
