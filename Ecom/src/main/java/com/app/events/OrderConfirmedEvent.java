package com.app.events;

public class OrderConfirmedEvent {
    private Long orderId;
    private Long productId;
    private String sku;
    private Integer quantity;
    private String user;
    private String product;

    public OrderConfirmedEvent() {
    }

    public OrderConfirmedEvent(Long orderId, Long productId, String sku, Integer quantity, String user, String product) {
        this.orderId = orderId;
        this.productId = productId;
        this.sku = sku;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
