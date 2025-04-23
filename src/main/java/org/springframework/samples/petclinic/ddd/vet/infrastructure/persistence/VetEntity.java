package org.springframework.samples.petclinic.ddd.vet.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.samples.petclinic.ddd.vet.domain.PersonName;
import org.springframework.samples.petclinic.ddd.vet.domain.VetId;

import java.util.Set;

@Entity
@Table(name = "vets")
class VetEntity {

	@EmbeddedId
	@AttributeOverride(name = "uuid", column = @Column(name = "id"))
	private VetId id;

	@Embedded
	private PersonName name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<SpecialtyEntity> specialties;

	protected VetEntity() {
	}

	public VetId getId() {
		return id;
	}

	public void setId(VetId id) {
		this.id = id;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public Set<SpecialtyEntity> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<SpecialtyEntity> specialties) {
		this.specialties = specialties;
	}

}
