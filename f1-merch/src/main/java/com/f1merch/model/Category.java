package com.f1merch.model;

public enum Category {
    TSHIRT("Футболки"),
    HOODIE("Худи"),
    CAP("Кепки"),
    JACKET("Куртки"),
    MODEL("Модели болидов"),
    ACCESSORY("Аксессуары");

    private final String title;

    Category(String title) { this.title = title; }

    public String getTitle() { return title; }
}
