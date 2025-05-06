package org.springframework.samples.petclinic.ddd.owner.infrastructure.persistence;

import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.ddd.owner.domain.Owner;

@Mapper(componentModel = "spring")
interface OwnerEntityMapper {

	OwnerEntity fromAggregate(Owner owner);

	default Owner toAggregate(OwnerEntity ownerEntity) {
		return Owner.Builder.reconstitute(ownerEntity.getId(), ownerEntity.getName(), ownerEntity.getAddress(),
				ownerEntity.getTelephone());
	}

}
