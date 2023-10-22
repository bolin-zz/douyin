package com.douyin.domain;

import java.util.List;

public class ProductUpdateRule {
    private String categoryId;
    private List senses;
    private long standardBrandId;
    private long spuId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List getSenses() {
        return senses;
    }

    public void setSenses(List senses) {
        this.senses = senses;
    }

    public long getStandardBrandId() {
        return standardBrandId;
    }

    public void setStandardBrandId(long standardBrandId) {
        this.standardBrandId = standardBrandId;
    }

    public long getSpuId() {
        return spuId;
    }

    public void setSpuId(long spuId) {
        this.spuId = spuId;
    }
}
