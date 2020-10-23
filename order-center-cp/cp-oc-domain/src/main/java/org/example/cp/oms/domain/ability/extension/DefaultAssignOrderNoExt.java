package org.example.cp.oms.domain.ability.extension;

import io.github.dddplus.annotation.Extension;
import org.example.cp.oms.spec.ext.IAssignOrderNoExt;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.validation.constraints.NotNull;

@Extension(code = IAssignOrderNoExt.DefaultCode, value = "defaultAssignOrderNoExt")
public class DefaultAssignOrderNoExt implements IAssignOrderNoExt {

    @Override
    public void assignOrderNo(@NotNull IOrderMain model) {

    }
}
