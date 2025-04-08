package org.springframework.samples.petclinic.vet.application.query;

import org.springframework.samples.petclinic.vet.application.VetDto;
import org.springframework.samples.petclinic.vet.domain.VetRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.common.application.annotation.Query;

import java.util.List;

@Query
@Transactional(readOnly = true)
class FindAllVetsImpl implements FindAllVets {

	private final VetRepository vetRepository;

	public FindAllVetsImpl(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	public List<VetDto> execute() {
		return vetRepository.findAll().stream().map(VetDto::from).toList();
	}

}
