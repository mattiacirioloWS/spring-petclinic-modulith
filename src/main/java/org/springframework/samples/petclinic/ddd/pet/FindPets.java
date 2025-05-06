package org.springframework.samples.petclinic.ddd.pet;

import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.layered.InterfaceLayer;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;

import java.util.List;
import java.util.UUID;

@InterfaceLayer
public interface FindPets {

	List<PetDto> byOwnerId(@NotNull UUID ownerId);

}
