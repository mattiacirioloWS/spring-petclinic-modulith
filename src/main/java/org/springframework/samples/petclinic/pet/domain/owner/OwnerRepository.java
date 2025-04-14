package org.springframework.samples.petclinic.pet.domain.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OwnerRepository {

	Page<Owner> findByLastNameStartingWith(String lastNamePrefix, Pageable pageable);

	Optional<Owner> findById(OwnerId id);

	Owner save(Owner owner);

}
