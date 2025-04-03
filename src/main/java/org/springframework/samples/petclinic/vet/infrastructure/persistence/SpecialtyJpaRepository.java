package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link SpecialtyEntity} entity.
 */
public interface SpecialtyJpaRepository extends JpaRepository<SpecialtyEntity, UUID> {

}
