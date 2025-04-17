package org.springframework.samples.petclinic.ddd.vet.application;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindVets {

	List<VetDto> findAll();

	Page<VetDto> findAll(@NotNull Pageable pageable);

}
