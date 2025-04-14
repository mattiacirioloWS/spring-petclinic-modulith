package org.springframework.samples.petclinic.pet.application.command;

import org.springframework.samples.petclinic.common.application.annotation.Command;
import org.springframework.samples.petclinic.pet.application.OwnerDto;
import org.springframework.samples.petclinic.pet.domain.owner.Owner;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerRepository;

@Command
class UpdateOwnerImpl implements UpdateOwner {

	private final OwnerRepository ownerRepository;

	UpdateOwnerImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public OwnerDto execute(OwnerDto owner) {
		return OwnerDto.from(ownerRepository.save(Owner.reconstitute(owner.getId(), owner.getFirstName(),
				owner.getLastName(), owner.getAddress(), owner.getCity(), owner.getTelephone())));
	}

}
