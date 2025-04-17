package org.springframework.samples.petclinic.ddd.vet.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface VetRepository {

	List<Vet> findAll();

	Page<Vet> findAll(Pageable pageable);

}
