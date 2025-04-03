package org.springframework.samples.petclinic.vet.domain;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.util.Assert;

/**
 * Models a {@link Vet Vet's} specialty (for example, dentistry).
 * This is considered an Entity within the Vet Aggregate.
 */
@Entity
public class Specialty {

	@Identity
	private final SpecialtyId id;
	private final String name;

    /**
     * Creates a new Specialty.
     * @param id The unique identifier. Must not be null.
     * @param name The name of the specialty. Must not be blank.
     */
    public Specialty(SpecialtyId id, String name) {
        Assert.notNull(id, "SpecialtyId must not be null");
        Assert.hasText(name, "Name must not be blank");
        this.id = id;
        this.name = name;
    }

    public SpecialtyId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        return id.equals(specialty.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Specialty{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
