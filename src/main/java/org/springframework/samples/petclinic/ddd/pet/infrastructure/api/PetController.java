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

import jakarta.validation.Valid;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDto;
import org.springframework.samples.petclinic.ddd.pet.application.command.AddVisit;
import org.springframework.samples.petclinic.ddd.pet.application.command.CreatePet;
import org.springframework.samples.petclinic.ddd.pet.application.command.UpdatePet;
import org.springframework.samples.petclinic.ddd.pet.application.query.FindPet;
import org.springframework.samples.petclinic.ddd.pet.application.query.FindPetTypes;
import org.springframework.samples.petclinic.ddd.pet.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Wick Dynex
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

	private static final String VIEWS_PETS_CREATE_FORM = "pets/createPetForm";

	private static final String VIEWS_PETS_UPDATE_FORM = "pets/updatePetForm";

	private final FindPetTypes findPetTypes;

	private final FindPet findPet;

	private final CreatePet createPet;

	private final UpdatePet updatePet;

	private final AddVisit addVisit;

	PetController(FindPetTypes findPetTypes, FindPet findPet, CreatePet createPet, UpdatePet updatePet,
			AddVisit addVisit) {
		this.findPetTypes = findPetTypes;
		this.findPet = findPet;
		this.createPet = createPet;
		this.updatePet = updatePet;
		this.addVisit = addVisit;
	}

	@ModelAttribute("types")
	public List<PetTypeDto> populatePetTypes() {
		return findPetTypes.findAll();
	}

	@ModelAttribute("pet")
	public PetDto findPet(@PathVariable("ownerId") UUID ownerId,
			@PathVariable(name = "petId", required = false) UUID petId) {

		if (petId == null) {
			return new PetDto();
		}

		return findPet.byIdAndOwnerId(new PetId(petId), new OwnerId(ownerId))
			.orElseThrow(() -> new IllegalArgumentException(
					"Pet not found with uuid: " + petId + ". Please ensure the ID is correct "));
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	public String initCreationForm(ModelMap model) {
		model.put("createPet", new CreatePetRequest());
		return VIEWS_PETS_CREATE_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(@ModelAttribute("createPet") @Valid CreatePetRequest pet, BindingResult result,
			RedirectAttributes redirectAttributes) {

		/*
		 * if (StringUtils.hasText(pet.getName()) && pet.isNew() &&
		 * owner.getPet(pet.getName(), true) != null) result.rejectValue("name",
		 * "duplicate", "already exists");
		 */

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		if (result.hasErrors()) {
			return VIEWS_PETS_CREATE_FORM;
		}

		PetDto createdPet = createPet.execute(new Name(pet.getName()), new BirthDate(pet.getBirthDate()),
				new PetTypeId(pet.getPetTypeId()), new OwnerId(pet.getOwnerId()));
		redirectAttributes.addFlashAttribute("message", "New Pet has been Added");
		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(ModelMap model, @ModelAttribute("pet") PetDto pet) {
		UpdatePetRequest updatePetRequest = new UpdatePetRequest();
		updatePetRequest.setName(pet.getName());
		updatePetRequest.setBirthDate(pet.getBirthDate());
		updatePetRequest.setPetTypeId(pet.getType().getId());
		model.put("updatePet", updatePetRequest);
		return VIEWS_PETS_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid UpdatePetRequest pet, @PathVariable(name = "ownerId") UUID ownerId,
			@PathVariable(name = "petId") UUID petId, BindingResult result, RedirectAttributes redirectAttributes) {

		/*
		 * String petName = pet.getName();
		 *
		 * // checking if the pet name already exists for the owner if
		 * (StringUtils.hasText(petName)) { PetEntity existingPet = owner.getPet(petName,
		 * false); if (existingPet != null && !existingPet.getId().equals(pet.getId())) {
		 * result.rejectValue("name", "duplicate", "already exists"); } }
		 */

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		if (result.hasErrors()) {
			return VIEWS_PETS_UPDATE_FORM;
		}

		// updatePetDetails(owner, pet);
		updatePet.execute(new PetId(petId), new Name(pet.getName()), new BirthDate(pet.getBirthDate()),
				new PetTypeId(pet.getPetTypeId()), new OwnerId(ownerId));
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownerId}";
	}

}
