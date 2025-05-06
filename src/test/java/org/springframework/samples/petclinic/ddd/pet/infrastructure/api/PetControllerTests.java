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

package org.springframework.samples.petclinic.ddd.pet.infrastructure.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.ddd.owner.infrastructure.persistence.OwnerEntity;
import org.springframework.samples.petclinic.ddd.owner.infrastructure.persistence.OwnerJpaRepository;
import org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence.PetEntity;
import org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence.PetTypeEntity;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 * @author Wick Dynex
 */
@WebMvcTest(value = PetController.class,
		includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
@DisabledInNativeImage
@DisabledInAotMode
class PetControllerTests {

	private static final UUID TEST_OWNER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

	private static final UUID TEST_PET_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerJpaRepository owners;

	@BeforeEach
	void setup() {
		PetTypeEntity cat = new PetTypeEntity();
		cat.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
		cat.setName("hamster");
		given(this.owners.findPetTypes()).willReturn(List.of(cat));

		OwnerEntity owner = new OwnerEntity();
		PetEntity pet = new PetEntity();
		PetEntity dog = new PetEntity();
		owner.addPet(pet);
		owner.addPet(dog);
		pet.setId(TEST_PET_ID);
		dog.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
		pet.setName("petty");
		dog.setName("doggy");
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(Optional.of(owner));
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"))
			.andExpect(model().attributeExists("pet"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty")
				.param("type", "hamster")
				.param("birthDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Nested
	class ProcessCreationFormHasErrors {

		@Test
		void testProcessCreationFormWithBlankName() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "\t \n")
					.param("birthDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "name"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "name", "required"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

		@Test
		void testProcessCreationFormWithDuplicateName() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "petty")
					.param("birthDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "name"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "name", "duplicate"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

		@Test
		void testProcessCreationFormWithMissingPetType() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty")
					.param("birthDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "type"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

		@Test
		void testProcessCreationFormWithInvalidBirthDate() throws Exception {
			LocalDate currentDate = LocalDate.now();
			String futureBirthDate = currentDate.plusMonths(1).toString();

			mockMvc
				.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty")
					.param("birthDate", futureBirthDate))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "typeMismatch.birthDate"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

		@Test
		void testInitUpdateForm() throws Exception {
			mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("pet"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
				.param("type", "hamster")
				.param("birthDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Nested
	class ProcessUpdateFormHasErrors {

		@Test
		void testProcessUpdateFormWithInvalidBirthDate() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", " ")
					.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "typeMismatch"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

		@Test
		void testProcessUpdateFormWithBlankName() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "  ")
					.param("birthDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "name"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "name", "required"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
		}

	}

}
