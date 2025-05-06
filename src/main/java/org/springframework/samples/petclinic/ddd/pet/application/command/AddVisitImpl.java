package org.springframework.samples.petclinic.ddd.pet.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.pet.application.VisitDto;
import org.springframework.samples.petclinic.ddd.pet.application.VisitDtoMapper;
import org.springframework.samples.petclinic.ddd.pet.domain.Pet;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetRepository;
import org.springframework.samples.petclinic.ddd.pet.domain.Visit;

import java.time.LocalDate;

@Command
public class AddVisitImpl implements AddVisit {

	private final PetRepository petRepository;

	private final VisitDtoMapper visitDtoMapper;

	public AddVisitImpl(PetRepository petRepository, VisitDtoMapper visitDtoMapper) {
		this.petRepository = petRepository;
		this.visitDtoMapper = visitDtoMapper;
	}

	@Override
	public VisitDto execute(@NotNull PetId petId, LocalDate date, @NotBlank String description) {
		Pet pet = petRepository.findById(petId).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
		Visit newVisit = pet.addVisit(date, description);
		petRepository.save(pet);
		return visitDtoMapper.fromAggregate(newVisit);
	}

}
