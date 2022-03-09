package com.haohan.platform.service.sys.modules.pds.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zgw on 2018/10/22.
 */
public class ComputeSumPrice implements Serializable {

    private BigDecimal num;

    private BigDecimal price;


    public ComputeSumPrice(BigDecimal num, BigDecimal price) {
        this.num = num;
        this.price = price;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static BigDecimal sum(List<ComputeSumPrice> computeParams) {
        BigDecimal val = BigDecimal.ZERO;
        for (ComputeSumPrice sumPrice : computeParams) {
            if (null == sumPrice.getNum() || null == sumPrice.getPrice()) {
                continue;
            }
            val = val.add(sumPrice.getNum().multiply(sumPrice.getPrice()));
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


}
