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

package org.springframework.samples.petclinic.vet.infrastructure.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.vet.application.SpecialtyDto;
import org.springframework.samples.petclinic.vet.application.VetDto;
import org.springframework.samples.petclinic.vet.application.query.FindAllVets;
import org.springframework.samples.petclinic.vet.application.query.FindVetsPage;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link VetController}
 */

@WebMvcTest(VetController.class)
@DisabledInNativeImage
@DisabledInAotMode
class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private FindVetsPage findVetsPage;

	@MockitoBean
	private FindAllVets findAllVets;

	private VetDto james() {
		return new VetDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), "James", "Carter", List.of());
	}

	private VetDto helen() {
		return new VetDto(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Helen", "Leary",
				List.of(new SpecialtyDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), "radiology")));
	}

	@BeforeEach
	void setup() {
		given(this.findAllVets.execute()).willReturn(List.of(james(), helen()));
		given(this.findVetsPage.execute(any(Integer.class), any(Integer.class)))
			.willReturn(new PageImpl<>(List.of(james(), helen())));
	}

	@Test
	void testShowVetListHtml() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/vets.html?page=1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("listVets"))
			.andExpect(view().name("vets/vetList"));
	}

	@Test
	void testShowResourcesVetList() throws Exception {
		ResultActions actions = mockMvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.vetList[0].id").value("11111111-1111-1111-1111-111111111111"));
	}

}
