package org.springframework.samples.petclinic.pet.domain.owner;

import java.util.UUID;

public final class Owner {

	private final OwnerId id;

	private final Firstname firstname;

	private final Lastname lastname;

	private final Address address;

	private final City city;

	private final Phone phone;

	private Owner(OwnerId id, Firstname firstname, Lastname lastname, Address address, City city, Phone phone) {
		if (id == null) {
			throw new IllegalArgumentException("Owner ID cannot be null");
		}
		if (firstname == null) {
			throw new IllegalArgumentException("Firstname cannot be null");
		}
		if (lastname == null) {
			throw new IllegalArgumentException("Lastname cannot be null");
		}
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		if (city == null) {
			throw new IllegalArgumentException("City cannot be null");
		}
		if (phone == null) {
			throw new IllegalArgumentException("Phone cannot be null");
		}
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.city = city;
		this.phone = phone;
	}

	public static Owner create(String firstname, String lastname, String address, String city, String phone) {
		return build(OwnerId.create(), firstname, lastname, address, city, phone);
	}

	public static Owner reconstitute(UUID ownerId, String firstname, String lastname, String address, String city,
			String phone) {
		return build(new OwnerId(ownerId), firstname, lastname, address, city, phone);
	}

	private static Owner build(OwnerId ownerId, String firstname, String lastname, String address, String city,
			String phone) {
		return new Owner(ownerId, new Firstname(firstname), new Lastname(lastname), new Address(address),
				new City(city), new Phone(phone));
	}

	public OwnerId getId() {
		return id;
	}

	public Firstname getFirstname() {
		return firstname;
	}

	public Lastname getLastname() {
		return lastname;
	}

	public Address getAddress() {
		return address;
	}

	public City getCity() {
		return city;
	}

	public Phone getPhone() {
		return phone;
	}

}
