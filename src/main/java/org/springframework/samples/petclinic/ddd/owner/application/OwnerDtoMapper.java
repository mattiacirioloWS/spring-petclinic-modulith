package org.springframework.samples.petclinic.ddd.owner.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.samples.petclinic.ddd.owner.domain.Owner;

@Mapper(componentModel = "spring")
public interface OwnerDtoMapper {

	@Mapping(target = "id", expression = "java(aggregate.getId().uuid())")
	@Mapping(source = "name.firstName", target = "firstName")
	@Mapping(source = "name.lastName", target = "lastName")
	@Mapping(source = "address.street", target = "address")
	@Mapping(source = "address.city", target = "city")
	@Mapping(source = "telephone.phoneNumber", target = "telephone")
	OwnerDto fromAggregate(Owner aggregate);

}
