package org.springframework.samples.petclinic.vet.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.jmolecules.ddd.annotation.Repository;

/**
 * Repository interface for managing {@link Specialty} entities.
 * While Specialty is part of the Vet aggregate, a separate repository
 * can be useful for lookups, especially for mapping purposes.
 */
@Repository
public interface Specialties {

    /**
     * Retrieves a specialty by its unique identifier.
     * @param specialtyId must not be {@literal null}.
     * @return the specialty with the given id or {@literal Optional#empty()} if none found.
     */
    Optional<Specialty> findById(SpecialtyId specialtyId);

    /**
     * Retrieves all specialties.
     * @return all specialties.
     */
    Collection<Specialty> findAll();

    /**
     * Retrieves specialties by their unique identifiers.
     * @param specialtyIds must not be {@literal null}.
     * @return a collection of specialties matching the given ids.
     */
    Collection<Specialty> findByIds(Set<SpecialtyId> specialtyIds);

    /**
     * Saves a given specialty.
     * @param specialty must not be {@literal null}.
     * @return the saved specialty.
     */
    Specialty save(Specialty specialty);

}
