package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.ddd.pet.domain.Name;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetTypeId;

import java.util.List;
import java.util.Optional;

interface PetJpaRepository extends JpaRepository<PetEntity, PetId> {

	/**
	 * Retrieve all {@link PetTypeEntity}s from the data store.
	 * @return a Collection of {@link PetTypeEntity}s.
	 */
	@Query("SELECT ptype FROM PetTypeEntity ptype ORDER BY ptype.name")
	List<PetTypeEntity> findAllPetTypes();

	List<PetEntity> findAllByOwnerId(OwnerId ownerId);

	Optional<PetEntity> findByIdAndOwnerId(PetId petId, OwnerId ownerId);

	@Query("SELECT ptype FROM PetTypeEntity ptype WHERE ptype.id = :petTypeId")
	Optional<PetTypeEntity> findPetTypeById(PetTypeId petTypeId);

	Boolean existsByOwnerIdAndNameAndIdNot(OwnerId ownerId, Name name, PetId id);

}
