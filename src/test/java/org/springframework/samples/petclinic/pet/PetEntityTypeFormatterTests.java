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

package org.springframework.samples.petclinic.pet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.pet.infrastructure.api.PetTypeFormatter;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.JpaPetRepository;
import org.springframework.samples.petclinic.pet.infrastructure.persistence.PetTypeEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
@DisabledInNativeImage
class PetEntityTypeFormatterTests {

	@Mock
	private JpaPetRepository pets;

	private PetTypeFormatter petTypeFormatter;

	@BeforeEach
	void setup() {
		this.petTypeFormatter = new PetTypeFormatter(pets);
	}

	@Test
	void testPrint() {
		PetTypeEntity petTypeEntity = new PetTypeEntity();
		petTypeEntity.setName("Hamster");
		String petTypeName = this.petTypeFormatter.print(petTypeEntity, Locale.ENGLISH);
		assertThat(petTypeName).isEqualTo("Hamster");
	}

	@Test
	void shouldParse() throws ParseException {
		given(this.pets.findPetTypes()).willReturn(makePetTypes());
		PetTypeEntity petTypeEntity = petTypeFormatter.parse("Bird", Locale.ENGLISH);
		assertThat(petTypeEntity.getName()).isEqualTo("Bird");
	}

	@Test
	void shouldThrowParseException() {
		given(this.pets.findPetTypes()).willReturn(makePetTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			petTypeFormatter.parse("Fish", Locale.ENGLISH);
		});
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link PetTypeEntity}
	 */
	private List<PetTypeEntity> makePetTypes() {
		List<PetTypeEntity> petTypeEntities = new ArrayList<>();
		petTypeEntities.add(new PetTypeEntity() {
			{
				setName("Dog");
			}
		});
		petTypeEntities.add(new PetTypeEntity() {
			{
				setName("Bird");
			}
		});
		return petTypeEntities;
	}

}
