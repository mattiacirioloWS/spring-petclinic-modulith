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
import org.springframework.samples.petclinic.ddd.pet.application.command.AddVisit;
import org.springframework.samples.petclinic.ddd.pet.application.query.FindPet;
import org.springframework.samples.petclinic.ddd.pet.application.PetDto;
import org.springframework.samples.petclinic.ddd.pet.application.VisitDto;
import org.springframework.samples.petclinic.ddd.pet.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.pet.domain.PetId;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author Wick Dynex
 */
@Controller
class VisitController {

	private final AddVisit addVisit;

	private final FindPet findPet;

	VisitController(AddVisit addVisit, FindPet findPet) {
		this.addVisit = addVisit;
		this.findPet = findPet;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Pet object always has an uuid (Even though uuid is not part of the form fields)
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("visit")
	public VisitDto loadPetWithVisit(@PathVariable("ownerId") UUID ownerId, @PathVariable("petId") UUID petId,
			Map<String, Object> model) {

		PetDto pet = findPet.byIdAndOwnerId(new PetId(petId), new OwnerId(ownerId))
			.orElseThrow(() -> new IllegalArgumentException("Pet with id " + petId + " not found"));
		model.put("pet", pet);

		VisitDto visit = new VisitDto();
		pet.getVisits().add(visit);
		return visit;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
	// called
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm() {
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
	// called
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@PathVariable UUID petId, @Valid VisitDto visit, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}

		addVisit.execute(new PetId(petId), visit.getDate(), visit.getDescription());
		redirectAttributes.addFlashAttribute("message", "Your visit has been booked");
		return "redirect:/owners/{ownerId}";
	}

}
