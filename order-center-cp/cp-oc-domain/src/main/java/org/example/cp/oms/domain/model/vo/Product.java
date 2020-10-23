package org.example.cp.oms.domain.model.vo;

import org.example.cp.oms.spec.model.vo.IProduct;

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
