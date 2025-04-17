package org.springframework.samples.petclinic.structure;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.jmolecules.archunit.JMoleculesDddRules;
import org.junit.jupiter.api.Test;

public class DDDStructureTests {

	private static final String PACKAGE = "org.springframework.samples.petclinic.ddd";

	@Test
	void checkAllClassesFollowDDDStructure() {
		JavaClasses classes = new ClassFileImporter().importPackages(PACKAGE);

		JMoleculesDddRules.all().check(classes);
	}

}
