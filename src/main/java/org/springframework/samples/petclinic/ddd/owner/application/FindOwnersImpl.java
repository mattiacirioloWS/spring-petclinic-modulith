package org.springframework.samples.petclinic.ddd.owner.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.samples.petclinic.ddd.owner.FindOwnerDetails;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerRepository;

import java.util.Optional;
import java.util.UUID;

@Query
public class FindOwnersImpl implements FindOwners, FindOwnerDetails {

	private final OwnerRepository ownerRepository;

	private final OwnerDtoMapper ownerDtoMapper;

	public FindOwnersImpl(OwnerRepository ownerRepository, OwnerDtoMapper ownerDtoMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerDtoMapper = ownerDtoMapper;
	}

	@Override
	public Optional<OwnerDto> findById(@NotNull OwnerId ownerId) {
		return ownerRepository.findById(ownerId).map(ownerDtoMapper::fromAggregate);
	}

	@Override
	public Page<OwnerDto> findByLastNameStartingWith(@NotNull String lastname, @NotNull Pageable pageable) {
		return ownerRepository.findByLastNameStartingWith(lastname, pageable).map(ownerDtoMapper::fromAggregate);
	}

	@Override
	public Optional<OwnerDetailsDto> byId(UUID ownerId) {
		return ownerRepository.findById(new OwnerId(ownerId))
			.map(owner -> new OwnerDetailsDto(owner.getId().uuid(), owner.getName().firstName(),
					owner.getName().lastName()));
	}

}
