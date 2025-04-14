/*
 * Copyright 2012-2024 the original author or authors.
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

package org.springframework.samples.petclinic.pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.pet.infrastructure.api.PetValidator;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.PetEntity;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.PetTypeEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link PetValidator}
 *
 * @author Wick Dynex
 */
@ExtendWith(MockitoExtension.class)
@DisabledInNativeImage
public class PetEntityValidatorTests {

	private PetValidator petValidator;

	private PetEntity petEntity;

	private PetTypeEntity petTypeEntity;

	private Errors errors;

	private static final String petName = "Buddy";

	private static final String petTypeName = "Dog";

	private static final LocalDate petBirthDate = LocalDate.of(1990, 1, 1);

	@BeforeEach
	void setUp() {
		petValidator = new PetValidator();
		petEntity = new PetEntity();
		petTypeEntity = new PetTypeEntity();
		errors = new MapBindingResult(new HashMap<>(), "pet");
	}

	@Test
	void testValidate() {
		petTypeEntity.setName(petTypeName);
		petEntity.setName(petName);
		petEntity.setType(petTypeEntity);
		petEntity.setBirthDate(petBirthDate);

		petValidator.validate(petEntity, errors);

		assertFalse(errors.hasErrors());
	}

	@Nested
	class ValidateHasErrors {

		@Test
		void testValidateWithInvalidPetName() {
			petTypeEntity.setName(petTypeName);
			petEntity.setName("");
			petEntity.setType(petTypeEntity);
			petEntity.setBirthDate(petBirthDate);

			petValidator.validate(petEntity, errors);

			assertTrue(errors.hasFieldErrors("name"));
		}

		@Test
		void testValidateWithInvalidPetType() {
			petEntity.setName(petName);
			petEntity.setType(null);
			petEntity.setBirthDate(petBirthDate);

			petValidator.validate(petEntity, errors);

			assertTrue(errors.hasFieldErrors("type"));
		}

		@Test
		void testValidateWithInvalidBirthDate() {
			petTypeEntity.setName(petTypeName);
			petEntity.setName(petName);
			petEntity.setType(petTypeEntity);
			petEntity.setBirthDate(null);

			petValidator.validate(petEntity, errors);

			assertTrue(errors.hasFieldErrors("birthDate"));
		}

	}

}
