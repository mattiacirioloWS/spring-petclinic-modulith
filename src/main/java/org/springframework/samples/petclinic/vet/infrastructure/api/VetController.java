package org.springframework.samples.petclinic.vet.infrastructure.api;

import java.util.List;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.application.VetService;
import org.springframework.samples.petclinic.vet.application.dto.VetDto; // Updated import
import org.springframework.samples.petclinic.vet.application.dto.VetListDto; // Updated import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for handling Vet related web requests.
 * Maps requests to the VetService and returns DTOs.
 */
@Controller
public class VetController {

	private final VetService vetService;

	public VetController(VetService vetService) {
		this.vetService = vetService;
	}

	@GetMapping("/vets.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Use VetService to get paginated DTOs
		Page<VetDto> paginated = findPaginated(page);
		return addPaginationModel(page, paginated, model);
	}

	private String addPaginationModel(int page, Page<VetDto> paginated, Model model) {
		List<VetDto> listVets = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		// Use the DTO list in the model
		model.addAttribute("listVets", listVets);
		// The view name remains the same
		return "vets/vetList";
	}

	private Page<VetDto> findPaginated(int page) {
		int pageSize = 5;
		// Page numbers are 1-based in the request, but 0-based in Pageable
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return vetService.findPaginatedVets(pageable);
	}

	@GetMapping({ "/vets" })
	@ResponseBody // Ensure response is serialized (JSON/XML)
	public org.springframework.samples.petclinic.vet.application.dto.VetListDto showResourcesVetList() { // Use fully qualified name or add import
		// Use VetService to get all vets as DTOs
		List<org.springframework.samples.petclinic.vet.application.dto.VetDto> vetDtos = (List<org.springframework.samples.petclinic.vet.application.dto.VetDto>) vetService.findAllVets(); // Use fully qualified name or add import
		// Wrap the DTO list in the VetListDto for proper formatting
		org.springframework.samples.petclinic.vet.application.dto.VetListDto vetListDto = new org.springframework.samples.petclinic.vet.application.dto.VetListDto(vetDtos); // Use fully qualified name or add import
		return vetListDto;
	}

}
