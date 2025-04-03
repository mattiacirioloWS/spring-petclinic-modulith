package org.springframework.samples.petclinic.vet.domain;

import java.util.Collection;
import java.util.Optional;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository interface for managing {@link Vet} aggregates.
 */
@Repository
public interface Vets {

	/**
	 * Retrieves a vet by its identifier.
	 *
	 * @param vetId must not be {@literal null}.
	 * @return the vet with the given id or {@literal Optional#empty()} if none found.
	 */
	Optional<Vet> findById(VetId vetId);

	/**
	 * Retrieves all vets.
	 *
	 * @return all vets.
	 */
	Collection<Vet> findAll();

	/**
	 * Retrieves a {@link Page} of vets meeting the paging restriction provided in the {@code Pageable} object.
	 *
	 * @param pageable the page settings.
	 * @return a page of vets.
	 */
	Page<Vet> findAll(Pageable pageable);

	/**
	 * Saves a given vet. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 *
	 * @param vet must not be {@literal null}.
	 * @return the saved vet will never be {@literal null}.
	 */
	Vet save(Vet vet);

}
