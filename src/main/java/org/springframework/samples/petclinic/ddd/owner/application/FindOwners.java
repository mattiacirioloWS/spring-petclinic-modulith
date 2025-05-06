package org.springframework.samples.petclinic.ddd.owner.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;

import java.util.Optional;

public interface FindOwners {

	Optional<OwnerDto> findById(@NotNull OwnerId ownerId);

	Page<OwnerDto> findByLastNameStartingWith(@NotNull String lastname, @NotNull Pageable pageable);

}
