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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.pet.application.ClinicService;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
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
@SpringBootTest
// Ensure that if the mysql profile is active we connect to the real database:
@AutoConfigureTestDatabase(replace = Replace.NONE)
// @TestPropertySource("/application-postgres.properties")
class ClinicServiceTests {

	@Autowired
	protected JpaOwnerRepository owners;

	@Autowired
	protected JpaPetRepository pets;

	@Autowired
	protected ClinicService clinicService;

	@Test
	@Transactional
	void shouldInsertPetIntoDatabaseAndGenerateId() {
		final UUID ownerId = UUID.fromString("66666666-6666-6666-6666-666666666666");

		int found = pets.findByOwnerId(ownerId).size();

		PetEntity petEntity = new PetEntity();
		petEntity.setName("bowser");
		Collection<PetTypeEntity> types = this.pets.findPetTypes();
		petEntity.setType(EntityUtils.getById(types, PetTypeEntity.class,
				UUID.fromString("22222222-2222-2222-2222-222222222222")));
		petEntity.setBirthDate(LocalDate.now());
		petEntity.setOwnerId(ownerId);
		PetEntity savedPetEntity = clinicService.savePet(petEntity);

		Optional<OwnerEntity> optionalOwner = this.owners.findById(ownerId);
		assertThat(optionalOwner).isPresent();
		OwnerEntity ownerEntity6 = optionalOwner.get();
		assertThat(ownerEntity6.getPetIds()).hasSize(found + 1);
		assertThat(ownerEntity6.getPetIds()).contains(savedPetEntity.getId());
		// checks that id has been generated
		assertThat(savedPetEntity.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldUpdatePetName() {
		Optional<OwnerEntity> optionalOwner = this.owners
			.findById(UUID.fromString("66666666-6666-6666-6666-666666666666"));
		assertThat(optionalOwner).isPresent();
		OwnerEntity ownerEntity6 = optionalOwner.get();

		int found = ownerEntity6.getPetIds().size();

		Optional<PetEntity> optionalPet7 = pets
			.findByIdAndOwnerId(UUID.fromString("77777777-7777-7777-7777-777777777777"), ownerEntity6.getId());
		assertThat(optionalPet7).isPresent();

		PetEntity petEntity7 = optionalPet7.get();
		String oldName = petEntity7.getName();

		String newName = oldName + "X";
		petEntity7.setName(newName);

		this.clinicService.savePet(petEntity7);

		optionalOwner = this.owners.findById(UUID.fromString("66666666-6666-6666-6666-666666666666"));
		assertThat(optionalOwner).isPresent();
		ownerEntity6 = optionalOwner.get();
		assertThat(ownerEntity6.getPetIds()).hasSize(found);
		petEntity7 = clinicService.findByIdAndOwnerId(petEntity7.getId(), ownerEntity6.getId()).get();
		assertThat(petEntity7.getName()).isEqualTo(newName);
	}

}
