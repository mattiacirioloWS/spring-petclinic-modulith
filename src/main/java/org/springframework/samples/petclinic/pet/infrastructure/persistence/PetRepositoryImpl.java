package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.pet.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
class PetRepositoryImpl implements PetRepository {

	private final JpaPetRepository jpaPetRepository;

	PetRepositoryImpl(JpaPetRepository jpaPetRepository) {
		this.jpaPetRepository = jpaPetRepository;
	}

	@Override
	public List<PetType> findPetTypes() {
		return jpaPetRepository.findPetTypes()
			.stream()
			.map(type -> PetType.reconstitute(type.getId(), type.getName()))
			.toList();
	}

	@Override
	public Boolean existsByIdAndOwnerId(PetId id, OwnerId ownerId) {
		return jpaPetRepository.existsByIdAndOwnerId(id.toUUID(), ownerId.toUUID());
	}

	@Override
	public Optional<Pet> findById(PetId petId) {
		return jpaPetRepository.findById(petId.toUUID()).map(reconstitutePet());
	}

	@Override
	public List<Pet> findPetsByOwnerId(OwnerId ownerId) {
		return jpaPetRepository.findByOwnerId(ownerId.toUUID()).stream().map(reconstitutePet()).toList();
	}

	@Override
	public Optional<Pet> findByIdAndOwnerId(PetId petId, OwnerId ownerId) {
		return jpaPetRepository.findByIdAndOwnerId(petId.toUUID(), ownerId.toUUID()).map(reconstitutePet());
	}

	@Override
	public Optional<Pet> findByNameAndOwnerId(Name name, OwnerId ownerId) {
		return jpaPetRepository.findByNameAndOwnerId(name.toString(), ownerId.toUUID()).map(reconstitutePet());
	}

	@Override
	@Transactional
	public Pet save(Pet pet) {
		PetEntity petEntity = petEntityFromAggregate(pet);
		PetEntity savedPetEntityEntity = jpaPetRepository.save(petEntity);
		return reconstitutePet().apply(savedPetEntityEntity);
	}

	private PetEntity petEntityFromAggregate(Pet pet) {
		return new PetEntity(pet.getId().toUUID(), pet.getName(), pet.getBirthDate(),
				petTypeEntityFromAggregate(pet.getPetType()),
				pet.getVisits().stream().map(visitEntityFromAggregate()).collect(Collectors.toSet()),
				pet.getOwnerId().toUUID());
	}

	private PetTypeEntity petTypeEntityFromAggregate(PetType petType) {
		return new PetTypeEntity(petType.id().toUUID(), petType.name().toString());
	}

	private Function<Visit, VisitEntity> visitEntityFromAggregate() {
		return visit -> new VisitEntity(visit.getId().toUUID(), visit.getDate(), visit.getDescription());
	}

	private Function<PetEntity, Pet> reconstitutePet() {
		return pet -> Pet.reconstitute(pet.getId(), pet.getName(), pet.getBirthDate(),
				PetType.reconstitute(pet.getType().getId(), pet.getType().getName()),
				pet.getVisits()
					.stream()
					.map(visit -> Visit.reconstitute(visit.getId(), pet.getId(), visit.getDate(),
							visit.getDescription()))
					.collect(Collectors.toSet()),
				pet.getOwnerId());
	}

}
