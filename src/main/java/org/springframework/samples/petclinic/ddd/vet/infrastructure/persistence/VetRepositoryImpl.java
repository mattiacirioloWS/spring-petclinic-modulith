package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;
import org.springframework.samples.petclinic.ddd.vet.domain.VetRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
class VetRepositoryImpl implements VetRepository {

	private final VetJpaRepository vetJpaRepository;

	private final VetEntityMapper vetEntityMapper;

	public VetRepositoryImpl(VetJpaRepository vetJpaRepository, VetEntityMapper vetEntityMapper) {
		this.vetJpaRepository = vetJpaRepository;
		this.vetEntityMapper = vetEntityMapper;
	}

	@Override
	public List<Vet> findAll() {
		return vetJpaRepository.findAll().stream().map(vetEntityMapper::toAggregate).toList();
	}

	@Override
	public Page<Vet> findAll(Pageable pageable) {
		return vetJpaRepository.findAll(pageable).map(vetEntityMapper::toAggregate);
	}

}
