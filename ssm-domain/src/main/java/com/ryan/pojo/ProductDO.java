package com.ryan.pojo;

import java.io.Serializable;

/**
 * 产品
 * @author YoriChen
 * @date 2018/6/25
 */
public class ProductDO implements Serializable {

    /**产品Id */
    private Long productId;

    /**产品编号 */
    private Long productNumber;

    /**产品名称 */
    private String productName;

    /**产品类别 */
    private String productClass;

    /**产品状态 */
    private Boolean productStatus;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Long productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "ProductVo{" +
                "productId=" + productId +
                ", productNumber=" + productNumber +
                ", productName='" + productName + '\'' +
                ", productClass='" + productClass + '\'' +
                ", productStatus=" + productStatus +
                '}';
    }
}
