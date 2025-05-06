package org.springframework.samples.petclinic.ddd.pet.domain;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Factory;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDate;

@Entity
public final class Visit {

	@Identity
	private final VisitId id;

	private final LocalDate date;

	private final String description;

	private Visit(VisitId id, LocalDate date, String description) {
		if (id == null) {
			throw new IllegalArgumentException("VisitId cannot be null");
		}
		if (date == null) {
			throw new IllegalArgumentException("Visit date is required");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("Visit description is required and cannot be blank");
		}
		this.id = id;
		this.date = date;
		this.description = description;
	}

	private Visit(LocalDate date, String description) {
		this(VisitId.create(), date == null ? LocalDate.now() : date, description);
	}

	private Visit(String description) {
		this(VisitId.create(), LocalDate.now(), description);
	}

	public VisitId getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	@Factory
	public static final class Builder {

		static Visit create(LocalDate date, String description) {
			return new Visit(date, description);
		}

		public static Visit reconstitute(VisitId id, LocalDate date, String description) {
			return new Visit(id, date, description);
		}

	}

}
