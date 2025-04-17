package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VetRepositoryImplTest {

	@Autowired
	private VetRepositoryImpl vetRepository;

	@Test
	void testFindAll() {
		List<Vet> all = vetRepository.findAll();
		assertNotNull(all);
		assertEquals(all.size(), 6);
	}

	@Test
	void shouldFindVets() {
		Optional<Vet> vets = vetRepository.findAll()
			.stream()
			.filter(v -> UUID.fromString("33333333-3333-3333-3333-333333333333").equals(v.getId().uuid()))
			.findFirst();

		assertTrue(vets.isPresent());
		Vet vet = vets.get();
		assertThat(vet.getName().lastName()).isEqualTo("Douglas");
		assertThat(vet.getSpecialties().size()).isEqualTo(2);
	}

}
