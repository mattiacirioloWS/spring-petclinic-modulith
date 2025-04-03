package org.springframework.samples.petclinic.vet.application.dto; // Updated package

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.samples.petclinic.vet.application.dto.VetDto; // Updated import

/**
 * Data Transfer Object representing a list of veterinarians.
 * Used for API responses, including XML marshalling.
 */
@XmlRootElement(name = "vets")
public class VetListDto {

	private List<VetDto> vets;

	public VetListDto() {
		this.vets = new ArrayList<>();
	}

	public VetListDto(List<VetDto> vets) {
		this.vets = vets;
	}

	@XmlElement(name = "vet")
	public List<VetDto> getVetList() {
		if (vets == null) {
			vets = new ArrayList<>();
		}
		return vets;
	}

	public void setVetList(List<VetDto> vets) {
		this.vets = vets;
	}

}
