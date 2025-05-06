package org.springframework.samples.petclinic.ddd.owner.application;

import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.owner.domain.*;

@Command
public class UpdateOwnerImpl implements UpdateOwner {

	private final OwnerRepository ownerRepository;

	private final OwnerDtoMapper ownerDtoMapper;

	public UpdateOwnerImpl(OwnerRepository ownerRepository, OwnerDtoMapper ownerDtoMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerDtoMapper = ownerDtoMapper;
	}

	@Override
	public OwnerDto execute(OwnerId ownerId, PersonName personName, FullAddress fullAddress, Telephone telephone) {
		Owner owner = ownerRepository.findById(ownerId)
			.orElseThrow(() -> new IllegalArgumentException("Owner not found"));
		owner.changeName(personName);
		owner.changeAddress(fullAddress);
		owner.changeTelephone(telephone);
		return ownerDtoMapper.fromAggregate(owner);
	}

}
