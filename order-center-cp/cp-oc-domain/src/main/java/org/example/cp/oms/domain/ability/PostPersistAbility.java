package org.example.cp.oms.domain.ability;

import io.github.dddplus.annotation.DomainAbility;
import io.github.dddplus.runtime.BaseDomainAbility;
import org.example.cp.oms.domain.CoreDomain;
import org.example.cp.oms.spec.ext.IPostPersistExt;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.validation.constraints.NotNull;

@DomainAbility(domain = CoreDomain.CODE, name = "落库后的扩展能力")
public class PostPersistAbility extends BaseDomainAbility<IOrderMain, IPostPersistExt> {

    public void afterPersist(@NotNull IOrderMain model) {
        firstExtension(model).afterPersist(model);
    }

    @Override
    public IPostPersistExt defaultExtension(@NotNull IOrderMain model) {
        return null;
    }
}
