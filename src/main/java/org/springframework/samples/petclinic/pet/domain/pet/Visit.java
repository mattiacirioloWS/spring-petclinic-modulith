package org.springframework.samples.petclinic.pet.domain.pet;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class Visit {

	private final VisitId id;

	private final PetId petId;

	private final LocalDate date;

	private final VisitDescription description;

	private Visit(VisitId id, PetId petId, LocalDate date, VisitDescription description) {
		if (id == null) {
			throw new IllegalArgumentException("Visit ID cannot be null");
		}
		if (petId == null) {
			throw new IllegalArgumentException("Pet ID cannot be null");
		}
		if (date == null) {
			throw new IllegalArgumentException("Visit date cannot be null");
		}
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		this.id = id;
		this.petId = petId;
		this.date = date;
		this.description = description;
	}

	public static Visit reconstitute(UUID id, UUID petId, LocalDate visitDate, String description) {
		return new Visit(new VisitId(id), new PetId(petId), visitDate, new VisitDescription(description));
	}

	static Visit create(PetId petId, LocalDate visitDate, String description) {
		return new Visit(VisitId.create(), petId, visitDate, new VisitDescription(description));
	}

	public VisitId getId() {
		return id;
	}

	public PetId getPetId() {
		return petId;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description.description();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (Visit) obj;
		return Objects.equals(this.id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "VisitAggregate[" + "id=" + id + ", " + "petId=" + petId + ", " + "visitDate=" + date + ", "
				+ "description=" + description + ']';
	}

	private record VisitDescription(String description) {
		public VisitDescription {
			if (description == null || description.trim().isEmpty()) {
				throw new IllegalArgumentException("Visit description cannot be null or empty");
			}
		}
	}

}
