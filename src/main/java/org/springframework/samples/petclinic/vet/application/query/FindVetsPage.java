package org.springframework.samples.petclinic.vet.application.query;

import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.vet.application.VetDto;

public interface FindVetsPage {

	Page<VetDto> execute(int page, int size);

}
