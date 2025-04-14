package org.springframework.samples.petclinic.pet.application;

import jakarta.validation.constraints.NotBlank;
import org.springframework.samples.petclinic.pet.domain.owner.Owner;

import java.util.UUID;

public class OwnerDto {

	private UUID id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private String address;

	@NotBlank
	private String city;

	@NotBlank
	private String telephone;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public static OwnerDto from(Owner owner) {
		OwnerDto ownerDto = new OwnerDto();
		ownerDto.setId(owner.getId().toUUID());
		ownerDto.setFirstName(owner.getFirstname().toString());
		ownerDto.setLastName(owner.getLastname().toString());
		ownerDto.setAddress(owner.getAddress().toString());
		ownerDto.setCity(owner.getCity().toString());
		ownerDto.setTelephone(owner.getPhone().toString());
		return ownerDto;
	}

}
