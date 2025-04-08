package org.springframework.samples.petclinic.vet.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vet {

	private final VetId id;

	private final VetName name;

	private final Set<Specialty> specialties;

	private Vet(VetId id, VetName name, Set<Specialty> specialties) {
		if (id == null) {
			throw new IllegalArgumentException("Vet ID cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Vet name cannot be null");
		}
		if (specialties == null) {
			throw new IllegalArgumentException("Specialties cannot be null");
		}
		this.id = id;
		this.name = name;
		this.specialties = new HashSet<>(specialties);
	}

	public static Vet create(VetName name) {
		return new Vet(VetId.create(), name, new HashSet<>());
	}

	public static Vet reconstitute(VetId id, VetName name, Set<Specialty> specialties) {
		return new Vet(id, name, specialties);
	}

	public VetId getId() {
		return id;
	}

	public VetName getName() {
		return name;
	}

	public Set<Specialty> getSpecialties() {
		return Collections.unmodifiableSet(specialties);
	}

	public void addSpecialty(Specialty specialty) {
		if (specialty == null) {
			throw new IllegalArgumentException("Specialty cannot be null");
		}
		specialties.add(specialty);
	}

	public void removeSpecialty(Specialty specialty) {
		if (specialty == null) {
			throw new IllegalArgumentException("Specialty cannot be null");
		}
		specialties.remove(specialty);
	}

	public void clearSpecialties() {
		specialties.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Vet vet = (Vet) o;
		return Objects.equals(id, vet.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Vet{" + "id=" + id + ", name=" + name + ", specialties=" + specialties + '}';
	}

}