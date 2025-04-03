# DDD Refactoring Plan for Spring PetClinic using jMolecules

## Introduction

This plan outlines the steps to refactor the Spring PetClinic application (currently using UUIDs) to align with Domain-Driven Design (DDD) principles using the jMolecules library. The refactoring will be done incrementally, focusing on one aggregate/bounded context at a time.

## Goals

*   Apply DDD concepts (Aggregates, Entities, Value Objects, Repositories).
*   Utilize jMolecules annotations and features (`@AggregateRoot`, `@Entity`, `@ValueObject`, `@Repository`, `@Association`, `@Identity`).
*   Establish clear Bounded Contexts (e.g., `owner`, `vet`).
*   Separate domain logic from infrastructure concerns (persistence, API).
*   Ensure tests are updated and passing after each step.

## Prerequisites

*   Familiarity with DDD concepts.
*   Understanding of the jMolecules library and its modules.
*   Project already migrated to use UUIDs as primary keys.
*   Gradle build tool configured for the project.

## Dependencies

Add the following dependencies to your `build.gradle` file. Verify and use the latest compatible versions from Maven Central or the jMolecules project.

```gradle
// build.gradle

// Add the jMolecules BOM for consistent versions
dependencyManagement {
    imports {
        mavenBom "org.jmolecules:jmolecules-bom:1.10.0" // Use version 1.10.0
    }
}

dependencies {
    // jMolecules Core Concepts (includes DDD annotations)
    implementation 'org.jmolecules:jmolecules-ddd' // Version managed by BOM

    // Individual jMolecules modules (if needed, instead of jmolecules-spring)
    // implementation 'org.jmolecules:jmolecules-concepts' // Core concepts if not pulled by ddd
    // implementation 'org.jmolecules:jmolecules-events' // For domain events if used later
    // implementation 'org.jmolecules.integrations:jmolecules-jpa' // For JPA integration if needed directly
    // implementation 'org.jmolecules.integrations:jmolecules-spring-data-commons' // For Spring Data Commons integration
    // implementation 'org.jmolecules.integrations:jmolecules-spring-data-jpa' // For Spring Data JPA specific integration

    // NOTE: Start with just jmolecules-ddd and add others only if compilation fails for specific annotations/classes.
    // The core annotations (@AggregateRoot, @Entity, @ValueObject, @Repository, @Association, @Identity)
    // are typically within jmolecules-ddd.

    // ArchUnit for enforcing architectural rules (Optional but highly recommended)
    testImplementation 'com.tngtech.archunit:archunit-junit5:1.3.0' // Check for latest version

    // ... other existing dependencies ...
}
```

Refresh your Gradle dependencies after adding these.

## Refactoring Steps

We will refactor context by context, starting with the simpler `vet` context.

---

### Step 1: Refactor Vet Aggregate (Vet Bounded Context)

**Goal:** Isolate the Vet and Specialty concepts into a dedicated `vet` bounded context with clear domain, application, and infrastructure layers.

1.  **Create Package Structure:**
    Create the following package structure under `org.springframework.samples.petclinic.vet`:
    *   `domain`: For domain objects (Vet, Specialty, Vets repository interface).
    *   `application`: For application services/use cases and DTOs.
        *   `application.dto`: For Data Transfer Objects (VetDto, SpecialtyDto, etc.).
    *   `infrastructure`: For implementations details.
        *   `infrastructure.persistence`: For JPA entities and repository implementations.
        *   `infrastructure.api`: For the REST controller (`VetController`), uses DTOs from application layer.

