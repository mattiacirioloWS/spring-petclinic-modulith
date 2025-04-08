package org.springframework.samples.petclinic.vet.application.query;

import org.springframework.samples.petclinic.vet.application.VetDto;

import java.util.List;

public interface FindAllVets {

	List<VetDto> execute();

}
