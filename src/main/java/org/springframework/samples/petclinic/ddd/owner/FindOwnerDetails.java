package org.springframework.samples.petclinic.ddd.owner;

import org.jmolecules.architecture.layered.InterfaceLayer;
import org.springframework.samples.petclinic.ddd.owner.application.OwnerDetailsDto;

import java.util.Optional;
import java.util.UUID;

@InterfaceLayer
public interface FindOwnerDetails {

	Optional<OwnerDetailsDto> byId(UUID ownerId);

}
