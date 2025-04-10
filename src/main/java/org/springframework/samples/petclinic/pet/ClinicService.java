package org.springframework.samples.petclinic.pet;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicService {

	private final OwnerRepository ownerRepository;

	private final PetRepository petRepository;

	public ClinicService(OwnerRepository ownerRepository, PetRepository petRepository) {
		this.ownerRepository = ownerRepository;
		this.petRepository = petRepository;
	}

	public List<PetType> findPetTypes() {
		return petRepository.findPetTypes();
	}

	public Optional<Owner> findOwnerById(UUID ownerId) {
		return ownerRepository.findById(ownerId);
	}

	public Optional<Pet> findByIdAndOwnerId(UUID petId, UUID ownerId) {
		return petRepository.findByIdAndOwnerId(petId, ownerId);
	}

	public Boolean existsByNameAndOwnerId(String name, UUID id) {
		return petRepository.existsByNameAndOwnerId(name, id);
	}

	public Pet savePet(Pet pet) {
		Pet savedPet = petRepository.save(pet);

		Owner owner = ownerRepository.findById(pet.getOwnerId())
			.orElseThrow(() -> new IllegalArgumentException(
					"Owner not found with id: " + pet.getOwnerId() + ". Please ensure the ID is correct "));
		owner.addPet(savedPet.getId());
		ownerRepository.save(owner);
		return savedPet;
	}

	public List<Pet> findPetsByOwnerId(UUID ownerId) {
		return petRepository.findByOwnerId(ownerId);
	}

}
