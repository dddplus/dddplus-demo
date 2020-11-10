package org.example.cp.oms.domain.model.vo;

import lombok.Data;
import org.example.cp.oms.spec.model.vo.IProduct;

/**
 * 订单里包含的增值服务产品.
 */
@Data
public class Product implements IProduct {
    private String code;

    void setCode(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
