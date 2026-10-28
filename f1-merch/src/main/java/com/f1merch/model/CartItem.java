package com.f1merch.model;

import java.math.BigDecimal;

public class CartItem {
    private final Product product;
    private final String size;
    private int quantity;

    public CartItem(Product product, String size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public String getSize() { return size; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /** Уникальный ключ позиции: товар + размер. */
    public String getKey() {
        return product.getId() + ":" + (size == null ? "" : size);
    }

    public BigDecimal getTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
