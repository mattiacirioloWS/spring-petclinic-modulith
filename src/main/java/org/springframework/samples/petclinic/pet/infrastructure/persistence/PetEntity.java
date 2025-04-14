/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.common.infrastructure.persistence.NamedEntity;
import org.springframework.samples.petclinic.pet.domain.pet.BirthDate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Wick Dynex
 */
@Entity
@Table(name = "pets")
class PetEntity extends NamedEntity {

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetTypeEntity type;

	@Column(name = "owner_id", nullable = false)
	private UUID ownerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pet_id")
	@OrderBy("date ASC")
	private Set<VisitEntity> visitEntities = new LinkedHashSet<>();

	public PetEntity() {
	}

	public PetEntity(UUID id, String name, BirthDate birthDate, PetTypeEntity petTypeEntity,
			Set<VisitEntity> visitEntities, UUID ownerId) {
		super(id, name);
		this.birthDate = birthDate.localDate();
		this.type = petTypeEntity;
		this.visitEntities = visitEntities;
		this.ownerId = ownerId;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetTypeEntity getType() {
		return this.type;
	}

	public void setType(PetTypeEntity type) {
		this.type = type;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public Collection<VisitEntity> getVisits() {
		return this.visitEntities;
	}

	public void addVisit(VisitEntity visitEntity) {
		getVisits().add(visitEntity);
	}

}
