package org.springframework.samples.petclinic.vet.application.dto; // Updated package

import java.util.UUID;

/**
 * Data Transfer Object for Specialty information.
 */
public record SpecialtyDto(UUID id, String name) {

}
