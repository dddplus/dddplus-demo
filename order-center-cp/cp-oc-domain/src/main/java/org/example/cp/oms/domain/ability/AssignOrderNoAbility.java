package org.example.cp.oms.domain.ability;

import org.example.cp.oms.domain.CoreDomain;
import org.example.cp.oms.domain.ability.extension.DefaultAssignOrderNoExt;
import org.example.cp.oms.spec.ext.IAssignOrderNoExt;
import org.example.cp.oms.spec.model.IOrderMain;
import io.github.dddplus.annotation.DomainAbility;
import io.github.dddplus.runtime.BaseDomainAbility;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@DomainAbility(domain = CoreDomain.CODE, name = "分配订单号的能力")
public class AssignOrderNoAbility extends BaseDomainAbility<IOrderMain, IAssignOrderNoExt> {

    @Resource
    private DefaultAssignOrderNoExt defaultAssignOrderNoExt;

    public void assignOrderNo(@NotNull IOrderMain model) {
        firstExtension(model).assignOrderNo(model);
    }

    @Override
    public IAssignOrderNoExt defaultExtension(@NotNull IOrderMain model) {
        return defaultAssignOrderNoExt;
    }
}
