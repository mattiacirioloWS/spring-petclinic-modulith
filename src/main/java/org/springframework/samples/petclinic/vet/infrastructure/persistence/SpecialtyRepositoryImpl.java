package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.samples.petclinic.vet.domain.Specialties;
import org.springframework.samples.petclinic.vet.domain.Specialty;
import org.springframework.samples.petclinic.vet.domain.SpecialtyId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link Specialties} repository interface using Spring Data JPA.
 */
@Repository // jMolecules annotation
@Component // Spring annotation
public class SpecialtyRepositoryImpl implements Specialties {

	private final SpecialtyJpaRepository specialtyJpaRepository;

	public SpecialtyRepositoryImpl(SpecialtyJpaRepository specialtyJpaRepository) {
		this.specialtyJpaRepository = specialtyJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Specialty> findById(SpecialtyId specialtyId) {
		return specialtyJpaRepository.findById(specialtyId.value()).map(this::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Specialty> findAll() {
		return specialtyJpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Specialty> findByIds(Set<SpecialtyId> specialtyIds) {
		List<UUID> ids = specialtyIds.stream().map(SpecialtyId::value).collect(Collectors.toList());
		return specialtyJpaRepository.findAllById(ids).stream().map(this::toDomain).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Specialty save(Specialty specialty) {
		SpecialtyEntity entity = toEntity(specialty);
		SpecialtyEntity savedEntity = specialtyJpaRepository.save(entity);
		return toDomain(savedEntity);
	}

	// --- Mapping Methods ---

	private Specialty toDomain(SpecialtyEntity entity) {
		if (entity == null) {
			return null;
		}
		return new Specialty(new SpecialtyId(entity.getId()), entity.getName());
	}

	private SpecialtyEntity toEntity(Specialty specialty) {
		if (specialty == null) {
			return null;
		}
		// Find existing or create new
		SpecialtyEntity entity = specialtyJpaRepository.findById(specialty.getId().value())
				.orElse(new SpecialtyEntity(specialty.getId().value(), specialty.getName()));
		// Update name in case it changed
		entity.setName(specialty.getName());
		return entity;
	}

}
