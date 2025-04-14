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
package org.springframework.samples.petclinic.pet.infrastructure.api;

import jakarta.validation.Valid;
import org.springframework.samples.petclinic.pet.application.PetDto;
import org.springframework.samples.petclinic.pet.application.PetTypeDto;
import org.springframework.samples.petclinic.pet.application.command.CreatePet;
import org.springframework.samples.petclinic.pet.application.command.UpdatePet;
import org.springframework.samples.petclinic.pet.application.query.PetNameUniqueness;
import org.springframework.samples.petclinic.pet.application.query.QueryOwners;
import org.springframework.samples.petclinic.pet.application.query.QueryPetTypes;
import org.springframework.samples.petclinic.pet.application.query.QueryPets;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final QueryPetTypes queryPetTypes;

	private final QueryPets queryPets;

	private final QueryOwners queryOwners;

	private final PetNameUniqueness petNameUniqueness;

	private final CreatePet createPet;

	private final UpdatePet updatePet;

	public PetController(QueryPetTypes queryPetTypes, QueryPets queryPets, QueryOwners queryOwners,
			PetNameUniqueness petNameUniqueness, CreatePet createPet, UpdatePet updatePet) {
		this.queryPetTypes = queryPetTypes;
		this.queryPets = queryPets;
		this.queryOwners = queryOwners;
		this.petNameUniqueness = petNameUniqueness;
		this.createPet = createPet;
		this.updatePet = updatePet;
	}

	@ModelAttribute("types")
	public List<PetTypeDto> populatePetTypes() {
		return queryPetTypes.findAll();
	}

	/*
	 * @ModelAttribute("owner") public Owner findOwner(@PathVariable("ownerId") UUID
	 * ownerId) { Optional<Owner> optionalOwner = clinicService.findOwnerById(ownerId);
	 * return optionalOwner.orElseThrow(() -> new IllegalArgumentException(
	 * "Owner not found with id: " + ownerId + ". Please ensure the ID is correct ")); }
	 */

	@ModelAttribute("pet")
	public PetDto findPet(@PathVariable("ownerId") UUID ownerId,
			@PathVariable(name = "petId", required = false) UUID petId) {

		if (petId == null) {
			return new PetDto();
		}

		Optional<PetDto> optionalPet = queryPets.findByIdAndOwnerId(petId, ownerId);
		return optionalPet.orElseThrow(() -> new IllegalArgumentException("Pet with id  " + petId
				+ " not found for the owner with id: " + ownerId + ". Please ensure the ID is correct "));
	}

	@ModelAttribute("ownerName")
	public String findOwnerName(@PathVariable("ownerId") UUID ownerId) {
		return queryOwners.findById(ownerId)
			.map(owner -> owner.getFirstName() + " " + owner.getLastName())
			.orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId
					+ ". Please ensure the ID is correct " + "and the owner exists in the database."));
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	public String initCreationForm(ModelMap model) {
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/new")
	@Transactional
	public String processCreationForm(@PathVariable("ownerId") UUID ownerId, @Valid @ModelAttribute("pet") PetDto pet,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (hasOwnerAPetWithSameName(ownerId, pet)) {
			result.rejectValue("name", "duplicate", "already exists");
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		if (result.hasErrors()) {
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		pet.setOwnerId(ownerId);

		createPet.execute(pet);

		redirectAttributes.addFlashAttribute("message", "New Pet has been Added");
		return "redirect:/owners/{ownerId}";
	}

	private boolean hasOwnerAPetWithSameName(UUID ownerId, PetDto pet) {
		return StringUtils.hasText(pet.getName()) && pet.isNew() && !petNameUniqueness.verify(ownerId, pet.getName());
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm() {
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	@Transactional
	public String processUpdateForm(@PathVariable("ownerId") UUID ownerId, @PathVariable("petId") UUID petId,
			@Valid @ModelAttribute("pet") PetDto pet, BindingResult result, RedirectAttributes redirectAttributes) {

		if (hasOwnerAPetWithSameName(ownerId, pet)) {
			result.rejectValue("name", "duplicate", "already exists");
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		if (result.hasErrors()) {
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		pet.setId(petId);
		pet.setOwnerId(ownerId);

		updatePet.execute(pet);
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownerId}";
	}

}
