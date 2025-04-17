package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { SpecialtyEntityMapper.class })
abstract class VetEntityMapper {

	@Autowired
	private SpecialtyEntityMapper specialtyEntityMapper;

	abstract VetEntity fromAggregate(Vet aggregate);

	Vet toAggregate(VetEntity entity) {
		return Vet.Builder.reconstitute(entity.getId(), entity.getName(),
				entity.getSpecialties().stream().map(specialtyEntityMapper::toAggregate).collect(Collectors.toSet()));
	}

}
