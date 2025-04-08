package org.springframework.samples.petclinic.vet.application;

import org.springframework.samples.petclinic.vet.domain.Specialty;

import java.io.Serializable;
import java.util.UUID;

public record SpecialtyDto(UUID id, String name) implements Serializable {
	public static SpecialtyDto from(Specialty specialty) {
		return new SpecialtyDto(specialty.id().toUUID(), specialty.getName());
	}
}