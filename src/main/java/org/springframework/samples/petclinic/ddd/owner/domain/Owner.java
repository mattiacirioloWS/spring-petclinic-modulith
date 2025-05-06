package org.springframework.samples.petclinic.ddd.owner.domain;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Factory;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
public final class Owner {

	@Identity
	private final OwnerId id;

	private PersonName name;

	private FullAddress address;

	private Telephone telephone;

	private Owner(OwnerId id, PersonName name, FullAddress address, Telephone telephone) {
		if (id == null) {
			throw new IllegalArgumentException("Id is required");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name is required");
		}
		if (address == null) {
			throw new IllegalArgumentException("Address is required");
		}
		if (telephone == null) {
			throw new IllegalArgumentException("Telephone is required");
		}
		this.id = id;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
	}

	public OwnerId getId() {
		return id;
	}

	public PersonName getName() {
		return name;
	}

	public void changeName(PersonName personName) {
		if (personName == null) {
			throw new IllegalArgumentException("Name is required");
		}
		this.name = personName;
	}

	public FullAddress getAddress() {
		return address;
	}

	public void changeAddress(FullAddress fullAddress) {
		if (fullAddress == null) {
			throw new IllegalArgumentException("Address is required");
		}
		this.address = fullAddress;
	}

	public Telephone getTelephone() {
		return telephone;
	}

	public void changeTelephone(Telephone telephone) {
		if (telephone == null) {
			throw new IllegalArgumentException("Telephone is required");
		}
		this.telephone = telephone;
	}

	@Factory
	public static final class Builder {

		public static Owner reconstitute(OwnerId id, PersonName name, FullAddress address, Telephone telephone) {
			return new Owner(id, name, address, telephone);
		}

		public static Owner create(PersonName name, FullAddress address, Telephone telephone) {
			return new Owner(OwnerId.create(), name, address, telephone);
		}

	}

}
