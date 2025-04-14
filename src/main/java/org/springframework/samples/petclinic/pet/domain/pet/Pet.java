package org.springframework.samples.petclinic.pet.domain.pet;

import org.springframework.samples.petclinic.pet.domain.owner.OwnerId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Pet {

	private final PetId id;

	private final Set<Visit> visits;

	private final OwnerId ownerId;

	private Name name;

	private final BirthDate birthDate;

	private final PetType petType;

	private Pet(final PetId id, final Name name, final BirthDate birthDate, final PetType petType,
			final Set<Visit> visits, final OwnerId ownerId) {
		if (id == null) {
			throw new IllegalArgumentException("Pet ID cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Pet name cannot be null");
		}
		if (birthDate == null) {
			throw new IllegalArgumentException("Pet birth date cannot be null");
		}
		if (petType == null) {
			throw new IllegalArgumentException("Pet Type cannot be null");
		}
		if (ownerId == null) {
			throw new IllegalArgumentException("Owner ID cannot be null");
		}

		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.petType = petType;
		if (visits == null) {
			this.visits = new HashSet<>();
		}
		else {
			this.visits = visits;
		}
		this.ownerId = ownerId;
	}

	private Pet(final PetId id, final Name name, final BirthDate birthDate, final PetType petType,
			final Set<Visit> visits, final OwnerId ownerId, PetRepository petRepository) {
		this(id, name, birthDate, petType, visits, ownerId);

		if (petRepository.findByNameAndOwnerId(name, ownerId)
			.filter(existingPet -> !existingPet.getId().equals(id))
			.isPresent()) {
			throw new IllegalArgumentException("Pet with this name already exists for this owner");
		}
	}

	public static Pet reconstitute(final UUID id, final String name, final LocalDate birthDate, final PetType petType,
			final Set<Visit> visits, final UUID ownerId) {
		return new Pet(new PetId(id), new Name(name), new BirthDate(birthDate), petType, visits, new OwnerId(ownerId));
	}

	public static Pet create(final Name name, final BirthDate birthDate, final PetType petType, final OwnerId ownerId,
			PetRepository petRepository) {
		return new Pet(PetId.create(), name, birthDate, petType, new HashSet<>(), ownerId, petRepository);
	}

	public static Pet update(final PetId id, final Name name, final BirthDate birthDate, final PetType petType,
			final Set<Visit> visits, final OwnerId ownerId, PetRepository petRepository) {
		if (!petRepository.existsByIdAndOwnerId(id, ownerId)) {
			throw new IllegalArgumentException("Pet with this ID does not exist for this owner");
		}
		return new Pet(id, name, birthDate, petType, visits, ownerId, petRepository);
	}

	public PetId getId() {
		return id;
	}

	public String getName() {
		return name.name();
	}

	public void changeName(final String name) {
		this.name = new Name(name);
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public PetType getPetType() {
		return petType;
	}

	public Set<Visit> getVisits() {
		return visits;
	}

	public void addVisit(final LocalDate date, final String description) {
		this.visits.add(Visit.create(this.id, date, description));
	}

	public OwnerId getOwnerId() {
		return ownerId;
	}

}
