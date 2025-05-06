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
package org.springframework.samples.petclinic.ddd.owner.infrastructure.api;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.ddd.owner.application.CreateOwner;
import org.springframework.samples.petclinic.ddd.owner.application.FindOwners;
import org.springframework.samples.petclinic.ddd.owner.application.OwnerDto;
import org.springframework.samples.petclinic.ddd.owner.application.UpdateOwner;
import org.springframework.samples.petclinic.ddd.owner.domain.FullAddress;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.owner.domain.PersonName;
import org.springframework.samples.petclinic.ddd.owner.domain.Telephone;
import org.springframework.samples.petclinic.ddd.pet.FindPets;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Wick Dynex
 */
@Controller
class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final FindOwners findOwners;

	private final FindPets findPets;

	private final CreateOwner createOwner;

	private final UpdateOwner updateOwner;

	OwnerController(FindOwners findOwners, FindPets findPets, CreateOwner createOwner, UpdateOwner updateOwner) {
		this.findOwners = findOwners;
		this.findPets = findPets;
		this.createOwner = createOwner;
		this.updateOwner = updateOwner;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("owner")
	public OwnerDto findOwner(@PathVariable(name = "ownerId", required = false) UUID ownerId) {
		return ownerId == null ? new OwnerDto()
				: findOwners.findById(new OwnerId(ownerId))
					.orElseThrow(() -> new IllegalArgumentException("Owner not found with uuid: " + ownerId
							+ ". Please ensure the ID is correct " + "and the owner exists in the database."));
	}

	@ModelAttribute("pets")
	public List<PetDto> findPets(@PathVariable(name = "ownerId", required = false) UUID ownerId) {
		if (ownerId == null) {
			return List.of();
		}
		List<PetDto> petDtos = findPets.byOwnerId(ownerId);
		return petDtos;
	}

	@GetMapping("/owners/new")
	public String initCreationForm() {
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@Valid OwnerDto owner, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "There was an error in creating the owner.");
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		OwnerDto newOwner = createOwner.execute(new PersonName(owner.getFirstName(), owner.getLastName()),
				new FullAddress(owner.getAddress(), owner.getCity()), new Telephone(owner.getTelephone()));
		redirectAttributes.addFlashAttribute("message", "New Owner Created");
		return "redirect:/owners/" + newOwner.getId();
	}

	@GetMapping("/owners/find")
	public String initFindForm() {
		return "owners/findOwners";
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, @RequestParam String ownerLastName,
			Model model) {
		// allow parameterless GET request for /owners to return all records
		if (ownerLastName == null) {
			ownerLastName = ""; // empty string signifies broadest possible search
		}

		// find owners by last name
		Page<OwnerDto> ownersResults = findPaginatedForOwnersLastName(page, ownerLastName);
		if (ownersResults.isEmpty()) {
			// no owners found
			model.addAttribute("notFound", true);
			return "owners/findOwners";
		}

		if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			return "redirect:/owners/" + ownersResults.iterator().next().getId();
		}

		// multiple owners found
		return addPaginationModel(page, model, ownersResults);
	}

	private String addPaginationModel(int page, Model model, Page<OwnerDto> paginated) {
		List<OwnerDto> listOwners = paginated.getContent();

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listOwners", listOwners);
		return "owners/ownersList";
	}

	private Page<OwnerDto> findPaginatedForOwnersLastName(int page, String lastname) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return findOwners.findByLastNameStartingWith(lastname, pageable);
	}

	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm() {
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid OwnerDto owner, BindingResult result,
			@PathVariable("ownerId") UUID ownerId, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "There was an error in updating the owner.");
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		if (!owner.getId().equals(ownerId)) {
			result.rejectValue("id", "mismatch", "The owner ID in the form does not match the URL.");
			redirectAttributes.addFlashAttribute("error", "Owner ID mismatch. Please try again.");
			return "redirect:/owners/{ownerId}/edit";
		}

		owner.setId(ownerId);
		updateOwner.execute(new OwnerId(owner.getId()), new PersonName(owner.getFirstName(), owner.getLastName()),
				new FullAddress(owner.getAddress(), owner.getCity()), new Telephone(owner.getTelephone()));
		redirectAttributes.addFlashAttribute("message", "Owner Values Updated");
		return "redirect:/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") UUID ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Optional<OwnerDto> optionalOwner = findOwners.findById(new OwnerId(ownerId));
		OwnerDto owner = optionalOwner.orElseThrow(() -> new IllegalArgumentException(
				"Owner not found with uuid: " + ownerId + ". Please ensure the ID is correct "));
		mav.addObject(owner);
		return mav;
	}

}
