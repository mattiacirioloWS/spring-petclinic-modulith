package org.springframework.samples.petclinic.vet.application.dto; // Updated package

import java.util.List;
import java.util.UUID;
import org.springframework.samples.petclinic.vet.application.dto.SpecialtyDto; // Updated import

/**
 * Data Transfer Object for Vet information, including specialties.
 */
public record VetDto(
    UUID id,
    String firstName,
    String lastName,
    List<SpecialtyDto> specialties,
    int nrOfSpecialties
) {

}
