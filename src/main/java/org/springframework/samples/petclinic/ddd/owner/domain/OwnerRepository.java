package org.springframework.samples.petclinic.ddd.owner.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface OwnerRepository {

	Page<Owner> findByLastNameStartingWith(String lastName, Pageable pageable);

	Optional<Owner> findById(OwnerId id);

	Page<Owner> findAll(Pageable pageable);

	Owner save(Owner owner);

}
