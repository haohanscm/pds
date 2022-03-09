package com.haohan.platform.service.sys.modules.xiaodian.entity.printer;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2019/3/25
 */
public class PrintParams implements Serializable {
    private String[] header;
    private PrintProduct[] products;
    private String[] footer;

    public PrintParams(String[] header, PrintProduct[] products, String[] footer) {
        this.header = header;
        this.products = products;
        this.footer = footer;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public PrintProduct[] getProducts() {
        return products;
    }

    public void setProducts(PrintProduct[] products) {
        this.products = products;
    }

    public String[] getFooter() {
        return footer;
    }

    public void setFooter(String[] footer) {
        this.footer = footer;
    }
}
