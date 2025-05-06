package org.springframework.samples.petclinic.ddd.owner.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.owner.domain.Owner;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class OwnerRepositoryImpl implements OwnerRepository {

	private final OwnerJpaRepository ownerJpaRepository;

	private final OwnerEntityMapper ownerEntityMapper;

	OwnerRepositoryImpl(OwnerJpaRepository ownerJpaRepository, OwnerEntityMapper ownerEntityMapper) {
		this.ownerJpaRepository = ownerJpaRepository;
		this.ownerEntityMapper = ownerEntityMapper;
	}

	@Override
	public Page<Owner> findByLastNameStartingWith(String lastName, Pageable pageable) {
		return ownerJpaRepository.findByNameLastNameStartingWith(lastName, pageable)
			.map(ownerEntityMapper::toAggregate);
	}

	@Override
	public Optional<Owner> findById(OwnerId id) {
		return ownerJpaRepository.findById(id).map(ownerEntityMapper::toAggregate);
	}

	@Override
	public Page<Owner> findAll(Pageable pageable) {
		return ownerJpaRepository.findAll(pageable).map(ownerEntityMapper::toAggregate);
	}

	@Override
	public Owner save(Owner owner) {
		OwnerEntity savedOwner = ownerJpaRepository.save(ownerEntityMapper.fromAggregate(owner));
		return ownerEntityMapper.toAggregate(savedOwner);
	}

}
