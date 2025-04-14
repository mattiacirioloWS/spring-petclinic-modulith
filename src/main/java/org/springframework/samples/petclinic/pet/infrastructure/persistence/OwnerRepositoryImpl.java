package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.pet.domain.owner.Owner;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;
import org.springframework.samples.petclinic.pet.domain.owner.OwnerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional(readOnly = true)
class OwnerRepositoryImpl implements OwnerRepository {

	private final JpaOwnerRepository jpaOwnerRepository;

	OwnerRepositoryImpl(JpaOwnerRepository jpaOwnerRepository) {
		this.jpaOwnerRepository = jpaOwnerRepository;
	}

	@Override
	public Page<Owner> findByLastNameStartingWith(String lastNamePrefix, Pageable pageable) {
		return jpaOwnerRepository.findByLastNameStartingWith(lastNamePrefix, pageable).map(reconstituteOwner());
	}

	@Override
	public Optional<Owner> findById(OwnerId id) {
		return jpaOwnerRepository.findById(id.toUUID()).map(reconstituteOwner());
	}

	@Override
	public Owner save(Owner owner) {
		OwnerEntity savedOwnerEntity = jpaOwnerRepository.save(fromAggregate(owner));
		return reconstituteOwner().apply(savedOwnerEntity);
	}

	private Function<OwnerEntity, Owner> reconstituteOwner() {
		return entity -> Owner.reconstitute(entity.getId(), entity.getFirstName(), entity.getLastName(),
				entity.getAddress(), entity.getCity(), entity.getTelephone());
	}

	private OwnerEntity fromAggregate(Owner owner) {
		return new OwnerEntity(owner.getId().toUUID(), owner.getFirstname().toString(), owner.getLastname().toString(),
				owner.getAddress().toString(), owner.getCity().toString(), owner.getPhone().toString());
	}

}
