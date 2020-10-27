package org.example.oms.d.stock.spec;

import io.github.dddplus.annotation.Domain;

@Domain(code = StockDomain.CODE, name = "库存支撑域")
public class StockDomain {
    public static final String CODE = "stock";
}