2.  **Refactor Domain (`vet.domain`):**
    *   Move `Vet.java` and `Specialty.java` from `org.springframework.samples.petclinic.vet` to `org.springframework.samples.petclinic.vet.domain`.
    *   **`SpecialtyId.java` (New Record):**
        *   Create a dedicated ID **record** `SpecialtyId(UUID value)` in `vet.domain`.
        *   This record wraps the `UUID`.
    *   **`Specialty.java`:**
        *   Remove `@Entity`, `@Table` annotations.
        *   Change the `id` field type from `UUID` to `SpecialtyId`. Annotate the `id` **field** with `@org.jmolecules.ddd.annotation.Identity`.
        *   Keep the `String name` field. Consider making the class immutable.
        *   Annotate the class with `@org.jmolecules.ddd.annotation.Entity`.
    *   **`VetId.java` (New Record):**
        *   Create a dedicated ID **record** `VetId(UUID value)` in `vet.domain`.
        *   This record wraps the `UUID`.
    *   **`Vet.java`:**
        *   Remove `@Entity`, `@Table`, `@ManyToMany`, `@JoinTable`, `@OrderBy`, `@XmlElement`, `@XmlRootElement` annotations.
        *   Change the `id` field type from `UUID` to `VetId`. Annotate the `id` **field** with `@org.jmolecules.ddd.annotation.Identity`.
        *   Update the `specialties` field: Change `Set<Specialty>` to `Set<SpecialtyId>`. Annotate this association with `@Association`.
        *   Annotate the class with `@org.jmolecules.ddd.annotation.AggregateRoot`.
        *   Ensure methods like `addSpecialty`, `getNrOfSpecialties` operate on the domain representation (using `SpecialtyId`).
    *   **`Vets.java` (New Interface):**
        *   Create a new interface `Vets` in `vet.domain`.
        *   Annotate with `@org.jmolecules.ddd.annotation.Repository`.
        *   Define necessary methods using domain types, e.g., `Optional<Vet> findById(VetId vetId);`, `Collection<Vet> findAll();`, `Page<Vet> findAll(Pageable pageable);`.

