/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code> </code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */
@DataJpaTest
// Ensure that if the mysql profile is active we connect to the real database:
@AutoConfigureTestDatabase(replace = Replace.NONE)
// @TestPropertySource("/application-postgres.properties")
class ClinicRepositoriesTests {

	@Autowired
	protected JpaOwnerRepository owners;

	@Autowired
	protected JpaPetRepository pets;

	Pageable pageable;

	@Test
	void shouldFindOwnersByLastName() {
		Page<OwnerEntity> owners = this.owners.findByLastNameStartingWith("Davis", pageable);
		assertThat(owners).hasSize(2);

		owners = this.owners.findByLastNameStartingWith("Daviss", pageable);
		assertThat(owners).isEmpty();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Optional<OwnerEntity> optionalOwner = this.owners
			.findById(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		assertThat(optionalOwner).isPresent();
		OwnerEntity ownerEntity = optionalOwner.get();
		assertThat(ownerEntity.getLastName()).startsWith("Franklin");

		assertThat(ownerEntity.getPetIds()).hasSize(1);
		List<PetEntity> petsByOwner = pets.findByOwnerId(ownerEntity.getId());
		assertThat(petsByOwner).hasSize(1);
		assertThat(petsByOwner.get(0).getType()).isNotNull();
		assertThat(petsByOwner.get(0).getType().getName()).isEqualTo("cat");
	}

	@Test
	@Transactional
	void shouldInsertOwner() {
		Page<OwnerEntity> owners = this.owners.findByLastNameStartingWith("Schultz", pageable);
		int found = (int) owners.getTotalElements();

		OwnerEntity ownerEntity = new OwnerEntity();
		ownerEntity.setFirstName("Sam");
		ownerEntity.setLastName("Schultz");
		ownerEntity.setAddress("4, Evans Street");
		ownerEntity.setCity("Wollongong");
		ownerEntity.setTelephone("4444444444");
		this.owners.save(ownerEntity);
		assertThat(ownerEntity.getId()).isNotNull();

		owners = this.owners.findByLastNameStartingWith("Schultz", pageable);
		assertThat(owners.getTotalElements()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Optional<OwnerEntity> optionalOwner = this.owners
			.findById(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		assertThat(optionalOwner).isPresent();
		OwnerEntity ownerEntity = optionalOwner.get();
		String oldLastName = ownerEntity.getLastName();
		String newLastName = oldLastName + "X";

		ownerEntity.setLastName(newLastName);
		this.owners.save(ownerEntity);

		// retrieving new name from database
		optionalOwner = this.owners.findById(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		assertThat(optionalOwner).isPresent();
		ownerEntity = optionalOwner.get();
		assertThat(ownerEntity.getLastName()).isEqualTo(newLastName);
	}

	@Test
	void shouldFindAllPetTypes() {
		Collection<PetTypeEntity> petTypeEntities = this.pets.findPetTypes();

		PetTypeEntity petTypeEntity1 = EntityUtils.getById(petTypeEntities, PetTypeEntity.class,
				UUID.fromString("11111111-1111-1111-1111-111111111111"));
		assertThat(petTypeEntity1.getName()).isEqualTo("cat");
		PetTypeEntity petTypeEntity4 = EntityUtils.getById(petTypeEntities, PetTypeEntity.class,
				UUID.fromString("44444444-4444-4444-4444-444444444444"));
		assertThat(petTypeEntity4.getName()).isEqualTo("snake");
	}

	@Test
	@Transactional
	void shouldAddNewVisitForPet() {
		Optional<PetEntity> optionalPet7 = pets.findByIdAndOwnerId(
				UUID.fromString("77777777-7777-7777-7777-777777777777"),
				UUID.fromString("66666666-6666-6666-6666-666666666666"));
		assertThat(optionalPet7).isPresent();
		PetEntity petEntity7 = optionalPet7.get();

		int found = petEntity7.getVisits().size();
		VisitEntity visitEntity = new VisitEntity();
		visitEntity.setDescription("test");

		petEntity7.addVisit(visitEntity);
		this.pets.save(petEntity7);

		assertThat(petEntity7.getVisits()) //
			.hasSize(found + 1) //
			.allMatch(value -> value.getId() != null);
	}

	@Test
	void shouldFindVisitsByPetId() {
		Optional<PetEntity> optionalPet7 = pets.findByIdAndOwnerId(
				UUID.fromString("77777777-7777-7777-7777-777777777777"),
				UUID.fromString("66666666-6666-6666-6666-666666666666"));
		assertThat(optionalPet7).isPresent();

		PetEntity petEntity7 = optionalPet7.get();
		Collection<VisitEntity> visitEntities = petEntity7.getVisits();

		assertThat(visitEntities) //
			.hasSize(2) //
			.allMatch(visit -> visit.getDate() != null);
	}

}
