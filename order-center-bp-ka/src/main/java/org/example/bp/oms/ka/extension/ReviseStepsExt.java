package org.example.bp.oms.ka.extension;

import io.github.dddplus.annotation.Extension;
import lombok.extern.slf4j.Slf4j;
import org.example.bp.oms.ka.KaPartner;
import org.example.cp.oms.spec.ext.IReviseStepsExt;
import org.example.cp.oms.spec.model.IOrderMain;

import java.util.List;

@Slf4j
@Extension(code = KaPartner.CODE, value = "kaReviseStepsExt")
public class ReviseStepsExt implements IReviseStepsExt {

    @Override
    public List<String> reviseSteps(IOrderMain model) {
        log.info("KA will not revise subsequent steps");
        return null;
    }
}
