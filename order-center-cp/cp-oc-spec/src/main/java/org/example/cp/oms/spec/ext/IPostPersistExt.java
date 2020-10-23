package org.example.cp.oms.spec.ext;

import io.github.dddplus.ext.IDomainExtension;
import org.example.cp.oms.spec.model.IOrderModel;

import javax.validation.constraints.NotNull;

/**
 * 落库后的处理扩展点.
 */
public interface IPostPersistExt extends IDomainExtension {

    void afterPersist(@NotNull IOrderModel model);
}
