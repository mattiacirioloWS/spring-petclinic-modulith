package org.springframework.samples.petclinic.structure;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import org.jmolecules.archunit.JMoleculesDddRules;
import org.jmolecules.ddd.annotation.*;
import org.jmolecules.event.annotation.DomainEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.samples.petclinic.ddd.common.application.Command;
import org.springframework.samples.petclinic.ddd.common.application.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.conditions.ArchConditions.or;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.springframework.samples.petclinic.structure.BoundedContextRules.*;
import static org.springframework.samples.petclinic.structure.DddArchUnitRules.*;

public class DDDStructureTests {

	private static final String PROJECT_PACKAGE = "org.springframework.samples.petclinic.ddd";

	static Stream<String> boundedContexts() {
		JavaClasses classes = new ClassFileImporter().importPackages(PROJECT_PACKAGE);

		Set<String> allPackageNames = classes.stream().map(JavaClass::getPackageName).collect(Collectors.toSet());

		return allPackageNames.stream()
			.map(classes::getPackage)
			.filter(Objects::nonNull)
			.filter(pkg -> pkg.isAnnotatedWith(BoundedContext.class))
			.map(JavaPackage::getName)
			.distinct();
	}

	@Test
	void checkAllClassesFollowDDDStructure() {
		JavaClasses classes = new ClassFileImporter().importPackages(PROJECT_PACKAGE);

		JMoleculesDddRules.all().check(classes);
	}

	@ParameterizedTest
	@MethodSource("boundedContexts")
	void dddElementsShouldBeInCorrectLayers(String basePackage) {
		JavaClasses classes = new ClassFileImporter().importPackages(basePackage);

		// Aggregates in Domain Layer
		classes().that()
			.areAnnotatedWith(AggregateRoot.class)
			.or()
			.areAnnotatedWith(Entity.class)
			.or()
			.areAnnotatedWith(Factory.class)
			.or()
			.areAnnotatedWith(ValueObject.class)
			.or()
			.areAnnotatedWith(Repository.class)
			.or()
			.areAnnotatedWith(DomainEvent.class)
			.should(beInDomainLayer(classes))
			.andShould(not(beInApplicationLayer(classes)))
			.andShould(not(beInInfrastructureLayer(classes)))
			.andShould(not(beInInterfaceLayer(classes)))
			.check(classes);

		// Services in Application Layer
		classes().that()
			.areAnnotatedWith(Service.class)
			.or()
			.areAnnotatedWith(Query.class)
			.or()
			.areAnnotatedWith(Command.class)
			.should(beInApplicationLayer(classes))
			.andShould(not(beInDomainLayer(classes)))
			.andShould(not(beInInfrastructureLayer(classes)))
			.andShould(not(beInInterfaceLayer(classes)))
			.check(classes);

	}

	@ParameterizedTest
	@MethodSource("boundedContexts")
	void enforceSpringServiceAndComponentInApplicationLayer(String basePackage) {
		JavaClasses classes = new ClassFileImporter().importPackages(basePackage);

		// Spring stereotypes in the correct layers
		classes().that()
			.areAnnotatedWith(Component.class)
			.or()
			.areAnnotatedWith(org.springframework.stereotype.Service.class)
			.should(or(beInApplicationLayer(classes), (beInInfrastructureLayer(classes))))
			.andShould(not(beInDomainLayer(classes)))
			.andShould(not(beInInterfaceLayer(classes)))
			.check(classes);
	}

	@ParameterizedTest
	@MethodSource("boundedContexts")
	void enforceSpringDataInInfrastructureLayerAndPersistencePackage(String basePackage) {
		JavaClasses classes = new ClassFileImporter().importPackages(basePackage);

		// Spring Data classes in the correct layers
		classes().that()
			.areAnnotatedWith(jakarta.persistence.Entity.class)
			.or()
			.areAnnotatedWith(Embeddable.class)
			.or()
			.areAnnotatedWith(MappedSuperclass.class)
			.or()
			.areAssignableTo(org.springframework.data.repository.Repository.class)
			.or()
			.areAssignableTo(org.springframework.data.repository.CrudRepository.class)
			.or()
			.areAssignableTo(org.springframework.data.repository.PagingAndSortingRepository.class)
			.should(beInInfrastructureLayer(classes))
			.andShould()
			.resideInAPackage("..persistence..")
			.andShould(not(beInDomainLayer(classes)))
			.andShould(not(beInApplicationLayer(classes)))
			.andShould(not(beInInterfaceLayer(classes)))
			.check(classes);
	}

	@ParameterizedTest
	@MethodSource("boundedContexts")
	void enforceSpringControllerInInfrastructureLayerAndApiPackage(String basePackage) {
		JavaClasses classes = new ClassFileImporter().importPackages(basePackage);

		// Spring Controller classes in the correct layers
		classes().that()
			.areAnnotatedWith(Controller.class)
			.or()
			.areAnnotatedWith(RestController.class)
			.should(beInInfrastructureLayer(classes))
			.andShould()
			.resideInAPackage("..api..")
			.andShould(not(beInDomainLayer(classes)))
			.andShould(not(beInApplicationLayer(classes)))
			.andShould(not(beInInterfaceLayer(classes)))
			.check(classes);
	}

	@ParameterizedTest
	@MethodSource("boundedContexts")
	void enforceLayeringDependencies(String basePackage) {
		JavaClasses classes = new ClassFileImporter().importPackages(basePackage);

		// Domain should not depend on other layers
		noClasses().that(areInDomainLayer(classes))
			.should()
			.dependOnClassesThat(areInApplicationLayer(classes))
			.orShould()
			.dependOnClassesThat(areInInfrastructureLayer(classes))
			.orShould()
			.dependOnClassesThat(areInInterfaceLayer(classes))
			.check(classes);

		// Application should not depend on Infrastructure
		noClasses().that(areInApplicationLayer(classes))
			.should()
			.dependOnClassesThat(areInInfrastructureLayer(classes))
			.check(classes);

		// Interface should not depend on Domain
		noClasses().that(areInInterfaceLayer(classes))
			.should()
			.dependOnClassesThat(areInDomainLayer(classes))
			.allowEmptyShould(true)
			.check(classes);
	}

	@Test
	void enforceJMoleculesAndSpringStereotypesAndJpaInsideBoundedContext() {
		JavaClasses classes = new ClassFileImporter().importPackages(PROJECT_PACKAGE);
		classes().that(needBoundedContext())
			.should(resideInBoundedContext(classes))
			.because("JMolecules and JPA classes must be inside a bounded context")
			.check(classes);
	}

	@Test
	void enforceBoundedContextIsolation() {
		JavaClasses classes = new ClassFileImporter().importPackages(PROJECT_PACKAGE);
		classes().that(resideInAnyBoundedContext(classes))
			.should(notDependOnOtherContextUnlessAllowed(classes))
			.because(
					"Bounded contexts must be isolated except via their interfaces, domain events or application services")
			.check(classes);
	}

}
