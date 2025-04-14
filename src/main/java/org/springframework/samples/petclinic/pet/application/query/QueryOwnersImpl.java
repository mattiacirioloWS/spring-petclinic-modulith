package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.common.application.annotation.Query;
import org.springframework.samples.petclinic.pet.application.OwnerDto;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerRepository;

import java.util.Optional;
import java.util.UUID;

@Query
public class QueryOwnersImpl implements QueryOwners {

	private final OwnerRepository ownerRepository;

	public QueryOwnersImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Optional<OwnerDto> findById(UUID ownerId) {
		return ownerRepository.findById(new OwnerId(ownerId)).map(OwnerDto::from);
	}

	@Override
	public Page<OwnerDto> findByLastNameStartingWith(String lastnamePrefix, Pageable pageable) {
		return ownerRepository.findByLastNameStartingWith(lastnamePrefix, pageable).map(OwnerDto::from);
	}

}
