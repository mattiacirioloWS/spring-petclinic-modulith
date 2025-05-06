package org.springframework.samples.petclinic.ddd.pet.domain;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AggregateRoot
public final class Pet {

	private final PetId id;

	private final List<Visit> visits;

	private final OwnerId ownerId;

	private final PetRepository petRepository;

	private Name name;

	private BirthDate birthDate;

	private PetType type;

	private OwnerName ownerName;

	private Pet(PetId petId, Name name, BirthDate birthDate, PetType type, List<Visit> visits, OwnerId ownerId,
			OwnerName ownerName, PetRepository petRepository) {

		if (petId == null) {
			throw new IllegalArgumentException("PetId cannot be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name is required");
		}
		if (birthDate == null) {
			throw new IllegalArgumentException("BirthDate is required");
		}
		if (type == null) {
			throw new IllegalArgumentException("Type is required");
		}
		if (visits == null) {
			throw new IllegalArgumentException("Visits cannot be null");
		}
		if (ownerId == null) {
			throw new IllegalArgumentException("OwnerId is required");
		}
		if (ownerName == null) {
			throw new IllegalArgumentException("OwnerName is required");
		}
		if (petRepository == null) {
			throw new IllegalArgumentException("PetRepository is required");
		}
		this.id = petId;
		this.name = name;
		this.birthDate = birthDate;
		this.type = type;
		this.visits = new ArrayList<>(visits);
		this.ownerId = ownerId;
		this.petRepository = petRepository;
		changeName(name);
	}

	private Pet(PetId petId, Name name, BirthDate birthDate, PetTypeId typeId, List<Visit> visits, OwnerId ownerId,
			OwnerName ownerName, PetRepository petRepository) {
		this(petId, name, birthDate,
				petRepository.findTypeById(typeId).orElseThrow(() -> new IllegalArgumentException("Invalid petType")),
				visits, ownerId, ownerName, petRepository);
	}

	public PetId getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public void changeName(Name name) {
		if (name == null) {
			throw new IllegalArgumentException("Name is required");
		}
		if (petRepository.hasOwnerAnotherPetWIthSameName(ownerId, id, name)) {
			throw new IllegalArgumentException("Name already exists");
		}
		this.name = name;
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public void changeBirthDate(BirthDate birthDate) {
		if (birthDate == null) {
			throw new IllegalArgumentException("BirthDate is required");
		}
		this.birthDate = birthDate;
	}

	public PetType getType() {
		return type;
	}

	public void changeType(PetTypeId typeId) {
		if (!type.getId().equals(typeId)) {
			this.type = retrievePetType(typeId);
		}
	}

	private PetType retrievePetType(PetTypeId typeId) {
		if (typeId == null) {
			throw new IllegalArgumentException("PetTypeId is required");
		}
		return petRepository.findTypeById(typeId).orElseThrow(() -> new IllegalArgumentException("Invalid petType"));
	}

	public List<Visit> getVisits() {
		return Collections.unmodifiableList(visits); // to prevent modifications outside
	}

	public Visit addVisit(LocalDate date, String description) {
		Visit visit = Visit.Builder.create(date, description);
		visits.add(visit);
		return visit;
	}

	public OwnerId getOwnerId() {
		return ownerId;
	}

	public OwnerName getOwnerName() {
		return ownerName;
	}

	public void changeOwnerName(OwnerName ownerName) {
		if (ownerName == null) {
			throw new IllegalArgumentException("OwnerName is required");
		}
		this.ownerName = ownerName;
	}

	@Factory
	public static final class Builder {

		public static Pet create(Name name, BirthDate birthDate, PetTypeId typeId, OwnerId ownerId, OwnerName ownerName,
				PetRepository petRepository) {
			return new Pet(PetId.create(), name, birthDate, typeId, new ArrayList<>(), ownerId, ownerName,
					petRepository);
		}

		public static Pet reconstitute(PetId petId, Name name, BirthDate birthDate, PetType type, List<Visit> visits,
				OwnerId owner, OwnerName ownerName, PetRepository petRepository) {
			return new Pet(petId, name, birthDate, type, visits, owner, ownerName, petRepository);
		}

	}

}
