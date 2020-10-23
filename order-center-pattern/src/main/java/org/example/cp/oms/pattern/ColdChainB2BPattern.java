package org.example.cp.oms.pattern;

import io.github.dddplus.annotation.Pattern;
import io.github.dddplus.ext.IIdentityResolver;
import org.example.cp.oms.spec.Patterns;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.validation.constraints.NotNull;

@Pattern(code = ColdChainB2BPattern.CODE, name = "冷链B2B模式", tags = Patterns.Tags.B2B)
public class ColdChainB2BPattern implements IIdentityResolver<IOrderMain> {
    public static final String CODE = Patterns.ColdChainB2B;

    @Override
    public boolean match(@NotNull IOrderMain model) {
        return model.isB2B() && model.isColdChain();
    }
}
