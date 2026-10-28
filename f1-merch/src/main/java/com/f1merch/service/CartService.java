package com.f1merch.service;

import com.f1merch.model.CartItem;
import com.f1merch.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Корзина хранится в HTTP-сессии: у каждого посетителя своя. */
@Service
@SessionScope
public class CartService implements Serializable {

    private static final BigDecimal FREE_SHIPPING_FROM = new BigDecimal("50000");
    private static final BigDecimal SHIPPING_PRICE = new BigDecimal("2500");

    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(Product product, String size, int quantity) {
        CartItem candidate = new CartItem(product, size, Math.max(1, quantity));
        CartItem existing = items.get(candidate.getKey());
        if (existing != null) {
            existing.setQuantity(Math.min(99, existing.getQuantity() + candidate.getQuantity()));
        } else {
            items.put(candidate.getKey(), candidate);
        }
    }

    public void updateQuantity(String key, int quantity) {
        CartItem item = items.get(key);
        if (item == null) return;
        if (quantity <= 0) items.remove(key);
        else item.setQuantity(Math.min(99, quantity));
    }

    public void remove(String key) { items.remove(key); }

    public void clear() { items.clear(); }

    public List<CartItem> getItems() { return new ArrayList<>(items.values()); }

    public boolean isEmpty() { return items.isEmpty(); }

    public int getCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal getSubtotal() {
        return items.values().stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getShipping() {
        if (isEmpty() || getSubtotal().compareTo(FREE_SHIPPING_FROM) >= 0) return BigDecimal.ZERO;
        return SHIPPING_PRICE;
    }

    public BigDecimal getTotal() { return getSubtotal().add(getShipping()); }

    public BigDecimal getFreeShippingFrom() { return FREE_SHIPPING_FROM; }
}
