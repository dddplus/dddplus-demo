package org.example.bp.oms.fresh.extension;

import io.github.dddplus.annotation.Extension;
import lombok.extern.slf4j.Slf4j;
import org.example.bp.oms.fresh.FreshPartner;
import org.example.cp.oms.spec.ext.IPostPersistExt;
import org.example.cp.oms.spec.model.IOrderMain;

@Slf4j
@Extension(code = FreshPartner.CODE, value = "freshPostPersistExt")
public class PostPersitExt implements IPostPersistExt {

    @Override
    public void afterPersist(IOrderMain model) {
        log.info("{} 落库了，我，Fresh，要发个MQ通知我的下游！", model);
    }
}
