package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.samples.petclinic.ddd.common.domain.Name;
import org.springframework.samples.petclinic.ddd.vet.domain.SpecialtyId;

@Entity
@Table(name = "specialties")
class SpecialtyEntity {

	@EmbeddedId
	@AttributeOverride(name = "uuid", column = @Column(name = "id"))
	private SpecialtyId id;

	@Embedded
	private Name name;

	protected SpecialtyEntity() {
	}

	public SpecialtyId getId() {
		return id;
	}

	public void setId(SpecialtyId id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

}
