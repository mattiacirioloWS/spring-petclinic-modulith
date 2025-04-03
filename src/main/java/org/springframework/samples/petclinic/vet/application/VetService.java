package org.springframework.samples.petclinic.vet.application;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.domain.Specialties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.application.dto.SpecialtyDto; // Updated import
import org.springframework.samples.petclinic.vet.application.dto.VetDto; // Updated import
import org.springframework.samples.petclinic.vet.domain.Specialties;
import org.springframework.samples.petclinic.vet.domain.Specialty;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for managing Vet-related use cases.
 */
@Service
public class VetService {

	private final Vets vetsRepository;
	private final Specialties specialtiesRepository; // Needed for mapping Specialty details

	public VetService(Vets vetsRepository, Specialties specialtiesRepository) {
		this.vetsRepository = vetsRepository;
		this.specialtiesRepository = specialtiesRepository;
	}

	/**
	 * Finds all vets and returns them as DTOs.
	 * @return A collection of VetDto.
	 */
	@Transactional(readOnly = true)
	public Collection<VetDto> findAllVets() {
		Collection<Vet> vets = vetsRepository.findAll();
		return vets.stream().map(this::toVetDto).collect(Collectors.toList());
	}

	/**
	 * Finds a paginated list of vets and returns them as DTOs.
	 * @param pageable Pagination information.
	 * @return A page of VetDto.
	 */
	@Transactional(readOnly = true)
	public Page<VetDto> findPaginatedVets(Pageable pageable) {
		Page<Vet> vetPage = vetsRepository.findAll(pageable);
		List<VetDto> vetDtos = vetPage.getContent().stream().map(this::toVetDto).collect(Collectors.toList());
		return new PageImpl<>(vetDtos, pageable, vetPage.getTotalElements());
	}

	// --- Mapping Methods ---

	private VetDto toVetDto(Vet vet) {
		if (vet == null) {
			return null;
		}

		// Fetch Specialty domain objects based on IDs to get names for the DTO
		Collection<Specialty> specialties = specialtiesRepository.findByIds(vet.getSpecialties());
		List<SpecialtyDto> specialtyDtos = specialties.stream()
			.map(this::toSpecialtyDto)
			.sorted((s1, s2) -> s1.name().compareTo(s2.name())) // Ensure consistent order
			.collect(Collectors.toList());

		return new VetDto(
			vet.getId().value(),
			vet.getFirstName(),
			vet.getLastName(),
			specialtyDtos,
			vet.getNrOfSpecialties()
		);
	}

	private SpecialtyDto toSpecialtyDto(Specialty specialty) {
		if (specialty == null) {
			return null;
		}
		return new SpecialtyDto(specialty.getId().value(), specialty.getName());
	}

}
