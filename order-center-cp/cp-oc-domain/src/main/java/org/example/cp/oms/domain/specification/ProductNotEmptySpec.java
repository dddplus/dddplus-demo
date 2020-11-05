package org.example.cp.oms.domain.specification;

import io.github.dddplus.annotation.Specification;
import io.github.dddplus.specification.ISpecification;
import io.github.dddplus.specification.Notification;
import org.example.cp.oms.spec.model.IOrderMain;

// 之前的implicit business rules，现在变成了explicit rules
@Specification("产品项不能空")
public class ProductNotEmptySpec implements ISpecification<IOrderMain> {

    @Override
    public boolean satisfiedBy(IOrderMain candidate, Notification notification) {
        if (candidate.productDelegate() == null || candidate.productDelegate().getProducts() == null || candidate.productDelegate().getProducts().isEmpty()) {
            return false;
        }

        return true;
    }
}
