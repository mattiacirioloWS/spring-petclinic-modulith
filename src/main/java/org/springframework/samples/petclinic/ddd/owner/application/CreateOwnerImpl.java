package org.springframework.samples.petclinic.ddd.owner.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.owner.domain.*;

@Command
public class CreateOwnerImpl implements CreateOwner {

	private final OwnerRepository ownerRepository;

	private final OwnerDtoMapper ownerDtoMapper;

	public CreateOwnerImpl(OwnerRepository ownerRepository, OwnerDtoMapper ownerDtoMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerDtoMapper = ownerDtoMapper;
	}

	@Override
	public OwnerDto execute(@NotNull PersonName personName, @NotNull FullAddress fullAddress,
			@NotNull Telephone telephone) {
		Owner owner = Owner.Builder.create(personName, fullAddress, telephone);
		ownerRepository.save(owner);
		return ownerDtoMapper.fromAggregate(owner);
	}

}
