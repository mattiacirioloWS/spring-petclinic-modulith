package org.springframework.samples.petclinic.ddd.owner.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.owner.domain.FullAddress;
import org.springframework.samples.petclinic.ddd.owner.domain.PersonName;
import org.springframework.samples.petclinic.ddd.owner.domain.Telephone;

public interface CreateOwner {

	OwnerDto execute(@NotNull PersonName personName, @NotNull FullAddress fullAddress, @NotNull Telephone telephone);

}
