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
package org.springframework.samples.petclinic.vet.application;

import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class VetTests {

	@Test
	void testSerialization() {
		VetDto vet = new VetDto(UUID.fromString("12312312-3123-1231-2312-312312312312"), "Zaphod", "Beeblebrox",
				List.of(new SpecialtyDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), "radiology")));

		VetDto other = SerializationUtils.clone(vet);

		assertThat(other.id()).isEqualTo(vet.id());
		assertThat(other.firstName()).isEqualTo(vet.firstName());
		assertThat(other.lastName()).isEqualTo(vet.lastName());
		assertThat(other.specialties()).hasSize(1).first().satisfies(specialty -> {
			assertThat(specialty.id()).isEqualTo(UUID.fromString("11111111-1111-1111-1111-111111111111"));
			assertThat(specialty.name()).isEqualTo("radiology");
		});
	}

}
