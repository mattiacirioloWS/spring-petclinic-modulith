package org.springframework.samples.petclinic.ddd.vet.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.ddd.vet.domain.Name;
import org.springframework.samples.petclinic.ddd.vet.domain.PersonName;
import org.springframework.samples.petclinic.ddd.vet.domain.Specialty;
import org.springframework.samples.petclinic.ddd.vet.domain.SpecialtyId;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;
import org.springframework.samples.petclinic.ddd.vet.domain.VetId;

import java.util.Set;

@SpringBootTest
class VetDtoMappingTest {

	@Autowired
	private VetDtoMapper vetDtoMapper;

	@Test
	void mapAggregateToDto() {
		VetId vetId = VetId.create();
		Specialty surgery = Specialty.Builder.reconstitute(SpecialtyId.create(), new Name("Surgery"));
		Vet vet = Vet.Builder.reconstitute(vetId, new PersonName("John", "Doe"), Set.of(surgery));
		VetDto vetDto = vetDtoMapper.fromAggregate(vet);
		assert vetDto.getId().equals(vetId.uuid());
		assert vetDto.getFirstName().equals(vet.getName().firstName());
		assert vetDto.getLastName().equals(vet.getName().lastName());
		assert vetDto.getSpecialties().size() == 1;
		SpecialtyDto surgeryDto = vetDto.getSpecialties().iterator().next();
		assert surgeryDto.getId().equals(surgery.getId().uuid());
		assert surgeryDto.getName().equals(surgery.getName().get());
	}

}
