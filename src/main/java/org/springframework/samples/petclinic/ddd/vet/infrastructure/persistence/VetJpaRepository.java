package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.ddd.vet.domain.VetId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

interface VetJpaRepository extends JpaRepository<VetEntity, VetId> {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	@Transactional(readOnly = true)
	@Cacheable("vets")
	List<VetEntity> findAll() throws DataAccessException;

	/**
	 * Retrieve all <code>Vet</code>s from data store in Pages
	 * @param pageable
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	@Cacheable("vets")
	Page<VetEntity> findAll(Pageable pageable) throws DataAccessException;

}
