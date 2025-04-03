package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.List; // Add missing import
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.domain.Specialty;
import org.springframework.samples.petclinic.vet.domain.SpecialtyId;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetId;
import org.springframework.samples.petclinic.vet.domain.Vets;
import org.springframework.stereotype.Component; // Using Component for Spring detection, could use @Service too
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link Vets} repository interface using Spring Data JPA.
 */
@Repository // jMolecules annotation
@Component // Spring annotation to make it injectable
public class VetRepositoryImpl implements Vets {

	private final VetJpaRepository vetJpaRepository;
	private final SpecialtyJpaRepository specialtyJpaRepository; // Needed for mapping

	public VetRepositoryImpl(VetJpaRepository vetJpaRepository, SpecialtyJpaRepository specialtyJpaRepository) {
		this.vetJpaRepository = vetJpaRepository;
		this.specialtyJpaRepository = specialtyJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Vet> findById(VetId vetId) {
		return vetJpaRepository.findById(vetId.value()).map(this::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Vet> findAll() {
		return vetJpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vet> findAll(Pageable pageable) {
		Page<VetEntity> vetEntitiesPage = vetJpaRepository.findAll(pageable);
		List<Vet> vets = vetEntitiesPage.getContent().stream().map(this::toDomain).collect(Collectors.toList());
		return new PageImpl<>(vets, pageable, vetEntitiesPage.getTotalElements());
	}

	@Override
	@Transactional
	public Vet save(Vet vet) {
		VetEntity entity = toEntity(vet);
		VetEntity savedEntity = vetJpaRepository.save(entity);
		return toDomain(savedEntity);
	}

	// --- Mapping Methods ---

	private Vet toDomain(VetEntity entity) {
		if (entity == null) {
			return null;
		}
		Set<SpecialtyId> specialtyIds = entity.getSpecialties()
			.stream()
			.map(specEntity -> new SpecialtyId(specEntity.getId()))
			.collect(Collectors.toSet());

		return new Vet(new VetId(entity.getId()), entity.getFirstName(), entity.getLastName(), specialtyIds);
	}

	private VetEntity toEntity(Vet vet) {
		if (vet == null) {
			return null;
		}
		VetEntity entity = vetJpaRepository.findById(vet.getId().value())
				.orElse(new VetEntity(vet.getId().value(), vet.getFirstName(), vet.getLastName()));

		entity.setFirstName(vet.getFirstName());
		entity.setLastName(vet.getLastName());

		// Handle specialties: Fetch existing SpecialtyEntity objects based on SpecialtyId
		Set<SpecialtyEntity> specialtyEntities = new HashSet<>();
		if (vet.getSpecialties() != null) {
			for (SpecialtyId specialtyId : vet.getSpecialties()) {
				specialtyJpaRepository.findById(specialtyId.value())
					.ifPresent(specialtyEntities::add);
				// Consider error handling if a SpecialtyId doesn't map to an existing SpecialtyEntity
			}
		}
		entity.setSpecialties(specialtyEntities);

		return entity;
	}

}
