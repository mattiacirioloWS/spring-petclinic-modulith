package org.springframework.samples.petclinic.ddd.owner.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.owner.domain.FullAddress;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.owner.domain.PersonName;
import org.springframework.samples.petclinic.ddd.owner.domain.Telephone;

public interface UpdateOwner {

	OwnerDto execute(@NotNull OwnerId ownerId, @NotNull PersonName personName, @NotNull FullAddress fullAddress,
			@NotNull Telephone telephone);

}
