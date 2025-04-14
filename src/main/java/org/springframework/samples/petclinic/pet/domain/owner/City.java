package org.springframework.samples.petclinic.pet.domain.owner;

import java.util.Objects;

public final class City {

	private final String city;

	public City(String city) {
		if (city == null || city.trim().isEmpty()) {
			throw new IllegalArgumentException("City cannot be null or empty");
		}
		this.city = city;
	}

	public String toString() {
		return city;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (City) obj;
		return Objects.equals(this.city, that.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city);
	}

}
