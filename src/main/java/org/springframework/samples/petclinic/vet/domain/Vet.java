package org.springframework.samples.petclinic.vet.domain;

import java.io.Serializable; // Add import for Serializable
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Association;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.util.Assert;

/**
 * Represents a veterinarian, acting as the Aggregate Root for the Vet Bounded Context.
 */
@AggregateRoot
public class Vet implements Serializable { // Implement Serializable

	private static final long serialVersionUID = 1L; // Add serialVersionUID

	@Identity
	private final VetId id;
	private final String firstName;
	private final String lastName;

    @Association
    private final Set<SpecialtyId> specialties;

    /**
     * Creates a new Vet.
     * @param id The unique identifier. Must not be null.
     * @param firstName The first name. Must not be blank.
     * @param lastName The last name. Must not be blank.
     * @param specialties The set of specialty IDs. Can be null or empty.
     */
    public Vet(VetId id, String firstName, String lastName, Set<SpecialtyId> specialties) {
        Assert.notNull(id, "VetId must not be null");
        Assert.hasText(firstName, "First name must not be blank");
        Assert.hasText(lastName, "Last name must not be blank");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = (specialties != null) ? new HashSet<>(specialties) : new HashSet<>();
    }

    public VetId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns an unmodifiable view of the specialties.
     * @return An unmodifiable set of SpecialtyIds.
     */
    public Set<SpecialtyId> getSpecialties() {
        return Collections.unmodifiableSet(specialties);
    }

    public int getNrOfSpecialties() {
        return this.specialties.size();
    }

    public void addSpecialty(SpecialtyId specialtyId) {
        Assert.notNull(specialtyId, "SpecialtyId must not be null");
        this.specialties.add(specialtyId);
    }

    public void removeSpecialty(SpecialtyId specialtyId) {
        Assert.notNull(specialtyId, "SpecialtyId must not be null");
        this.specialties.remove(specialtyId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vet vet = (Vet) o;

        return id.equals(vet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Vet{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", specialties=" + specialties +
               '}';
    }
}
