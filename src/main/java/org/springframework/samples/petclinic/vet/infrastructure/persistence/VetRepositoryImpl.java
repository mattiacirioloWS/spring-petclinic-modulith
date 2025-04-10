package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
class VetRepositoryImpl implements VetRepository {

	private final JpaVetRepository jpaVetRepository;

	public VetRepositoryImpl(JpaVetRepository jpaVetRepository) {
		this.jpaVetRepository = jpaVetRepository;
	}

	@Override
	public Optional<Vet> findById(VetId id) {
		return jpaVetRepository.findById(id.toUUID()).map(this::toVet);
	}

	@Override
	public List<Vet> findAll() {
		return jpaVetRepository.findAll().stream().map(this::toVet).collect(Collectors.toList());
	}

	@Override
	public Page<Vet> findAll(Pageable pageable) {
		return jpaVetRepository.findAll(pageable).map(this::toVet);
	}

	@Override
	public List<Vet> findBySpecialty(Specialty specialty) {
		return jpaVetRepository.findBySpecialties_Name(specialty.getName())
			.stream()
			.map(this::toVet)
			.collect(Collectors.toList());
	}

	private Vet toVet(VetEntity entity) {
		return Vet.reconstitute(new VetId(entity.getId()), new VetName(entity.getFirstName(), entity.getLastName()),
				entity.getSpecialties()
					.stream()
					.map(specialty -> new Specialty(new SpecialtyId(specialty.getId()),
							new SpecialtyName(specialty.getName())))
					.collect(Collectors.toSet()));
	}

}
