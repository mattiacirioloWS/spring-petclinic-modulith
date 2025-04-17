package org.springframework.samples.petclinic.ddd.vet.domain;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Factory;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.samples.petclinic.ddd.common.domain.PersonName;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@AggregateRoot
public final class Vet {

	@Identity
	private final VetId id;

	private final PersonName name;

	private final List<Specialty> specialties;

	private Vet(VetId id, PersonName name, Set<Specialty> specialties) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (specialties == null) {
			throw new IllegalArgumentException("Specialties cannot be null");
		}
		this.id = id;
		this.name = name;
		// immutable set so that it cannot be modified outside the aggregate
		this.specialties = specialties.stream().sorted(Comparator.comparing(s -> s.getName().name())).toList();
	}

	public VetId getId() {
		return id;
	}

	public PersonName getName() {
		return name;
	}

	public List<Specialty> getSpecialties() {
		return specialties;
	}

	@Factory
	public static final class Builder {

		public static Vet reconstitute(VetId id, PersonName name, Set<Specialty> specialties) {
			return new Vet(id, name, specialties);
		}

	}

}