3.  **Refactor Infrastructure (`vet.infrastructure`):**
    *   **Persistence (`.persistence`):**
        *   Create `VetEntity.java` and `SpecialtyEntity.java`. These classes will contain the JPA annotations (`@Entity`, `@Table`, `@Id`, `@ManyToMany`, etc.) removed from the domain objects. Map the `UUID` ID correctly for your database (`@Column(columnDefinition = "UUID")` or `@JdbcTypeCode(SqlTypes.VARCHAR)` depending on JPA provider/DB).
        *   Create `VetJpaRepository` interface extending `org.springframework.data.jpa.repository.JpaRepository<VetEntity, UUID>`.
        *   Create `VetRepositoryImpl.java` implementing the `vet.domain.Vets` interface.
            *   Inject `VetJpaRepository`.
            *   Implement the methods defined in `Vets`. This involves:
                *   Calling the `VetJpaRepository` methods.
                *   **Mapping** between the domain `Vet`/`Specialty` objects and the JPA `VetEntity`/`SpecialtyEntity` objects. This is a crucial step. You might use manual mapping or a library like MapStruct.
            *   Annotate this implementation class with `@org.springframework.stereotype.Repository` (Spring's annotation) and `@org.jmolecules.ddd.annotation.Repository` (jMolecules).
    *   **API (`.api`):**
        *   Move `VetController.java` from `org.springframework.samples.petclinic.vet` to `org.springframework.samples.petclinic.vet.infrastructure.api`.
        *   Update `VetController` to inject and use the *Application Service* (created next) instead of the repository.
        *   Modify controller methods to accept/return DTOs (defined in `vet.application.dto`) instead of domain objects.

4.  **Create Application Layer (`vet.application`):**
    *   **DTOs (`.dto`):**
        *   Create `SpecialtyDto.java`, `VetDto.java`, and `VetListDto.java` (replacing the old XML wrapper) in the `vet.application.dto` package. These are simple records or classes holding data for API transfer.
    *   **Service:**
        *   Create `VetService.java` (or similar name). Annotate with `@org.springframework.stereotype.Service`.
        *   Inject the `vet.domain.Vets` and `vet.domain.Specialties` repository interfaces.
        *   Implement methods required by the `VetController`, e.g., `findAllVets()`, `findPaginatedVets(Pageable pageable)`.
        *   These methods will call the domain repositories, get domain objects, map them to DTOs (defined in `.dto`), and return the DTOs.

5.  **Update Tests:**
    *   **`VetControllerTests.java`:**
        *   Update package import for `VetController` (to `infrastructure.api`) and DTOs (to `application.dto`).
        *   Mock the new `VetService` instead of `VetRepository`.
        *   Update `given/when` clauses for the service methods (which now return DTOs from `application.dto`).
        *   Update assertions (`jsonPath`, `model().attribute`) to expect DTO structures (from `application.dto`) and UUID strings.
    *   **`VetTests.java`:**
        *   Update package import for `Vet` and `Specialty`.
        *   Ensure tests focus solely on the domain logic of the `Vet` and `Specialty` objects (now free of persistence/framework annotations). Use the dedicated `VetId` and `SpecialtyId` classes.
    *   **`ClinicServiceTests.java`:**
        *   Remove tests related to finding Vets (e.g., `shouldFindVets`). This logic is now tested via `VetService` unit tests or `VetRepositoryImpl` integration tests. Keep tests related to other contexts for now. Update any remaining usage of `EntityUtils` for Vets/Specialties to use the new ID types or remove if obsolete.
    *   **(New) `VetRepositoryImplTests.java`:**
        *   Create an integration test for `VetRepositoryImpl` using `@DataJpaTest`.
        *   Test the repository methods, focusing on correct persistence and mapping between domain objects and JPA entities. Use an appropriate test database profile (H2, Testcontainers).

6.  **Run Tests:** Execute `./gradlew test` and ensure all tests pass before proceeding.

---

### Step 2: Refactor Owner Aggregate (Owner Bounded Context)

**Goal:** Isolate Owner, Pet, Visit, and PetType concepts into an `owner` bounded context.

*(Follow the same detailed substeps as Step 1, adapting for the Owner aggregate)*

1.  **Create Package Structure:** `owner.domain`, `owner.application` (including `owner.application.dto`), `owner.infrastructure`, `owner.infrastructure.persistence`, `owner.infrastructure.api`.
2.  **Refactor Domain (`owner.domain`):**
    *   Move `Owner.java`, `Pet.java`, `Visit.java`, `PetType.java` to `owner.domain`.
    *   Remove JPA/Spring annotations from all moved classes.
    *   **Create Dedicated ID Records:**
        *   Create `OwnerId.java` as a **record** `OwnerId(UUID value)`.
        *   Create `PetId.java` as a **record** `PetId(UUID value)`.
        *   Create `VisitId.java` as a **record** `VisitId(UUID value)`.
        *   Create `PetTypeId.java` as a **record** `PetTypeId(UUID value)`.
    *   **Update Domain Classes:**
        *   `Owner`: Annotate `@AggregateRoot`. Change `id` type to `OwnerId`. Annotate the `id` **field** with `@Identity`. Change `pets` type to `Set<Pet>`.
        *   `Pet`: Annotate `@Entity`. Change `id` type to `PetId`. Annotate the `id` **field** with `@Identity`. Change `type` field type to `PetTypeId` and annotate with `@Association`. Change `visits` type to `Set<Visit>`.
        *   `Visit`: Annotate `@Entity`. Change `id` type to `VisitId`. Annotate the `id` **field** with `@Identity`. Remove any direct reference to `Pet` if it exists (relationship managed by `Pet`).
        *   `PetType`: Annotate `@AggregateRoot`. Change `id` type to `PetTypeId`. Annotate the `id` **field** with `@Identity`.
    *   **Define Repository Interfaces:**
        *   `Owners`: Annotate `@Repository`. Define methods using `Owner`, `OwnerId`.
        *   `PetTypes`: Annotate `@Repository`. Define methods using `PetType`, `PetTypeId`.
3.  **Refactor Infrastructure (`owner.infrastructure`):**
    *   **Persistence:** Create `OwnerEntity`, `PetEntity`, `VisitEntity`, `PetTypeEntity` with JPA annotations. Create `OwnerJpaRepository` (implementing `Owners`) and `PetTypeJpaRepository` (implementing `PetTypes`) with mapping logic. Annotate implementations with `@Repository`.
    *   **API:** Move `OwnerController`, `PetController`, `VisitController` to `owner.infrastructure.api`. Update them to use Application Services and DTOs (from `owner.application.dto`). Move `PetTypeFormatter` here or handle formatting via DTOs/services.
4.  **Create Application Layer (`owner.application`):**
    *   **DTOs (`.dto`):** Create necessary DTOs (e.g., `OwnerDto`, `PetDto`, `VisitDto`, `PetTypeDto`) in `owner.application.dto`.
    *   **Service:** Create `OwnerService`, `PetService` (or combined `OwnerManagementService`). Inject `Owners`, `PetTypes` repositories. Implement use cases needed by controllers, mapping between domain objects and DTOs.
5.  **Update Tests:**
    *   Update `OwnerControllerTests`, `PetControllerTests`, `VisitControllerTests` to mock Application Services and use DTOs from `owner.application.dto`. Update assertions accordingly.
    *   Update `ClinicServiceTests`: Remove remaining Owner/Pet/Visit tests. Update any remaining usage of `EntityUtils` to use the new ID types or remove if obsolete.
    *   Create `OwnerRepositoryImplTests`, `PetTypeRepositoryImplTests` (`@DataJpaTest`), testing mapping and persistence with the new ID types.
    *   Update `PetValidatorTests`, `PetTypeFormatterTests` to work with the new domain structure and ID types.

6.  **Run Tests:** Execute `./gradlew test`.

---

### Step 3: Refactor Shared Model & Cleanup

**Goal:** Address remaining shared classes and perform final cleanup.

1.  **Analyze Shared Classes:** Review `BaseEntity`, `NamedEntity`, `Person` (in `org.springframework.samples.petclinic.model`).
2.  **Refactor/Remove `BaseEntity`:** The concept of a base entity with an ID is now handled by individual domain aggregates having their own dedicated ID **records** (e.g., `OwnerId`, `VetId`) and the `@Identity` annotation on the corresponding field within the aggregate/entity. The infrastructure mapping layer handles persistence IDs. `BaseEntity` can likely be removed. Update `Person` and any other direct subclasses accordingly.
3.  **Refactor `Person`/`NamedEntity`:**
    *   Decide on a strategy:
        *   **Option A (Duplication):** Copy relevant fields/logic (like `firstName`, `lastName`, `name`) into `owner.domain.Owner`, `vet.domain.Vet`, `owner.domain.PetType`, `vet.domain.Specialty`. Remove the original `Person`/`NamedEntity`. This is often simpler initially.
        *   **Option B (Shared Kernel):** Create a new `kernel` or `shared` module/package for truly shared domain concepts like `Person`. Ensure this shared module has no infrastructure dependencies.
        *   **Option C (Composition):** Embed `Person` or its attributes as a Value Object within `Owner` and `Vet`.
    *   Refactor `Owner` and `Vet` domain classes based on the chosen strategy. Remove dependencies on the old `model` package if duplicating/moving.
4.  **Remove `model` Package:** If all classes within `org.springframework.samples.petclinic.model` have been moved or replaced, delete the package.
5.  **Remove `service` Package:** The logic previously in `ClinicServiceTests` should now reside within specific application services (`OwnerService`, `VetService`) or repository tests. Delete the `service` package and `ClinicServiceTests.java`.
6.  **Remove `EntityUtils.java`:** This utility was used for finding entities by integer ID in collections. This functionality is replaced by stream operations using the dedicated UUID-based ID classes (e.g., `findById(OwnerId)`) within tests or potentially by specific repository query methods if needed. Delete `EntityUtils.java`.
7.  **Review Configuration:** Check `PetClinicApplication.java`, `CacheConfiguration.java`, etc., for any remaining dependencies on old package structures or classes (especially the `model` package). Update imports as needed.
8.  **(Optional) Add ArchUnit Tests:** Create tests using ArchUnit to enforce dependency rules between layers (domain, application, infrastructure) and bounded contexts.

9.  **Run Tests:** Execute `./gradlew test` one final time.

---

This plan provides a high-level roadmap. Each step requires careful implementation and thorough testing. Remember to commit changes frequently after each successful sub-step.
