package org.springframework.samples.petclinic.vet.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VetRepository {

	Optional<Vet> findById(VetId id);

	List<Vet> findAll();

	Page<Vet> findAll(Pageable pageable);

	List<Vet> findBySpecialty(Specialty specialty);

}
