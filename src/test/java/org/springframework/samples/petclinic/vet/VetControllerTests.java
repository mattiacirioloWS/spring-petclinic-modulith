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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.vet.application.VetService; // Import new service
import org.springframework.samples.petclinic.vet.application.dto.SpecialtyDto; // Updated DTO import
import org.springframework.samples.petclinic.vet.application.dto.VetDto; // Updated DTO import
import org.springframework.samples.petclinic.vet.infrastructure.api.VetController; // Import new controller
import java.util.UUID; // Add UUID import
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize; // Import for checking list size

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(VetController.class) // Target the new controller location
@DisabledInNativeImage
@DisabledInAotMode
class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VetService vetService; // Mock the application service

	private VetDto jamesDto() {
		return new VetDto(
				UUID.fromString("00000000-0000-0000-0000-000000000001"), // Convert String to UUID
				"James",
				"Carter",
				Collections.emptyList(),
				0
		);
	}

	private VetDto helenDto() {
		SpecialtyDto radiologyDto = new SpecialtyDto(UUID.fromString("10000000-0000-0000-0000-000000000001"), "radiology"); // Convert String to UUID
		return new VetDto(
				UUID.fromString("00000000-0000-0000-0000-000000000002"), // Convert String to UUID
				"Helen",
				"Leary",
				List.of(radiologyDto),
				1
		);
	}

	@BeforeEach
	void setup() {
		// Mock service methods to return DTOs
		given(this.vetService.findAllVets()).willReturn(Lists.newArrayList(jamesDto(), helenDto()));
		given(this.vetService.findPaginatedVets(any(Pageable.class)))
				.willReturn(new PageImpl<>(Lists.newArrayList(jamesDto(), helenDto())));
	}

	@Test
	void testShowVetListHtml() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/vets.html?page=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("listVets")) // Expect DTO list
				.andExpect(model().attribute("listVets", hasSize(2))) // Check size
				.andExpect(view().name("vets/vetList"));
	}

	@Test
	void testShowResourcesVetList() throws Exception {
		ResultActions actions = mockMvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Assert DTO structure in JSON
				.andExpect(jsonPath("$.vetList").exists())
				.andExpect(jsonPath("$.vetList", hasSize(2)))
				.andExpect(jsonPath("$.vetList[0].id").value("00000000-0000-0000-0000-000000000001"))
				.andExpect(jsonPath("$.vetList[0].firstName").value("James"))
				.andExpect(jsonPath("$.vetList[1].id").value("00000000-0000-0000-0000-000000000002"))
				.andExpect(jsonPath("$.vetList[1].firstName").value("Helen"))
				.andExpect(jsonPath("$.vetList[1].specialties", hasSize(1)))
				.andExpect(jsonPath("$.vetList[1].specialties[0].name").value("radiology"));
	}

}
