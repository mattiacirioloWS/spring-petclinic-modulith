package org.springframework.samples.petclinic.ddd.pet.infrastructure.api;

import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.ddd.pet.application.query.FindPetTypes;
import org.springframework.samples.petclinic.ddd.pet.application.PetTypeDto;
import org.springframework.stereotype.Component;

@Component
class PetTypeConverter implements Converter<String, PetTypeDto> {

	private final FindPetTypes findPetTypes;

	PetTypeConverter(FindPetTypes findPetTypes) {
		this.findPetTypes = findPetTypes;
	}

	@Override
	public PetTypeDto convert(String source) {
		return findPetTypes.findAll()
			.stream()
			.filter(petTypeDto -> petTypeDto.getId().toString().equals(source))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid PetType ID: " + source));
	}

}
