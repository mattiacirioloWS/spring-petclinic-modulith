package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.ddd.common.domain.Name;
import org.springframework.samples.petclinic.ddd.common.domain.PersonName;
import org.springframework.samples.petclinic.ddd.vet.domain.Specialty;
import org.springframework.samples.petclinic.ddd.vet.domain.SpecialtyId;
import org.springframework.samples.petclinic.ddd.vet.domain.Vet;
import org.springframework.samples.petclinic.ddd.vet.domain.VetId;

import java.util.Set;
import java.util.UUID;

@SpringBootTest
class VetEntityMappingTest {

	@Autowired
	VetEntityMapper vetEntityMapper;

	@Test
	void testToAggregateMappingAndBack() {
		VetEntity vetEntity = new VetEntity();
		vetEntity.setId(new VetId(UUID.randomUUID()));
		PersonName name = new PersonName("John", "Doe");
		vetEntity.setName(name);

		SpecialtyEntity specialtyEntity = new SpecialtyEntity();
		specialtyEntity.setId(new SpecialtyId(UUID.randomUUID()));
		specialtyEntity.setName(new Name("Surgery"));

		vetEntity.setSpecialties(Set.of(specialtyEntity));

		Vet vet = vetEntityMapper.toAggregate(vetEntity);

		assert vet.getId().equals(vetEntity.getId());
		assert vet.getName().equals(vetEntity.getName());
		assert vet.getSpecialties().size() == 1;
		Specialty specialty = vet.getSpecialties().stream().findFirst().get();
		assert specialty.getId().equals(specialtyEntity.getId());
		assert specialty.getName().equals(specialtyEntity.getName());

		VetEntity mappedBack = vetEntityMapper.fromAggregate(vet);
		assert mappedBack.getId().equals(vet.getId());
		assert mappedBack.getName().equals(vet.getName());
		assert mappedBack.getSpecialties().size() == 1;
		SpecialtyEntity mappedSpecialtyEntity = mappedBack.getSpecialties().stream().findFirst().get();
		assert mappedSpecialtyEntity.getId().equals(specialty.getId());
		assert mappedSpecialtyEntity.getName().equals(specialty.getName());
	}

}
