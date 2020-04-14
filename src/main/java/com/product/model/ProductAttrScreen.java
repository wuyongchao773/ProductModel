package com.product.model;

public class ProductAttrScreen {
    private Integer productId;

    private String productAttrId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductAttrId() {
        return productAttrId;
    }

    public void setProductAttrId(String productAttrId) {
        this.productAttrId = productAttrId == null ? null : productAttrId.trim();
    }
}