package org.springframework.samples.petclinic.ddd.pet.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.pet.application.VisitDto;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;

import java.time.LocalDate;

public interface AddVisit {

	VisitDto execute(@NotNull PetId petId, LocalDate date, @NotBlank String description);

}
