package org.springframework.samples.petclinic.pet.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.pet.application.OwnerDto;

import java.util.Optional;
import java.util.UUID;

public interface QueryOwners {

	Optional<OwnerDto> findById(UUID ownerId);

	Page<OwnerDto> findByLastNameStartingWith(String lastname, Pageable pageable);

}
