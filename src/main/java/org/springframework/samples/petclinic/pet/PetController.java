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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	private final ClinicService clinicService;

	public PetController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@ModelAttribute("types")
	public List<PetType> populatePetTypes() {
		return clinicService.findPetTypes();
	}

	/*
	 * @ModelAttribute("owner") public Owner findOwner(@PathVariable("ownerId") UUID
	 * ownerId) { Optional<Owner> optionalOwner = clinicService.findOwnerById(ownerId);
	 * return optionalOwner.orElseThrow(() -> new IllegalArgumentException(
	 * "Owner not found with id: " + ownerId + ". Please ensure the ID is correct ")); }
	 */

	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("ownerId") UUID ownerId,
			@PathVariable(name = "petId", required = false) UUID petId) {

		if (petId == null) {
			return new Pet();
		}

		Optional<Pet> optionalPet = clinicService.findByIdAndOwnerId(petId, ownerId);
		return optionalPet.orElseThrow(() -> new IllegalArgumentException("Pet with id  " + petId
				+ " not found for the owner with id: " + ownerId + ". Please ensure the ID is correct "));
	}

	/*
	 * @InitBinder("owner") public void initOwnerBinder(WebDataBinder dataBinder) {
	 * dataBinder.setDisallowedFields("id"); }
	 */

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
	public String processCreationForm(@PathVariable("ownerId") UUID ownerId, @Valid Pet pet, BindingResult result,
			RedirectAttributes redirectAttributes) {

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

		clinicService.savePet(pet);

		redirectAttributes.addFlashAttribute("message", "New Pet has been Added");
		return "redirect:/owners/{ownerId}";
	}

	private boolean hasOwnerAPetWithSameName(UUID ownerId, Pet pet) {
		return StringUtils.hasText(pet.getName()) && pet.isNew()
				&& clinicService.existsByNameAndOwnerId(pet.getName(), ownerId);
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm() {
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@PathVariable("ownerId") UUID ownerId, @Valid Pet pet, BindingResult result,
			RedirectAttributes redirectAttributes) {

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

		clinicService.savePet(pet);
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownerId}";
	}

}
