package org.springframework.samples.petclinic.structure;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.jmolecules.architecture.layered.DomainLayer;
import org.jmolecules.architecture.layered.InfrastructureLayer;
import org.jmolecules.architecture.layered.InterfaceLayer;

import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;

public class DddArchUnitRules {

	public static ArchCondition<JavaClass> beInDomainLayer(JavaClasses classes) {
		return new ArchCondition<>("reside in a package annotated with @DomainLayer") {
			@Override
			public void check(JavaClass javaClass, ConditionEvents events) {
				if (RecursivePackageRules.findPackageOrAncestorAnnotatedWith(DomainLayer.class, classes, javaClass)
					.isEmpty()) {
					events.add(violated(javaClass, String
						.format("Class <%s> is DDD‑annotated but not in a @DomainLayer package", javaClass.getName())));
				}
			}
		};
	}

	public static DescribedPredicate<JavaClass> areInDomainLayer(JavaClasses classes) {
		return new DescribedPredicate<>("reside in a package annotated with @DomainLayer") {
			@Override
			public boolean test(JavaClass javaClass) {
				return RecursivePackageRules.findPackageOrAncestorAnnotatedWith(DomainLayer.class, classes, javaClass)
					.isPresent();
			}
		};
	}

	public static DescribedPredicate<JavaClass> areInApplicationLayer(JavaClasses classes) {
		return new DescribedPredicate<>("reside in a package annotated with @ApplicationLayer") {
			@Override
			public boolean test(JavaClass javaClass) {
				return RecursivePackageRules
					.findPackageOrAncestorAnnotatedWith(ApplicationLayer.class, classes, javaClass)
					.isPresent();
			}
		};
	}

	public static ArchCondition<JavaClass> beInApplicationLayer(JavaClasses classes) {
		return new ArchCondition<>("reside in a package annotated with @ApplicationLayer") {
			@Override
			public void check(JavaClass javaClass, ConditionEvents events) {
				if (RecursivePackageRules.findPackageOrAncestorAnnotatedWith(ApplicationLayer.class, classes, javaClass)
					.isEmpty()) {
					events.add(violated(javaClass,
							String.format("Class <%s> is DDD‑annotated but not in a @ApplicationLayer package",
									javaClass.getName())));
				}
			}
		};
	}

	public static DescribedPredicate<JavaClass> areInInfrastructureLayer(JavaClasses classes) {
		return new DescribedPredicate<>("reside in a package annotated with @InfrastructureLayer") {
			@Override
			public boolean test(JavaClass javaClass) {
				return RecursivePackageRules
					.findPackageOrAncestorAnnotatedWith(InfrastructureLayer.class, classes, javaClass)
					.isPresent();
			}
		};
	}

	public static ArchCondition<JavaClass> beInInfrastructureLayer(JavaClasses classes) {
		return new ArchCondition<>("reside in a package annotated with @InfrastructureLayer") {
			@Override
			public void check(JavaClass javaClass, ConditionEvents events) {
				if (RecursivePackageRules
					.findPackageOrAncestorAnnotatedWith(InfrastructureLayer.class, classes, javaClass)
					.isEmpty()) {
					events.add(violated(javaClass,
							String.format("Class <%s> is DDD‑annotated but not in a @InfrastructureLayer package",
									javaClass.getName())));
				}
			}
		};
	}

	public static DescribedPredicate<JavaClass> areInInterfaceLayer(JavaClasses classes) {
		return new DescribedPredicate<>("reside in a package annotated with @InterfaceLayer") {
			@Override
			public boolean test(JavaClass javaClass) {
				return RecursivePackageRules
					.findPackageOrAncestorAnnotatedWith(InterfaceLayer.class, classes, javaClass)
					.isPresent();
			}
		};
	}

	public static ArchCondition<JavaClass> beInInterfaceLayer(JavaClasses classes) {
		return new ArchCondition<>("reside in a package annotated with @InterfaceLayer") {
			@Override
			public void check(JavaClass javaClass, ConditionEvents events) {
				if (RecursivePackageRules.findPackageOrAncestorAnnotatedWith(InterfaceLayer.class, classes, javaClass)
					.isEmpty()) {
					events.add(violated(javaClass, String.format(
							"Class <%s> is DDD‑annotated but not in a @InterfaceLayer package", javaClass.getName())));
				}
			}
		};
	}

}
