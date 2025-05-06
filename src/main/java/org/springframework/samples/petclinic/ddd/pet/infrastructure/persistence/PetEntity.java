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
package org.springframework.samples.petclinic.ddd.pet.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.ddd.pet.domain.*;

import java.util.LinkedHashSet;
import java.util.Set;

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
class PetEntity {

	@EmbeddedId
	@AttributeOverride(name = "uuid", column = @Column(name = "id"))
	private PetId id;

	@Embedded
	private Name name;

	@Embedded
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@AttributeOverride(name = "localDate", column = @Column(name = "birth_date"))
	private BirthDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetTypeEntity type;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pet_id")
	@OrderBy("date ASC")
	private final Set<VisitEntity> visits = new LinkedHashSet<>();

	@Embedded
	@AttributeOverride(name = "uuid", column = @Column(name = "owner_id"))
	private OwnerId ownerId;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "firstName", column = @Column(name = "owner_first_name")),
			@AttributeOverride(name = "lastName", column = @Column(name = "owner_last_name")) })
	private OwnerName ownerName;

	public PetId getId() {
		return id;
	}

	public void setId(PetId id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(BirthDate birthDate) {
		this.birthDate = birthDate;
	}

	public PetTypeEntity getType() {
		return type;
	}

	public void setType(PetTypeEntity type) {
		this.type = type;
	}

	public Set<VisitEntity> getVisits() {
		return visits;
	}

	public OwnerId getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(OwnerId ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerName getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(OwnerName ownerName) {
		this.ownerName = ownerName;
	}

}
