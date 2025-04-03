package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the {@link VetEntity} entity.
 */
public interface VetJpaRepository extends JpaRepository<VetEntity, UUID> {

	/**
	 * Retrieve all {@link VetEntity}s from the data store.
	 * @param pageable the pagination information
	 * @return A page of vets.
	 */
	@Transactional(readOnly = true)
	@Query("SELECT vet FROM VetEntity vet")
	Page<VetEntity> findAll(Pageable pageable);

}
