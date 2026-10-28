package com.f1merch.model;

/** Команды с фирменными цветами — используются для оформления карточек товаров. */
public enum Team {
    FERRARI("Ferrari", "#dc0000", "#ff6b4a"),
    RED_BULL("Red Bull Racing", "#1e2b5c", "#3671c6"),
    MERCEDES("Mercedes", "#00a19c", "#27f4d2"),
    MCLAREN("McLaren", "#ff8000", "#ffb366"),
    ASTON_MARTIN("Aston Martin", "#006f62", "#229971"),
    WILLIAMS("Williams", "#00285e", "#64c4ff"),
    F1("Formula 1", "#15151e", "#e10600");

    private final String title;
    private final String colorDark;
    private final String colorLight;

    Team(String title, String colorDark, String colorLight) {
        this.title = title;
        this.colorDark = colorDark;
        this.colorLight = colorLight;
    }

    public String getTitle() { return title; }
    public String getColorDark() { return colorDark; }
    public String getColorLight() { return colorLight; }

    public String getGradient() {
        return "linear-gradient(135deg, " + colorDark + " 0%, " + colorLight + " 100%)";
    }
}
