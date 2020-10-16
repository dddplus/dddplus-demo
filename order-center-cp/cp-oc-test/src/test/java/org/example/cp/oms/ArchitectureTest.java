package org.example.cp.oms;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import io.github.dddplus.ArchitectureEnforcer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class ArchitectureTest {

    private JavaClasses classes;

    @Before
    public void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("org.example.cp.oms", "org.example.bp");
    }

    @Test
    public void requiredRules() {
        for (ArchRule rule : ArchitectureEnforcer.requiredRules) {
            rule.check(classes);
        }
    }

    @Test
    public void optionalInterfaceNameStartsWithI() {
        try {
            ArchitectureEnforcer.optionalInterfaceNameStartsWithI().check(classes);
        } catch (AssertionError ignored) {
            log.warn("{}", ignored.getMessage());
        }
    }

    @Test
    public void optionalDddLayerRule() {
        try {
            ArchitectureEnforcer.optionalDddLayerRule().check(classes);
        } catch (AssertionError ignored) {
            log.warn("{}", ignored.getMessage());
        }
    }
}
