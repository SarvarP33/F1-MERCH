package com.f1merch.model;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Category category;
    private final Team team;
    private final String icon;
    private final List<String> sizes;
    private final boolean featured;

    public Product(Long id, String name, String description, BigDecimal price,
                   Category category, Team team, String icon, List<String> sizes, boolean featured) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.team = team;
        this.icon = icon;
        this.sizes = sizes;
        this.featured = featured;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Category getCategory() { return category; }
    public Team getTeam() { return team; }
    public String getIcon() { return icon; }
    public List<String> getSizes() { return sizes; }
    public boolean isFeatured() { return featured; }
    public boolean hasSizes() { return sizes != null && !sizes.isEmpty(); }
}
