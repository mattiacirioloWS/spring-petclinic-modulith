package org.springframework.samples.petclinic.ddd.vet.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.samples.petclinic.ddd.vet.domain.VetRepository;

import java.util.List;

@Query
class FindVetsImpl implements FindVets {

	private final VetRepository vetRepository;

	private final VetDtoMapper vetDtoMapper;

	FindVetsImpl(VetRepository vetRepository, VetDtoMapper vetDtoMapper) {
		this.vetRepository = vetRepository;
		this.vetDtoMapper = vetDtoMapper;
	}

	@Override
	public List<VetDto> findAll() {
		return vetRepository.findAll().stream().map(vetDtoMapper::fromAggregate).toList();
	}

	@Override
	public Page<VetDto> findAll(@NotNull Pageable pageable) {
		return vetRepository.findAll(pageable).map(vetDtoMapper::fromAggregate);
	}

}
