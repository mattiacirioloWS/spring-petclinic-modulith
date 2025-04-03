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
package org.springframework.samples.petclinic.vet; // Keep original package for test structure

import java.util.HashSet;
import java.util.Objects; // Needed for class-based equals/hashCode
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.vet.domain.SpecialtyId; // Import domain ID
import org.springframework.samples.petclinic.vet.domain.Vet; // Import domain Vet
import org.springframework.samples.petclinic.vet.domain.VetId; // Import domain ID

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the {@link Vet} domain object.
 * Focuses on domain logic, not persistence or framework concerns.
 * @author Dave Syer
 * @author Cline (Refactored for DDD)
 */
class VetTests {

	@Test
	void createValidVet() {
		VetId vetId = new VetId(UUID.randomUUID());
		String firstName = "Zaphod";
		String lastName = "Beeblebrox";
		Set<SpecialtyId> specialties = new HashSet<>();

		Vet vet = new Vet(vetId, firstName, lastName, specialties);

		assertThat(vet.getId()).isEqualTo(vetId);
		assertThat(vet.getFirstName()).isEqualTo(firstName);
		assertThat(vet.getLastName()).isEqualTo(lastName);
		assertThat(vet.getSpecialties()).isNotNull().isEmpty();
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
	}

	@Test
	void createVetWithNullIdThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(null, "Zaphod", "Beeblebrox", null);
		});
	}

	@Test
	void createVetWithBlankFirstNameThrowsException() {
		VetId vetId = VetId.random();
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, "", "Beeblebrox", null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, " ", "Beeblebrox", null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, null, "Beeblebrox", null);
		});
	}

	@Test
	void createVetWithBlankLastNameThrowsException() {
		VetId vetId = VetId.random();
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, "Zaphod", "", null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, "Zaphod", " ", null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Vet(vetId, "Zaphod", null, null);
		});
	}

	@Test
	void addAndGetSpecialties() {
		VetId vetId = VetId.random();
		Vet vet = new Vet(vetId, "Helen", "Leary", null); // Start with no specialties

		SpecialtyId radiologyId = SpecialtyId.random();
		SpecialtyId surgeryId = SpecialtyId.random();

		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
		assertThat(vet.getSpecialties()).isEmpty();

		vet.addSpecialty(radiologyId);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties()).containsExactly(radiologyId);

		vet.addSpecialty(surgeryId);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties()).containsExactlyInAnyOrder(radiologyId, surgeryId);

		// Adding the same specialty again should have no effect on the Set
		vet.addSpecialty(radiologyId);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties()).containsExactlyInAnyOrder(radiologyId, surgeryId);
	}

	@Test
	void removeSpecialty() {
		VetId vetId = VetId.random();
		SpecialtyId radiologyId = SpecialtyId.random();
		SpecialtyId surgeryId = SpecialtyId.random();
		Set<SpecialtyId> initialSpecialties = new HashSet<>(Set.of(radiologyId, surgeryId));

		Vet vet = new Vet(vetId, "Helen", "Leary", initialSpecialties);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);

		vet.removeSpecialty(radiologyId);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties()).containsExactly(surgeryId);

		// Removing a non-existent specialty should have no effect
		vet.removeSpecialty(SpecialtyId.random());
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);

		vet.removeSpecialty(surgeryId);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
		assertThat(vet.getSpecialties()).isEmpty();
	}

	@Test
	void addNullSpecialtyThrowsException() {
		Vet vet = new Vet(VetId.random(), "Helen", "Leary", null);
		assertThrows(IllegalArgumentException.class, () -> {
			vet.addSpecialty(null);
		});
	}

	@Test
	void removeNullSpecialtyThrowsException() {
		Vet vet = new Vet(VetId.random(), "Helen", "Leary", Set.of(SpecialtyId.random()));
		assertThrows(IllegalArgumentException.class, () -> {
			vet.removeSpecialty(null);
		});
	}

}
