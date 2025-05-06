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
package org.springframework.samples.petclinic.ddd.owner.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.samples.petclinic.ddd.owner.domain.FullAddress;
import org.springframework.samples.petclinic.ddd.owner.domain.OwnerId;
import org.springframework.samples.petclinic.ddd.owner.domain.PersonName;
import org.springframework.samples.petclinic.ddd.owner.domain.Telephone;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Oliver Drotbohm
 * @author Wick Dynex
 */
@Entity
@Table(name = "owners")
class OwnerEntity {

	@EmbeddedId
	@AttributeOverride(name = "uuid", column = @Column(name = "id"))
	private OwnerId id;

	@Embedded
	private PersonName name;

	@Embedded
	@AttributeOverride(name = "street", column = @Column(name = "address"))
	private FullAddress address;

	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "telephone"))
	private Telephone telephone;

	public OwnerId getId() {
		return id;
	}

	public void setId(OwnerId id) {
		this.id = id;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public FullAddress getAddress() {
		return address;
	}

	public void setAddress(FullAddress address) {
		this.address = address;
	}

	public Telephone getTelephone() {
		return telephone;
	}

	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}

}
