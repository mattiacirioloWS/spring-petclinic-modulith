package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection; // Add missing import
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors; // Add missing import

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.vet.domain.Specialty;
import org.springframework.samples.petclinic.vet.domain.SpecialtyId;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetId;
import org.springframework.samples.petclinic.vet.domain.Vets; // Import the domain interface

/**
 * Integration tests for {@link VetRepositoryImpl}.
 */
@DataJpaTest
@Import({ VetRepositoryImpl.class, SpecialtyRepositoryImpl.class }) // Import implementations to test
class VetRepositoryImplTests {

	// Define UUID constants based on data.sql for vets
	private static final UUID VET_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000001"); // James Carter
	private static final UUID VET_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002"); // Helen Leary
	private static final UUID SPECIALTY_ID_1 = UUID.fromString("10000000-0000-0000-0000-000000000001"); // radiology
	private static final UUID SPECIALTY_ID_2 = UUID.fromString("10000000-0000-0000-0000-000000000002"); // surgery
	private static final UUID SPECIALTY_ID_3 = UUID.fromString("10000000-0000-0000-0000-000000000003"); // dentistry

	@Autowired
	private Vets vetsRepository; // Inject the domain repository interface

	@Autowired
	private VetJpaRepository vetJpaRepository; // Inject underlying JPA repo for setup/verification if needed

	@Autowired
	private SpecialtyJpaRepository specialtyJpaRepository; // Inject underlying JPA repo for setup/verification

	@Test
	void shouldFindVetById() {
		VetId vetIdToFind = new VetId(VET_ID_2); // Helen Leary

		Optional<Vet> optionalVet = vetsRepository.findById(vetIdToFind);

		assertThat(optionalVet).isPresent();
		Vet vet = optionalVet.get();
		assertThat(vet.getId()).isEqualTo(vetIdToFind);
		assertThat(vet.getFirstName()).isEqualTo("Helen");
		assertThat(vet.getLastName()).isEqualTo("Leary");
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties()).containsExactly(new SpecialtyId(SPECIALTY_ID_1)); // radiology
	}

	@Test
	void shouldFindAllVets() {
		java.util.Collection<Vet> allVets = vetsRepository.findAll(); // Use fully qualified name or add import
		assertThat(allVets).hasSize(6); // Based on data.sql
		// Add more specific assertions if needed
	}

	@Test
	void shouldSaveNewVet() {
		VetId newVetId = VetId.random();
		Vet newVet = new Vet(newVetId, "Zaphod", "Beeblebrox", null);

		vetsRepository.save(newVet);

		Optional<Vet> foundVet = vetsRepository.findById(newVetId);
		assertThat(foundVet).isPresent();
		assertThat(foundVet.get().getFirstName()).isEqualTo("Zaphod");
		assertThat(foundVet.get().getNrOfSpecialties()).isEqualTo(0);
	}

	@Test
	void shouldSaveVetWithSpecialties() {
		VetId newVetId = VetId.random();
		SpecialtyId surgery = new SpecialtyId(SPECIALTY_ID_2);
		SpecialtyId dentistry = new SpecialtyId(SPECIALTY_ID_3);
		Set<SpecialtyId> specialties = Set.of(surgery, dentistry);

		Vet newVet = new Vet(newVetId, "Ford", "Prefect", specialties);

		vetsRepository.save(newVet);

		Optional<Vet> foundVetOpt = vetsRepository.findById(newVetId);
		assertThat(foundVetOpt).isPresent();
		Vet foundVet = foundVetOpt.get();
		assertThat(foundVet.getFirstName()).isEqualTo("Ford");
		assertThat(foundVet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(foundVet.getSpecialties()).containsExactlyInAnyOrder(surgery, dentistry);

		// Verify underlying entity association (optional but good for sanity check)
		Optional<VetEntity> foundEntityOpt = vetJpaRepository.findById(newVetId.value());
		assertThat(foundEntityOpt).isPresent();
		assertThat(foundEntityOpt.get().getSpecialties()).hasSize(2);
		Set<UUID> entitySpecialtyIds = foundEntityOpt.get().getSpecialties().stream()
				.map(SpecialtyEntity::getId)
				.collect(java.util.stream.Collectors.toSet()); // Use fully qualified name or add import
		assertThat(entitySpecialtyIds).containsExactlyInAnyOrder(SPECIALTY_ID_2, SPECIALTY_ID_3);
	}

	@Test
	void shouldUpdateVetSpecialties() {
		VetId vetIdToUpdate = new VetId(VET_ID_1); // James Carter (initially no specialties)
		SpecialtyId surgery = new SpecialtyId(SPECIALTY_ID_2);

		// Fetch existing vet
		Optional<Vet> vetOpt = vetsRepository.findById(vetIdToUpdate);
		assertThat(vetOpt).isPresent();
		Vet vet = vetOpt.get();
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);

		// Add specialty and save
		vet.addSpecialty(surgery);
		vetsRepository.save(vet);

		// Fetch again and verify
		Optional<Vet> updatedVetOpt = vetsRepository.findById(vetIdToUpdate);
		assertThat(updatedVetOpt).isPresent();
		Vet updatedVet = updatedVetOpt.get();
		assertThat(updatedVet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(updatedVet.getSpecialties()).containsExactly(surgery);

		// Remove specialty and save
		updatedVet.removeSpecialty(surgery);
		vetsRepository.save(updatedVet);

		// Fetch again and verify removal
		Optional<Vet> finalVetOpt = vetsRepository.findById(vetIdToUpdate);
		assertThat(finalVetOpt).isPresent();
		assertThat(finalVetOpt.get().getNrOfSpecialties()).isEqualTo(0);
	}

}
