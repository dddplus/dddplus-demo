package org.example.cp.oms.domain;

import io.github.dddplus.runtime.IStartupListener;
import io.github.dddplus.runtime.registry.DomainArtifacts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StartupListener implements IStartupListener {

    @Override
    public void onStartComplete() {
        log.info("DDD framework booted!");

        DomainArtifacts artifacts = DomainArtifacts.getInstance();
        log.info("Domains:");
        for (DomainArtifacts.Domain domain : artifacts.getDomains()) {
            log.info("    {}", domain.getName());
        }

        log.info("Specifications:");
        for (DomainArtifacts.Specification specification : artifacts.getSpecifications()) {
            log.info("    {}", specification.getName());
        }

        log.info("Steps:");
        for (Map.Entry<String, List<DomainArtifacts.Step>> entry : artifacts.getSteps().entrySet()) {
            log.info("    activity: {}", entry.getKey());
            for (DomainArtifacts.Step step : entry.getValue()) {
                log.info("        step: {}, tags:{}", step.getCode(), step.getTags());
            }
        }

        log.info("Extensions:");
        for (DomainArtifacts.Extension extension : artifacts.getExtensions()) {
            log.info("    {}: partners:{}, patterns:{}", extension.getExt().getCanonicalName(), extension.getPartners(), extension.getPatterns());
        }

    }
}
