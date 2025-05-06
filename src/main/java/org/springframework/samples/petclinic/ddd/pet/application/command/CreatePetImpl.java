package org.springframework.samples.petclinic.ddd.pet.application.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.owner.FindOwnerDetails;
import org.springframework.samples.petclinic.ddd.owner.application.OwnerDetailsDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.*;

@Command
public class CreatePetImpl implements CreatePet {

	private final PetRepository petRepository;

	private final FindOwnerDetails findOwnerDetails;

	private final PetDtoMapper petDtoMapper;

	public CreatePetImpl(PetRepository petRepository, FindOwnerDetails findOwnerDetails, PetDtoMapper petDtoMapper) {
		this.petRepository = petRepository;
		this.findOwnerDetails = findOwnerDetails;
		this.petDtoMapper = petDtoMapper;
	}

	@Override
	public PetDto execute(@NotNull Name name, @NotNull BirthDate birthDate, @NotNull PetTypeId petTypeId,
			@NotNull OwnerId ownerId) {
		OwnerDetailsDto owner = findOwnerDetails.byId(ownerId.uuid())
			.orElseThrow(() -> new IllegalArgumentException("Owner not found"));
		Pet pet = Pet.Builder.create(name, birthDate, petTypeId, ownerId,
				new OwnerName(owner.getFirstName(), owner.getLastName()), petRepository);
		Pet savedPet = petRepository.save(pet);
		return petDtoMapper.fromAggregate(savedPet);
	}

}
