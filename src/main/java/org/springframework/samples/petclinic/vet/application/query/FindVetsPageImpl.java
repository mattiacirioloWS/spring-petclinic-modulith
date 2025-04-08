package org.springframework.samples.petclinic.vet.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.application.VetDto;
import org.springframework.samples.petclinic.vet.domain.VetRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.common.application.annotation.Query;

@Query
@Transactional(readOnly = true)
class FindVetsPageImpl implements FindVetsPage {

	private final VetRepository vetRepository;

	public FindVetsPageImpl(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	public Page<VetDto> execute(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return vetRepository.findAll(pageable).map(VetDto::from);
	}

}
