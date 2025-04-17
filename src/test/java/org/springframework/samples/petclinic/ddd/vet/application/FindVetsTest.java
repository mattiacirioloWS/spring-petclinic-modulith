package org.springframework.samples.petclinic.ddd.vet.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class FindVetsTest {

	@Autowired
	private FindVets findVets;

	@Test
	void testSpecialtiesSort() {
		findVets.findAll()
			.stream()
			.filter(v -> v.getId().equals(UUID.fromString("33333333-3333-3333-3333-333333333333")))
			.findFirst()
			.ifPresent(vet -> {
				List<SpecialtyDto> specialties = vet.getSpecialties();
				assert specialties.size() == 2;
				assert specialties.get(0).getName().equals("dentistry");
				assert specialties.get(1).getName().equals("surgery");
			});
	}

}
