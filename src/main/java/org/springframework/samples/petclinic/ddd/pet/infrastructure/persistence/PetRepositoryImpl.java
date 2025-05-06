package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import org.springframework.samples.petclinic.ddd.pet.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class PetRepositoryImpl implements PetRepository {

	private final PetJpaRepository petJpaRepository;

	private final PetTypeEntityMapper petTypeEntityMapper;

	private final PetEntityMapper petEntityMapper;

	private final VisitEntityMapper visitEntityMapper;

	PetRepositoryImpl(PetJpaRepository petJpaRepository, PetTypeEntityMapper petTypeEntityMapper,
			PetEntityMapper petEntityMapper, VisitEntityMapper visitEntityMapper) {
		this.petJpaRepository = petJpaRepository;
		this.petTypeEntityMapper = petTypeEntityMapper;
		this.petEntityMapper = petEntityMapper;
		this.visitEntityMapper = visitEntityMapper;
	}

	@Override
	public List<PetType> findAllPetTypes() {
		return petJpaRepository.findAllPetTypes().stream().map(petTypeEntityMapper::toAggregate).toList();
	}

	@Override
	public List<Pet> findPetsByOwnerId(OwnerId ownerId) {
		return petJpaRepository.findAllByOwnerId(ownerId).stream().map(this::toAggregate).toList();
	}

	@Override
	public Optional<Pet> findByIdAndOwnerId(PetId petId, OwnerId ownerId) {
		return petJpaRepository.findByIdAndOwnerId(petId, ownerId).map(this::toAggregate);
	}

	@Override
	public Optional<PetType> findTypeById(PetTypeId typeId) {
		return petJpaRepository.findPetTypeById(typeId).map(petTypeEntityMapper::toAggregate);
	}

	@Override
	public Pet save(Pet pet) {
		PetEntity petEntity = petEntityMapper.fromAggregate(pet);
		PetEntity savedPetEntity = petJpaRepository.save(petEntity);
		return toAggregate(savedPetEntity);
	}

	@Override
	public Optional<Pet> findById(PetId petId) {
		return petJpaRepository.findById(petId).map(this::toAggregate);
	}

	@Override
	public Boolean hasOwnerAnotherPetWIthSameName(OwnerId ownerId, PetId id, Name name) {
		return petJpaRepository.existsByOwnerIdAndNameAndIdNot(ownerId, name, id);
	}

	private Pet toAggregate(PetEntity petEntity) {
		return Pet.Builder.reconstitute(petEntity.getId(), petEntity.getName(), petEntity.getBirthDate(),
				petTypeEntityMapper.toAggregate(petEntity.getType()),
				petEntity.getVisits().stream().map(visitEntityMapper::toAggregate).toList(), petEntity.getOwnerId(),
				petEntity.getOwnerName(), this);
	}

}
